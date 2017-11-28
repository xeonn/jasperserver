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
 * @version: $Id: commons.main.js 9192 2015-08-12 19:52:08Z yplakosh $
 */

define(function(require){

    "use strict";
    var domReady = require("!domReady");
    require("commons.minimal.main");
    require("namespace");
    require("core.accessibility");
    require("core.events.bis");
    require("core.key.events");
    var stdnav = require("stdnav");

    var actionModel = require("actionModel.modelGenerator");
    var primaryNavigation = require("actionModel.primaryNavigation");
    var globalSearch = require("repository.search.globalSearchBoxInit");
    var layoutModule = require("core.layout");
    var jrsConfigs = require("jrs.configs");

    domReady(function(){
        layoutModule.initialize();
        primaryNavigation.initializeNavigation(); //navigation setup
        actionModel.initializeOneTimeMenuHandlers(); //menu setup

        // JRS-specific stdnav plugins from jrs-ui
        var stdnavPluginActionMenu = require("stdnavPluginActionMenu");
        var stdnavPluginDynamicList = require("stdnavPluginDynamicList");
        stdnavPluginActionMenu.activate(stdnav);
        stdnavPluginDynamicList.activate(stdnav);

        jrsConfigs.initAdditionalUIComponents && globalSearch.initialize();
        //isNotNullORUndefined(window.accessibilityModule) && accessibilityModule.initialize();

        //trigger protorype's dom onload manualy
        document.fire("dom:loaded");
    });

});
