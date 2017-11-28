define(["require","css!importExport.css","jquery","underscore","backbone","../model/ImportModel","../model/ImportStateModel","../enum/importPendingStatesEnum","../enum/brokenDependencyStrategyEnum","../../export/view/LoadingDialog","./MergeTenantDialogView","./ImportDependentResourcesDialogView","./ImportWarningsDialogView","tenantImportExport/import/factory/warningsFactory","common/component/notification/Notification","common/view/mixin/epoxyViewMixin","text!tenantImportExport/import/template/importTemplate.htm","bundle!ImportExportBundle","bundle!CommonBundle"],function(e){"use strict";function t(){var e=this.stateModel.get("warnings");return a.map(e,function(e){return f(e)})}function i(e,i){i=i||"warning";var o=t.call(this);"import.finished"!==e||a.isEmpty(o)||this.warningsDialogView.open({items:o}),this.notification.show({delay:!1,message:b[e]||b["import.error.unexpected"],type:i}),this.trigger("import:finished",this.model.get("organization")),this.model.reset(this.type)}function o(){var e=this.stateModel.get("error");e.errorCode===m.BROKEN_DEPS?this.dependentResourcesDialogView.open({items:e.parameters}):e.errorCode===m.TENANT_MISMATCH&&this.mergeTenantDialogView.open({fileTenantId:e.parameters[0],selectedTenantId:this.model.get("organization")})}function n(e,t){return function(i){e.model.set("brokenDependencies",t),e.doImport()}}function s(e){this.model.cancel(),this.stateModel.set("phase",e)}e("css!importExport.css");var r=e("jquery"),a=e("underscore"),l=e("backbone"),d=e("../model/ImportModel"),h=e("../model/ImportStateModel"),m=e("../enum/importPendingStatesEnum"),c=e("../enum/brokenDependencyStrategyEnum"),p=e("../../export/view/LoadingDialog"),g=e("./MergeTenantDialogView"),u=e("./ImportDependentResourcesDialogView"),w=e("./ImportWarningsDialogView"),f=e("tenantImportExport/import/factory/warningsFactory"),D=e("common/component/notification/Notification"),T=e("common/view/mixin/epoxyViewMixin"),E=e("text!tenantImportExport/import/template/importTemplate.htm"),b=e("bundle!ImportExportBundle"),x=e("bundle!CommonBundle"),y=l.View.extend({tagName:"form",className:"import-view",id:"importDataFile",events:{"change input[type='file']":"validateFile","click .checkBox label":"_clickOnCheckbox"},initialize:function(){this.model||(this.model=new d(null,{form:this.el})),this.stateModel=new h,this.loadingDialog=new p({content:x["dialog.overlay.loading"]}),this.mergeTenantDialogView=new g,this.dependentResourcesDialogView=new u,this.warningsDialogView=new w,this.notification=new D,this.listenTo(this.stateModel,"change:phase",this._handleImportPhase,this);var e={delay:!1,message:b["import.error.cancelled"]},t={delay:!1,message:b["import.error.unexpected"]};this.listenTo(this.model,"change",this._onModelChange),this.listenTo(this.model,"error:notFound",a.bind(this.notification.show,this.notification,e)),this.listenTo(this.stateModel,"error:notFound",a.bind(this.notification.show,this.notification,e)),this.listenTo(this.model,"error:internalServerError",a.bind(this.notification.show,this.notification,t)),this.listenTo(this.stateModel,"error:internalServerError",a.bind(this.notification.show,this.notification,t)),this.listenTo(this.model,"error",a.bind(this.loadingDialog.close,this.loadingDialog)),this.listenTo(this.stateModel,"error",a.bind(this.loadingDialog.close,this.loadingDialog)),this.listenTo(this.mergeTenantDialogView,"button:import",function(){this.model.set("mergeOrganization",!0),this.doImport()},this),this.listenTo(this.mergeTenantDialogView,"button:cancel",a.bind(s,this,h.STATE.CANCELLED)),this.listenTo(this.dependentResourcesDialogView,"button:skip",n(this,c.SKIP)),this.listenTo(this.dependentResourcesDialogView,"button:include",n(this,c.INCLUDE)),this.listenTo(this.dependentResourcesDialogView,"button:cancel",a.bind(s,this,h.STATE.CANCELLED)),this.epoxifyView()},render:function(e){return this.type=e.type,this.model.reset(this.type,{organization:e.tenantId}),this.$el.html(a.template(E)({i18n:b,model:this.model.toJSON()})),this.applyEpoxyBindings(),this},validateFile:function(e){this.model.set("fileName",r(e.target).val());var t=r(e.target),i=t.parent();this.model.isValid(!0)?i.removeClass("error"):i.addClass("error")},doImport:function(){var e=this;this.loadingDialog.open(),this.model.isValid(!0)&&this.model.save().always(function(t){e.stateModel.set(t)})},_handleImportPhase:function(){var e=this.stateModel.get("phase");e!==h.STATE.INPROGRESS&&this.loadingDialog.close(),e===h.STATE.READY&&i.call(this,"import.finished","success")||e===h.STATE.FAILED&&i.call(this,this.stateModel.get("error").errorCode)||e===h.STATE.CANCELLED&&i.call(this,"import.error.cancelled")||e===h.STATE.PENDING&&o.call(this)},_clickOnCheckbox:function(e){var t=r(e.target).next();t[0].disabled||(t[0].checked=!t[0].checked,t.trigger("change"))},_onModelChange:function(){this.model.isValid(!0)}});return a.extend(y.prototype,T),y});