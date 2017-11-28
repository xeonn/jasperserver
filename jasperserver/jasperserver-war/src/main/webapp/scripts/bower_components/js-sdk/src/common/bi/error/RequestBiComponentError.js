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
 * @version: $Id: RequestBiComponentError.js 270 2014-10-13 19:58:03Z agodovanets $
 */

define(function (require) {
    "use strict";

    var BiComponentError = require("./BiComponentError"),
        json3 = require("json3"),
        errorCodes = require("./enum/biComponentErrorCodes"),
        messages = require("./enum/biComponentErrorMessages");

    return BiComponentError.extend({
        constructor: function (xhr, code) {
            this.xmlHttpRequest = xhr;

            var errorCode = code,
                responseJson,
                msg = undefined,
                parameters = undefined;
            if(!code){
                switch (xhr.status) {
                    case 401:
                        errorCode = errorCodes.AUTHENTICATION_ERROR;
                        break;
                    case 403:
                        errorCode = errorCodes.AUTHORIZATION_ERROR;
                        break;
                    default :
                        errorCode = errorCodes.UNEXPECTED_ERROR;
                }
            }
            msg = messages[errorCode];
            try {
                responseJson = json3.parse(xhr.responseText);
            } catch(ex) {}

            if (responseJson) {
                if(responseJson.errorCode){
                    errorCode = responseJson.errorCode;
                    msg = responseJson.message;
                    parameters = responseJson.parameters;
                } else {
                    msg += (" : " + responseJson.message);
                }
            }

            BiComponentError.prototype.constructor.call(this, errorCode, msg, parameters);
        }
    });
});