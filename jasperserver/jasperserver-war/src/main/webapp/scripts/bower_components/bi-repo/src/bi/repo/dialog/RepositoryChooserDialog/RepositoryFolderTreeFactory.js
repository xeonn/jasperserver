/*
 * Copyright (C) 2005 - 2015 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */


/**
 * @author: Kostiantyn Tsaregradskyi
 * @version: $Id: RepositoryFolderTreeFactory.js 362 2016-02-25 19:27:36Z dgorbenk $
 */

define(function (require) {
	"use strict";

	var Tree = require('common/component/tree/Tree'),
		$ = require("jquery"),
		_ = require("underscore"),
		json3 = require("json3"),
		TreeDataLayer = require('common/component/tree/TreeDataLayer'),
		TooltipTreePlugin = require('common/component/tree/plugin/TooltipPlugin'),
		ContextMenuTreePlugin = require("common/component/tree/plugin/ContextMenuTreePlugin"),
		repositoryResourceTypes = require("bi/repo/enum/repositoryResourceTypes"),

		repositoryFoldersTreeLevelTemplate = require('text!bi/repo/dialog/RepositoryChooserDialog/template/repositoryFoldersTreeLevelTemplate.htm'),

		i18n = require('bundle!CommonBundle'),
		defaultSettings = require("settings/generalSettings"),

		processors = {
			folderTreeProcessor: {
				processItem: function(item) {
					item._node = true;
					item._readonly = !(item.value.permissionMask == 1 || item.value.permissionMask & 4);
					return item;
				}
			},

			filterPublicFolderProcessor : {
				processItem: function(item) {
					if (item.value.uri !== repositoryFoldersTreeFactory.settings.publicFolderUri && item.value.uri !== repositoryFoldersTreeFactory.settings.tempFolderUri){
						return item;
					}
				}
			},

			i18n : {
				processItem: function(item) {
					item.i18n = i18n;
					return item;
				}
			},

			tenantProcessor: {
				processItem: function(item) {
					item._node = true;
					item.value.label = item.value.tenantName;
					item.value.uri = item.value.tenantUri;
					return item;
				}
			}
		};

	var repositoryFoldersTreeFactory = function(options) {

		var tree = Tree.use(TooltipTreePlugin, {
			i18n: i18n,
			contentTemplate: options.tooltipContentTemplate
		}).create().instance({
			additionalCssClasses: "folders",
			dataUriTemplate: options.contextPath + "/rest_v2/resources?{{= id != '@fakeRoot' ? 'folderUri=' + id : ''}}&recursive=false&type=" + repositoryResourceTypes.FOLDER + "&offset={{= offset }}&limit={{= limit }}",
			levelDataId: "uri",
			itemsTemplate: repositoryFoldersTreeLevelTemplate,
			collapsed: true,
			lazyLoad: true,
			rootless:true,
			selection: { allowed: true, multiple: false },
			customDataLayers: {
				//workaround for correct viewing of '/public' and '/' folder labels
				"/": _.extend(new TreeDataLayer({
					dataUriTemplate: options.contextPath + "/flow.html?_flowId=searchFlow&method=getNode&provider=repositoryExplorerTreeFoldersProvider&uri=/&depth=1",
					processors: _.chain(processors).omit("filterPublicFolderProcessor", "tenantProcessor").values().value(),
					getDataArray: function (data) {
						data = json3.parse($(data).text());
						var publicFolder = _.find(data.children, function(item) {
								return item.uri === '/public';
							}),
							res = [{ id: "@fakeRoot", label: data.label, uri: "/", resourceType: 'folder', permissionMask: computePermissionMask(data.extra),_links: {content: "@fakeContentLink"} }];

						if (publicFolder){
							res.push({ id: "/public", label: publicFolder.label, uri: "/public", resourceType: 'folder', permissionMask: computePermissionMask(publicFolder.extra), _links: {content: "@fakeContentLink"} });
						}

						return res;
					}
				}), {
					accept: 'text/html',
					dataType: 'text'
				})
			},
			processors: [processors.i18n, processors.folderTreeProcessor, processors.filterPublicFolderProcessor],
			getDataArray: function(data, status, xhr){
				return data ? data[repositoryResourceTypes.RESOURCE_LOOKUP] : [];
			}
		});

		// Adding some methods which allows us to open and select tree node after tree has been initialized
		// This functionality is expected in the new version of Tree so until that awesome moment we'll keep these two methods
		// to actually do this.

		tree._selectTreeNode = function(pathToSelect, $scrollContainer) {

			if (pathToSelect === "") {
				return;
			}

			var onceRootNodeIsReady = function () {

				var afterFolderTreeIsOpened = _.bind(function () {

					var folderToSelect = pathToSelect;

					if (folderToSelect === "/") {
						folderToSelect = "@fakeRoot";
					}

					this.select(folderToSelect);

					// scroll to item
					if ($scrollContainer && $scrollContainer.length) {
						var $tree = this.$el,
							$selectedItem = this.getLevel(folderToSelect).$el;

						if (!$selectedItem) {
							return;
						}

						var scrollTo = ($selectedItem.offset().top - $tree.offset().top) - $scrollContainer.height() / 2 + $selectedItem.height() / 2;

						$scrollContainer.scrollTop(scrollTo);
					}
				}, this);


				///////////////////////////////////////

				// To highlight preSelectedFolder on tree we need to open all his parent folders.
				// To open all his parent folders we need to build from these parents an array and then open them one by one
				// So, if preSelectedFolder looks like "/path/Samples/Reports", we need
				// to open "/path" and then "/path/Samples"
				// But in some cases (these cases are "/" and "/public" folders) there is no need to open any folders
				// because they are located in the top level in our tree

				if (pathToSelect === "/" || pathToSelect === "/public") {

					afterFolderTreeIsOpened();

				} else {

					var tmp = pathToSelect.replace(/\/$/, "");
					var pathToOpen = tmp.substr(0, tmp.lastIndexOf("/"));

					// if pathToOpen is empty that means we need to open root folder
					pathToOpen = pathToOpen || "/";

					var dfd = new $.Deferred();
					this._openPath(pathToOpen, dfd, 0);
					dfd.done(afterFolderTreeIsOpened);
				}

			};

			this.rootLevel.on("ready", _.bind(onceRootNodeIsReady, this));
		};

		tree._openPath = function(path, dfd, index) {

			if (!path) {
				return dfd.resolve();
			}

			var self = this,
				pathFragmentToOpen,
				splitPath,
				level;

			if (path === "/") {
				splitPath = ["/"];
			} else {
				splitPath = path.split("/");
				splitPath[0] = "/"; // simply because split always adds "" at the beginning
			}

			// Here comes the trick again: if path is like "/public/Samples then it will be split to
			// ["/", "public", "Samples"]
			// and that means that the first node to open would be "/" and then "/public", but this is wrong:
			// in our tree node "/public" located on the top-level, not under "/" like in common file systems.
			// so we have to modify array ["/", "public", "Samples"] into ["/public", "Samples"]
			if (splitPath[0] === "/" && splitPath[1] === "public") {
				splitPath = _.union(["/public"], _.rest(splitPath, 2));
			}

			index = index || 0;
			// did we get to the end ?
			if (index === splitPath.length) {
				return dfd.resolve();
			}

			pathFragmentToOpen = _.first(splitPath, index + 1).join("/");

			if (pathFragmentToOpen === "/") {
				pathFragmentToOpen = "@fakeRoot";
			}

			pathFragmentToOpen = pathFragmentToOpen.replace(/\/\//g, "/");

			level = this.getLevel(pathFragmentToOpen);

			if (level) { // if level exists open it and go ahead
				if (level.collapsed) { // open if collapsed
					level.once("ready", function() {
						self._openPath(path, dfd, index + 1); // open next level
					});

					level.open();
				} else {
					this._openPath(path, dfd, index + 1);
				}
			}
		};

		return tree;
	};


	function computePermissionMask(extra){
		var mask = 2;

		extra.isWritable && (mask = mask | 4);
		extra.isRemovable && (mask = mask | 16);
		extra.isAdministrable && (mask = 1);

		return mask;
	}

	repositoryFoldersTreeFactory.settings = defaultSettings;


	return repositoryFoldersTreeFactory;

});