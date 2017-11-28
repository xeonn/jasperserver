define(["require","underscore","bundle!all","bi/repo/model/RepositoryResourceModel","common/component/dialog/DialogWithModelInputValidation","text!scheduler/saveDialog/template/saveDialogTemplate.htm"],function(e){"use strict";var o=e("underscore"),i=e("bundle!all"),t=e("bi/repo/model/RepositoryResourceModel"),a=e("common/component/dialog/DialogWithModelInputValidation"),n=e("text!scheduler/saveDialog/template/saveDialogTemplate.htm"),l={};return a.extend({theDialogIsOpen:!1,saveDialogTemplate:n,constructor:function(e){e||(e={}),l=o.extend({},e);var t=this.extendModel(e.model),n=this._getLabelForSaveButton(t),s="report.scheduling.saveDialog.cancel";a.prototype.constructor.call(this,{skipLocation:!!l.skipLocation,modal:!0,model:t,minHeight:267,minWidth:440,resizable:!0,additionalCssClasses:"newResizeableDialog schedulerSaveDialog",title:i["report.scheduling.saveDialog.save"],content:o.template(this.saveDialogTemplate,{i18n:i,model:o.extend({},t.attributes),skipLocation:!!l.skipLocation,isEmbedded:l.isEmbedded,isEditMode:l.isEditMode}),buttons:[{label:i[n],action:"save",primary:!0},{label:i[s],action:"cancel",primary:!1}]}),this.on("button:save",o.bind(this._onSaveDialogSaveButtonClick,this)),this.on("button:cancel",o.bind(this._onSaveDialogCancelButtonClick,this))},initialize:function(){a.prototype.initialize.apply(this,arguments)},restoreModel:function(){this.originalModelValidation&&(this.model.validation=this.originalModelValidation)},extendModel:function(e){return this.originalModelValidation=e.validation,e.validation=o.extend({},t.prototype.validation,{name:null,parentFolderUri:null,label:[{required:!0,msg:i["report.scheduling.saveDialog.validation.not.empty.label"]},{maxLength:t.LABEL_MAX_LENGTH,msg:i["report.scheduling.saveDialog.validation.too.long.label"]}],description:[{required:!1},{maxLength:t.DESCRIPTION_MAX_LENGTH,msg:i["report.scheduling.saveDialog.validation.too.long.description"]}]}),e},startSaveDialog:function(){this._openDialog()},closeDialog:function(){this.restoreModel(),this._closeDialog()},_openDialog:function(){this.theDialogIsOpen||(this.bindValidation(),a.prototype.open.apply(this,arguments),this.$contentContainer.find("[name=label]").focus(),this.theDialogIsOpen=!0)},_closeDialog:function(){this.theDialogIsOpen&&(this.unbindValidation(),this.clearValidationErrors(),a.prototype.close.apply(this,arguments),this.theDialogIsOpen=!1)},_getLabelForSaveButton:function(){return"report.scheduling.saveDialog.save"},_onSaveDialogCancelButtonClick:function(){this.restoreModel(),this._closeDialog()},_onSaveDialogSaveButtonClick:function(){this.model.isValid(!0)&&this.performSave()},performSave:function(){var e=this;this.model.checkSaveValidation().done(function(){e.model.save({},{success:o.bind(e._saveSuccessCallback,e),error:o.bind(e._saveErrorCallback,e)})}).fail(function(){e.trigger("saveValidationFailed")})},_saveSuccessCallback:function(e,i){this._closeDialog(),o.isFunction(l.onSaveDone)&&l.onSaveDone()},_saveErrorCallback:function(e,t,a){var n=this,s=!1,r=!1;try{s=JSON.parse(t.responseText)}catch(d){}s.error&&(s=s.error),o.isArray(s)||(s=[s]),o.each(s,function(e){if("label"===e.field){var o="";"error.not.empty"===e.errorCode&&(o=i["error.not.empty.label"]),"version.not.match"===e.errorCode&&(o=i["report.scheduling.resource.exists"]),"mandatory.parameter.error"===e.errorCode&&(o=i["report.scheduling.saveDialog.parameterIsMissing"]),""!==o&&(n.invalidField("[name=label]",o),r=!0)}}),r===!1&&o.isFunction(l.onSaveFail)&&l.onSaveFail(e,t,a)}})});