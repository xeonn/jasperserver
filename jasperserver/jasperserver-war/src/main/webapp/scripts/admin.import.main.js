/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights reserved.
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
 * @version: $Id: admin.import.main.js 43122 2014-03-18 12:44:22Z psavushchik $
 */

define(function(require){
    "use strict";

    var domReady = require("!domReady"),
        logging = require("administer.logging"),
        Administer = require("administer.base"),
        jrsConfigs = require("jrs.configs"),
        jrsImport = require("import"),
        _ = require("underscore"),
        jaspersoft = require("namespace");

    require("components.statecontrollertrait");
    require("export");
    require("import.formmodel");
    require("import.extendedformview");
    require("import.app");

    domReady(function() {
        Administer.urlContext = jrsConfigs.urlContext;

        _.extend(jaspersoft.i18n, jrsConfigs.Import.i18n);

        logging.initialize();

        jrsImport.App.initialize({
            container: jrsConfigs.Import.initParams.container,
            type : jrsConfigs.Import.initParams.type,
            namespace: jrsImport
        });
    });
});