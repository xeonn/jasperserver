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
 * @author: Igor Nesterenko
 * @version: $Id: WebfontsComponentModel.js 45580 2014-05-10 14:31:59Z inesterenko $
 */

define(function (require) {
    "use strict";

    var BaseComponentModel = require("./BaseComponentModel"),
        jiveTypes = require("../enum/jiveTypes"),
        _ = require("underscore");

    return BaseComponentModel.extend({

        defaults: function () {
            return  {
                id: null,
                type: jiveTypes.WEBFONTS,
                webfonts: []
            }
        },

        initialize: function(attrs) {

            if (attrs && attrs.webfonts){
                this._handleWebfonts(attrs.webfonts);
            }

            BaseComponentModel.prototype.initialize.apply(this, arguments);
        },

        _handleWebfonts: function(webfonts){

            var modulesToLoad = _.map(webfonts, function(webfont){
                  return "csslink!" + webfont.path;
                });

            if (!_.isEmpty(modulesToLoad)){
                require(modulesToLoad, function() {
                    // TODO: add fix for IE
                });
            }

        }
    });
});