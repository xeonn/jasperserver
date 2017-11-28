define(["require","underscore","jquery","backbone.validation","common/validation/ValidationErrorMessage","common/extension/epoxyExtension","attributes/enum/permissionMasksEnum"],function(t){var e=t("underscore"),i=t("jquery"),r=t("backbone.validation"),n=t("common/validation/ValidationErrorMessage"),a=t("common/extension/epoxyExtension"),s=t("attributes/enum/permissionMasksEnum"),o=255,u=2e3,l=a.Model.extend({defaults:{id:void 0,name:void 0,value:"",description:"",inherited:!1,permissionMask:1,secure:!1},constructor:function(){this._initModelWithPermissionDefaults&&this._initModelWithPermissionDefaults(),a.Model.apply(this,arguments)},initialize:function(){this.get("id")||this.setId(),this.validateSameNames=!1,this.setState("originalState"),this.setState("confirmedState")},url:function(){var t=encodeURIComponent(this.id).replace("'","%27");return t=t.replace("'","%27"),this.collection.url(this.isNew()?"":t)},validation:{name:[{required:!0,msg:new n("attributes.error.attribute.name.empty")},{maxLength:o,msg:new n("attributes.error.attribute.name.too.long",o)},{doesNotContainSymbols:"\\\\/",msg:new n("attributes.error.attribute.name.invalid")},{fn:function(){if(this.attr){for(var t,i,r,a=this.attr.length,o=0;a>o;o++){if(r=this.attr[o],e.defaults(r,this.defaults),i=this.holder===r.holder&&this.get("inherited")===r.inherited){t="attributes.error.attribute.name.already.exist";break}if(r.inherited&&r.permissionMask===s.READ_ONLY){t="attributes.error.attribute.name.already.exist.at.higher.level";break}}return this.attr=null,this.holder=null,t&&new n(t)}}},{fn:function(){return this.validateIfSecure?new n("attributes.error.attribute.secure.renaming.not.allowed"):void 0}}],value:[{maxLength:u,msg:new n("attributes.error.attribute.value.too.long",u)},{fn:function(){return this.validateIfSecure?(this.validateIfSecure=!1," "):void 0}}],description:[{maxLength:o,msg:new n("attributes.error.attribute.description.too.long",o)}]},setId:function(){var t=this.get("name"),e=this.get("id");t!==e&&this.set("id",t)},toggleSameNamesValidation:function(){this.validateSameNames=!this.validateSameNames},resetField:function(t){this.set(t,this.defaults[t])},reset:function(t,e){var i={};return e=this.getState(e),i[t]=e[t],this.set(t?i:e),this},isRenamed:function(){return this.get("name")!==this.get("id")},isOriginallyInherited:function(){return this.originalState.inherited},isOverridden:function(){return!this.compareAttribute("inherited")},compareAttribute:function(t){return this.originalState[t]===this.confirmedState[t]},setState:function(t,i){i=i||this.attributes,this[t||"originalState"]=e.clone(i)},getState:function(t){return this[t||"originalState"]},trimAttrs:function(t,r){e.each(t,function(t){var e=this.get(t);this.set(t,i.trim(e),r)},this)},toJSON:function(t){t=t||{};var i=this.getState().value===this.getState("confirmedState").value,r=this.get("secure"),n=this.isNew()||this.isOverridden(),s=a.Model.prototype.toJSON.apply(this,arguments);return t.omitValue&&!n&&i&&r&&e.omit(s,"value")||s}});return e.extend(l.prototype,r.mixin),l});