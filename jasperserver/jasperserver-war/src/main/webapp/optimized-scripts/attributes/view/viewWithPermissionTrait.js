define(["require","underscore","serverSettingsCommon/enum/confirmDialogTypesEnum","attributes/enum/permissionMasksEnum"],function(i){var e=i("underscore"),s=i("serverSettingsCommon/enum/confirmDialogTypesEnum"),n=i("attributes/enum/permissionMasksEnum"),o={1:1,2:2,32:3,0:4};return{_onViewInitialize:function(){this.listenTo(this.model,"change:_embedded",e.bind(this._onPermissionChange,this))},_onViewRender:function(){var i=this.model.get("inherited"),s=this.model.getPermission(),o=s&&s.mask,t=e.isNumber(o)?o:this.model.get("permissionMask");if(i&&t!==n.ADMINISTRATOR){var r=this.$el.find("option:selected");r.prevAll().attr("disabled","disabled")}},_onPermissionChange:function(i,e){var s=this._isPermissionLimited(e);this._showPermissionConfirm(!1),this.modelChanged._embedded&&s?this.editMode?this._showPermissionConfirm(!0):this._openPermissionConfirm():!this.editMode&&i.setState("confirmedState")},_showPermissionConfirm:function(i){this.permissionConfirmShouldBeShown=i},_isPermissionLimited:function(i){var e=this.model,s=e.getState("confirmedState"),n=o[e.getPermission(i).mask],t=o[e.getPermission(s._embedded).mask];return n>t},_openPermissionConfirm:function(i){this.trigger("open:confirm",s.PERMISSION_CONFIRM,i||{})}}});