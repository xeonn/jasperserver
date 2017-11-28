/*
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
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
 * @version: $Id: treeFactory.js 706 2014-12-02 08:49:20Z psavushchik $
 */

define(function (require) {
    "use strict";

    var Tree = require('./Tree'),
        $ = require("jquery"),
        _ = require("underscore"),
        json3 = require("json3"),
        TreeDataLayer = require('./TreeDataLayer'),
        TooltipTreePlugin = require('./plugin/TooltipPlugin'),
        repositoryResourceTypes = require("common/enum/repositoryResourceTypes"),
        repositoryFoldersTreeLevelTemplate = require('text!./template/repositoryFoldersTreeLevelTemplate.htm'),
        i18n = require('bundle!CommonBundle'),
        jrsConfigs = require('jrs.configs'),

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
                    if (item.value.uri !== jrsConfigs.publicFolderUri && item.value.uri !== jrsConfigs.tempFolderUri){
                        return item;
                    }
                }
            },

            i18n : {
                processItem: function(item) {
                    item.i18n = i18n;
                    return item;
                }
            }
        };

    return {
        repositoryFoldersTree: function(options) {
            return Tree.use(TooltipTreePlugin).create().instance({
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
                        processors: _.chain(processors).omit("filterPublicFolderProcessor").values().value(),
                        getDataArray: function (data) {
                            data = json3.parse($(data).text());
                            var publicFolder = _.find(data.children, function(item) {
                                return item.uri === '/public';
                            });

                            return [
                                { id: "@fakeRoot", label: data.label, uri: "/", resourceType: 'folder', permissionMask: computePermissionMask(data.extra),_links: {content: "@fakeContentLink"} },
                                { id: "/public", label: publicFolder.label, uri: "/public", resourceType: 'folder', permissionMask: computePermissionMask(publicFolder.extra), _links: {content: "@fakeContentLink"} }
                            ];
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
        }
    }


    function computePermissionMask(extra){
        var mask = 2;

        extra.isWritable && (mask = mask | 4);
        extra.isRemovable && (mask = mask | 16);
        extra.isAdministrable && (mask = 1);

        return mask;
    }
});