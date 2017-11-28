define("scheduler/view/job",["require","underscore","backbone","components.dialogs","text!scheduler/template/job.htm","bundle!jasperserver_messages","jrs.configs","moment","common/util/xssUtil"],function(e){var t=e("underscore"),n=e("backbone"),i=(e("components.dialogs"),e("text!scheduler/template/job.htm")),s=e("bundle!jasperserver_messages"),o=e("jrs.configs"),l=(e("moment"),e("common/util/xssUtil"));return n.View.extend({tagName:"li",className:"jobs first leaf",events:{"click .editJob":"edit","click .deleteJob":"remove","change .enableJob":"enable"},initialize:function(e){e.app&&(this.app=e.app),this.template=t.template(i),this.model.on("change",this.render,this)},render:function(){return this.$el.html(this.template({model:this.model.toJSON(),i18n:s,timeZoneOffsetFunction:function(e){return-1*getTZOffset(o.usersTimeZone,e)*60}})),this},edit:function(){this.app.router.navigate("edit/"+this.model.id,!0)},remove:function(){var e,t=this;e=s["report.scheduling.delete.label"].replace("{label}",l.escape(t.model.get("label"))).replace("{newline}","<br><br>"),this.app.handleTwoButtonDialog(e,function(){t.model.destroy()})},enable:function(e){this.model.state(e.target.checked)}})});