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
 * @version: $Id: designerInheritedTrait.js 8900 2015-05-06 20:57:14Z yplakosh $
 */

define(function(require) {

    var _ = require("underscore");

    return {
        _findInheritedViewByName: function(views, name) {
            return _.find(views, function(view) {
                return view.isInherited() && view.model.get("name") === name;
            });
        },

        _filterInheritedViews: function(data) {
            if (data) {
                var attributes = data.attribute;

                this.inheritedAttributes = _.filter(attributes, function(attribute) {
                    return attribute.inherited && !this._findInheriteds(attribute.name);
                }, this);
            } else {
                this.inheritedAttributes = null;
            }
        },

        _findInheriteds: function(name) {
            return this._findModelsWhere({name: name, inherited: true});
        },

        _revertInheritedRemoval: function(name) {
            if (!this._findInheriteds(name)) {
                var inheritedView = this._findInheritedViewByName(this.changedViews, name);
                inheritedView && this._revertViewRemoval(inheritedView);
            }
        },

        _searchForInherited: function(models) {
            var self = this;

            return this.collection.search(models)
                .done(function(data) {
                    self._filterInheritedViews(data);
                    self.collection.addItemsToCollection(self.inheritedAttributes);
                });
        },

        _removeInheritedView: function(model) {
            var inheritedModels = this.collection.where({name: model.get("name"), inherited: true});

            _.each(inheritedModels, function(inheritedModel) {
                inheritedModel && !_.isEqual(model, inheritedModel) && this.removeView(null, inheritedModel);
            }, this);
        },

        _addInheritedView: function(model) {
            if (this.inheritedAttributes && this.inheritedAttributes.length) {
                model.resetField("id");
                this.collection.addItemsToCollection(this.inheritedAttributes);
            }
        }
    };

});