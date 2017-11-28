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
 * @author: Olesya Bobruyko
 * @version: $Id: RowView.js 8900 2015-05-06 20:57:14Z yplakosh $
 */

define(function(require) {
    var _ = require("underscore"),
        i18n = require("bundle!AttributesBundle"),
        i18n2 = require("bundle!CommonBundle"),
        BaseRow = require("common/component/baseTable/childView/BaseRow"),
        attributesTypesEnum = require("attributes/enum/attributesTypesEnum"),
        rowTemplatesFactory = require("attributes/factory/rowTemplatesFactory");

    var SECURE_VALUE_SUBSTITUTION = "~secure~";

    var RowView = BaseRow.extend({
        className: "table-row",

        template: _.template(rowTemplatesFactory({readOnly: true})),

        templateHelpers: function() {
            return {
                i18n: i18n,
                i18n2: i18n2,
                type: this.type,
                types: attributesTypesEnum,
                encrypted: SECURE_VALUE_SUBSTITUTION
            };
        },

        computeds: {
            encrypt: {
                get: function() {
                    return SECURE_VALUE_SUBSTITUTION;
                },
                set: function(value) {
                    this.setBinding("value", value);
                }
            }
        },

        events: {
            "mouseover": "_onMouseOver",
            "mouseout": "_onMouseOut"
        },

        initialize: function(options) {
            this.type = options.type;

            BaseRow.prototype.initialize.apply(this, arguments);
        },

        _onMouseOver: function(e) {
            this.trigger("mouseover", this.model, e);
        },

        _onMouseOut: function(e) {
            this.trigger("mouseout", this.model, e);
        }
    });

    return RowView;
})
;