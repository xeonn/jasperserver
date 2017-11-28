define(["require","jquery","underscore","common/transport/request","jrs.configs","common/model/RepositoryResourceModel"],function(e){"use strict";var t=e("jquery"),a=e("underscore"),o=e("common/transport/request"),r=e("jrs.configs"),n=e("common/model/RepositoryResourceModel");return n.extend({defaults:{uri:void 0,label:void 0,columns:[],dataSourceUri:void 0},validation:{columns:function(e){var t=[];return a.each(e,function(e,a){e.label||t.push({rowId:a,name:"label"})}),0!==t.length?this.trigger("validationFailed",t):this.trigger("validationPassed"),null}},type:"simpleDomain",url:function(){return r.contextPath+"/rest_v2/domains"},isNew:function(){return!0},constructor:function(e,t){n.prototype.constructor.apply(this,arguments),this.dataSource=t.dataSource,t.dataSource&&t.dataSource.uri&&this.set("dataSourceUri",t.dataSource.uri)},save:function(e,t){return a.defaults(t||(t={}),{headers:{Accept:"application/json","Content-Type":"application/simpleDomain+json; charset=UTF-8"}}),n.prototype.save.call(this,e,t)},parseMetadata:function(e){return a.each(e.columns,function(e){e.show=!0}),e},parse:function(e){var t=n.prototype.parse.apply(this,arguments);return t.dataSource&&(t.dataSourceUri=t.dataSource.dataSourceReference,delete t.dataSource),t.metadata&&(t.columns=t.metadata.columns,delete t.metadata),t},toJSON:function(){var e=n.prototype.toJSON.apply(this,arguments);return e.dataSource={dataSourceReference:e.dataSourceUri},e.metadata={columns:a.reduce(e.columns,function(e,t){return t.show&&e.push(t),e},[]),queryLanguage:"csv"},delete e.dataSourceUri,delete e.columns,e},fetchMetadata:function(){var e=this,a=t.Deferred();return o({type:"POST",url:r.contextPath+"/rest_v2/connections",dataType:"json",data:JSON.stringify(this.dataSource),headers:{"Content-Type":"application/repository.customDataSource+json",Accept:"application/table.metadata+json"}}).done(function(t){t=e.parseMetadata(t),e.set("columns",t.columns),a.resolve(t)}).fail(function(e){a.reject(e)}),a}})});