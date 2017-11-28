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
 * @version: $Id: treeFactory.js 1178 2015-05-06 20:40:12Z yplakosh $
 */

define(function (require) {
    "use strict";

    var Tree = require('./Tree'),
        $ = require("jquery"),
        _ = require("underscore"),
        json3 = require("json3"),
        TreeDataLayer = require('./TreeDataLayer'),
        TooltipTreePlugin = require('./plugin/TooltipPlugin'),
        ContextMenuTreePlugin = require("./plugin/ContextMenuTreePlugin"),
        SearchTreePlugin = require('./plugin/SearchPlugin'),
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

    return {
        repositoryFoldersTree: function(options) {
            return Tree.use(TooltipTreePlugin, {
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
        },

        tenantFoldersTree: function(options) {
            return Tree
                .use(TooltipTreePlugin)
                .use(SearchTreePlugin)
                .use(ContextMenuTreePlugin, {
                    contextMenu: options.contextMenu
                }).create().instance(_.extend({}, {
                    itemsTemplate: repositoryFoldersTreeLevelTemplate,
                    selection: {allowed: {left: true, right: true}, multiple: false},
                    rootless: true,
                    collapsed: true,
                    lazyLoad: true,
                    dataUriTemplate: jrsConfigs.contextPath + "/rest_v2/organizations?{{= id != 'organizations' ? 'rootTenantId=' + id : ''}}&offset={{= offset }}&limit={{= limit }}&maxDepth=1",
                    levelDataId: "id",
                    getDataArray: function(data, status, xhr) {
                        return data ? data[repositoryResourceTypes.ORGANIZATION] : [];
                    },
                    processors: [processors.tenantProcessor],
                    customDataLayers: {
                        //workaround for correct viewing of '/' tenant label
                        "/": _.extend(new TreeDataLayer({
                            dataUriTemplate: jrsConfigs.contextPath + "/flow.html?_flowId=treeFlow&method=getNode&provider=tenantTreeFoldersProvider&uri=/&prefetch=%2F",
                            processors: [processors.tenantProcessor],
                            getDataArray: function(data) {
                                data = json3.parse($(data).text());

                                return [
                                    { id: data.id, tenantName: data.label, tenantUri: "/", resourceType: "folder", _links: {content: "@fakeContentLink"} }
                                ];
                            }
                        }), {
                            accept: 'text/html',
                            dataType: 'text'
                        })
                    }}))
        }
    };


    function computePermissionMask(extra){
        var mask = 2;

        extra.isWritable && (mask = mask | 4);
        extra.isRemovable && (mask = mask | 16);
        extra.isAdministrable && (mask = 1);

        return mask;
    }
});