define(["require","underscore","jquery","dataSource/view/BaseDataSourceView","dataSource/model/JdbcDataSourceModel","dataSource/model/JdbcDriverModel","dataSource/view/dialog/UploadJdbcDriverDialog","text!dataSource/template/jdbcSpecificTemplate.htm","text!dataSource/template/jdbcCustomFieldTemplate.htm","core.events.bis","bundle!jasperserver_messages"],function(e){"use strict";var t=e("underscore"),r=(e("jquery"),e("dataSource/view/BaseDataSourceView")),i=e("dataSource/model/JdbcDataSourceModel"),a=e("dataSource/model/JdbcDriverModel"),o=e("dataSource/view/dialog/UploadJdbcDriverDialog"),d=e("text!dataSource/template/jdbcSpecificTemplate.htm"),s=e("text!dataSource/template/jdbcCustomFieldTemplate.htm"),l=e("core.events.bis"),n=e("bundle!jasperserver_messages");return r.extend({PAGE_TITLE_NEW_MESSAGE_CODE:"resource.datasource.jdbc.page.title.new",PAGE_TITLE_EDIT_MESSAGE_CODE:"resource.datasource.jdbc.page.title.edit",modelConstructor:i,events:function(){var e={};return t.extend(e,r.prototype.events,{"keyup input[type='text'][name!='driverClass'], input[type='password'], textarea":"updateModelProperty","change input[type='text'][name!='driverClass'], input[type='password'], textarea, select":"updateModelProperty","keyup input[type='text'][name='driverClass']":"manuallySetDriverClass","change input[type='text'][name='driverClass']":"manuallySetDriverClass","click #driverUploadButton":"uploadDriver"}),e}(),initialize:function(){r.prototype.initialize.apply(this,arguments),this.listenTo(this.model,"connectionUrlUpdate",this.updateConnectionUrl),this.listenTo(this.model,"customAttributesUpdate",this.updateCustomAttributes),this.listenTo(this.model,"driverClassChange",this.changeDriver),this.listenTo(this.model.drivers,"change",this.updateDriverOption),this.listenTo(this.model.drivers,"add",this.addDriverOption)},updateDriverOption:function(e){var t=this.$("select[name='selectedDriverClass'] option[value='"+e.get("jdbcDriverClass")+"']"),r=e.get("label")+" ("+e.get("jdbcDriverClass")+")";e.get("available")||(r=n["resource.dataSource.jdbc.driverMissing"]+" "+r),t.text(r),this.model.getCurrentDriver()===e&&this.changeUploadDriverButtonState()},addDriverOption:function(e){if(!e.isOtherDriver){var r=this.$("select[name='selectedDriverClass'] option[value='"+a.OTHER_DRIVER+"']"),i=e.get("jdbcDriverClass"),o=this;e.get("available")||(i=n["resource.dataSource.jdbc.driverMissing"]+" "+i),r.before("<option value='"+e.get("jdbcDriverClass")+"'>"+i+"</option>"),t.defer(function(){o.$("select[name='selectedDriverClass']").val(e.get("jdbcDriverClass")),o.model.trigger("change:driverClass")})}},manuallySetDriverClass:function(){var e=this.$("input[type='text'][name='driverClass']").val(),t={driverClass:e};this.model.set(t,{silent:!0}),this.model.validate(t),this.changeUploadDriverButtonState()},updateConnectionUrl:function(){this.$("input[name='connectionUrl']").val(this.model.get("connectionUrl"));var e=this.model.getCurrentDriver();e.isOtherDriver()||e.isUploadedDriver()||this.model.validate({connectionUrl:this.model.get("connectionUrl")})},updateCustomAttributes:function(){var e=this,r=this.model.getCurrentDriver().getCustomAttributes();t.each(r,function(t){e.$("input[name='"+t+"']").val(e.model.get(t))});var i=this.model.pick(r);this.model.validate(i)},changeDriver:function(){this.renderDriverCustomAttributeFields(),this.changeUploadDriverButtonState()},changeUploadDriverButtonState:function(){var e=this.model.getCurrentDriver(),t=this.$("#driverUploadButton");if(e.isOtherDriver())l[""===this.model.get("driverClass")?"disable":"enable"](t[0]),t.find(".wrap").text(n["resource.dataSource.jdbc.upload.addDriverButton"]);else{l.enable(t[0]);var r=e.get("available")?n["resource.dataSource.jdbc.upload.editDriverButton"]:n["resource.dataSource.jdbc.upload.addDriverButton"];t.find(".wrap").text(r)}},uploadDriver:function(){this.model.drivers.driverUploadEnabled&&this.model.get("driverClass")&&(this.driverUploadDialog&&this.stopListening(this.driverUploadDialog),delete this.driverUploadDialog,this.initDriverUploadDialog(),this.driverUploadDialog.show())},initDriverUploadDialog:function(){var e=this;this.driverUploadDialog=new o({driverAvailable:this.model.getCurrentDriver().get("available"),driverClass:this.model.get("isOtherDriver")?this.model.get("driverClass"):this.model.getCurrentDriver().get("jdbcDriverClass")}),this.driverUploadDialog.on("driverUpload",function(r){e.model.drivers.markDriverAsAvailable(r.jdbcDriverClass),t.defer(t.bind(e.model.validate,e.model))})},render:function(){return this.$el.empty(),this.renderJdbcSpecificSection(),this.renderTimezoneSection(),this.renderTestConnectionSection(),this},templateData:function(){var e=r.prototype.templateData.apply(this,arguments);return t.extend(e,{drivers:this.model.drivers.toJSON(),otherDriverValue:a.OTHER_DRIVER,driverUploadEnabled:this.model.drivers.driverUploadEnabled}),e},renderJdbcSpecificSection:function(){this.$el.append(t.template(d,this.templateData())),this.renderDriverCustomAttributeFields(),this.changeUploadDriverButtonState()},renderDriverCustomAttributeFields:function(){var e=this,r="";if(this.model.get("isOtherDriver"))r+=t.template(s,{hint:n["resource.dataSource.jdbc.hint1"],label:n["resource.dataSource.jdbc.driver"],name:"driverClass",title:n["resource.analysisConnection.driver"],value:this.model.get("driverClass"),i18n:n});else{var i=this.model.getCurrentDriver().getCustomAttributes();t.each(i,function(i){r+=t.template(s,{hint:"",label:n["resource.dataSource.jdbc."+i],name:i,title:n["resource.dataSource.jdbc.requiredTitle"].replace("{0}",n["resource.dataSource.jdbc."+i].toLowerCase()),value:e.model.get(i),i18n:n})})}this.$("[name=jdbcSpecificFieldsContainer]").html(r)},remove:function(){this.driverUploadDialog&&this.driverUploadDialog.remove(),r.prototype.remove.apply(this,arguments)}})});