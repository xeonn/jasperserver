define(["require","./Menu","common/component/base/AttachableComponent","jquery","underscore"],function(t){"use strict";var e=t("./Menu"),n=t("common/component/base/AttachableComponent");t("jquery"),t("underscore");return e.extend(n.extend({constructor:function(t,o,r,s){this.padding=r||{top:0,left:0},n.call(this,o,this.padding),e.call(this,t,s)},show:function(){return n.prototype.show.apply(this,arguments),e.prototype.show.apply(this,arguments)}}).prototype)});