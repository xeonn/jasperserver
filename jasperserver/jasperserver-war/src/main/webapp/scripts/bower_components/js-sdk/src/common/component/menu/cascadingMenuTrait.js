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
 * @author: Andriy Godovanets, Kostiantyn Tsaregradskyi
 * @version: $Id: cascadingMenuTrait.js 812 2015-01-27 11:01:30Z psavushchik $
 */

define(function (require) {
    "use strict";

    var Menu = require("./Menu"),
        HoverMenu = require("./HoverMenu"),
        _ = require("underscore");

    /**
     * @mixin cascadingMenu
     * @description Object that extends any Menu component with options cascade. Adds support of "children" property for "options" param.
     * @example
     *  var CascadingContextMenu = ContextMenu.extend(cascadingMenuTrait);
     *  var cascadingContextMenu = new CascadingContextMenu([
     {
         label: "Remove Summary", action: "removeSummary"
     },
     {
         label: "Change Function",
         children: [
             { label : "Distinct Count", action: "distinctCount" },
             { label : "Count All", action: "countAll" }
         ]
     }
     ], "#someElement", { toggle: true });
     */
    var cascadingMenuTrait =  {

        /**
         * @description initializes mouseover handler. Calls _showSubMenu.
         * @memberof cascadingMenu
         * @access protected
         */
        _onInitialize: function(options) {
            this.on("mouseover", _.bind(this._showSubMenu, this))
        },

        /**
         * @description show sub menu on option mouse over.
         * @memberof cascadingMenu
         * @access protected
         */
        _showSubMenu: function(optionView) {
            if (optionView.model.has("children")) {
                var subView = this._getSubMenu(optionView.cid, optionView.model.get("children"), optionView);
                subView && subView.show();
            }
        },

        /**
         * @description returns new SubMenu instance.
         * @access protected
         * @memberof cascadingMenu
         * @returns {SubMenu}
         */
        _getSubMenu: _.memoize(function(cid, options, parentOption) {
            return new SubMenu(options, parentOption);
        })
    };


    /**
     *  @memberof cascadingMenu
     *  @access private
     */
    var SubMenu = HoverMenu.extend(_.extend(
        /** @lends SubMenu.prototype */

        {

        /**
         * @constructor SubMenu
         * @extends HoverMenu
         * @access private
         * @description SubMenu component.
         * @param {object} options
         * @param {Backbone.View} parentOption
         * @mixes cascadingMenuTrait
         */

        constructor: function(options, parentOption) {
            this.parentOption = parentOption;

            HoverMenu.call(this, options, parentOption.$el);
        },

        /**
         * @desc show sub menu on parent option hover with some left offset
         */
        show: function() {
            var offset = this.$attachTo.offset(),
                width = this.$attachTo.width();

            this.$el.css({
                top: offset.top,
                left: offset.left + width
            });

            return Menu.prototype.show.apply(this, arguments);
        },


        /**
         * @desc on menu mouse out handler.
         * @param {Backbone.View} optionView
         * @param {SubMenu} menu
         * @param {event} ev
         * @access protected
         */
        _onMenuMouseOut: function(optionView, menu, ev) {
            var children = optionView.model.get("children");

            if (children) {
                var subView = this._getSubMenu(optionView.cid),
                    subViewOffset = subView.$el.offset(),
                    posX = ev.pageX - subViewOffset.left,
                    posY = ev.pageY - subViewOffset.top;

                this._menuHovered = posX >= 0 && posX <= subView.$el.width() + 3 &&
                     posY >= 0 && posY <= subView.$el.height() + 3;

            } else {
                this._menuHovered = false;
            }

            this._tryHide();
        }

    }, cascadingMenuTrait));

    return cascadingMenuTrait;
});