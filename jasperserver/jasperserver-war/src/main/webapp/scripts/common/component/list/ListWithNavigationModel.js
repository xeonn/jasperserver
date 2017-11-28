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
 * @author: Sergey Prilukin
 * @version: $Id: ListWithNavigationModel.js 43947 2014-04-02 17:51:07Z sergey.prilukin $
 */

/**
 * Model for ListWithNavigation.
 *
 * It adds "active" concept to the model:
 *      Each item from the model could be "activated",
 *      but only one item could be "active" at one time.
 *
 * Descendant implementations can override setActive and getActive methods
 * so "activation" concept will be meant differently
 *
 */

define([
    "backbone",
    "underscore",
    "common/component/list/ListWithSelectionModel"
], function (Backbone, _, ListWithSelectionModel) {
    'use strict';

    var ListWithNavigationModel = ListWithSelectionModel.extend({

        /* API */

        activate: function(index, options) {

            if (typeof index !== "number") {
                this.setActive({});
                return;
            }

            options = options || {};

            if (index >= this.get("bufferStartIndex") && index <= this.get("bufferEndIndex")) {

                var item = this.get("items")[index - this.get("bufferStartIndex")];

                if (item) {
                    this.setActive(_.extend(options, {index: index, item: item}));
                }
            } else {
                var that = this;

                //We have to fetch value of the item by it's index.
                this.getData({
                    offset: index,
                    limit: 1
                }).done(function(values) {
                        //Now after we have fetched this item,
                        //we can activate it
                        that.setActive(_.extend(options, {index: index, item: values.data[0]}));
                    }).fail(this.fetchFailed);
            }
        },

        setActive: function(options) {
            if (this.active && options.index === this.active.index && options.item.value === this.active.value) {
                return;
            }

            this.active = options && typeof options.index === "number" ? {
                index: options.index,
                value: options.item.value,
                label: options.item.label
            } : undefined;

            if (!options || !options.silent) {
                this.trigger("change");
                this.trigger("active:changed", this.active);
            }
        },

        getActive: function() {
            return this.active;
        }
    });

    return ListWithNavigationModel;
});
