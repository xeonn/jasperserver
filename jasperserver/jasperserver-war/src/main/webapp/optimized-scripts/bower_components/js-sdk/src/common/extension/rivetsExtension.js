define(["require","rivets.original","common/util/browserDetection","underscore","iota-observable","jquery","json3","common/component/colorPicker/SimpleAttachableColorPicker"],function(i){"use strict";function t(i){return s(i).is("select, :checkbox, :radio, :button, :file, :image, :submit")?"change":"keyup change"}function e(i){return i instanceof s?i:s(i)}var n=i("rivets.original"),r=i("common/util/browserDetection"),o=i("underscore"),a=i("iota-observable"),s=i("jquery"),c=i("json3"),l=i("common/component/colorPicker/SimpleAttachableColorPicker"),d=["true","false"];if(r.isIE8()){n.adapters["."]={subscribe:function(i,t,e){i.on(t,e)},unsubscribe:function(i,t,e){i.off(t,e)},read:function(i,t){return i.get(t)},publish:function(i,t,e){i.set(t,e)}};var u=n.bind;n.bind=function(i,t){a.makeObservable(t),u.apply(this,arguments)}}return n.configure({templateDelimiters:["[[","]]"]}),n.adapters[":"]={subscribe:function(i,t,e){return i.on("change:"+t,e)},unsubscribe:function(i,t,e){return i.off("change:"+t,e)},read:function(i,t){return i.get(t)},publish:function(i,t,e){var n={};return n[t]=e,i.set(n),i.validate&&i.validate(n),i}},n.binders["validation-error-class"]={publishes:!1,bind:function(i){var t=this.view.models[this.keypath.split(":")[0]],n=this.keypath.split(":")[1];this._onAttrValidated=function(t,n,r){e(i)[r?"addClass":"removeClass"]("error")},t.on("validate:"+n,this._onAttrValidated)},unbind:function(i){var t=this.view.models[this.keypath.split(":")[0]],e=this.keypath.split(":")[1];t.off("validate:"+e,this._onAttrValidated)},routine:function(i,t){}},n.binders["validation-error-text"]={publishes:!1,bind:function(i){var t=this.view.models[this.keypath.split(":")[0]],n=this.keypath.split(":")[1];this._onAttrValidated=function(t,n,r){e(i).text(r||"")},t.on("validate:"+n,this._onAttrValidated)},unbind:function(i){var t=this.view.models[this.keypath.split(":")[0]],e=this.keypath.split(":")[1];t.off("validate:"+e,this._onAttrValidated)},routine:function(i,t){}},n.binders.input={publishes:!0,bind:function(i){s(i).on(t(i),this.publish)},unbind:function(i){s(i).off(t(i),this.publish)},routine:function(i,t){n.binders.value.routine.call(this,i,t)}},n.binders.slide=function(i,t){return s(i)[t?"slideDown":"slideUp"]({complete:function(){!t&&s(i).hide()}})},n.binders.enabled=function(i,t){return t?s(i).removeAttr("disabled"):s(i).attr("disabled","disabled")},n.binders.disabled=function(i,t){n.binders.enabled(i,!t)},n.formatters.escapeCharacters={read:function(i){return s("<div/>").html(i).text()},publish:function(i){return s("<div/>").text(i).html()}},n.formatters.toInteger={publish:function(i){return isNaN(1*i)||""===i?i:1*i}},n.formatters.toBoolean={read:function(i){return i.toString()},publish:function(i){return-1!==o.indexOf(d,i)?c.parse(i):i}},n.formatters.prependText=function(i,t){return"'"===t.charAt(0)&&"'"===t.charAt(t.length-1)&&(t=t.slice(1,t.length-1)),t+" "+(o.isUndefined(i)?"":i)},n.binders.colorpicker={publishes:!0,bind:function(i){var t=e(i),n=!!t.data("showTransparentInput"),r=t.data("label");this.attachableColorPicker=new l(t,{top:5,left:5},{label:r,showTransparentInput:n}),this.callback=function(i){var t=this.observer;t.publish(i)},this.attachableColorPicker.on("color:selected",o.bind(this.callback,this))},unbind:function(i){this.attachableColorPicker.off("color:selected",o.bind(this.callback,this)),this.attachableColorPicker.remove()},routine:function(i,t){var n=e(i).find(".colorIndicator");this.attachableColorPicker.highlightColor(t),n.css("background-color",t)}},n.binders["radio-div"]={publishes:!0,bind:function(i){var t=e(i);this.callback=function(){var i=this.observer,e=t.data("value");i.publish(e)},t.on("click",o.bind(this.callback,this))},unbind:function(i){e(i).off("click",o.bind(this.callback,this))},routine:function(i,t){var n=e(i),r=n.siblings("div[rv-radio-div]");n.data("value")===t&&(n.addClass("checked"),n.children(".radioChild").addClass("checked"),r.removeClass("checked"),r.children(".radioChild").removeClass("checked"))}},n.binders["checkbox-div"]={publishes:!0,bind:function(i){var t=e(i);this.callback=function(){var i=this.observer,t=i.key.path,e=this.model;i.publish(!e.get(t))},t.on("click",o.bind(this.callback,this))},unbind:function(i){e(i).off("click",o.bind(this.callback,this))},routine:function(i,t){var n=e(i);t?(n.addClass("checked"),n.children(".checkboxChild").addClass("checked")):(n.removeClass("checked"),n.children(".checkboxChild").removeClass("checked"))}},n});