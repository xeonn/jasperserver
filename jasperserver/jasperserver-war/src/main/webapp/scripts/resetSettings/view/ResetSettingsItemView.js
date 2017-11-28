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
 * @version: $Id: ResetSettingsItemView.js 8889 2015-05-05 14:07:05Z obobruyk $
 */

define(function(require) {
    var _ = require("underscore"),
        BaseRow = require("common/component/baseTable/childView/BaseRow"),
        confirmDialogTypesEnum = require("serverSettingsCommon/enum/confirmDialogTypesEnum"),
        itemViewTemplate = require("text!resetSettings/templates/itemViewTemplate.htm");


    var ResetSettingsItemView = BaseRow.extend({
        tagName: "div",
        className: "table-row",

        template: _.template(itemViewTemplate),

        events: {
            "click .delete": "_onDeleteClick",
            "mouseover": "_onMouseOver",
            "mouseout": "_onMouseOut"
        },

        _onMouseOver: function(e) {
            this.trigger("mouseover", this.model, e);
        },

        _onMouseOut: function(e) {
            this.trigger("mouseout", this.model, e);
        },

        _onDeleteClick: function(e) {
            this.trigger("open:confirm", confirmDialogTypesEnum.DELETE_CONFIRM, this, this.model);
        }
    });

    return ResetSettingsItemView;
});