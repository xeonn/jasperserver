define(["require","./TreePlugin","underscore","common/component/tooltip/Tooltip"],function(t){"use strict";var i=t("./TreePlugin"),o=t("underscore"),e=t("common/component/tooltip/Tooltip");return i.extend({initialize:function(t){this.tooltip=e.attachTo(t.attachTo,o.omit(t,"el")),this.listensToList=!1,i.prototype.initialize.apply(this,arguments)},itemsRendered:function(t,i){var o=this;this.listensToList||(this.listensToList=!0,this.listenTo(i,"list:item:mouseover",function(t){o.tooltip.show(t)}),this.listenTo(i,"list:item:mouseout",function(){o.tooltip.hide()}))},remove:function(){e.detachFrom(this.$el),this.tooltip.remove(),i.prototype.remove.apply(this,arguments)}})});