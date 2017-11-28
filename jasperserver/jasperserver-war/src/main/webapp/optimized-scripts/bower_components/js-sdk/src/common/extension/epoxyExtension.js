define(["require","backbone.epoxy.original","underscore"],function(t){var e=t("backbone.epoxy.original"),a=t("underscore");return e.binding.addHandler("validationErrorClass",{init:function(t,e,a,n){var i=t.data("modelAttr"),r=this.view.model;this._onAttrValidated=function(e,a,n){t[n?"addClass":"removeClass"]("error")},r.on("validate:"+i,this._onAttrValidated)},get:function(t,e,a){return t.val()},set:function(t,e){t.val(e)},clean:function(){var t=this.$el.data("modelAttr");this.view.model.off("validate:"+t,this._onAttrValidated)}}),e.binding.addHandler("validationErrorText",{init:function(t,e,a,n){var i=t.data("modelAttr"),r=this.view.model;this._onAttrValidated=function(e,a,n){t.text(n||"")},r.on("validate:"+i,this._onAttrValidated)},get:function(t,e,a){return t.val()},set:function(t,e){t.val(e)},clean:function(){var t=this.$el.data("modelAttr");this.view.model.off("validate:"+t,this._onAttrValidated)}}),e.binding.addFilter("escapeCharacters",{get:function(t){return a.escape(t)},set:function(t){return a.unescape(t)}}),e});