define(["jquery.ui","text!jr.jive.sort.vm.css","text!jr.jive.filterDialog.tmpl"],function(t,e,i){var o={initialized:!1,selected:null,sortComponents:null,operators:null,fdc:{},init:function(i){var o,n,r,l,a=this;if(a.sortComponents=i.components.sort,!a.initialized){if(t("head").append('<style id="jive-sort-stylesheet">'+e+"</style>"),a.setupFilterDialog(),i.on("beforeAction",function(){a.fdc.filterDialog.hide()}),t(document).bind("contextmenu",function(){return!1}),a.sortComponents)for(n=0,r=a.sortComponents.length;r>n;n++)if(a.sortComponents[n].config.genericProperties){a.operators=a.sortComponents[n].config.genericProperties.operators;break}a.initialized=!0}l=t("div.sortlink"),"createTouch"in document?(t("document").bind("touchmove",function(){a.touchStartOn=void 0}),l.on("click",function(t){return t.preventDefault(),t.stopPropagation(),!1}),l.on("touchstart",function(t){t.preventDefault(),!t.isStartData&&(a.touchStartOn={element:t.target,timeStamp:t.timeStamp}),t.isStartData=!0}),l.on("touchend",function(e){return e.preventDefault(),a.isLongTouch(e)?(e.stopPropagation(),!1):(o=a.getComponent(t(this).data("uuid")),void(o&&o.sort()))}),l.on("touchend",function(e){if(e.preventDefault(),a.isLongTouch(e)){var i=e.changedTouches?e.changedTouches[0]:e.originalEvent.changedTouches[0];a.showFilterDialogForComponent(t(this).data("uuid"),i)}})):(l.on("click",function(e){e.preventDefault(),o=a.getComponent(t(this).data("uuid")),o&&o.sort()}),l.on("mousedown",function(e){3==e.which&&a.showFilterDialogForComponent(t(this).data("uuid"),e)}))},isLongTouch:function(t){var e=this;if(!e.touchStartOn)return!1;var i=t.target==e.touchStartOn.element,o=t.timeStamp-e.touchStartOn.timeStamp;return i&&o>400&&!t.scrollEvent},getComponent:function(t){var e,i=this.sortComponents,o=null;if(t&&i&&i.length>0)for(e=0;e<i.length;e++)if(t===i[e].config.id){o=i[e];break}return o},setupFilterDialog:function(){var e=this,o=t("#jive_sort_component");0==o.length&&(o=t('<div id="jive_sort_component"></div>').appendTo("body")),o.empty(),o.append(i),o.on("click",".hidefilter",function(){t(this).parent().hide()}),e.fdc.filterDialog=o.find("#jrSortComponentFilterDialog"),e.fdc.operatorTypeSelector=e.fdc.filterDialog.find("select.filterOperatorTypeValueSelector"),e.fdc.filterStart=e.fdc.filterDialog.find("input.filterValueStart"),e.fdc.filterEnd=e.fdc.filterDialog.find("input.filterValueEnd"),e.fdc.filterSubmit=e.fdc.filterDialog.find("input.submitFilter"),e.fdc.filterClear=e.fdc.filterDialog.find("input.clearFilter"),e.fdc.operatorTypeSelector.on("change",function(){t(this).val().indexOf("BETWEEN")>=0?e.fdc.filterEnd.show():e.fdc.filterEnd.hide()}),e.fdc.filterSubmit.on("click",function(){e.selected.filter(e.getFilterData())}),e.fdc.filterClear.on("click",function(){var t=e.getFilterData();t.clearFilter=!0,e.selected.filter(t)}),e.fdc.filterDialog.draggable()},showFilterDialogForComponent:function(e,i){var o,n,r=this,l=r.fdc.filterDialog,a=r.fdc.operatorTypeSelector,f=r.fdc.filterStart,c=r.fdc.filterEnd,d=r.getComponent(e);d&&d.config.isFilterable?(r.selected=d,o=d.config.filterData.filterType.toLocaleLowerCase(),n=r.operators[o],a.empty(),t.each(n,function(e,i){a.append(t("<option/>",{value:i.key,text:i.val}))}),d.config.filterData.filterTypeOperator.indexOf("BETWEEN")>=0?c.show():c.hide(),d.config.filterData.fieldValueStart.length>0||d.config.filterData.fieldValueEnd.length>0?r.fdc.filterClear.show():r.fdc.filterClear.hide(),f.val(d.config.filterData.fieldValueStart),c.val(d.config.filterData.fieldValueEnd),d.config.filterData.filterTypeOperator&&a.val(d.config.filterData.filterTypeOperator),l.css({position:"absolute","z-index":999998,left:i.pageX+"px",top:i.pageY+"px"}),l.show()):r.selected=null},getFilterData:function(){var t=this;return{tableUuid:t.selected.config.datasetUuid,fieldValueStart:t.fdc.filterStart.val(),fieldValueEnd:t.fdc.filterEnd.val(),filterTypeOperator:t.fdc.operatorTypeSelector.val()}}};return o});