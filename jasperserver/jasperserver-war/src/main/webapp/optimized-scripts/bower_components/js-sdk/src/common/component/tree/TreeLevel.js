define(["require","underscore","common/component/panel/Panel","common/component/panel/trait/collapsiblePanelTrait","common/component/list/view/ListWithSelection","common/component/tree/plugin/TooltipPlugin","common/component/singleSelect/dataprovider/DataProviderNew"],function(t){function e(){this.$el.removeClass(this.loadingClass).addClass(this.openClass),a.invoke(this.plugins,"itemsRendered",this.list.model,this.list);var t=this.list.$("> .viewPortChunk > ul > li").addClass(this.cid).length,e=a.isFunction(this.levelHeight)?this.levelHeight():this.levelHeight;this.list.$el.css(e&&t*this.listItemHeight>e?{height:e+"px","overflow-y":"auto"}:{height:"auto",overflow:"auto"}),this.list._calcViewPortHeight(),this.trigger("ready",this)}function i(t,e){this.listRenderError=!0,this.trigger("listRenderError",t,e,this)}function s(t){var e=[];a.chain(t).keys(t).each(function(i){e.push(t[i])}),this.selection.multiple||l(this),this.trigger("selection:change",e,this,[])}function n(t){var e=[];a.chain(t).keys(t).each(function(i){e.push(t[i])}),this.selection.multiple||l(this),this.trigger("item:dblclick",e)}function o(t,e){for(var i=0;i<e.length;i++)if(e[i]===t)return!0}function r(t,e,i,s){i&&i.exclude&&a.isArray(i.exclude)&&o(t,i.exclude)||(void 0===s||s>0)&&a.chain(t.items).keys().each(function(n){i&&i.exclude&&a.isArray(i.exclude)&&o(t.items[n],i.exclude)||e.call(t.items[n],i),r(t.items[n],e,i,void 0===s?s:--s)})}function l(t,e){e=e&&{exclude:e.exclude},r(t,function(){this.list.clearSelection(),this.list.model.selection=[]},e)}var a=t("underscore"),h=t("common/component/panel/Panel"),c=t("common/component/panel/trait/collapsiblePanelTrait"),d=t("common/component/list/view/ListWithSelection"),u=t("common/component/tree/plugin/TooltipPlugin"),p=t("common/component/singleSelect/dataprovider/DataProviderNew");return h.extend({constructor:function(t){t||(t={}),a.extend(t,{traits:[c]}),h.prototype.constructor.call(this,t)},initialize:function(t){t||(t={}),this.id=t.id,this.owner=t.owner,this.parent=t.parent,this.itemsTemplate=t.itemsTemplate,this.listItemHeight=t.listItemHeight,this.levelHeight=t.levelHeight,this.lazyLoad=!!t.lazyLoad,this.selection=t.selection||{allowed:!1,multiple:!1},this.resource=t.resource,this.bufferSize=t.bufferSize,this.cache="undefined"!=typeof t.cache?t.cache:!1,this.items={},this.plugins=[];for(var e={el:this.el,model:this.model},i=0,s=t.plugins.length;s>i;i++){var n=t.plugins[i].constr;n===u&&this.parent||this.plugins.push(new n(a.extend({},e,t.plugins[i].options)))}this.dataLayer=this.owner.getDataLayer(this),a.invoke(this.plugins,"dataLayerObtained",this.dataLayer),h.prototype.initialize.apply(this,arguments)},render:function(){return this.list=new d({markerClass:"."+this.cid,eventListenerPattern:"."+this.cid+":not(.readonly) > p",el:this.$contentContainer,itemsTemplate:this.itemsTemplate,listItemHeight:this.listItemHeight,lazy:!0,selection:this.selection,bufferSize:this.bufferSize,getData:this._getDataProvider(this.cache)}),this.list.on("render:data",a.bind(e,this)),this.list.on("listRenderError",a.bind(i,this)),this.list.on("selection:change",a.bind(s,this)),this.list.on("item:dblclick",a.bind(n,this)),this.lazyLoad?this.on("open",this._onOpen,this):this.list.renderData(),this},_getDataProvider:function(t){var e=this,i=a.bind(function(t){return this.obtainData(t,e)},this.dataLayer),s={};return t&&("object"==typeof t&&(s=a.extend(s,t)),s.request=s.request||i,this.dataProvider=new p(s),i=this.dataProvider.getData),function(t){return t||(t={offset:0,limit:100}),t.id=e.id,i(a.extend({},e.owner.context,t),e)}},_onOpen:function(){this.$el.removeClass(this.openClass).addClass(this.loadingClass),this.list.renderData()},setElement:function(t){var e=h.prototype.setElement.apply(this,arguments);return this.list&&(this.list.setElement(this.$contentContainer),this.list.totalItems=-1),a.each(this.plugins,function(e){e.setElement(t)}),this.lazyLoad||this.list&&this.list.renderData(),this.collapsed||(this.open({silent:!0,depth:0}),this.lazyLoad&&this.list.renderData()),e},open:function(t){r(this,h.prototype.open,t,t?t.depth:0),h.prototype.open.call(this,t)},close:function(t){h.prototype.close.call(this,t),r(this,h.prototype.close,t,t?t.depth:void 0),l(this)},getLevel:function(t){return a(this.items).reduce(function(e,i){return e||(i.id===t?i:i.getLevel(t))},!1)},refresh:function(t){this.listRenderError||(this.lazyLoad&&!this.collapsed||!this.lazyLoad?(this.once("ready",a.bind(r,this,this,this.refresh,t,1)),this.list.model.fetch({top:0,bottom:this.list.model.bufferSize,force:!0})):(this.list.model.set("bufferStartIndex",void 0,{silent:!0}),this.list.model.set("bufferEndIndex",void 0,{silent:!0}),r(this,this.refresh,t)))},recalcConstraints:function(){this.list.resize()},fetchVisibleData:function(){this.list._fetchVisibleData()},resetSelection:function(t){l(this,t),t&&t.silent||this.trigger("selection:change",[])},hasItems:function(){return!!this.$("> .subcontainer > .viewPortChunk > ul > li").length},clearCache:function(){a.each(this.items,function(t){t.clearCache()}),this.dataProvider&&this.dataProvider.clear()},remove:function(){var t=this,e=-1;if(a.forEach(a.keys(this.items),function(e){t.items[e].remove()}),this.dataProvider&&this.dataProvider.clear(),this.list.remove(),this.parent){var i=this.parent.id,s=this.owner.getDataLayer(i);if(s.predefinedData&&s.predefinedData[i]&&a.isArray(s.predefinedData[i])){for(var n=0;n<s.predefinedData[i].length;n++)if(s.predefinedData[i][n].id===this.id){e=n;break}e>-1&&s.predefinedData[i].splice(e,1)}delete this.parent.items[this.id]}h.prototype.remove.apply(this,arguments)}})});