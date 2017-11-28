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
 * @version: $Id: ListWithSelectionModel.js 43947 2014-04-02 17:51:07Z sergey.prilukin $
 */

/*global window, Backbone, _, jaspersoft.components */

/**
 * Model extended from ScalableListModel which allows to work with selection.
 *
 * Model API (in addition to ScalableListModel API):
 *      addValueToSelection         - adds one value to selection and triggers "selection:add" event
 *      addRangeToSelection         - adds range from last adding of single selection to passed
 *                                    index and triggers "selection:addRange"
 *      removeValueFromSelection    - removes value from selection and triggers "selection:remove"
 *      toggleSelection             - toggles selection and triggers appropriate event
 *      selectionContains           - return true if value is selected and false otherwise
 *      clearSelection              - clear selection and trigger "selection:clear" event
 *      getSelection                - returns current selection as sparse array
 *      select                      - select passed values
 *      selectAll                   - select all values
 *      invertSelection             - inverts selection
 *
 * this Model implementation looks for following additional options in hash provided to constructor:
 *      selection       - initial selection
 */

define([
    "backbone",
    "underscore",
    "common/component/list/ScalableListModel"
], function (Backbone, _, ScalableListModel) {
    'use strict';

    var ListWithSelectionModel = ScalableListModel.extend({

        /*
            Main init function
         */
        initialize: function(options) {
            ScalableListModel.prototype.initialize.call(this, options);

            this.selection = [];

            if (!this._isSelectionEmpty(options.value)) {
                this._initializeSelection(options.value);
            }
        },

        /* Internal helper methods */

        // Initial selection
        _initializeSelection: function(selection) {

            this.selection = [];

            if (this._isSelectionEmpty(selection)){
                this._triggerSelectionChange();
            }

            if (_.isArray(selection) || typeof selection === "string") {
                //If selection is an array with not matched indexes or simply a string,
                //need to initialize selection after first data fetch

                var pendingSelection = {};

                if (typeof selection === "string") {
                    pendingSelection[selection] = true;
                } else {
                    //Creating hash with selected values optimized for constant search time
                    for (var i = 0; i < selection.length; i++) {
                        pendingSelection[selection[i]] = true;
                    }
                }

                this._selectPendingSelection(pendingSelection);
            } else {
                //Ok, selection is just usual hash with indexes as keys -
                //can init it immediately
                for (var key in selection) {
                    if (selection.hasOwnProperty(key)) {
                        var value = selection[key];
                        if (value !== undefined) {
                            this.selection[key] = value;
                        }
                    }
                }

                this._triggerSelectionChange();
            }
        },

        _isSelectionEmpty: function(selection) {
            if (!selection) {
                return true;
            } else if (_.isArray(selection) && selection.length == 0) {
                return true;
            } else if (_.isArray(selection) || typeof selection === "string") {
                return false;
            }

            for (var index in selection) {
                if (selection.hasOwnProperty(index) && selection[index] !== undefined) {
                    return false;
                }
            }

            return true;
        },

        _selectPendingSelection: function(pendingSelection) {
            if (this.get("bufferStartIndex") == 0 && this.get("bufferEndIndex") == this.get("total") - 1) {
                //All data from this range already fetched
                this._convertInitialArrayToSelection(this.get("items"), pendingSelection);
            } else {
                //Need to fetch data
                var that = this;
                this.getData().done(function(values) {
                    that._convertInitialArrayToSelection(values.data, pendingSelection);
                }).fail(this.fetchFailed);
            }
        },

        _convertInitialArrayToSelection: function(data, pendingSelection) {
            for (var i = 0; i < data.length; i++) {
                if (pendingSelection[data[i].value]) {
                    this.selection[i] = data[i].value;
                }
            }

            this._triggerSelectionChange();
        },

        _triggerSelectionChange: function() {
            this._calcSelectionStartIndex();
            this.trigger("change").trigger("selection:change");
        },

        _calcSelectionStartIndex: function() {
            if (typeof this.selectionStartIndex !== "undefined") {
                return;
            }

            for (var index in this.selection) {
                if (this.selection.hasOwnProperty(index) && this.selection[index] !== undefined) {
                    this.selectionStartIndex = parseInt(index, 10);
                    break;
                }
            }
        },

        _processFetchedDataForRangeSelection: function(data, rangeStart, rangeEnd) {
            for (var i = 0; i < data.length; i++) {
                this.selection[i + rangeStart] = data[i].value;
            }

            this.trigger("selection:addRange", {start: rangeStart, end: rangeEnd, selection: this.selection});
        },

        _fetchAllDataAndModifySelection: function(modifySelection) {
            var that = this;
            this.getData().done(function(values) {
                for (var i = 0; i < values.data.length; i++) {
                    modifySelection(values.data[i].value, i);
                }

                that._triggerSelectionChange();
            }).fail(this.fetchFailed);

            return this;
        },

        /* Methods which supposed to be overridden */

        /*-------------------------
         * API
         -------------------------*/

        /*
            Adds value selection and triggers add event
        */
        addValueToSelection: function(value, index) {
            this.selectionStartIndex = index;
            this.selection[index] = value;
            return this.trigger("selection:add", {value: value, index: index});
        },

        /*
            Adds value selection and triggers add event
        */
        addRangeToSelection: function(value, index) {

            //No range determined - use single item selection
            if (typeof this.selectionStartIndex === "undefined") {
                this.addValueToSelection(value, index);
                return;
            }

            //Same element selected no need to fire range selection
            if (this.selectionStartIndex === index) {
                return;
            }

            //Calculate range dimensions
            var rangeStart = Math.min(this.selectionStartIndex, index);
            var rangeEnd = Math.max(this.selectionStartIndex, index);
            this.selectionStartIndex = index;

            if (rangeStart >= this.get("bufferStartIndex") && rangeEnd <= this.get("bufferEndIndex")) {
                //All data from this range already fetched
                var data = Array.prototype.slice.call(this.get("items"),
                    rangeStart - this.get("bufferStartIndex"), rangeEnd - this.get("bufferStartIndex") + 1);
                this._processFetchedDataForRangeSelection(data, rangeStart, rangeEnd);
            } else {
                //Need to fetch data
                var that = this;
                this.getData({
                    offset: rangeStart,
                    limit: rangeEnd - rangeStart + 1}
                ).done(function(values) {
                    that._processFetchedDataForRangeSelection(values.data, rangeStart, rangeEnd);
                }).fail(this.fetchFailed);
            }
        },

        /*
             Removes value from selection and triggers remove event
         */
        removeValueFromSelection: function(value, index) {
            //this.selectionStartIndex = index;
            this.selection[index] = undefined;
            this.trigger("selection:remove", {value: value, index: index});
        },

        /*
            Does selection toggle for passed value
         */
        toggleSelection: function(value, index) {
            if (this.selectionContains(value, index)) {
                this.removeValueFromSelection(value, index);
            } else {
                this.addValueToSelection(value, index);
            }
        },

        /*
            Returns true if value present in selection
        */
        selectionContains: function(value, index) {
            return this.selection[index] === value;
        },

        /*
            Removes all items from selection
         */
        clearSelection: function() {
            this.selection = [];
            return this.trigger("selection:clear");
        },

        /*
            Returns all selected values
        */
        getSelection: function() {
            return this.selection;
        },

        /*
            Selects passed selection
         */
        select: function(selection) {
            this._initializeSelection(selection);
        },

        /*
            Selects all values
         */
        selectAll: function() {
            this.selection = [];
            var that = this;
            this._fetchAllDataAndModifySelection(function(value, index) {
                that.selection[index] = value;
            });
        },

        /*
            Inverts selection
         */
        invertSelection: function() {
            var oldSelection = this.selection;
            this.selection = [];

            var that = this;
            this._fetchAllDataAndModifySelection(function(value, index) {
                if (oldSelection[index] === undefined) {
                    that.selection[index] = value;
                }
            });
        }
    });

    return ListWithSelectionModel;
});
