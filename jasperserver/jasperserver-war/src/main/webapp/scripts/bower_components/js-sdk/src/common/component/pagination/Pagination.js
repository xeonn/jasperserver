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
 * Pagination component.
 *
 * @author: Taras Bidyuk
 * @version: $Id: Pagination.js 812 2015-01-27 11:01:30Z psavushchik $
 */

define(function (require) {
    "use strict";

    var Backbone = require("backbone"),
        $ = require("jquery"),
        template = require("text!common/component/pagination/template/paginationContentTemplate.htm"),
        containerTemplate = require("text!common/component/pagination/template/paginationContainerTemplate.htm"),
        PaginationModel = require("./model/PaginationModel"),
        i18n = require("bundle!CommonBundle"),
        _ = require("underscore");

    require("css!pagination.css");

    var DEFAULT_SET_OPTIONS = {
        silent: false,
        validate: true
    };

    return Backbone.View.extend(
        /** @lends Pagination.prototype */
        {


        template: _.template(template),

        el: containerTemplate,

        events: {
            "click button.toLeft.first": "firstPage",
            "click button.left.prev": "prevPage",
            "change input.current": "currentPage",
            "click button.right.next": "nextPage",
            "click button.toRight.last": "lastPage"
        },

        /**
         * @constructor Pagination
         * @classdesc Pagination component.
         * @param {object} options
         * @param {boolean} [options.silent=false] - turn on/off events
         * @param {boolean} [options.validate=true] - turn on/off validation
         */
        constructor: function(options){
            var options = options || {};

            this.options = _.extend({}, DEFAULT_SET_OPTIONS, _.pick(options, ["silent", "validate"]));

            this.model = new PaginationModel(options, this.options);

            this.listenTo(this.model, "validated:invalid", this.onError);
            this.listenTo(this.model, "change", this.render);

            Backbone.View.apply(this, arguments);
        },

        /**
         * @description Changes page to first. Triggers event.
         * @fires Pagination#pagination:change
         */
        firstPage: function(){
            this.model.set("current", 1, this.options);
            this.trigger("pagination:change", this.model.get("current"));
        },

        /**
         * @description Changes page to previous. Triggers event.
         * @fires Pagination#pagination:change
         */
        prevPage: function(){
            var current = this.model.get("current");
            var step = this.model.get("step");
            this.model.set("current", current-step, this.options);
            this.trigger("pagination:change", this.model.get("current"));
        },

        /**
         * @description Changes page to curent. Triggers event.
         * @fires Pagination#pagination:change
         */
        currentPage: function(){
            this.model.set("current", (parseInt(this.$el.find(".current").val()) || this.model.get("current")), this.options);
            this.trigger("pagination:change", this.model.get("current"));
        },

        /**
         * @description Changes page to next. Triggers event.
         * @fires Pagination#pagination:change
         */
        nextPage: function(){
            var current = this.model.get("current");
            var step = this.model.get("step");
            this.model.set("current", current+step, this.options);
            this.trigger("pagination:change", this.model.get("current"));
        },

        /**
         * @description Changes page to last. Triggers event.
         * @fires Pagination#pagination:change
         */
        lastPage: function(){
            var total = this.model.get("total");
            this.model.set("current", total, this.options);
            this.trigger("pagination:change", this.model.get("current"));
        },

        /**
         * @description Triggered on error.
         * @param {object} model - Pagination model
         * @param {object} error - Error object
         * @fires Pagination#pagination:error
         */
        onError: function(model, error){
            this.trigger("pagination:error", error);
        },

        /**
         * @description Hides pagination.
         */
        hide: function(){
            this.$el.hide();
        },

        /**
         * @description Shows pagination.
         */
        show: function(){
            this.$el.show();
        },

        /**
         * @description Triggered on error.
         * @fires Pagination#pagination:error
         */
        resetSetOptions: function(options){
            this.options = options ? _.extend({}, DEFAULT_SET_OPTIONS, _.pick(options, ["silent", "validate"])) : DEFAULT_SET_OPTIONS;
        },

        /**
         * @description Renders pagination.
         * @returns {Pagination}
         */
        render: function(){
            this.$el.html(this.template(_.extend({i18n: i18n}, this.model.toJSON())));
            return this;
        }
    });
});