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
 * @version: $Id: css.js 270 2014-10-13 19:58:03Z agodovanets $
 */

define(function (require) {
    "use strict";

    var originalCssPlugin = require("requirejs.plugin.css"),
        _ = require("underscore"),
        jrsConfigs = require("jrs.configs");

    var customizedCssPlugin = _.clone(originalCssPlugin);

    customizedCssPlugin.load = function(cssId, req, load, config) {

	    var defaultThemePath = jrsConfigs.currentThemePath || "themes/default";

        cssId = jrsConfigs.contextPath + "/" + defaultThemePath + "/" + cssId;
        originalCssPlugin.load.call(this, cssId, req, load, config);
    };

    return customizedCssPlugin;
});
