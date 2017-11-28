define(["require","dataSource/model/BaseDataSourceModel","dataSource/collection/SubDataSourceCollection","common/enum/repositoryResourceTypes","underscore","bundle!jasperserver_messages"],function(e){"use strict";var t=e("dataSource/model/BaseDataSourceModel"),r=e("dataSource/collection/SubDataSourceCollection"),a=e("common/enum/repositoryResourceTypes"),o=e("underscore"),s=e("bundle!jasperserver_messages");return t.extend({type:a.VIRTUAL_DATA_SOURCE,defaults:function(){var e={};return o.extend(e,t.prototype.defaults,{subDataSources:[]}),e}(),validation:function(){var e={};return o.extend(e,t.prototype.validation,{subDataSources:[{arrayMinLength:1,msg:s["ReportDataSourceValidator.error.sub.datasources.needed"]},{fn:function(e,t,r){var a=o.map(e,function(e){return e.id.toLowerCase()}),u={},n=[];o.each(a,function(e){e in u?u[e]++:u[e]=1});for(var i in u)u[i]>1&&n.push(i);return n.length>0?s["ReportDataSourceValidator.error.sub.datasources.id.duplicates"].replace("{0}",n.join(", ")):void 0}},{fn:function(){for(var e=null,t=0;t<this.subDataSources.models.length;t++)this.subDataSources.models[t].isValid(!0)||(e=!0);return e}}]}),e}(),initialize:function(e,a){t.prototype.initialize.apply(this,arguments),this.subDataSources=new r(this.get("subDataSources")),this.listenTo(this.subDataSources,"change reset",this.updateSubDataSourcesArray),a.dependentResources&&a.dependentResources.length>0&&this.subDataSources.forEach(function(e){e.set("readOnly",!0)})},updateSubDataSourcesArray:function(){this.set("subDataSources",this.subDataSources.toJSON())}})});