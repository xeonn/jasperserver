jaspersoft.components.NotificationView=function(i,t,e,o,n,s){var a={DEFAULT_TEMPLATE_ID:"componentsNotificationTemplate"};return s.NotificationViewTrait.extend({initialize:function(){e.bindAll(this),s.NotificationViewTrait.prototype.initialize.call(this,arguments);var i=n.createTemplate(s.NotificationView.DEFAULT_TEMPLATE_ID);if(!i)throw Error("Not found template by id '{0}'".replace("{0}",s.NotificationView.DEFAULT_TEMPLATE_ID));this.notificationTemplate=i},render:function(){var i=this.notificationTemplate();return this.$el=t(i),this.el=this.$el[0],this},showNotification:function(i,t,e){this.hideNotification(),this.hideTimer&&clearTimeout(this.hideTimer),this.$el.addClass(t?t:"success").find(".message").text(i),e&&(this.hideTimer=setTimeout(this.hideNotification,e))},hideNotification:function(){this.$el.removeClass("success").removeClass("error")}},a)}(JRS.Export,jQuery,_,Backbone,jaspersoft.components.templateEngine,jaspersoft.components);