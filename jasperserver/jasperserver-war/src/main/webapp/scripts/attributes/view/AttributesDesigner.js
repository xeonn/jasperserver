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
 * @version: $Id: AttributesDesigner.js 8924 2015-05-21 17:16:54Z obobruyk $
 */

define(function(require) {
    var _ = require("underscore"),
        $ = require("jquery"),
        Backbone = require("backbone"),
        i18n = require("bundle!AttributesBundle"),
        confirmDialogTypesEnum = require("serverSettingsCommon/enum/confirmDialogTypesEnum"),
        attributesTypesEnum = require("attributes/enum/attributesTypesEnum"),
        permissionMasksEnum = require("attributes/enum/permissionMasksEnum"),
        confirmationDialogFactory = require("attributes/factory/confirmationDialogFactory"),
        tableTemplatesFactory = require("attributes/factory/tableTemplatesFactory"),
        errorFactory = require("attributes/factory/errorFactory"),
        AttributesViewer = require("attributes/view/AttributesViewer"),
        Notification = require("common/component/notification/Notification"),
        AlertDialog = require("common/component/dialog/AlertDialog");

    var AttributesDesigner = AttributesViewer.extend({
        className: "attributesDesigner",

        ui: {
            addNewBtn: ".addNewItem"
        },

        events: {
            "click @ui.addNewBtn": "_addNewChildView",
            "mousedown .filterItems, .actions.table-column button, .permission.table-column select, .secure.table-column input": "_checkCurrentAttribute"
        },

        childEvents: {
            "active": "_activeChildView",
            "changed": "_saveChildViewToChangedList",
            "open:confirm": "_openConfirm",
            "validate": "_validateChildView"
        },

        initialize: function(options) {
            options = options || {};

            var type = {type: options.type};

            this.notification = new Notification();
            this.alertDialog = new AlertDialog();
            this.model = new Backbone.Model();
            this.changedViews = [];

            AttributesViewer.prototype.initialize.apply(this, arguments);

            this.childViewOptions = _.extend({}, options.childViewOptions, type);
            this.emptyViewOptions = _.extend({}, options.emptyViewOptions, type);

            this._initConfirmationDialogs();

            this._initFilters && this._initFilters(options);

            !_.isEmpty(options.buttons) && options.buttonsContainer && this._initButtons && this._initButtons(options);

            this._initEvents();
        },

        render: function(hideFilters) {
            AttributesViewer.prototype.render.apply(this, arguments);

            this._renderFilters && this._renderFilters(hideFilters);

            return this;
        },

        hide: function() {
            this._resetFilters && this._resetFilters();

            AttributesViewer.prototype.hide.apply(this, arguments);
        },

        saveChildren: function() {
            var self = this,
                allModels = [],
                updatedModels = [],
                model;

            this.validationDfD = new $.Deferred();
            this.saveDfD = new $.Deferred();

            // general validation deferred which resolves itself after all validations are performed
            this.currentChildView
                ? this.currentChildView.runValidation({dfd: this.validationDfD})
                : this.validationDfD.resolve();

            this.validationDfD.done(function() {
                _.each(self.changedViews, function(view) {
                    model = view.model;

                    allModels.push(model);
                    !view.isDeleted && updatedModels.push(model);
                }, self);

                allModels.length
                    ? self.collection.save(allModels, updatedModels)
                    .done(_.bind(self._successAjaxCallback, self))
                    .fail(_.bind(self._errorAjaxCallback, self))
                    : self.saveDfD.resolve();
            });

            return this.saveDfD;
        },

        revertChanges: function() {
            this.revertDfd = new $.Deferred();

            var self = this,
                dfd = new $.Deferred(),
                view;

            this.currentChildView ?
                this.currentChildView.toggleActive().done(dfd.resolve) : dfd.resolve();

            dfd.done(function() {
                var length = self.changedViews.length;

                for (var i = length - 1; i >= 0; i--) {
                    view = self.changedViews[i];
                    if (!view.isDeleted) {
                        !view.model.isNew()
                            ? view.model.reset().setState("confirmedState", view.model.getState())
                            : self.collection.remove(view.model);
                    } else {
                        self._revertViewRemoval(view);
                    }
                }

                self._resetChangedList();
                self.revertDfd.resolve();
            });

            return this.revertDfd;
        },

        remove: function() {
            $(window).off("beforeunload", this._onPageLeave);

            this.confirmationDialog && this.confirmationDialog.remove();
            this.notification && this.notification.remove();
            this.alertDialog && this.alertDialog.remove();

            AttributesViewer.prototype.remove.apply(this, arguments);
        },

        getTemplate: function() {
            return _.template(tableTemplatesFactory());
        },

        containsUnsavedItems: function() {
            return !!this.changedViews.length;
        },

        removeView: function(view, model) {
            view = view || this._findChildrenByModel(model);
            model = model || view.model;

            view.isDeleted = {
                index: this.collection.indexOf(model)
            };

            this._saveChildViewToChangedList(view, !model.isNew());

            this._removeModel(model);
        },

        /*******************/
        /* Private methods */
        /*******************/

        _initEvents: function() {
            $(window).on("beforeunload", _.bind(this._onPageLeave, this));
        },

        _initConfirmationDialogs: function() {
            this.confirmationDialogs = {};

            _.each(confirmDialogTypesEnum, function(type) {
                this.confirmationDialogs[type] = confirmationDialogFactory(type);
            }, this);

            this.listenTo(this.confirmationDialogs[confirmDialogTypesEnum.DELETE_CONFIRM], "button:yes", this._onDeleteConfirm);
            this.listenTo(this.confirmationDialogs[confirmDialogTypesEnum.NAME_CONFIRM], "button:yes", this._onNameConfirm);
            this.listenTo(this.confirmationDialogs[confirmDialogTypesEnum.NAME_CONFIRM], "button:no", _.bind(this._revertChangedModelProperty, this, "name"));
            this.listenTo(this.confirmationDialogs[confirmDialogTypesEnum.CANCEL_CONFIRM], "button:yes", this.revertChanges);
            this.listenTo(this.confirmationDialogs[confirmDialogTypesEnum.EDIT_CONFIRM], "button:yes", this._onEditConfirm);

            this._initPermissionConfirmEvents && this._initPermissionConfirmEvents();
        },

        _successAjaxCallback: function(data) {
            this.notification.show({message: i18n["attributes.notification.message.saved"], type: "success"});

            var deletedModels = this._getChangedModels(true),
                changedModels = this._getChangedModels(),
                renamedModels = _.filter(changedModels, function(model) {
                    return model.isRenamed();
                }),
                sendRequest = !this._isServerLevel() && this._searchForInherited && (deletedModels.length || renamedModels.length),
                attributes,
                view;

            sendRequest && this._searchForInherited(_.union(deletedModels, renamedModels))
                .then(this.saveDfD.resolve, this.saveDfD.reject);

            if (data) {
                attributes = data.attribute;

                _.each(attributes, function(attribute) {
                    view = this._findChildrenByModel(attribute.name);
                    view && view._onSaveSuccess();
                }, this);
            }

            this._resetChangedList();

            !sendRequest && this.saveDfD.resolve();
        },

        _errorAjaxCallback: function(response) {
            this.alertDialog.setMessage(errorFactory(response));
            this.alertDialog.open();

            this.saveDfD.reject();
        },

        _toggleAddNewItemButton: function(show) {
            var $addNewItem = $(this.ui.addNewBtn);

            show ? $addNewItem.show() : $addNewItem.hide();
        },

        _checkCurrentAttribute: function(e) {
            if (this.currentChildView) {
                e.preventDefault();
                e.stopPropagation();
                this.confirmationDialogs[confirmDialogTypesEnum.EDIT_CONFIRM].open();
            }
        },

        _onPageLeave: function(e) {
            if (this.containsUnsavedItems()) {
                (e || window.event).returnValue = i18n["attributes.dialog.unsaved.changes"];
                return i18n["attributes.dialog.unsaved.changes"];
            }
        },

        _onEditConfirm: function() {
            this.currentChildView.cancel();
        },

        _onNameConfirm: function() {
            this.validateDfD && this.validateDfD.resolve();
        },

        _onDeleteConfirm: function() {
            var childView = this.model.get("changedChildView"),
                model = childView.model,
                modelName = model.get("name"),
                modelIsRenamed = model.isRenamed();

            if (childView.isInherited() && !modelIsRenamed) {
                childView.model.reset();
            } else {
                this.removeView(childView);
                this._revertInheritedRemoval && this._revertInheritedRemoval(modelName);
            }
        },

        _isServerLevel: function() {
            return (this.type === attributesTypesEnum.SERVER) || (this.collection.getContext().id === null);
        },

        /***********************/
        /* Child views methods */
        /***********************/

        _revertViewRemoval: function(view) {
            var index = view.isDeleted && view.isDeleted.index,
                indexInCollection = _.isNumber(index) ? index : this.collection.models.length;

            this._deleteViewFromChangedList(view);
            view.model.reset();
            this.collection.add(view.model, {at: indexInCollection});
            delete view.isDeleted;
        },

        _revertChangedModelProperty: function(property) {
            var childView = this.model.get("changedChildView") || this.currentChildView;

            childView.model.reset(property, "confirmedState");
        },

        _addNewChildView: function() {
            var model = new this.collection.model();

            this.collection.add(model);

            var view = this._findChildrenByModel(model);
            this._saveChildViewToChangedList(view, true);

            view.toggleActive();
        },

        _scrollToChildView: function(childView) {
            var $parentElement = this.$el.closest(".body"),
                parentElementHeight = $parentElement.height(),
                $childViewEl = childView.$el,
                childViewElHeight = $childViewEl.height(),
                childViewPosition = $childViewEl.position(),
                scrollTo = parentElementHeight < (childViewPosition.top + childViewElHeight)
                    && {scrollTop: $parentElement.scrollTop() + (childViewPosition.top + childViewElHeight - parentElementHeight)};

            scrollTo && $parentElement.animate(scrollTo, 900);
        },

        _successValidationCallback: function(childView, dfd, data) {
            var self = this,
                model = childView.model;

            this._filterInheritedViews && this._filterInheritedViews(data);

            this._validateIfSecure(childView);

            childView.toggleIfModelIsValid().done(function() {
                self._removeInheritedView && self._removeInheritedView(model);
                self._addInheritedView && self._addInheritedView(model);

                self._showPermissionConfirm && self._showPermissionConfirm(childView);

                self._resetFilters && self._resetFilters();
                dfd.resolve();
            });
        },

        _getChangedModels: function(deletedOnly, toJSON) {
            var model;

            return _.compact(_.map(this.changedViews, function(view) {
                model = deletedOnly
                    ? view.isDeleted && view.model
                    : view.model;

                if (model && !model.get("inherited")) {
                    return !toJSON ? model : model.toJSON();
                }
            }));
        },

        _deleteViewFromChangedList: function(childView, index) {
            index = index || _.indexOf(this.changedViews, childView);

            index !== -1 && this.changedViews.splice(index, 1);
        },

        _removeModel: function(model) {
            this.collection.remove(model);
        },

        _findModelsWhere: function(attributes) {
            return this.collection.findWhere(attributes);
        },

        _findChildrenByModel: function(model) {
            model = _.isString(model) ? this._findModelsWhere({name: model}) : model;

            return model && this.children.findByModel(model);
        },

        _resetChangedList: function() {
            this.changedViews.length = 0;

            this._triggerChangeEvent();
        },

        _triggerChangeEvent: function() {
            this.toggleButtons && this.toggleButtons();

            this.trigger("change");
        },

        _validateIfSecure: function(childView) {
            var isSecure = childView.model.get("secure"),
                valueChanged = childView.getChangedProperties("value"),
                nameChanged = childView.getChangedProperties("name");

            childView.model.validateIfSecure = (!childView.model.isNew() && isSecure && !valueChanged && nameChanged) || false;
        },

        /******************************/
        /*  Child view event handlers */
        /******************************/

        _activeChildView: function(childView, active, dfd) {
            this._setCurrentChildView(active ? childView : null);
            this._scrollToChildView(childView);
            dfd && dfd.resolve();
        },

        _saveChildViewToChangedList: function(childView, modelChanged) {
            var index = _.indexOf(this.changedViews, childView);

            index !== -1
                ? (!modelChanged && this._deleteViewFromChangedList(childView, index))
                : modelChanged && this.changedViews.push(childView);

            this._triggerChangeEvent();
        },

        _openConfirm: function(childView, type, options) {
            this.validateDfD = options.dfd;
            this.model.set("changedChildView", childView);

            var confirmDialog = this.confirmationDialogs[type],
                openConfirm = type !== confirmDialogTypesEnum.CANCEL_CONFIRM ? true : this.containsUnsavedItems();

            switch (type) {
                case confirmDialogTypesEnum.DELETE_CONFIRM:
                    confirmDialog.setContent(_.template(i18n["attributes.confirm.delete.dialog.text"], {
                        name: childView.model.get("name")
                    }));
                    break;
                case confirmDialogTypesEnum.PERMISSION_CONFIRM:
                    this._getPermissionConfirmContent &&
                    confirmDialog.setContent(this._getPermissionConfirmContent(childView.editMode));
                    break;
            }

            openConfirm && confirmDialog.open();
        },

        _validateChildView: function(childView, options) {
            var model = childView.model,
                dfd = options && options.dfd ? options.dfd : new $.Deferred(),
                changedModels = this._getChangedModels(),
                modelIsOriginallyInherited = !model.isOriginallyInherited(),
                successCallback = _.bind(this._successValidationCallback, this, childView, dfd),
                groupSearch = this._isServerLevel();

            childView.getChangedProperties("name")
                ? this.collection.validateSearch(model, changedModels, modelIsOriginallyInherited, groupSearch).then(successCallback)
                : successCallback();

            return dfd;
        },

        _setCurrentChildView: function(nextCurrentChildView) {
            this._toggleAddNewItemButton(!nextCurrentChildView);

            var currentChildView = this.currentChildView;

            if (currentChildView) {
                var currentChildViewModel = currentChildView.model,
                    removeModel = currentChildViewModel.isNew() && !currentChildView.isStateConfirmed();

                removeModel && this.removeView(currentChildView, currentChildViewModel);
            }

            this.currentChildView = nextCurrentChildView;
        }
    });

    return AttributesDesigner;
});