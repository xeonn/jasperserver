var baseList={isResponsive:function(t){return $(t.up(0)).hasClassName(layoutModule.RESPONSIVE_CLASS)},isCollapsible:function(t){return $(t.up(0)).hasClassName(layoutModule.COLLAPSIBLE_CLASS)},selectItem:function(t){if(this._allowSelections){if(this.isItemDisabled(t))return;$(t).addClassName(layoutModule.SELECTED_CLASS)}},deselectItem:function(t){$(t).removeClassName(layoutModule.SELECTED_CLASS)},isItemSelected:function(t){return $(t).hasClassName(layoutModule.SELECTED_CLASS)},disableItem:function(t){buttonManager.disable(t)},enableItem:function(t){buttonManager.enable(t)},isItemDisabled:function(t){buttonManager.isDisabled(t)},openItem:function(t){$(t).removeClassName(layoutModule.CLOSED_CLASS).addClassName(layoutModule.OPEN_CLASS).isOpen=!0},isItemOpen:function(t){return $(t).hasClassName(layoutModule.OPEN_CLASS)||$(t).isOpen&&!$(t).hasClassName(layoutModule.CLOSED_CLASS)},closeItem:function(t){$(t).removeClassName(layoutModule.OPEN_CLASS).addClassName(layoutModule.CLOSED_CLASS).isOpen=!1}},dynamicList={lists:{},activeListId:null,_templateHash:{},messages:{listNItemsSelected:"#{count} items selected"},getDynamicListForElement:function(t){var e=$(t);return e.match("ul,ol")||(e=e.up("ul,ol"),0!==e.length)?dynamicList.lists[e.id]:null}};dynamicList.ListItem=function(t){this._itemId=void 0,this._list=void 0,this.first=!1,this.last=!1,t&&(this._value=t.value?t.value:{},this._label=t.label?t.label:"",this._subList=t.subList,this._cssClassName="cssClassName"in t?t.cssClassName:void 0,this._templateDomId="templateDomId"in t?t.templateDomId:void 0,this._respondOnItemEvents=Object.isUndefined(t.respondOnItemEvents)?!0:t.respondOnItemEvents,this._excludeFromEventHandling="excludeFromEventHandling"in t?t.excludeFromEventHandling:void 0,this._excludeFromSelectionTriggers="excludeFromSelectionTriggers"in t?t.excludeFromSelectionTriggers:void 0)},dynamicList.ListItem.addVar("DEFAULT_TEMPLATE_DOM_ID","dynamicListItemTemplate"),dynamicList.ListItem.addVar("DEFAULT_ITEM_ID_PREFIX","item"),dynamicList.ListItem.addVar("DEFAULT_SUB_LIST_ID_SUFFIX","SubList"),dynamicList.ListItem.addMethod("getId",function(){return this._itemId}),dynamicList.ListItem.addMethod("setList",function(t){this._list=t,this.getList()&&(this._itemId=this.getList().getNextItemId())}),dynamicList.ListItem.addMethod("unsetList",function(){this._list=null,this._itemId=void 0}),dynamicList.ListItem.addMethod("getList",function(){return this._list}),dynamicList.ListItem.addMethod("setValue",function(t){return this._value=t}),dynamicList.ListItem.addMethod("getValue",function(){return this._value}),dynamicList.ListItem.addMethod("setLabel",function(t){return this._label=t}),dynamicList.ListItem.addMethod("getLabel",function(){return this._label}),dynamicList.ListItem.addMethod("setCssClassName",function(t){this._cssClassName=t}).addMethod("getCssClassName",function(){return this._cssClassName}),dynamicList.ListItem.addMethod("setTemplateDomId",function(t){this._templateDomId=t}),dynamicList.ListItem.addMethod("getTemplateDomId",function(){return this._templateDomId}),dynamicList.ListItem.addMethod("show",function(t){if(t){this._element=this.processTemplate(this._getTemplate()),this._getElement().setAttribute("id",this._generateId()),this._getElement().setAttribute("tabindex",-1),this.first&&this.getList().tabindex&&this._getElement().writeAttribute("tabindex",this.getList().tabindex),this._getElement().listItem=this,this.refreshStyle();var e=t.childElements(),s=this.index(),i=s-1;i>-1&&i<e.length?this._getElement().insert({after:e[i]}):$(t).insert(this._getElement())}}),dynamicList.ListItem.addMethod("refresh",function(){var t=!1;this._getElement()&&(this.getList()?(document.activeElement===this._getElement()&&(t=!0),this._element=this.processTemplate(this._getElement()),this.refreshStyle(),t&&jQuery(this._getElement()).focus()):(this._getElement().remove(),this._element=null))}),dynamicList.ListItem.addMethod("refreshStyle",function(){var t=this._getElement();t&&(t.templateClassName&&(t.className=t.templateClassName),this.first&&t.addClassName(layoutModule.FIRST_CLASS),this.last&&t.addClassName(layoutModule.LAST_CLASS),this.isSelected()&&t.addClassName(layoutModule.SELECTED_CLASS),this.isDisabled()&&t.addClassName(layoutModule.DISABLED_CLASS),this.getCssClassName()&&t.addClassName(this.getCssClassName()),this.isComposite||this===this.getList().cursor&&t.addClassName("cursor"))}),dynamicList.ListItem.addMethod("isRendered",function(){return isNotNullORUndefined(this._getElement())}),dynamicList.ListItem.addMethod("disable",function(){baseList.disableItem(this._getElement())}).addMethod("enable",function(){baseList.enableItem(this._getElement())}).addMethod("isDisabled",function(){return baseList.isItemDisabled(this._getElement())}),dynamicList.ListItem.addMethod("processTemplate",function(t){var e=t.childElements()[0];e.cleanWhitespace();var s=e.childElements().length;return s==e.childNodes.length?e.insert(xssUtil.escape(this.getLabel())):e.childNodes[s].data=this.getLabel(),t}),dynamicList.ListItem.addMethod("focus",function(){this._getElement().focus()}),dynamicList.ListItem.addMethod("remove",function(){this.getList().removeItems([this])}),dynamicList.ListItem.addMethod("isSelected",function(){return this.getList().isItemSelected(this)}),dynamicList.ListItem.addMethod("select",function(){this.getList().selectItem(this,!0)}),dynamicList.ListItem.addMethod("deselect",function(){this.getList().deselectItem(this)}),dynamicList.ListItem.addMethod("index",function(){this.getList().getItems().indexOf(this)}),dynamicList.ListItem.addMethod("_getElement",function(){if(!this._element){var t=$(this._generateId());this._element=Object.isElement(t)?t:void 0}return this._element}),dynamicList.ListItem.addMethod("_getTemplate",function(){var t=this._templateDomId;dynamicList._templateHash[t]||(dynamicList._templateHash[t]=t);var e=$(dynamicList._templateHash[t]).cloneNode(!0);return e.templateId=t,e.templateClassName=e.className,e}),dynamicList.ListItem.addMethod("_generateId",function(){return this.getList()&&this.getList().getId()?this.getList().getId()+"_"+this.DEFAULT_ITEM_ID_PREFIX+this.getId():null}),dynamicList.ListItem.addMethod("_isElementInExcluded",function(t,e){var s=t.element();return this._excludeFromEventHandling&&null!=matchAny(s,this._excludeFromEventHandling)}),dynamicList.ListItem.addMethod("_isExcludedFromSelectionTriggers",function(t){var e=t.element();return this._excludeFromSelectionTriggers&&null!=matchAny(e,this._excludeFromSelectionTriggers)}),dynamicList.CompositeItem=function(t){dynamicList.ListItem.call(this,t),this.isComposite=!0,this._items=t.items,this._openUp=t.openUp,this._subList=null,this._subListOptions=t.listOptions?t.listOptions:{},this._listTagName="ul",this.OPEN_HANDLER_PATTERN=t.openHandlerPattern?t.openHandlerPattern:this.OPEN_HANDLER_PATTERN,this.CLOSE_HANDLER_PATTERN=t.closeHandlerPattern?t.closeHandlerPattern:this.CLOSE_HANDLER_PATTERN},dynamicList.CompositeItem.prototype=deepClone(dynamicList.ListItem.prototype),dynamicList.CompositeItem.addVar("OPEN_HANDLER_PATTERN","[openHandler=openHandler]"),dynamicList.CompositeItem.addVar("CLOSE_HANDLER_PATTERN","[closeHandler=closeHandler]"),dynamicList.CompositeItem.addMethod("getItems",function(){return this._items}),dynamicList.CompositeItem.addMethod("setItems",function(t){this._items=t}),dynamicList.CompositeItem.addMethod("addItem",function(t){this._items.push(t)}),dynamicList.CompositeItem.addMethod("removeItems",function(t){this._items=this._items.reject(function(e){return t.include(e)}),this._subList.removeItems(t)}),dynamicList.CompositeItem.addMethod("show",function(t){this._listTagName=t.tagName,dynamicList.ListItem.prototype.show.call(this,t),baseList.closeItem(this._getElement()),this._items&&this._showSubList()}),dynamicList.CompositeItem.addMethod("_showSubList",function(){var t=this._getSubListId(),e=new Element(this._listTagName,{id:t});this._getElement().insert(this._openUp?{top:e}:{bottom:e});var s=this._subListOptions;if(this._subList=new dynamicList.List(t,{allowSelections:"allowSelections"in s?s.allowSelections:this.getList()._allowSelections,responsive:"responsive"in s?s.responsive:this.getList()._responsive,selectionDefaultsToCursor:"selectionDefaultsToCursor"in s?s.selectionDefaultsToCursor:this.getList()._selectionDefaultsToCursor,collapsible:"collapsible"in s?s.collapsible:this.getList()._collapsible,multiSelect:"multiSelect"in s?s.multiSelect:this.getList()._multiSelect,cssClassName:"cssClassName"in s?s.cssClassName:this.getList()._cssClassName,listTemplateDomId:"listTemplateDomId"in s?s.listTemplateDomId:this.getList()._listTemplateDomId,itemTemplateDomId:"itemTemplateDomId"in s?s.itemTemplateDomId:this.getList()._itemTemplateDomId,itemCssClassName:"itemCssClassName"in s?s.itemCssClassName:this.getList()._itemCssClassName,comparator:"comparator"in s?s.comparator:this.getList()._comparator,items:this._items}),this._subList._initEvents=function(){},this._subList.show(),this._subList._parentList=this.getList(),this._subList.getItems().each(function(t){t.parentItem=this}.bind(this)),this===this.getList().cursor){var i=this.getFirstChild();if(i){if(this.deselect(),i.getList().setCursor(i),this._getElement()){var n=jQuery(this._getElement());n.removeClass("cursor"),n.addClass("supercursor")}i._getElement()&&window.setTimeout(function(){jQuery(i._getElement()).focus()},0)}}}),dynamicList.CompositeItem.addMethod("refresh",function(){dynamicList.ListItem.prototype.refresh.call(this),this._items&&(this._subList?this._subList.refresh():this._showSubList())}),dynamicList.CompositeItem.addMethod("getFirstChild",function(){return this._subList.getItems().length<1?null:this._subList.getItems()[0]}),dynamicList.CompositeItem.addMethod("refreshStyle",function(){dynamicList.ListItem.prototype.refreshStyle.call(this),this._getElement()&&(baseList.isItemOpen(this._getElement())?(baseList.openItem(this._getElement()),this===this.getList().cursor&&(this._subList&&this._subList.cursor?this._getElement().addClassName("supercursor"):this._getElement().addClassName("cursor"))):(baseList.closeItem(this._getElement()),this===this.getList().cursor&&this._getElement().addClassName("cursor")),this._subList&&this._subList.refreshStyle())}),dynamicList.CompositeItem.addMethod("_isOpenHandler",function(t){return t.match(this.OPEN_HANDLER_PATTERN)}).addMethod("_isCloseHandler",function(t){return t.match(this.CLOSE_HANDLER_PATTERN)}),dynamicList.CompositeItem.addMethod("_getSubListId",function(){return this._generateId()+"_"+this.DEFAULT_SUB_LIST_ID_SUFFIX}),dynamicList.TemplatedListItem=function(t){t&&(this.tooltipText="tooltipText"in t?t.tooltipText:null),dynamicList.ListItem.call(this,t)};var tempFunc=function(){};tempFunc.prototype=dynamicList.ListItem.prototype,dynamicList.TemplatedListItem.prototype=new tempFunc,dynamicList.TemplatedListItem.prototype.constructor=dynamicList.TemplatedListItem,dynamicList.TemplatedListItem.prototype.processTemplate=function(t){var e=Mustache.to_html(t.innerHTML,this.getValue());return t.innerHTML=xssUtil.escape(e),null!=this.tooltipText&&new JSTooltip(t,{text:xssUtil.escape(this.tooltipText)}),t},dynamicList.UnderscoreTemplatedListItem=function(t){dynamicList.TemplatedListItem.call(this,t),t&&(this._template="template"in t?t.template:"")};var tempFunc=function(){};tempFunc.prototype=dynamicList.TemplatedListItem.prototype,dynamicList.UnderscoreTemplatedListItem.prototype=new tempFunc,dynamicList.UnderscoreTemplatedListItem.prototype.constructor=dynamicList.UnderscoreTemplatedListItem,dynamicList.UnderscoreTemplatedListItem.prototype._getTemplate=function(){return this._template},dynamicList.UnderscoreTemplatedListItem.prototype.processTemplate=function(){var t=jQuery(_.template(this._template,xssUtil.escape(this.getValue())))[0];return t.templateClassName=t.className,null!=this.tooltipText&&new JSTooltip(t,{text:xssUtil.escape(this.tooltipText)}),t},dynamicList.List=function(t,e){this._id=t,this._items=[],this._selectedItems=[],this._lastSelectedItem=null,this.cursor=null,this._nextId=1,this.draggables=[],this._parentList=null,e&&(this._selectionDefaultsToCursor="selectionDefaultsToCursor"in e?e.selectionDefaultsToCursor:!0,this._allowSelections="allowSelections"in e?e.allowSelections:!0,this._cssClassName="cssClassName"in e?e.cssClassName:"",this._excludeFromEventHandling="excludeFromEventHandling"in e?e.excludeFromEventHandling:!1,this._excludeFromSelectionTriggers="excludeFromSelectionTriggers"in e?e.excludeFromSelectionTriggers:!1,this._multiSelect="multiSelect"in e?e.multiSelect:!1,this._selectOnMousedown="selectOnMousedown"in e?e.selectOnMousedown:!0,this._setCursorOnMousedown="setCursorOnMousedown"in e?e.setCursorOnMousedown:!0,this._listTemplateDomId=e.listTemplateDomId,this._itemTemplateDomId=e.itemTemplateDomId,this._itemCssClassName=e.itemCssClassName,this._comparator=e.comparator,this.dragPattern=e.dragPattern,this.scroll=e.scroll,this.setItems(e.items)),this._createFromTemplate(),this._registerCustomScroll(),dynamicList.activeListId=this.getId(),this._msgNItemsSelected=new Template(dynamicList.messages.listNItemsSelected),dynamicList.lists[this._id]=this},dynamicList.List.addVar("Event",{ITEM_SELECTED:"item:selected",ITEM_UNSELECTED:"item:unselected",ITEM_MOUSEUP:"item:mouseup",ITEM_MOUSEDOWN:"item:mousedown",ITEM_CLICK:"item:click",ITEM_DBLCLICK:"item:dblclick",ITEM_OPEN:"item:open",ITEM_CLOSED:"item:closed",ITEM_CONTEXTMENU:"item:contextmenu",ITEM_BEFORE_SELECT_OR_UNSELECT:"item:beforeSelectOrUnselect"}),dynamicList.List.addVar("DND_WRAPPER_TEMPLATE","column_two"),dynamicList.List.addVar("DND_ITEM_TEMPLATE","column_two:resourceName"),dynamicList.List.addMethod("getNextItemId",function(){return this._nextId++}),dynamicList.List.addMethod("getId",function(){return this._id}),dynamicList.List.addMethod("getItems",function(){return this._items}),dynamicList.List.addMethod("setItems",function(t){if(t){var e=this.cursor,s=this._getElement(),i=!1;s&&(s===document.activeElement||jQuery.contains(s,document.activeElement))&&(i=!0),this._items=[],this.resetSelected(),this.addItems(t);var n;e&&(n=this.getCursor()),i&&(n&&n._getElement()?jQuery(n._getElement()).focus():s&&jQuery(s).focus())}}),dynamicList.List.addMethod("addItems",function(t){t&&(t.compact().each(function(t){this._prepareListItem(t),this._items.push(t)}.bind(this)),this._comparator&&(this._items=this._items.sort(this._comparator)))}),dynamicList.List.addMethod("insertItems",function(t,e){e&&(e=e.compact(),e.each(function(t){this._prepareListItem(t)}.bind(this)),this._items.splice.apply(this._items,[t,0].concat(e)),this._comparator&&(this._items=this._items.sort(this._comparator)))}),dynamicList.List.addMethod("_prepareListItem",function(t){t&&(t.setList(this),this._itemTemplateDomId&&!t.getTemplateDomId()&&t.setTemplateDomId(this._itemTemplateDomId),this._itemCssClassName&&!t.getCssClassName()&&t.setCssClassName(this._itemCssClassName),this._excludeFromEventHandling&&!t._excludeFromEventHandling&&(t._excludeFromEventHandling=this._excludeFromEventHandling),this._excludeFromSelectionTriggers&&!t._excludeFromSelectionTriggers&&(t._excludeFromSelectionTriggers=this._excludeFromSelectionTriggers))}),dynamicList.List.addMethod("removeItems",function(t){if(t&&isArray(t)){var e=this.getCursor(),s=!1;t.each(function(t){this._removeItemFromSelected(t),e===t&&(e=this.getNextItem(t),e||(e=this.getPreviousItem(t)))}.bind(this)),this._items=this._items.reject(function(e){return t.include(e)}),t.each(function(t){t.unsetList(),t.refresh()}),e!==this.getCursor()&&this.setCursor(e),s&&(this.getCursor()&&this.getCursor()._getElement()?jQuery(this.getCursor()._getElement().focus()):this._getElement()&&jQuery(this._getElement().focus()))}}),dynamicList.List.addMethod("sort",function(t){t&&(this._comparator=t),this._comparator&&this.getItems().sort(this._comparator)}),dynamicList.List.addMethod("setCursor",function(t){var e=!1;document.activeElement&&jQuery.contains(this._getElement(),document.activeElement)&&(e=!0),this.cursor&&this.cursor.getList()&&this.cursor._getElement()&&jQuery(this.cursor._getElement()).removeClass("cursor"),this.cursor=t,t&&t.getList()&&t._getElement()&&(this.scrollUpTo(t),this._allowSelections&&this._selectionDefaultsToCursor&&this.getSelectedItems().length<1&&this.selectItem(t),e&&jQuery(this.cursor._getElement()).focus(),jQuery(this.cursor._getElement()).addClass("cursor"))}),dynamicList.List.addMethod("getCursor",function(){return this.cursor&&this.cursor.getList()&&this.cursor._getElement()&&jQuery(this.cursor._getElement()).closest("BODY").length>0?this.cursor:this._selectedItems.length>0&&this._selectedItems[this._selectedItems.length-1]._getElement()&&(this.setCursor(this._selectedItems[this._selectedItems.length-1]),jQuery(this.cursor._getElement()).closest("BODY").length>0)?this.cursor:this._items.length>0?(this.setCursor(this._items[0]),this.cursor):null}),dynamicList.List.addMethod("getCursorElement",function(){return this.getCursor()?this.getCursor()._getElement():null}),dynamicList.List.addMethod("getSelectedItems",function(){return this._selectedItems}),dynamicList.List.addMethod("isItemSelected",function(t){return this.getSelectedItems().include(t)}),dynamicList.List.addMethod("selectItem",function(t,e,s,i){var n=this.fire(this.Event.ITEM_BEFORE_SELECT_OR_UNSELECT,{item:t});if(!n.stopSelectOrUnselect&&(!(this._multiSelect&&this._selectedItems.length>1&&this.isItemSelected(t))||e||s||i)){var l=this.isItemSelected(t)&&i,o=!(this._multiSelect&&e||l),a=this.isItemSelected(t)&&e&&!l,d=this._multiSelect&&!a&&isNotNullORUndefined(this._lastSelectedItem)&&s,m=!a&&!d;if(o&&this.resetSelected(),a&&!o&&this._removeItemFromSelected(t),d){var r=this._items.indexOf(this._lastSelectedItem),h=this._items.indexOf(t),c=Math.min(r,h),u=Math.max(r,h);if(c>-1)for(var _=c;u>=_;_++)this._addItemToSelected(this._items[_],!1);else this._addItemToSelected(this._items[u],!1)}m&&this._addItemToSelected(t,!(s&&this._multiSelect)),this.setCursor(t)}}),dynamicList.List.addMethod("deselectItem",function(t){this._removeItemFromSelected(t),this.setCursor(t)}),dynamicList.List.addMethod("deselectOthers",function(t,e,s,i){var n=this.fire(this.Event.ITEM_BEFORE_SELECT_OR_UNSELECT,{item:t});if(!n.stopSelectOrUnselect&&this._multiSelect&&this._selectedItems.length>1&&this.isItemSelected(t)&&!(e||s||i)){var l=this._selectedItems.findAll(function(e){return e!=t});l.each(function(t){this._removeItemFromSelected(t)}.bind(this))}}),dynamicList.List.addMethod("resetSelected",function(t){var e=this._selectedItems;this._selectedItems=[],e.each(function(t){var e=t.getList();e&&e!==this&&e.resetSelected(!0),t.refreshStyle(),this.fire(this.Event.ITEM_UNSELECTED,{item:t})}.bind(this)),this._parentList&&!t&&this._parentList.resetSelected()}),dynamicList.List.addMethod("scrollDownTo",function(t){var e=this._getElement().parentNode;if(e&&t&&t._getElement()){var s=e.scrollTop,i=e.offsetHeight,n=t._getElement().offsetTop,l=t._getElement().offsetHeight,o=n+l-(s+i);o>0&&(e.scrollTop+=o)}}),dynamicList.List.addMethod("scrollUpTo",function(t){var e=this._getElement().parentNode;if(e&&t&&t._getElement()){var s=e.scrollTop,i=(e.offsetHeight,t._getElement().offsetTop),n=(t._getElement().offsetHeight,t._getElement().offsetHeight),l=s+n-i;l>0&&(e.scrollTop-=l)}}),dynamicList.List.addMethod("getNextItem",function(t){var e=this.getItems(),s=e.indexOf(t);return~s?this.getItems()[s+1]:null}),dynamicList.List.addMethod("getPreviousItem",function(t){var e=this.getItems(),s=e.indexOf(t);return~s?this.getItems()[s-1]:null}),dynamicList.List.addMethod("selectNext",function(t){var e=t.memo.targetEvent,s=this.getCursor(),i=null,n=null;if(s._subList&&s._getElement()&&jQuery(s._getElement()).hasClass(layoutModule.OPEN_CLASS)){if(!jQuery(s._getElement()).hasClass("supercursor")&&s._subList.getItems().length>0)return n=s._subList.getItems()[0],jQuery(s._getElement()).addClass("supercursor"),e.preventDefault(),e.stopPropagation(),s._subList.setCursor(n),void jQuery(s._subList.cursor._getElement()).focus();if(i=s._subList.getCursor(),n=i.getList().getNextItem(i))return i.getList().selectNext(t)}jQuery(s._getElement()).removeClass("supercursor"),s._subList&&(s._subList.cursor=null),n=s.getList().getNextItem(s),n&&(this._multiSelect&&isShiftHeld(e)?this.isItemSelected(n)?s.getList().deselectItem(s):this._addItemToSelected(n,!1):(this.resetSelected(),s.getList().selectItem(n)),this.setCursor(n)),e.preventDefault(),e.stopPropagation()}),dynamicList.List.addMethod("selectPrevious",function(t){var e=t.memo.targetEvent,s=this.getCursor(),i=null,n=null;if(jQuery(s._getElement()).hasClass("supercursor")){if(i=s._subList.getCursor(),n=i.getList().getPreviousItem(i))return i.getList().selectPrevious(t);s._subList.cursor&&(s._subList.cursor._element&&jQuery(s._subList.cursor._element).removeClass("cursor"),s._subList.cursor=null),isShiftHeld(e)&&this._multiSelect||s._subList.resetSelected(),jQuery(s._getElement()).removeClass("supercursor"),this.setCursor(s),jQuery(s._getElement()).focus()}else n=s.getList().getPreviousItem(s),n&&(this._multiSelect&&isShiftHeld(e)?this.isItemSelected(n)?s.getList().deselectItem(s):this._addItemToSelected(n,!1):(this.resetSelected(),s.getList().selectItem(n)),this.setCursor(n));e.preventDefault(),e.stopPropagation()}),dynamicList.List.addMethod("selectPageDown",function(t){var e=t.memo.targetEvent;e.preventDefault(),e.stopPropagation();var s=this.getCursor(),i=s._getElement().offsetHeight,n=this._getElement().parentNode,l=n.scrollTop+(n.offsetHeight-i);l>n.offsetHeight&&(l=n.offsetHeight),n.scrollTop=l;for(var o=s,a=null;o&&o._getElement().offsetTop+o._getElement().offsetHeight<n.scrollTop+n.offsetHeight;)a=o,isShiftHeld(e)&&(this.isItemSelected(a)?s.getList().deselectItem(s):this._addItemToSelected(a,!1)),o=a.getList().getNextItem(a);a&&(this._multiSelect&&isShiftHeld(e)||(this.resetSelected(),s.getList().selectItem(a)),this.setCursor(a))}),dynamicList.List.addMethod("selectPageUp",function(t){var e=t.memo.targetEvent;e.preventDefault(),e.stopPropagation();var s=this.getCursor(),i=s._getElement().offsetHeight,n=this._getElement().parentNode,l=n.scrollTop+(n.offsetHeight-i);l>n.offsetHeight&&(l=n.offsetHeight),n.scrollTop=l;for(var o=s,a=null;o&&o._getElement().offsetTop>n.scrollTop-n.offsetHeight;)a=o,this._multiSelect&&isShiftHeld(e)&&(this.isItemSelected(a)?s.getList().deselectItem(s):this._addItemToSelected(a,!1)),o=a.getList().getPreviousItem(a);a&&(this._multiSelect&&isShiftHeld(e)||(this.resetSelected(),s.getList().selectItem(a)),this.setCursor(a))}),dynamicList.List.addMethod("selectOutwards",function(t){if(!(this.getSelectedItems().length<1)){var e=this.getSelectedItems()[0],s=e._getElement(),i=!baseList.isItemOpen(s)&&e.parentItem;i?(e.deselect(),i.select(),i._getElement().focus()):(baseList.closeItem(s),this.fire(this.Event.ITEM_CLOSED,{targetEvent:t,item:e}))}}),dynamicList.List.addMethod("selectInwards",function(t){var e=this.cursor;if(!e){if(this.getSelectedItems().length<1)return;e=this.getSelectedItems()[0]}if(e.isComposite){var s=e._getElement();if(baseList.isItemOpen(s)){var i=e.getFirstChild();if(i){if(e.deselect(),i.getList().setCursor(i),e._getElement()){var n=jQuery(e._getElement());n.is(".cursor")&&(n.removeClass("cursor"),n.addClass("supercursor"))}i._getElement()&&jQuery(i._getElement()).focus()}}else baseList.openItem(s),this.fire(this.Event.ITEM_OPEN,{targetEvent:t,item:e})}}),dynamicList.List.addMethod("show",function(){dynamicList.activeListId=this.getId(),this._getElement().update();var t=this.getItems(),e=t.length;t.each(function(t,s){t.first=0===s,t.last=s===e-1,t.show(this._getElement())}.bind(this)),this.draggables=[],this.scroll&&this.scroll.refresh(),this._initEvents()}),dynamicList.List.addMethod("refresh",function(){if(this._getElement()){this.getCursor();var t,e=this._getElement().parentNode;t=e?e.scrollTop:0,this.refreshStyle();var s=this._getElement().childElements(),i=[],n=this.getCursor(),l=!1;n&&n.getList()&&n._getElement()&&(document.activeElement===n._getElement()||jQuery.contains(n._getElement(),document.activeElement))&&(l=!0),this.getItems().each(function(t,e){if(t.first=0===e,t.last=e===this.getItems().length-1,t.isRendered())if(t.index()!=s.indexOf(t._getElement())){var n=baseList.isItemOpen(t._getElement());t._getElement().remove(),t.show(this._getElement()),n&&baseList.openItem(t._getElement())}else t.refresh();else t.show(this._getElement());i.push(t._getElement())}.bind(this)),s.each(function(t){!i.include(t)&&t.parentNode&&t.remove()}),this.setCursor(n),l&&$(this.getCursorElement()).focus(),e&&(e.scrollTop=t)}}),dynamicList.List.addMethod("refreshStyle",function(t){var e=this._getElement();e&&(e.templateClassName&&(e.className=e.templateClassName),this._cssClassName&&e.addClassName(this._cssClassName))}),dynamicList.List.addMethod("fire",function(t,e){var s=$(this._getElement());return s?s.fire(t,e):null}),dynamicList.List.addMethod("observe",function(t,e){this._getElement().observe(t,e)}),dynamicList.List.addMethod("stopObserving",function(t,e){this._getElement().stopObserving(t,e)}),dynamicList.List.addMethod("_getElement",function(){return this._element||(this._element=$(this.getId())),this._element}),dynamicList.List.addMethod("getItemByEvent",function(t){if(t)for(var e=Event.element(t);e&&e.readAttribute&&e.readAttribute("id")!==this.getId();){var s=e.listItem;if(s&&null!=s.getList()){var i=s.getList(),n=i.getId()==this.getId(),l=this._getElement().contains(i._getElement());if(n||l)return s._label=xssUtil.unescape(s._label),s;break}e=$(e.parentNode)}return null}),dynamicList.List.addMethod("_createFromTemplate",function(){var t=this._getElement().readAttribute("tabindex");this.tabindex=parseInt(t&&t.length>0?t:-1),this._getElement().insert({after:this._getTemplateElement(this._getElement())}),this._getElement().remove(),this._element=null,this._getElement().update(),this.tabindex&&this.tabindex.length>0&&this._getElement().writeAttribute("tabindex",this.tabindex),disableSelectionWithoutCursorStyle(this._getElement())}),dynamicList.List.addMethod("_getTemplateElement",function(t){var e=this._listTemplateDomId;dynamicList._templateHash[e]||(dynamicList._templateHash[e]=e);var s=$(dynamicList._templateHash[e]).cloneNode(!0);return s.writeAttribute("id",this.getId()),s.templateId=e,s.templateClassName=s.className,cloneCustomAttributes(t,s),s}),dynamicList.List.addMethod("_addItemToSelected",function(t,e){t&&!this.isItemSelected(t)&&(this._selectedItems.push(t),e&&(this._lastSelectedItem=t),t.refreshStyle(),this._parentList?this._parentList._addItemToSelected(t,e):this.fire(this.Event.ITEM_SELECTED,{item:t}))}),dynamicList.List.addMethod("_removeItemFromSelected",function(t){t&&this.isItemSelected(t)&&(this._selectedItems=this._selectedItems.without(t),t.refreshStyle(),this._parentList?this._parentList._removeItemFromSelected(t):this.fire(this.Event.ITEM_UNSELECTED,{item:t}))}),dynamicList.List.addMethod("_buildDnDOverlay",function(t){t.setStyle({width:null,height:null}),t.items.length>1?t.update(this._msgNItemsSelected.evaluate({count:t.items.length})):1==t.items.length&&t.update(xssUtil.escape(t.items[0].getLabel()))}),dynamicList.List.addMethod("_registerCustomScroll",function(){if(!this.scroll&&this._getElement()){var t=this._getElement().up(layoutModule.SWIPE_SCROLL_PATTERN);if(t){var e=layoutModule.scrolls.get(t.identify());e&&(this.scroll=e)}}}),dynamicList.List.addMethod("createDraggableIfNeeded",function(t){var e=t.element();if(this.dragPattern&&!this.draggables[e.identify()]){var s=matchAny(e,[this.dragPattern],!0);if(s){if(!s||this.draggables[s.identify()])return;var i=this.getItemByEvent(t);this.draggables[s.identify()]=new Draggable(s,{superghosting:!0,mouseOffset:!0,onStart:this.setDragStartState.bind(this,i),onEnd:this.setDragEndState.bind(this,i)})}}}),dynamicList.List.addMethod("setDragStartState",function(t,e,s){var i=t._getElement().templateClassName;i&&e.element.addClassName(i),e.element.addClassName(layoutModule.DRAGGING_CLASS).addClassName(this.getId()),e.element.items=this.getSelectedItems().slice(0),this._buildDnDOverlay(e.element),e.options.scroll=this._getElement(),e.options.scrollSensitivity=layoutModule.SCROLL_SENSITIVITY,Draggables.dragging=this.regionID||!0}),dynamicList.List.addMethod("setDragEndState",function(t,e,s){delete Draggables.dragging}),dynamicList.List.addMethod("_mouseupHandler",function(t){var e=t.element(),s=matchMeOrUp(e,layoutModule.BUTTON_PATTERN)&&this.getItemByEvent(t);if(s&&!s._isElementInExcluded(t)){if(t.listEvent=!0,s._respondOnItemEvents&&!t.isInvoked){if(this.fire(this.Event.ITEM_MOUSEUP,{targetEvent:t,item:s}),!s._isExcludedFromSelectionTriggers(t)){var i=isMetaHeld(t),n=isShiftHeld(t),l=isRightClick(t),o=!this._selectOnMousedown&&!TouchController.element_scrolled&&(!isSupportsTouch()||t.changedTouches.length>=1);if(o&&s.getList().selectItem(s,i,n,l),!o&&this._multiSelect&&this._selectedItems.length>1&&this.isItemSelected(s)&&!(i||n||l)&&(this._lastSelectedItem=s),s.getList().deselectOthers(s,isMetaHeld(t),isShiftHeld(t),isRightClick(t)),this.twofingers){this.twofingers=!1;var a=jQuery(e).parents("li:first");a.hasClass("selected")&&document.fire(layoutModule.ELEMENT_CONTEXTMENU,{targetEvent:t,node:e})}}this.createDraggableIfNeeded(t)}t.isInvoked=!0}}),dynamicList.List.addMethod("_mousedownHandler",function(t){t="dataavailable"==t.type?t.memo.targetEvent:t;var e=t.element(),s=matchMeOrUp(e,layoutModule.BUTTON_PATTERN+","+layoutModule.LIST_ITEM_WRAP_PATTERN)&&this.getItemByEvent(t);s&&!s._isElementInExcluded(t)&&(t.listEvent=!0,t.touches&&2==t.touches.length?this.twofingers=!0:this.twofingers=!1,s.isComposite&&(s._isOpenHandler(t.target)||s._isCloseHandler(t.target))?(t.stopPropagation(),t.preventDefault()):s._respondOnItemEvents&&!t.isInvoked&&(this.fire(this.Event.ITEM_MOUSEDOWN,{targetEvent:t,item:s}),s._isExcludedFromSelectionTriggers(t)||(this._selectOnMousedown&&s.getList().selectItem(s,isMetaHeld(t),isShiftHeld(t),isRightClick(t)),this._setCursorOnMousedown&&!isMetaHeld(t)&&s.getList().setCursor(s)),s.focus()),t.isInvoked=!0)}),dynamicList.List.addMethod("_mouseoverHandler",function(t){matchMeOrUp(t.element(),layoutModule.BUTTON_PATTERN)&&this.createDraggableIfNeeded(t)}),dynamicList.List.addMethod("_clickHandler",function(t){var e=matchMeOrUp(t.element(),layoutModule.BUTTON_PATTERN)&&this.getItemByEvent(t);if(e&&!e._isElementInExcluded(t)){if(!t.isInvoked){if(e._respondOnItemEvents&&this.fire(this.Event.ITEM_CLICK,{targetEvent:t,item:e}),!e.isComposite)return;var s=e._getElement(),i=t.element();e._isCloseHandler(i)&&baseList.isItemOpen(s)?(baseList.closeItem(s),this.fire(this.Event.ITEM_CLOSED,{targetEvent:t,item:e})):e._isOpenHandler(i)&&!baseList.isItemOpen(s)&&(baseList.openItem(s),this.fire(this.Event.ITEM_OPEN,{targetEvent:t,item:e}))}t.isInvoked=!0}}),dynamicList.List.addMethod("_dblclickHandler",function(t){var e=matchMeOrUp(t.element(),layoutModule.BUTTON_PATTERN)&&this.getItemByEvent(t);e&&!e._isElementInExcluded(t)&&(e._respondOnItemEvents&&!t.isInvoked&&this.fire(this.Event.ITEM_DBLCLICK,{targetEvent:t,item:e}),t.isInvoked=!0)}),dynamicList.List.addMethod("_initEvents",function(){var t=this._getElement();this.draggables=[],isSupportsTouch()?(t.stopObserving("touchstart").observe("touchstart",this._mousedownHandler.bindAsEventListener(this)),t.stopObserving("drag:touchstart").observe("drag:touchstart",this._mousedownHandler.bindAsEventListener(this)),t.stopObserving("touchend").observe("touchend",this._mouseupHandler.bindAsEventListener(this))):(t.stopObserving("mouseup").observe("mouseup",this._mouseupHandler.bindAsEventListener(this)),t.stopObserving("mousedown").observe("mousedown",this._mousedownHandler.bindAsEventListener(this)),t.stopObserving("drag:mousedown").observe("drag:mousedown",this._mousedownHandler.bindAsEventListener(this))),isIPad||t.stopObserving("mouseover").observe("mouseover",this._mouseoverHandler.bindAsEventListener(this)),t.stopObserving("click").observe("click",this._clickHandler.bindAsEventListener(this)),
t.stopObserving("dblclick").observe("dblclick",this._dblclickHandler.bindAsEventListener(this)),t.stopObserving("key:down").observe("key:down",this.selectNext.bindAsEventListener(this)),t.stopObserving("key:up").observe("key:up",this.selectPrevious.bindAsEventListener(this)),t.stopObserving("key:right").observe("key:right",this.selectInwards.bindAsEventListener(this)),t.stopObserving("key:left").observe("key:left",this.selectOutwards.bindAsEventListener(this)),t.stopObserving("key:pagedown").observe("key:pagedown",this.selectPageDown.bindAsEventListener(this))}),dynamicList.UnderscoreTemplatedList=function(t,e){e&&(this._template="template"in e?e.template:""),dynamicList.List.apply(this,arguments)};var tempFunc=function(){};tempFunc.prototype=dynamicList.List.prototype,dynamicList.UnderscoreTemplatedList.prototype=new tempFunc,dynamicList.UnderscoreTemplatedList.prototype.constructor=dynamicList.UnderscoreTemplatedList,dynamicList.UnderscoreTemplatedList.prototype._getTemplateElement=function(t){var e=jQuery(_.template(this._template,{}))[0];return e.writeAttribute("id",this.getId()),e.templateClassName=e.className,cloneCustomAttributes(t,e),e};