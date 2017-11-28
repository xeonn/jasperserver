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

define(function (require) {
    "use strict";

    var CustomDataSourceModel = require("dataSource/model/CustomDataSourceModel"),
		connectionTypes = require("dataSource/enum/connectionTypes"),
        jasperServerConfig = require("bundle!jasperserver_config");

    return CustomDataSourceModel.extend({

        initialize: function(attributes, options){
            CustomDataSourceModel.prototype.initialize.apply(this, arguments);
            if(!this.isNew()){
                this.set("password", jasperServerConfig["input.password.substitution"]);
            }
        }
    });

});