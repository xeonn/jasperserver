define(["require","underscore","backbone","components.dialogs","text!scheduler/template/list/oneJob.htm","text!scheduler/template/list/oneMasterJob.htm","bundle!all","jrs.configs","moment","common/util/xssUtil","common/component/dialog/ConfirmationDialog"],function(e){"use strict";var t=e("underscore"),o=e("backbone"),n=(e("components.dialogs"),e("text!scheduler/template/list/oneJob.htm")),i=e("text!scheduler/template/list/oneMasterJob.htm"),s=e("bundle!all"),l=e("jrs.configs"),m=(e("moment"),e("common/util/xssUtil")),r=e("common/component/dialog/ConfirmationDialog");return o.View.extend({tagName:"li",className:"jobs first leaf",events:{"click [name=editJob]":"edit","click [name=deleteJob]":"remove","change [name=enableJob]":"enable"},initialize:function(e){this.options=t.extend({},e);var o=this.options.masterViewMode?i:n;this.template=t.template(o),this.model.on("change",this.render,this)},render:function(){return this.$el.html(this.template({model:this.model.toJSON(),i18n:s,timeZoneOffsetFunction:function(e){return-1*getTZOffset(l.usersTimeZone,e)*60}})),this},edit:function(){this.trigger("editJobPressed",this.model.id)},remove:function(){var e=this,t=s["report.scheduling.editing.job.confirm.delete"].replace("{name}",m.escape(this.model.get("label"))).replace("{newline}","<br><br>"),o=new r({title:s["report.scheduling.editing.job.confirm.title"],text:t,additionalCssClasses:"schedulerJobRemoveDialog"});this.listenTo(o,"button:yes",function(){e.model.destroy()}),o.open()},enable:function(e){this.model.state(e.target.checked)}})});