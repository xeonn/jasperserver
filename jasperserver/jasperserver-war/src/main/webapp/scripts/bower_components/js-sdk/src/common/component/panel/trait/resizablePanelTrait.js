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
 * @version: $Id: resizablePanelTrait.js 812 2015-01-27 11:01:30Z psavushchik $
 */

define(function(require){
    "use strict";

    var _ = require("underscore"),
        $ = require("jquery"),
        abstractPanelTrait = require("./abstractPanelTrait");

    /**
     * @mixin resizablePanelTrait
     * @description Mixin that adds methods to Panel.
     * @augments abstractPanelTrait
     */
    return _.extend({}, abstractPanelTrait, {
        /**
         * Sets properties.
         * @memberof resizablePanelTrait
         * @param {object} options
         * @param {string} [options.handles="e, s, se"] - jQuery ui resizable handles.
         * @param {number} [options.minWidth=10] - jQuery ui minWidth.
         * @param {number} [options.maxWidth=null] - jQuery ui maxWidth.
         * @param {selector} options.resizableEl - selector of resizable element.
         */
        onConstructor: function(options) {
            this.handles = options.handles || "e, s, se";
            this.minWidth = options.minWidth || 10;
            this.minHeight = options.minHeight || 10;
            this.maxWidth = options.maxWidth || null;
            this.alsoResize = options.alsoResize || false;
            this.resizableEl = options.resizableEl;
        },

        /**
         * Initializes jQuery UI Resizable for element.
         * @memberof resizablePanelTrait
         * @param el
         */
        afterSetElement: function(el){
            this.$resizableEl = this.$el.find(this.resizableEl).length ? this.$el.find(this.resizableEl) : this.$el;

            this.$resizableEl.resizable({
                handles: this.handles,
                minHeight: this.minHeight,
                minWidth: this.minWidth,
                maxWidth: this.maxWidth,
                alsoResize: this.alsoResize,
                resize: _.bind(this._onResize, this),
                start: _.bind(this._onStart, this),
                stop: _.bind(this._onStop, this)
            });
        },

        /**
         * Destroys jQuery UI Resizable.
         * @memberof resizablePanelTrait
         */
        onRemove: function() {
            try {
                this.$el.resizable("destroy");
            } catch (e) {
                // destroyed already, skip
            }
        },

        /**
         * Additional methods of trait.
         * @memberof resizablePanelTrait
         */
        extension: {
            /**
             * Resize handler.
             * @fires "resize"
             * @param {object} e - jQuery event.
             * @param {object} ui - ui object.
             * @alias extension._onResize
             * @memberof! resizablePanelTrait
             * @private
             */
            _onResize: function(e, ui) {
                this.trigger("resize", e, ui);
            },

            /**
             * Resize start handler.
             * @fires "resizeStart"
             * @param {object} e - jQuery event.
             * @param {object} ui - ui object.
             * @alias extension._onStart
             * @memberof! resizablePanelTrait
             * @private
             */
            _onStart: function(e, ui){
                this.trigger("resizeStart", e, ui);
            },

            /**
             * Resize stop handler.
             * @fires "resizeStop"
             * @param {object} e - jQuery event.
             * @param {object} ui - ui object.
             * @alias extension._onStop
             * @memberof! resizablePanelTrait
             * @private
             */
            _onStop: function(e, ui){
                this.trigger("resizeStop", e, ui);
            }
        }
    });
});