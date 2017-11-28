define(["require","dataSource/model/BaseDataSourceModel","underscore","jquery","jrs.configs","common/config/requestSettings","dataSource/enum/connectionTypes","common/enum/repositoryResourceTypes","components.dialogs","bundle!all","bundle!jasperserver_config"],function(e){"use strict";var t=e("dataSource/model/BaseDataSourceModel"),s=e("underscore"),r=e("jquery"),o=e("jrs.configs"),i=e("common/config/requestSettings"),n=e("dataSource/enum/connectionTypes"),a=e("common/enum/repositoryResourceTypes"),u=e("components.dialogs"),p=e("bundle!all"),c=e("bundle!jasperserver_config"),d=t.extend({type:a.CUSTOM_DATA_SOURCE,constructor:function(e,r){this.defaults=s.extend({},this.defaults,{dataSourceName:r.dataSourceType,connectionType:n.CUSTOM}),t.prototype.constructor.apply(this,arguments)},initialize:function(){var e=t.prototype.initialize.apply(this,arguments);return this.customFields=[],this.testable=!1,this.queryTypes=null,this.initialization=r.Deferred(),this.getCustomFieldsDefinition(),e},getCustomFieldsDefinition:function(){var e={},t=this;return s.extend(e,i,{Accept:"application/json"}),r.ajax({type:"GET",headers:e,url:o.contextPath+"/rest_v2/customDataSources/"+this.get("dataSourceName")}).done(function(e){e&&e.propertyDefinitions&&s.isArray(e.propertyDefinitions)&&(t.resetValidation(),t.testable=!!e.testable,t.queryTypes=e.queryTypes?e.queryTypes:null,s.each(e.propertyDefinitions,function(e){var r={};e.properties&&(e.properties=s(e.properties).reduce(function(e,t){return e[t.key]=t.value,e},{})),t.customFields.push(e),t.defaults[e.name]=e.defaultValue,t.options.isEditMode||t.set(e.name,e.defaultValue),"password"===e.name&&t.options.isEditMode&&!t.isNew()&&t.set("password",c["input.password.substitution"]),e.properties&&e.properties.mandatory&&(r[e.name]={required:!0,msg:p[t.get("dataSourceName")+"."+e.name+".required"]||p["required.field.specify.value"]},s.extend(t.validation,r))})),t.options.isEditMode||t.set(t.parse(t.attributes),{silent:!0}),t.initialization.resolve()}).fail(function(e){var t=!1,s="Failed to load custom data source definition. ";try{t=JSON.parse(e.responseText)}catch(r){}t&&(t[0]&&t[0].errorCode?s+="<br/>The reason is: "+t[0].errorCode:t.message&&(s+="<br/>The reason is: "+t.message),s+="<br/><br/>The full response from the server is: "+e.responseText),u.errorPopup.show(s)})},parse:function(e){var r=t.prototype.parse.apply(this,arguments);return r=s.extend(r,this.parseProperties(e.properties)),delete e.properties,r},parseProperties:function(e){var t={};return s.isEmpty(e)||s.each(e,function(e){t[e.key]="password"===e.key?c["input.password.substitution"]:e.value}),t},toJSON:function(){var e=t.prototype.toJSON.apply(this,arguments);return this.customFieldsToJSON(e,this.customFields)},customFieldsToJSON:function(e,t){return s.isEmpty(t)||(e.properties=[],s.each(t,function(t){var s=e[t.name],r="password"===t.name;(!r||r&&s!==c["input.password.substitution"])&&(e.properties.push({key:t.name,value:s}),delete e[t.name])})),e},resetValidation:function(){this.validation=s.clone(d.prototype.validation)}});return d});