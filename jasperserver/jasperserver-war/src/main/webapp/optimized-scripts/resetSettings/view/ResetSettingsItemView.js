define(["require","underscore","common/component/baseTable/childView/BaseRow","serverSettingsCommon/enum/confirmDialogTypesEnum","text!resetSettings/templates/itemViewTemplate.htm"],function(e){var t=e("underscore"),o=e("common/component/baseTable/childView/BaseRow"),n=e("serverSettingsCommon/enum/confirmDialogTypesEnum"),i=e("text!resetSettings/templates/itemViewTemplate.htm"),m=o.extend({tagName:"div",className:"table-row",template:t.template(i),events:{"click .delete":"_onDeleteClick",mouseover:"_onMouseOver",mouseout:"_onMouseOut"},_onMouseOver:function(e){this.trigger("mouseover",this.model,e)},_onMouseOut:function(e){this.trigger("mouseout",this.model,e)},_onDeleteClick:function(){this.trigger("open:confirm",n.DELETE_CONFIRM,this,this.model)}});return m});