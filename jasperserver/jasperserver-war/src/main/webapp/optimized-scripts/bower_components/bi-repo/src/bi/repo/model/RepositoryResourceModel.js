define(["require","underscore","common/model/BaseModel","backbone.validation","bundle!js-sdk/RepositoryResourceBundle","common/util/i18nMessage","common/enum/httpStatusCodes","request"],function(e){"use strict";function t(e,t,r,n,i){return a({type:t,dataType:"json",url:e.contextPath+"/rest_v2/resources"+r+"?overwrite="+n+"&createFolders="+i,headers:{Accept:"application/json","Content-Location":e.get("uri")}}).done(function(t){e.set("uri",t.uri)})}var r=e("underscore"),n=e("common/model/BaseModel"),i=e("backbone.validation"),o=e("bundle!js-sdk/RepositoryResourceBundle"),s=e("common/util/i18nMessage").extend({bundle:o}),a=(e("common/enum/httpStatusCodes"),e("request")),d={LABEL_MAX_LENGTH:100,NAME_MAX_LENGTH:100,DESCRIPTION_MAX_LENGTH:250,NAME_NOT_SUPPORTED_SYMBOLS:"~!#\\$%^|\\s`@&*()\\-+={}\\[\\]:;\"'\\<\\>,?/\\|\\\\"},u=/application\/repository\.([^\+]+)\+json/,l=n.extend({idAttribute:"uri",type:void 0,urlRoot:function(){return(this.contextPath?this.contextPath+"/":"")+"rest_v2/resources"},defaults:{name:void 0,parentFolderUri:void 0,uri:void 0,label:void 0,description:void 0,permissionMask:void 0,creationDate:void 0,updateDate:void 0,version:void 0},validation:{name:function(e,t,r){return e&&e.length>d.NAME_MAX_LENGTH?new s("error.field.max.length","name",d.NAME_MAX_LENGTH):e&&new RegExp("["+d.NAME_NOT_SUPPORTED_SYMBOLS+"]","g").test(e)?new s("error.field.bad.symbols","name",d.NAME_NOT_SUPPORTED_SYMBOLS):void 0},label:function(e,t,n){return r.isEmpty(e)?new s("error.field.required","label"):e.length>d.LABEL_MAX_LENGTH?new s("error.field.max.length","label",d.LABEL_MAX_LENGTH):void 0},description:function(e,t,r){return e&&e.length>d.NAME_MAX_LENGTH?new s("error.field.max.length","description",d.DESCRIPTION_MAX_LENGTH):void 0},parentFolderUri:[{required:!0,msg:new s("error.field.required","parentFolderUri")}]},url:function(){return this.isNew()?this.urlRoot()+encodeURI(this.get("parentFolderUri")):this.urlRoot()+encodeURI(this.id)},constructor:function(e,t){t||(t={}),r.defaults(t,{parse:!0}),n.call(this,e,t)},initialize:function(e,t){this.contextPath=t.contextPath,t.type&&(this.type=t.type),this.on("change:parentFolderUri change:name",this._updateUri),this.on("change:uri",this._updateNameAndParentFolderUri),n.prototype.initialize.apply(this,arguments)},clone:function(){return new this.constructor(this.attributes,{contextPath:this.contextPath})},parse:function(e){return"undefined"!=typeof e.uri?(e.name=l.getNameFromUri(e.uri),e.parentFolderUri=l.getParentFolderFromUri(e.uri)):e.parentFolderUri&&e.name&&(e.uri=l.constructUri(e.parentFolderUri,e.name)),e},toJSON:function(){var e=this.serialize();return delete e.name,delete e.parentFolderUri,e},_updateUri:function(){var e=this.get("name"),t=this.get("parentFolderUri"),r=l.constructUri(t,e);r&&this.set("uri",r)},_updateNameAndParentFolderUri:function(){var e=this.get("uri"),t=l.getNameFromUri(e),r=l.getParentFolderFromUri(e);this.set({name:t,parentFolderUri:r})},fetch:function(e){return r.defaults(e||(e={}),{headers:{Accept:"application/json"}}),e.url=this.url()+"?expanded="+(e.expanded===!0),delete e.expanded,n.prototype.fetch.call(this,e)},sync:function(e,t,r){if("read"===e){var i=r.success,o=this;r.success=function(e,t,r){var n=r.getResponseHeader("Content-Type"),s=u.exec(n);if(!s||!s[1])throw new Error("Unsupported response content type: "+n);o.type=s[1],i&&i(e,t,r)}}return n.prototype.sync.call(this,e,t,r)},save:function(e,t,i){var o;if(r.isUndefined(e)||r.isNull(e)||r.isObject(e)?(o=e||{},i=t):(o={})[e]=t,!this.type)throw new Error("Resource type is unspecified. It's not possible to save a resource without it's type specified");return r.defaults(i||(i={}),{headers:{Accept:"application/json","Content-Type":"application/repository."+this.type+"+json; charset=UTF-8"}}),i.url=this.url()+"?createFolders="+(i.createFolders===!0),i.url+="&overwrite="+(i.overwrite===!0),i.url+="&expanded="+(i.expanded===!0),i.url+="&dry-run="+(i.dryRun===!0),delete i.createFolders,delete i.overwrite,delete i.expanded,delete i.expanded,delete i.dryRun,n.prototype.save.call(this,o,i)},isWritable:function(){var e=this.get("permissionMask"),t=!1;return r.isUndefined(e)||(t=1===e||4&e),t},copyTo:function(e,r,n){return t(this,"POST",e,!!r,arguments.length<3||n)},moveTo:function(e,r,n){return t(this,"PUT",e,!!r,arguments.length<3||n)}},{settings:d,getNameFromUri:function(e){if(e){var t=e.split("/");return t[t.length-1]}},getParentFolderFromUri:function(e){if(e){var t=e.split("/");return 2===t.length&&""!==t[1]?"/":t.slice(0,t.length-1).join("/")}},constructUri:function(e,t){return t&&e?-1!==e.indexOf("/",e.length-1)?e+t:e+"/"+t:void 0},generateResourceName:function(e){var t="";return e&&(t=e.replace(new RegExp("["+l.settings.NAME_NOT_SUPPORTED_SYMBOLS+"]","g"),"_")),t}});return r.extend(l.prototype,i.mixin),l});