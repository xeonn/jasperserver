define(["require","./TreePlugin","underscore","jquery"],function(t){var e=t("./TreePlugin"),n=t("underscore"),i=(t("jquery"),n.memoize(function(t){var e=n.map(t.options,function(t){return t.model.get("action")});return e},function(t){return t.cid}));return e.extend({initialize:function(t){if(!t)throw new Error("Initialization error. Options required.");if(this.contextMenus=t.contextMenus,this.contextMenu=t.contextMenu,this.defaultSelectedItems=t.defaultSelectedItems||function(){},!this.contextMenus&&!this.contextMenu)throw new Error("contextMenu or contextMenus must be specified.");if(this.showContextMenuCondition=t.showContextMenuCondition,!this.showContextMenuCondition&&!this.contextMenu)throw new Error("contextMenu must be specified for default behaviour");!this.showContextMenuCondition&&(this.showContextMenuCondition=function(){return this.contextMenu}),this.model=t.model,e.prototype.initialize.apply(this,arguments)},itemAction:function(t){var e=t.get("action");this.treeItem.action=e},itemsRendered:function(t,e){var n=this;this.listenTo(e,"list:item:contextmenu",function(t,e){if(this.currentContextMenu=n.showContextMenuCondition(t),this.lastContextMenu&&this.lastContextMenu.hide(),this.lastContextMenu=this.currentContextMenu,this.currentContextMenu){this.currentContextMenu.treeItem=t;var o=this.currentContextMenu.treeItem.action;o||(o=n.defaultSelectedItems.call(this.currentContextMenu,t,i(this.currentContextMenu)))?this.currentContextMenu.resetSelection([o]):this.currentContextMenu.resetSelection(),this.currentContextMenu.show({left:e.pageX,top:e.pageY}),this.listenTo(this.currentContextMenu.collection,"select",function(t,e){this.itemAction.call(this.currentContextMenu,e)},this)}},this)},remove:function(){this.stopListening(),this.contextMenu&&this.contextMenu.remove()}})});