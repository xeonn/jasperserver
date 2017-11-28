define(["require","jquery","underscore","common/component/list/model/ListWithSelectionModel","common/component/list/view/ScalableList"],function(e){"use strict";var t=e("jquery"),i=e("underscore"),s=e("common/component/list/model/ListWithSelectionModel"),o=e("common/component/list/view/ScalableList"),l=1,n={LEFT:"left",RIGHT:"right"},h=o.extend({ListModel:s,initialize:function(e){return i.bindAll(this,"onGlobalMouseup","onGlobalMousemove","_autoScroll","fetch"),this.eventListenerPattern=e.eventListenerPattern||"li",this.markerClass=e.markerClass?e.markerClass:"",this.selectedClass=e.selectedClass?e.selectedClass:"selected",this._initSelection(e.selection),o.prototype.initialize.call(this,e)},delegateEvents:function(){var e=o.prototype.delegateEvents.apply(this,arguments);return this.$el.on("mousedown",this.eventListenerPattern,i.bind(this.onMousedown,this)).on("mousemove",this.eventListenerPattern,i.bind(this.onMousemove,this)).on("dblclick",this.eventListenerPattern,i.bind(this.onMousedblclick,this)).on("contextmenu",this.eventListenerPattern,i.bind(this.onItemEvent,this,this.$el)).on("mouseout",this.eventListenerPattern,i.bind(this.onItemEvent,this,this.$el)).on("mouseover",this.eventListenerPattern,i.bind(this.onItemEvent,this,this.$el)),e},undelegateEvents:function(){var e=o.prototype.undelegateEvents.apply(this,arguments);return this.$el.off("mousedown").off("mousemove").off("dblclick").off("contextmenu").off("mouseout").off("mouseover"),e},_initSelection:function(e){e=i.extend({allowed:{}},e);var t=e.allowed,s=i.isObject(t)?t.left:t;this.selection={allowed:{left:"undefined"!=typeof s?s:!0,right:"undefined"!=typeof t.right?t.right:!1},multiple:"undefined"!=typeof e.multiple?e.multiple:!0}},initListeners:function(){o.prototype.initListeners.call(this),this.listenTo(this.model,"selection:clear",this.clearSelection,this),this.listenTo(this.model,"selection:add",this.selectValue,this),this.listenTo(this.model,"selection:addRange",this.selectRange,this),this.listenTo(this.model,"selection:remove",this.deselectValue,this),t("body").on("mouseup",this.onGlobalMouseup).on("mousemove",this.onGlobalMousemove)},postProcessChunkModelItem:function(e,t){o.prototype.postProcessChunkModelItem.call(this,e,t),e.selected=this.model.selectionContains&&this.model.selectionContains(e.value,e.index)},onMousedblclick:function(e){if(!this.selection.multiple&&(this.selection.allowed.left||this.selection.allowed.right)&&!this.getDisabled()){var t=this._getDomItemData(e.currentTarget);this._singleSelect(e,t.value,t.index),this.selectionChanged&&(this._triggerSelectionChanged(),this._triggerDblclicked(),this.selectionChanged=!1)}},getItemByEvent:function(e){return this._getDomItemData(e.currentTarget).item},onItemEvent:function(e,t){var i=t.type,s=this.getItemByEvent(t);this.trigger("list:item:"+i,s,t)},onMousedown:function(e){if(!this.getDisabled()&&(this.selection.allowed.left&&1===e.which||this.selection.allowed.right&&3===e.which)){var t=1===e.which?n.LEFT:n.RIGHT;this.selection.multiple&&(this[t+"MouseButtonPressed"]=!0,this.mouseDownPos=this._getMousePos(e));var i=this._getDomItemData(e.currentTarget);this.selection.multiple?this._multiSelect(e,i.value,i.index):this._singleSelect(e,i.value,i.index)}},onMousemove:function(e){if(this._allowMouseMoveSelection()&&(this._stopAutoScroll(),this._mouseMoved(e,this.mouseDownPos))){this.mouseDownPos=this._getMousePos(e);var t=this._getDomItemData(e.currentTarget);this.model.selectionContains(t.value,t.index)||this.model.addRangeToSelection(t.value,t.index)}},onGlobalMouseup:function(e){var t=this;(this.leftMouseButtonPressed||this.rightMouseButtonPressed)&&(i.each(n,function(e){t[e+"MouseButtonPressed"]=!1}),delete this.mouseDownPos,this._stopAutoScroll(),this.selectionChanged&&(this._triggerSelectionChanged(),this.selectionChanged=!1))},onGlobalMousemove:function(e){this._allowMouseMoveSelection()&&(this.mousePosY=e.clientY,this.scrollInterval||(this.scrollInterval=setInterval(this._autoScroll,this.manualScrollInterval)))},clearSelection:function(){this.$el.find("li."+this.selectedClass+this.markerClass).removeClass(this.selectedClass),this.selectionChanged=!0},selectValue:function(e){this.$el.find("li"+this.markerClass+"[data-index='"+e.index+"']:not(."+this.selectedClass+")").addClass(this.selectedClass),this.selection.multiple?this.selectionChanged=!0:this._triggerSelectionChanged()},selectRange:function(e){var i=this,s=Math.max(this.model.get("bufferStartIndex"),e.start),o=Math.min(this.model.get("bufferEndIndex"),e.end);this.$el.find("li"+this.markerClass+":not(."+this.selectedClass+")").each(function(){var e=t(this),l=parseInt(e.attr("data-index"),10);if(l>=s&&o>=l){var n=i.model.get("items")[l-i.model.get("bufferStartIndex")];i.model.selectionContains(n.value,l)&&e.addClass(i.selectedClass)}}),this.selectionChanged=!0},deselectValue:function(e){this.$el.find("li[data-index='"+e.index+"']."+this.selectedClass+this.markerClass).removeClass(this.selectedClass),this.selection.multiple?this.selectionChanged=!0:this._triggerSelectionChanged()},_autoScroll:function(){var e,t=this.$el.offset().top,i=this.$el.scrollTop(),s="undefined"!=typeof this.scrollTop?this.scrollTop:i,o=!1;this.mousePosY<t?(e=s-3*this.itemHeight,o=i>e):this.mousePosY>t+this.viewPortHeight&&(e=s+3*this.itemHeight,o=e>i),o&&(this.$el.scrollTop(e),this.scrollTop=this.$el.scrollTop(),this._fetchVisibleData()),this._selectRangeOnAutoScroll(s,e)},_selectRangeOnAutoScroll:function(e,t){var i;if(t>e?i=this._getVisibleItems().bottom:e>t&&(i=this._getVisibleItems().top),i){var s=this.model.get("items")[i-this.model.get("bufferStartIndex")];s&&(this.model.selectionContains(s.value,i)||this.model.addRangeToSelection(s.value,i))}},_stopAutoScroll:function(){this.scrollInterval&&(clearInterval(this.scrollInterval),this.scrollInterval=void 0,this.scrollTop=void 0)},_multiSelect:function(e,t,i){e.shiftKey?this.model.addRangeToSelection(t,i):e.ctrlKey||e.metaKey?this._singleSelect(e,t,i):this.model.toggleSelection(t,i)},_singleSelect:function(e,t,i){this.model.clearSelection().addValueToSelection(t,i)},_triggerSelectionChanged:function(){this.trigger("selection:change",this.getValue())},_triggerDblclicked:function(){this.trigger("item:dblclick",this.getValue())},_getDomItemData:function(e){for(var i=t(e),s=i.attr("data-index");void 0===s;)i=i.parent(),s=i.attr("data-index");s=parseInt(s,10);var e=this.model.get("items")[s-this.model.get("bufferStartIndex")];return{item:e,value:e.value,index:s}},_mouseMoved:function(e,t){return Math.abs(t.x-e.clientX)+Math.abs(t.y-e.clientY)>=l},_getMousePos:function(e){return{x:e.clientX,y:e.clientY}},_allowMouseMoveSelection:function(){return(this.selection.allowed.left&&this.leftMouseButtonPressed||this.selection.allowed.right&&this.rightMouseButtonPressed)&&this.selection.multiple&&!this.getDisabled()},getValue:function(){return this.model.getSelection()},setValue:function(e,t){return t=t||{},t&&t.silent||t.modelOptions&&t.modelOptions.silent||this.model.once("selection:change",this._triggerSelectionChanged,this),t=t.modelOptions,this.model.select(e,t),this},selectAll:function(e){return e&&e.silent||this.model.once("selection:change",this._triggerSelectionChanged,this),this.model.selectAll(),this},selectNone:function(e){return this.setValue({},e)},invertSelection:function(e){return e&&e.silent||this.model.once("selection:change",this._triggerSelectionChanged,this),this.model.invertSelection(),this},setDisabled:function(e){return this.disabled=e,this.disabled?this.$el.addClass("disabled"):this.$el.removeClass("disabled"),this},getDisabled:function(){return this.disabled},remove:function(){return t("body").off("mouseup",this.onGlobalMouseup).off("mousemove",this.onGlobalMousemove),o.prototype.remove.call(this)}});return h});