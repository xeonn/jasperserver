define(["require","underscore","jquery","backbone","bundle!AttributesBundle","serverSettingsCommon/enum/confirmDialogTypesEnum","attributes/enum/attributesTypesEnum","attributes/enum/permissionMasksEnum","attributes/factory/confirmationDialogFactory","attributes/factory/tableTemplatesFactory","attributes/factory/errorFactory","attributes/view/AttributesViewer","common/component/notification/Notification","common/component/dialog/AlertDialog"],function(e){var t=e("underscore"),i=e("jquery"),n=e("backbone"),o=e("bundle!AttributesBundle"),s=e("serverSettingsCommon/enum/confirmDialogTypesEnum"),r=e("attributes/enum/attributesTypesEnum"),a=(e("attributes/enum/permissionMasksEnum"),e("attributes/factory/confirmationDialogFactory")),l=e("attributes/factory/tableTemplatesFactory"),h=e("attributes/factory/errorFactory"),d=e("attributes/view/AttributesViewer"),c=e("common/component/notification/Notification"),u=e("common/component/dialog/AlertDialog"),m=d.extend({className:"attributesDesigner",ui:{addNewBtn:".addNewItem"},events:{"click @ui.addNewBtn":"_addNewChildView","mousedown .filterItems, .actions.table-column button, .permission.table-column select, .secure.table-column input":"_checkCurrentAttribute"},childEvents:{active:"_activeChildView",changed:"_saveChildViewToChangedList","open:confirm":"_openConfirm",validate:"_validateChildView"},initialize:function(e){e=e||{};var i={type:e.type};this.notification=new c,this.alertDialog=new u,this.model=new n.Model,this.changedViews=[],d.prototype.initialize.apply(this,arguments),this.childViewOptions=t.extend({},e.childViewOptions,i),this.emptyViewOptions=t.extend({},e.emptyViewOptions,i),this._initConfirmationDialogs(),this._initFilters&&this._initFilters(e),!t.isEmpty(e.buttons)&&e.buttonsContainer&&this._initButtons&&this._initButtons(e),this._initEvents()},render:function(e){return d.prototype.render.apply(this,arguments),this._renderFilters&&this._renderFilters(e),this},hide:function(){this._resetFilters&&this._resetFilters(),d.prototype.hide.apply(this,arguments)},saveChildren:function(){var e,n=this,o=[],s=[];return this.validationDfD=new i.Deferred,this.saveDfD=new i.Deferred,this.currentChildView?this.currentChildView.runValidation({dfd:this.validationDfD}):this.validationDfD.resolve(),this.validationDfD.done(function(){t.each(n.changedViews,function(t){e=t.model,o.push(e),!t.isDeleted&&s.push(e)},n),o.length?n.collection.save(o,s).done(t.bind(n._successAjaxCallback,n)).fail(t.bind(n._errorAjaxCallback,n)):n.saveDfD.resolve()}),this.saveDfD},revertChanges:function(){this.revertDfd=new i.Deferred;var e,t=this,n=new i.Deferred;return this.currentChildView?this.currentChildView.toggleActive().done(n.resolve):n.resolve(),n.done(function(){for(var i=t.changedViews.length,n=i-1;n>=0;n--)e=t.changedViews[n],e.isDeleted?t._revertViewRemoval(e):e.model.isNew()?t.collection.remove(e.model):e.model.reset().setState("confirmedState",e.model.getState());t._resetChangedList(),t.revertDfd.resolve()}),this.revertDfd},remove:function(){i(window).off("beforeunload",this._onPageLeave),this.confirmationDialog&&this.confirmationDialog.remove(),this.notification&&this.notification.remove(),this.alertDialog&&this.alertDialog.remove(),d.prototype.remove.apply(this,arguments)},getTemplate:function(){return t.template(l())},containsUnsavedItems:function(){return!!this.changedViews.length},removeView:function(e,t){e=e||this._findChildrenByModel(t),t=t||e.model,e.isDeleted={index:this.collection.indexOf(t)},this._saveChildViewToChangedList(e,!t.isNew()),this._removeModel(t)},_initEvents:function(){i(window).on("beforeunload",t.bind(this._onPageLeave,this))},_initConfirmationDialogs:function(){this.confirmationDialogs={},t.each(s,function(e){this.confirmationDialogs[e]=a(e)},this),this.listenTo(this.confirmationDialogs[s.DELETE_CONFIRM],"button:yes",this._onDeleteConfirm),this.listenTo(this.confirmationDialogs[s.NAME_CONFIRM],"button:yes",this._onNameConfirm),this.listenTo(this.confirmationDialogs[s.NAME_CONFIRM],"button:no",t.bind(this._revertChangedModelProperty,this,"name")),this.listenTo(this.confirmationDialogs[s.CANCEL_CONFIRM],"button:yes",this.revertChanges),this.listenTo(this.confirmationDialogs[s.EDIT_CONFIRM],"button:yes",this._onEditConfirm),this._initPermissionConfirmEvents&&this._initPermissionConfirmEvents()},_successAjaxCallback:function(e){this.notification.show({message:o["attributes.notification.message.saved"],type:"success"});var i,n,s=this._getChangedModels(!0),r=this._getChangedModels(),a=t.filter(r,function(e){return e.isRenamed()}),l=!this._isServerLevel()&&this._searchForInherited&&(s.length||a.length);l&&this._searchForInherited(t.union(s,a)).then(this.saveDfD.resolve,this.saveDfD.reject),e&&(i=e.attribute,t.each(i,function(e){n=this._findChildrenByModel(e.name),n&&n._onSaveSuccess()},this)),this._resetChangedList(),!l&&this.saveDfD.resolve()},_errorAjaxCallback:function(e){this.alertDialog.setMessage(h(e)),this.alertDialog.open(),this.saveDfD.reject()},_toggleAddNewItemButton:function(e){var t=i(this.ui.addNewBtn);e?t.show():t.hide()},_checkCurrentAttribute:function(e){this.currentChildView&&(e.preventDefault(),e.stopPropagation(),this.confirmationDialogs[s.EDIT_CONFIRM].open())},_onPageLeave:function(e){return this.containsUnsavedItems()?((e||window.event).returnValue=o["attributes.dialog.unsaved.changes"],o["attributes.dialog.unsaved.changes"]):void 0},_onEditConfirm:function(){this.currentChildView.cancel()},_onNameConfirm:function(){this.validateDfD&&this.validateDfD.resolve()},_onDeleteConfirm:function(){var e=this.model.get("changedChildView"),t=e.model,i=t.get("name"),n=t.isRenamed();e.isInherited()&&!n?e.model.reset():(this.removeView(e),this._revertInheritedRemoval&&this._revertInheritedRemoval(i))},_isServerLevel:function(){return this.type===r.SERVER||null===this.collection.getContext().id},_revertViewRemoval:function(e){var i=e.isDeleted&&e.isDeleted.index,n=t.isNumber(i)?i:this.collection.models.length;this._deleteViewFromChangedList(e),e.model.reset(),this.collection.add(e.model,{at:n}),delete e.isDeleted},_revertChangedModelProperty:function(e){var t=this.model.get("changedChildView")||this.currentChildView;t.model.reset(e,"confirmedState")},_addNewChildView:function(){var e=new this.collection.model;this.collection.add(e);var t=this._findChildrenByModel(e);this._saveChildViewToChangedList(t,!0),t.toggleActive()},_scrollToChildView:function(e){var t=this.$el.closest(".body"),i=t.height(),n=e.$el,o=n.height(),s=n.position(),r=i<s.top+o&&{scrollTop:t.scrollTop()+(s.top+o-i)};r&&t.animate(r,900)},_successValidationCallback:function(e,t,i){var n=this,o=e.model;this._filterInheritedViews&&this._filterInheritedViews(i),this._validateIfSecure(e),e.toggleIfModelIsValid().done(function(){n._removeInheritedView&&n._removeInheritedView(o),n._addInheritedView&&n._addInheritedView(o),n._showPermissionConfirm&&n._showPermissionConfirm(e),n._resetFilters&&n._resetFilters(),t.resolve()})},_getChangedModels:function(e,i){var n;return t.compact(t.map(this.changedViews,function(t){return n=e?t.isDeleted&&t.model:t.model,n&&!n.get("inherited")?i?n.toJSON():n:void 0}))},_deleteViewFromChangedList:function(e,i){i=i||t.indexOf(this.changedViews,e),-1!==i&&this.changedViews.splice(i,1)},_removeModel:function(e){this.collection.remove(e)},_findModelsWhere:function(e){return this.collection.findWhere(e)},_findChildrenByModel:function(e){return e=t.isString(e)?this._findModelsWhere({name:e}):e,e&&this.children.findByModel(e)},_resetChangedList:function(){this.changedViews.length=0,this._triggerChangeEvent()},_triggerChangeEvent:function(){this.toggleButtons&&this.toggleButtons(),this.trigger("change")},_validateIfSecure:function(e){var t=e.model.get("secure"),i=e.getChangedProperties("value"),n=e.getChangedProperties("name");e.model.validateIfSecure=!e.model.isNew()&&t&&!i&&n||!1},_activeChildView:function(e,t,i){this._setCurrentChildView(t?e:null),this._scrollToChildView(e),i&&i.resolve()},_saveChildViewToChangedList:function(e,i){var n=t.indexOf(this.changedViews,e);-1!==n?!i&&this._deleteViewFromChangedList(e,n):i&&this.changedViews.push(e),this._triggerChangeEvent()},_openConfirm:function(e,i,n){this.validateDfD=n.dfd,this.model.set("changedChildView",e);var r=this.confirmationDialogs[i],a=i!==s.CANCEL_CONFIRM?!0:this.containsUnsavedItems();switch(i){case s.DELETE_CONFIRM:r.setContent(t.template(o["attributes.confirm.delete.dialog.text"],{name:e.model.get("name")}));break;case s.PERMISSION_CONFIRM:this._getPermissionConfirmContent&&r.setContent(this._getPermissionConfirmContent(e.editMode))}a&&r.open()},_validateChildView:function(e,n){var o=e.model,s=n&&n.dfd?n.dfd:new i.Deferred,r=this._getChangedModels(),a=!o.isOriginallyInherited(),l=t.bind(this._successValidationCallback,this,e,s),h=this._isServerLevel();return e.getChangedProperties("name")?this.collection.validateSearch(o,r,a,h).then(l):l(),s},_setCurrentChildView:function(e){this._toggleAddNewItemButton(!e);var t=this.currentChildView;if(t){var i=t.model,n=i.isNew()&&!t.isStateConfirmed();n&&this.removeView(t,i)}this.currentChildView=e}});return m});