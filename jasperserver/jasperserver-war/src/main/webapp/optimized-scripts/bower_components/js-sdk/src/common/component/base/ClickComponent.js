define(["require","common/component/base/AttachableComponent","jquery","underscore"],function(t){"use strict";var o=t("common/component/base/AttachableComponent"),n=t("jquery"),e=t("underscore");return o.extend({constructor:function(t){o.apply(this,arguments),e.bindAll(this,"_onElementClick","_onDocumentMousedown"),this.$attachTo.on("click",this._onElementClick),n("body").on("mousedown",this._onDocumentMousedown)},_onElementClick:function(){this.show()},_onDocumentMousedown:function(t){n.contains(this.$el[0],t.target)||this.$el.is(t.target)||n.contains(this.$attachTo[0],t.target)||this.$attachTo.is(t.target)||this.hide()},remove:function(){this.$attachTo.off("click",this._onElementClick),n("body").off("mousedown",this._onDocumentMousedown)}})});