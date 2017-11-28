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
 * @author Sergey Prilukin
 * @version: $Id: MultiSelect.js 43947 2014-04-02 17:51:07Z sergey.prilukin $
 */

/**
 * Selecteditems list. Part of MultiSelect.
 */

define([
    "jquery",
    "backbone",
    "underscore",
    "common/component/multiSelect/AvailableItemsList",
    "common/component/multiSelect/SelectedItemsList",
    "common/component/multiSelect/SelectedItemsDataProvider",
    "text!common/component/multiSelect/templates/multiSelectTemplate.htm"
], function ($, Backbone, _, AvailableItemsList, SelectedItemsList, SelectedItemsDataProvider, multiSelectTemplate) {
    'use strict';

    var SELECTION_CHANGE_TIMEOUT = 100;

    var MultiSelect = Backbone.View.extend({

        initialize: function(options) {
            this.template = _.template(multiSelectTemplate);

            this.availableItemsList = options.availableItemsList || new AvailableItemsList({
                getData: options.getData,
                bufferSize: options.bufferSize,
                loadFactor: options.loadFactor,
                chunksTemplate: options.chunksTemplate,
                scrollTimeout: options.scrollTimeout
            });

            this.selectedItemsDataProvider = new SelectedItemsDataProvider({
                getData: options.getData
            });

            this.selectedItemsList = new SelectedItemsList({
                getData: this.selectedItemsDataProvider.getData,
                bufferSize: options.bufferSize,
                loadFactor: options.loadFactor,
                chunksTemplate: options.chunksTemplate,
                scrollTimeout: options.scrollTimeout
            });


            this.initListeners();

            //Do not trigger selection chnaged first time
            if (typeof options.value !== "undefined") {
                this.silent = true;
                this.availableItemsList.setValue(options.value);
            }

            this.render();
        },

        initListeners: function() {
            this.listenTo(this.availableItemsList, "selection:change", this.selectionChange, this);
            this.listenTo(this.availableItemsList, "expand", this.onExpand, this);
            this.listenTo(this.availableItemsList, "collapse", this.onCollapse, this);
            this.listenTo(this.selectedItemsList, "selection:remove", this.selectionRemoved, this);
        },

        render: function() {
            this.availableItemsList.undelegateEvents();
            this.selectedItemsList.undelegateEvents();

            var multiSelect = $(this.template({
                isIPad: navigator.platform === "iPad"
            }));

            this.availableItemsList.render();
            this.selectedItemsList.render();

            multiSelect.append(this.availableItemsList.el);
            multiSelect.append(this.selectedItemsList.el);

            this.$el.empty();
            this.$el.append(multiSelect);

            this.availableItemsList.delegateEvents();
            this.selectedItemsList.delegateEvents();

            return this;
        },

        /* Event Handlers */

        selectionChange: function(selection) {
            clearTimeout(this.selectionChangeTimeout);

            this.selectionChangeTimeout = setTimeout(
                _.bind(this.selectionChangeInternal, this, selection), SELECTION_CHANGE_TIMEOUT);
        },

        selectionRemoved: function (selection) {
            var indexMapping = this.selectedItemsDataProvider.getReverseIndexMapping();

            var availableSelection = this.availableItemsList.getValue();
            var newAvailableSelection = {};

            for (var i in availableSelection) {
                if (availableSelection.hasOwnProperty(i)) {
                    var index = indexMapping ? indexMapping[i] : i;
                    if (index !== undefined && selection[index] === undefined) {
                        var value = availableSelection[i];
                        if (value !== undefined) {
                            newAvailableSelection[i] = value;
                        }
                    }
                }
            }

            this.availableItemsList.setValue(newAvailableSelection);
        },

        onExpand: function() {
            this.selectedItemsList.$el.addClass("disabled");
            this.trigger("expand");
        },

        onCollapse: function() {
            this.selectedItemsList.$el.removeClass("disabled");
            this.trigger("collapse");
        },

        /* Internal helper methods */
        selectionChangeInternal: function(selection) {
            var that = this;
            this.selectedItemsDataProvider.setSelectedData(selection);
            this.selectedItemsList.fetch(function() {
                that.selectedItemsList.resize();
            });

            if (!this.silent) {
                this.triggerSelectionChange();
            } else {
                delete this.silent;
            }
        },

        triggerSelectionChange: function() {
            this.trigger("selection:change", this.getValue());
        },

        /* API */

        renderData: function() {
            this.availableItemsList.renderData();
            this.selectedItemsList.renderData();

            return this;
        },

        fetch: function(callback) {
            this.availableItemsList.fetch(callback);
            //this.selectedItemsList.fetch();
        },

        setValue: function(value, options) {
            if (options && options.silent) {
                this.silent = true;
            }

            this.availableItemsList.setValue(value);
        },

        getValue: function() {
            var value = this.availableItemsList.getValue();

            //We have to compact result
            var result = [];
            var i = 0;
            for (var index in value) {
                if (value.hasOwnProperty(index) && value[index] !== undefined) {
                    result[i++] = value[index];
                }
            }

            return result;
        },

        setDisabled: function(disabled) {
            this.availableItemsList.setDisabled(disabled);
            this.selectedItemsList.setDisabled(disabled);
            return this;
        },

        getDisabled: function() {
            return this.availableItemsList.getDisabled();
        },

        remove: function() {
            this.availableItemsList.remove();
            this.selectedItemsList.remove();

            Backbone.View.prototype.remove.call(this);
        }
    });

    //Support for non-AMD modules
    window.MultiSelect = MultiSelect;

    return MultiSelect;
});

