define(["require","jquery","backbone","underscore","common/component/singleSelect/manager/KeyboardManager","common/component/singleSelect/view/SingleSelectList","common/component/singleSelect/model/SingleSelectListModel","common/component/singleSelect/dataprovider/SearcheableDataProvider","common/component/singleSelect/manager/DropDownManager","text!common/component/singleSelect/templates/singleSelectTemplate.htm","text!common/component/singleSelect/templates/listTemplate.htm","text!common/component/singleSelect/templates/itemsTemplate.htm","text!common/component/singleSelect/templates/dropDownTemplate.htm","bundle!ScalableInputControlsBundle","common/util/browserDetection","common/util/xssUtil"],function(e){"use strict";var t=e("jquery"),i=e("backbone"),n=e("underscore"),o=e("common/component/singleSelect/manager/KeyboardManager"),s=e("common/component/singleSelect/view/SingleSelectList"),l=e("common/component/singleSelect/model/SingleSelectListModel"),a=e("common/component/singleSelect/dataprovider/SearcheableDataProvider"),d=e("common/component/singleSelect/manager/DropDownManager"),h=e("text!common/component/singleSelect/templates/singleSelectTemplate.htm"),r=e("text!common/component/singleSelect/templates/listTemplate.htm"),u=e("text!common/component/singleSelect/templates/itemsTemplate.htm"),c=e("text!common/component/singleSelect/templates/dropDownTemplate.htm"),m=e("bundle!ScalableInputControlsBundle"),p=e("common/util/browserDetection"),f=e("common/util/xssUtil"),g=t("body"),v=i.View.extend({events:function(){return{"keydown input":this.keyboardManager.onKeydown,"focus input":"onFocus","blur input":"onBlur",mousedown:"onMousedown",mouseup:"onMouseup","click .sSelect-input":"onClickOnInput"}},initialize:function(e){n.bindAll(this,"onGlobalMouseup","onGlobalMousedown","onGlobalMousemove","onMousedown","calcOffsetForListView","collapse"),this.model||(this.model=new i.Model),this.model.set("expanded",!1,{silent:!0}),this.model.set("criteria","",{silent:!0}),this.model.set("value",{},{silent:!0}),this.label=e.label,this.template=n.template(e.template||h),this.dropDownTemplate=n.template(e.dropDownTemplate||c),this.keyboardManager=new o({keydownTimeout:e.keydownTimeout,context:this,deferredKeydownHandler:this.processKeydown,immediateHandleCondition:this.immediateHandleCondition,immediateKeydownHandler:this.immediateKeydownHandler,stopPropagation:!0}),this.listViewModel=this._createListViewModel(e),this.listView=this._createListView(e),this.render(),this.dropDownManager=new d({dropDownEl:this.$dropDownEl,calcOffset:this.calcOffsetForListView,isOffsetChanged:this.isDimensionsChangedforListView,onOffsetChanged:this.collapse}),this.initListeners(),this.setValue(e.value,{modelOptions:{silent:!0}})},_createListView:function(e){return new s({el:e.listElement||t(r),model:this.listViewModel,chunksTemplate:e.chunksTemplate,itemsTemplate:e.itemsTemplate||u,scrollTimeout:e.scrollTimeout})},_createListViewModel:function(e){return this.searcheableDataProvider=new a({getData:e.getData}),new l({getData:this._getGetData(),bufferSize:e.bufferSize,loadFactor:e.loadFactor})},_getGetData:function(){return this.searcheableDataProvider.getData},_getActiveValueIndex:function(e){var t=this.searcheableDataProvider.getIndexMapping();return t?t[e.index]:e.index},initListeners:function(){this.listenTo(this.listView,"selection:change",this.selectionChange,this),this.listenTo(this.listView,"item:mouseup",this.itemMouseup,this),this.listenTo(this.model,"change:expanded",this.changeExpandedState,this),this.listenTo(this.model,"change:value",this.changeValue,this),this.listenTo(this.model,"change:disabled",this.changeDisabled,this),this.listenTo(this.model,"change:criteria",this.changeFilter,this),this.$dropDownEl.on("mousedown",this.onMousedown),t("body").on("mousedown",this.onGlobalMousedown).on("dataavailable",this.onGlobalMousedown).on("mouseup",this.onGlobalMouseup).on("mousemove",this.onGlobalMousemove)},render:function(){var e=t(this.template({label:this.label,isIPad:p.isIPad(),value:this.model.get("value").label,expanded:this.model.get("expanded"),i18n:m}));return this.renderListView(),this.$el.empty(),this.$el.append(e),this},renderListView:function(){this.listRendered||(this.$dropDownEl=t(this.dropDownTemplate({isIPad:"iPad"===navigator.platform})),this.listView.undelegateEvents(),this.$dropDownEl.append(this.listView.$el),t("body").append(this.$dropDownEl),this.listView.delegateEvents(),this.listRendered=!0),this.model.get("expanded")?this.$dropDownEl.show():this.$dropDownEl.hide()},renderData:function(){return this.listView.renderData(),this},itemMouseup:function(){if(!this.model.get("disabled")){delete this.preventBlur,this.$el.find("input").focus();var e=this.model.get("activeValue");e.value!==this.model.get("value").value?(this.model.unset("activeValue"),this.model.set("value",e)):this.collapse()}},selectionChange:function(){var e=this.listView.getActiveValue();e?(this.listView.scrollTo(e.index),this.model.set("activeValue",{value:e.value,label:e.label,index:this._getActiveValueIndex(e)}),this.updateValueFromActive()):this.model.unset("activeValue")},onClickOnInput:function(){this.model.get("expanded")?this.collapse():this.expand()},onFocus:function(){this.model.get("expanded")||this.$el.find(".sSelect").addClass("focused")},onBlur:function(){this.preventBlur||(this.collapse(),this.$el.find(".sSelect").removeClass("focused"))},onMousedown:function(){this.preventBlur=!0},onMouseup:function(){this.preventBlur&&(delete this.preventBlur,this.model.get("expanded")&&this.$el.find("input").focus())},changeExpandedState:function(e){e.get("expanded")?this.doExpand():this.doCollapse()},changeValue:function(){this.collapse(),this.updateControlLabel(),this.silent?delete this.silent:this.trigger("selection:change",this.model.get("value").value),this.$el.find("input").val(""),this.model.set("criteria","")},changeDisabled:function(){var e=this.model.get("disabled");e?(this.$el.addClass("disabled"),this.$el.find("input[type='text']").attr("disabled","disabled"),this.collapse()):(this.$el.find("input[type='text']").removeAttr("disabled"),this.$el.removeClass("disabled")),this.listView.setDisabled(e)},onGlobalMousedown:function(e){if(this.model.get("expanded")){if(e.target===this.el||this.$el.find(e.target).length>0||e.target===this.$dropDownEl[0]||this.$dropDownEl.find(e.target).length>0)return void this.$el.find("input").focus();this.preventBlur=!1,this.onBlur()}},onGlobalMouseup:function(){this.onMouseup()},onGlobalMousemove:function(e){this.preventBlur&&e.stopPropagation()},onUpKey:function(){if(this.model.get("expanded")){var e=this.listView.getActiveValue();if(!e||0===e.index)return void this.collapse();this.listView.activatePrevious()}else this.expand()},onDownKey:function(){if(this.model.get("expanded")){var e=this.listView.getActiveValue();e?this.listView.activateNext():this.listView.activateFirst()}else this.expand()},onEnterKey:function(e){e.preventDefault(),this.model.get("expanded")?this.model.get("activeValue")?this.itemMouseup():this.collapse():this.expand()},onEscKey:function(){this.model.get("expanded")&&this.collapse()},onHomeKey:function(){this.model.get("expanded")?this.listView.activateFirst():this.expand()},onEndKey:function(){this.model.get("expanded")?this.listView.activateLast():this.expand()},onPageUpKey:function(){this.model.get("expanded")?this.listView.pageUp():this.expand()},onPageDownKey:function(){this.model.get("expanded")?this.listView.pageDown():this.expand()},onTabKey:function(){},doExpand:function(){this.$el.find(".sSelect").removeClass("collapsed").addClass("expanded").addClass("focused"),this.$el.find("input").focus(),this.expandListView(),this.listView.lazy?this.listView.fetch(n.bind(this.listView.resize,this.listView)):this.listView.resize(),"undefined"!=typeof this.model.get("value").value&&this.setValueToList(),this.trigger("expand",this)},expandListView:function(){this.$dropDownEl.show(),this.dropDownManager.startCalc()},doCollapse:function(){this.model.unset("activeValue"),this.$el.find(".sSelect").removeClass("expanded").addClass("collapsed"),this.$el.find("input").val(""),this.$dropDownEl.hide(),this.model.set("criteria",""),this.dropDownManager.stopCalc(),this.trigger("collapse",this)},calcOffsetForListView:function(){var e=this.$el.offset(),t=g.offset().top+e.top+this.$el.height()+this.$el.find(".sSelect-listContainer").height()-2,i=g.offset().left+e.left;return{top:t,left:i,width:this.$el.find(".sSelect").width()}},isDimensionsChangedforListView:function(e,t){return Math.floor(e.top)!==Math.floor(t.top)||Math.floor(e.left)!==Math.floor(t.left)},immediateHandleCondition:function(){return!this.model.get("expanded")},immediateKeydownHandler:function(e){this.expand(),this.keyboardManager.deferredHandleKeyboardEvent(e)},processKeydown:function(){this.model.set("criteria",this.$el.find("input").val())},changeFilter:function(){var e=this;this._getGetData()({criteria:this.model.get("criteria")}).done(function(){e.listView.fetch(function(){e.setValueToList()})})},setValueToList:function(e){var t=this.model.get("value"),i=t.value;if("undefined"!=typeof t.index){var n=this.searcheableDataProvider.getReverseIndexMapping(),o=n?n[t.index]:t.index;i={},"undefined"!=typeof o&&(i[o]=t.value)}this.listView.setValue(i,e)},convertExternalValueToInternalFormat:function(e){var t={};if("undefined"!=typeof e)if("string"==typeof e||null===e)t={value:e};else for(var i in e)if(e.hasOwnProperty(i)&&void 0!==e[i]){t={value:e[i],index:parseInt(i,10)};break}return t},updateValueFromActive:function(){var e=this.model.get("value"),t=this.model.get("activeValue");e.value===t.value&&"undefined"==typeof e.label&&(e.label=t.label,this.updateControlLabel())},updateControlLabel:function(){var e=this.model.get("value");if(n.isEmpty(e)||null==e.value)this.render();else{var t=this.getControlLabelByValue(e);this.$el.find(".sSelect-input").attr("title",t).find("> span").html(f.escape(t))}},getControlLabelByValue:function(e){return null==e.label?e.value:e.label},fetch:function(e,t){return this.listView.fetch(e,t),this},reset:function(e){return this.listView.reset(e),this},expand:function(){return this.model.get("disabled")?void 0:(this.model.set("expanded",!0),this)},collapse:function(){return this.model.set("expanded",!1),this},getValue:function(){return this.model.get("value").value},setValue:function(e,t){var e=this.convertExternalValueToInternalFormat(e);t&&t.silent&&(this.silent=!0),this.model.set("value",e),this.setValueToList()},setDisabled:function(e){return this.model.set("disabled",e),this},getDisabled:function(){return this.model.get("disabled")},remove:function(){this.$dropDownEl.off("mousedown",this.onMousedown).remove(),this.listView.remove(),i.View.prototype.remove.call(this),t("body").off("mousedown",this.onGlobalMousedown).off("dataavailable",this.onGlobalMousedown).off("mouseup",this.onGlobalMouseup).off("mousemove",this.onGlobalMousemove)}});return v});