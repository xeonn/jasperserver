define(["require","css!lists","jquery","underscore","backbone","./TreeLevel","./TreeDataLayer","text!./template/treeLevelListTemplate.htm","text!./template/treeLevelTemplate.htm"],function(e){"use strict";function t(e){var i=e.list.model.get("items"),n=this;i=s.where(i,{_node:!0}),e.$(".node").each(function(r,l){var a=i[r].id;e.items[a]?e.items[a].setElement(l):(e.items[a]=new o(s.extend({},n._options,{el:l,item:i[r],parent:e})),n.listenTo(e.items[a],"listRenderError",s.bind(function(e,t,i){this.trigger("levelRenderError",e,t,i)},n)),e.listenTo(e.items[a],"ready",s.bind(t,n)),e.listenTo(e.items[a],"selection:change",function(t,i,s){i&&s.push(i),e.list.model.clearSelection(),e.trigger("selection:change",t,e,s)}),e.listenTo(e.items[a],"item:dblclick",function(t){e.list.model.clearSelection(),e.trigger("item:dblclick",t)}),e.items[a].render())}).length&&e.$(".j-view-port-chunk").css({height:"auto"})}e("css!lists");var i=e("jquery"),s=e("underscore"),n=e("backbone"),o=e("./TreeLevel"),r=e("./TreeDataLayer"),l=e("text!./template/treeLevelListTemplate.htm"),a=e("text!./template/treeLevelTemplate.htm"),h={ADD:"add",UPDATE:"update"},d=n.View.extend({template:s.template(a),_plugins:[],el:function(){return this.template()},constructor:function(e){e||(e={}),this.defaultDataLayer=new r(e),this.customDataLayers=e.customDataLayers,e.type?(this._type=e.type,e.type=void 0):this._type="tree",e.collapserSelector||(e.collapserSelector="b.icon:eq(0)"),e.predefinedData?(this.defaultDataLayer.predefinedData=e.predefinedData,e.predefinedData=void 0):this.defaultDataLayer.predefinedData={},e.rootless&&(this.rootless=e.rootless,e.rootless=void 0),e.additionalCssClasses&&(this.additionalCssClasses=e.additionalCssClasses,e.additionalCssClasses=void 0),e.getDataLayer&&(this.getDataLayer=e.getDataLayer,e.getDataLayer=void 0),this._options=s.extend({itemsTemplate:l,plugins:this._plugins,owner:this},e),this.context=e.context||{};for(var t=0,i=this._plugins.length;i>t;t++)this._plugins[t].processors&&(this.processors=this.processors.concat(this._plugins[t].processors));n.View.apply(this,arguments)},initialize:function(e){this.rootLevel=new o(s.extend({},this._options,{levelHeight:e.rootLevelHeight},{el:this.$("li")[0]},{item:{id:"/",value:{id:"/",resourceType:"folder"}}})),this.rootless&&this.$el.find("ul:first").addClass("hideRoot"),this.additionalCssClasses&&this.$el.find("ul:first").addClass(this.additionalCssClasses),this.listenTo(this.rootLevel,"ready",s.bind(t,this)),this.listenTo(this.rootLevel,"selection:change",s.bind(function(e,t,i){t&&i&&s.isArray(i)&&(this.rootLevel.selection.multiple||this.rootLevel.resetSelection({silent:!0,exclude:[i[0]]})),this.trigger("selection:change",e)},this)),this.listenTo(this.rootLevel,"item:dblclick",s.bind(function(e){this.trigger("item:dblclick",e)},this));for(var i=0,n=this._plugins.length;n>i;i++)this._plugins[i].constr.treeInitialized.call(this,this._plugins[i].options);this.rootLevel.render(),e.collapsed&&e.lazyLoad&&this.rootless&&this.expand("/")},remove:function(){for(var e=0,t=this._plugins.length;t>e;e++)this._plugins[e].constr.treeRemoved.call(this);this.rootLevel.remove(),n.View.prototype.remove.apply(this,arguments)},expand:function(e,t){var i=this.getLevel(e);return i&&i.open(t),this},collapse:function(e,t){var i=this.getLevel(e);return i&&i.close(t),this},_getNodeById:function(e){var t,i,n=this.getLevel(e);if(n)return n.parent?(t=n.parent.list,{node:n.item,nodeElement:n.$el,itsList:t}):null;var o=!1;if("list"===this._type)o=this.rootLevel;else if("tree"===this._type){var r=e.split("/");if(r=r.slice(0,r.length-1).join("/"),""===r&&(r="@fakeRoot"),o=this.getLevel(r),!o)return null}return o?(t=o.list,i=s.find(t.model.get("items"),function(t){return t.id===e}),i?{node:i,nodeElement:t.$el.find("li.leaf.selected"),itsList:t}:null):null},select:function(e){var t=this._getNodeById(e);t&&(t.itsList.model.clearSelection().addValueToSelection(t.node.value,t.node.index),this.trigger("selection:change",t.itsList.getValue()))},deselect:function(e){},addItem:function(e,t){var i=this.getLevel(e),s=h.ADD;return i&&this._getNodeItems(e,i,t,s),this},updateItem:function(e,t){var i=t?this.getLevel(t):this.rootLevel;return i&&this._getNodeItems(i.id,i,e),this},resetSelection:function(e){return this.rootLevel.list.clearSelection(),this.rootLevel.list.model.selection=[],this.rootLevel.resetSelection(e),this},refresh:function(e){e&&(this.context=e),this.rootLevel.refresh()},recalcConstraints:function(){this.rootLevel.recalcConstraints()},fetchVisibleData:function(){this.rootLevel.fetchVisibleData()},clearCache:function(){this.rootLevel.clearCache()},getLevel:function(e){return this.rootLevel.id===e?this.rootLevel:this.rootLevel.getLevel(e)},getDataLayer:function(e){return this.customDataLayers&&this.customDataLayers[e.id]?this.customDataLayers[e.id]:this.defaultDataLayer},_addItem:function(e,t,i){return!s.findWhere(e,{id:i.id})&&e.push(i),e},_updateItem:function(e,t){return s.each(e,function(e){e.id===t.id&&s.extend(e,t)},this),e},_getNodeItems:function(e,t,i,n){var o,r=this.getDataLayer(t),l=t.list.model.get("items")?t.list.model.get("items"):[];r.predefinedData=r.predefinedData||{},o=n===h.ADD?this._addItem(l,e,i):this._updateItem(l,i),r.predefinedData[e]=s.sortBy(o,"label"),t.list.lazy=!1,t.list.fetch()},_selectTreeNode:function(e,t){if(s.isString(e)&&""!==e){var n=s.bind(function(){var o=s.bind(function(){var i=e;if("/"===i&&(i="@fakeRoot"),this.select(i),"list"===this._type)this.rootLevel.on("ready",n);else if("tree"===this._type&&t&&t.length){var s=this.$el,o=this._getNodeById(i);if(!o)return;var r=o.nodeElement;if(!r)return;var l=r.offset().top-s.offset().top-t.height()/2+r.height()/2;t.scrollTop(l)}},this);if("/"===e||"/public"===e||"list"===this._type)o();else{var r=e.replace(/\/$/,""),l=r.substr(0,r.lastIndexOf("/"));l=l||"/";var a=new i.Deferred;this._openPath(l,a,0),a.done(o)}},this);this.rootLevel.isReady()?n():this.rootLevel.on("ready",n)}},_openPath:function(e,t,i){if(!e)return t.resolve();var n,o,r,l=this;return"/"===e?o=["/"]:(o=e.split("/"),o[0]="/"),"/"===o[0]&&"public"===o[1]&&(o=s.union(["/public"],s.rest(o,2))),i=i||0,i===o.length?t.resolve():(n=s.first(o,i+1).join("/"),"/"===n&&(n="@fakeRoot"),n=n.replace(/\/\//g,"/"),r=this.getLevel(n),void(r&&(r.collapsed?(r.once("ready",function(){l._openPath(e,t,i+1)}),r.open()):this._openPath(e,t,i+1))))}},{instance:function(e){return new this(e)}});return{use:function(e,t){return function(e){return{use:function(t,i){return e.prototype._plugins.push({constr:t,options:i}),this},create:function(){return e}}}(d.extend({_plugins:[{constr:e,options:t}]}))},create:function(){return d}}});