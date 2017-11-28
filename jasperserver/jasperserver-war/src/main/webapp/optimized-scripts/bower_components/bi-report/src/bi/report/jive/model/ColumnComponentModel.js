define(["require","./BaseComponentModel","./FormatModel","../enum/jiveTypes","jquery","underscore","../collection/ConditionCollection","../enum/interactiveComponentTypes","../../enum/reportEvents","../util/jiveDataConverter"],function(t){function e(){return this.get("canSort")?this.get("sortOrder")?{order:this.get("sortOrder")}:{}:void 0}function i(){if(this.get("canFilter")){var t=this.get("filtering")?this.get("filtering").filterData.fieldValueStart:void 0,e=this.get("filtering")?this.get("filtering").filterData.fieldValueEnd:void 0;return"datetime"===g.dataTypeToSchemaFormat[this.get("dataType")]?"between"!==this.get("filterOperator")&&"not_between"!==this.get("filterOperator")||null==t||null==e?null!=t&&this.set("filterValue",g.jQueryUiTimestampToIsoTimestamp(t,this.parent&&this.parent.config?this.parent.config.genericProperties:void 0),{silent:!0}):this.set("filterValue",[g.jQueryUiTimestampToIsoTimestamp(t,this.parent&&this.parent.config?this.parent.config.genericProperties:void 0),g.jQueryUiTimestampToIsoTimestamp(e,this.parent&&this.parent.config?this.parent.config.genericProperties:void 0)],{silent:!0}):"time"===g.dataTypeToSchemaFormat[this.get("dataType")]&&("between"!==this.get("filterOperator")&&"not_between"!==this.get("filterOperator")||null==t||null==e?null!=t&&this.set("filterValue",g.jQueryUiTimeToIsoTime(t,this.parent&&this.parent.config?this.parent.config.genericProperties:void 0),{silent:!0}):this.set("filterValue",[g.jQueryUiTimeToIsoTime(t,this.parent&&this.parent.config?this.parent.config.genericProperties:void 0),g.jQueryUiTimeToIsoTime(e,this.parent&&this.parent.config?this.parent.config.genericProperties:void 0)],{silent:!0})),null!=this.get("filterOperator")&&null!=this.get("filterValue")?{operator:this.get("filterOperator"),value:this.get("filterValue")}:{}}return void 0}function o(){return{actionName:"editTextElement",editTextElementData:{applyTo:"heading",tableUuid:this.get("parentId"),columnIndex:this.get("columnIndex"),dataType:this.get("dataType"),headingName:this.get("columnLabel"),fontName:this.headingFormat.get("font").name,fontSize:this.headingFormat.get("font").size+"",fontBold:this.headingFormat.get("font").bold,fontItalic:this.headingFormat.get("font").italic,fontUnderline:this.headingFormat.get("font").underline,fontColor:this.headingFormat.get("font").color,fontHAlign:this.headingFormat.get("align").charAt(0).toUpperCase()+this.headingFormat.get("align").slice(1),fontBackColor:"transparent"===this.headingFormat.get("backgroundColor")?"000000":this.headingFormat.get("backgroundColor"),mode:"transparent"===this.headingFormat.get("backgroundColor")?"Transparent":"Opaque"}}}var a=t("./BaseComponentModel"),n=t("./FormatModel"),r=t("../enum/jiveTypes"),s=t("jquery"),l=t("underscore"),d=t("../collection/ConditionCollection"),h=t("../enum/interactiveComponentTypes"),c=t("../../enum/reportEvents"),g=t("../util/jiveDataConverter");return a.extend({api:{sort:{},move:{},format:{},filter:{},hide:{},unhide:{},resize:{}},defaults:function(){return{canFilter:!1,canFormatConditionally:!1,canSort:!1,clearData:{},columnIndex:0,columnLabel:"",conditionalFormattingData:{},dataType:void 0,filterData:{},filtering:{},headerToolbar:{},headingsTabContent:{},id:null,parentId:null,proxySelector:null,selector:null,module:"jive.interactive.column",type:r.COLUMN,valuesTabContent:{},sortOrder:void 0,filterOperator:void 0,filterValue:void 0}},constructor:function(){this.headingFormat=new n,this.detailsRowFormat=new n,this.conditions=new d,a.prototype.constructor.apply(this,arguments)},initialize:function(t){this.config=t,this.events={ACTION_PERFORMED:"action",BEFORE_ACTION_PERFORMED:"beforeAction"},this.attachEvents()},attachEvents:function(){this.listenTo(this.headingFormat,"change",function(){this.trigger("change:headingFormat",this)},this),this.listenTo(this.detailsRowFormat,"change",function(){this.trigger("change:detailsRowFormat",this)},this),this.listenTo(this.conditions,"reset",function(){this.trigger("change:conditions",this)},this),this.on("change",function(){(this.hasChanged("filterValue")||this.hasChanged("filterOperator"))&&this.trigger("change:filter",this)},this)},sort:function(t){var e=this,i={action:this.config.headerToolbar["sort"+t.order+"Btn"].sortData};i.action.sortData.tableUuid=e.config.parentId,e._notify({name:e.events.BEFORE_ACTION_PERFORMED}),e.trigger(c.ACTION,i.action)},move:function(t){var e=this,i={action:{actionName:"move",moveColumnData:{tableUuid:e.config.parentId,columnToMoveIndex:e.config.columnIndex,columnToMoveNewIndex:t.index}}};e._notify({name:e.events.BEFORE_ACTION_PERFORMED}),e.trigger(c.ACTION,i.action)},format:function(t){var e=this,i={action:t};e._notify({name:e.events.BEFORE_ACTION_PERFORMED}),e.trigger(c.ACTION,i.action)},filter:function(t){var e=this,i=s.extend({},e.config.filtering.filterData,t),o={action:{actionName:"filter",filterData:i}};e._notify({name:e.events.BEFORE_ACTION_PERFORMED}),e.trigger(c.ACTION,o.action)},hide:function(){var t=this,e={action:{actionName:"hideUnhideColumns",columnData:{tableUuid:t.config.parentId,hide:!0,columnIndexes:[this.config.columnIndex]}}};t._notify({name:t.events.BEFORE_ACTION_PERFORMED}),t.trigger(c.ACTION,e.action)},unhide:function(t){var e=this,i={action:{actionName:"hideUnhideColumns",columnData:{tableUuid:e.config.parentId,hide:!1,columnIndexes:t?t:[this.config.columnIndex]}}};e._notify({name:e.events.BEFORE_ACTION_PERFORMED}),e.trigger(c.ACTION,i.action)},resize:function(t){var e=this,i={action:{actionName:"resize",resizeColumnData:{tableUuid:e.config.parentId,columnIndex:this.config.columnIndex,direction:"right",width:t.width}}};e._notify({name:e.events.BEFORE_ACTION_PERFORMED}),e.trigger(c.ACTION,i.action)},parse:function(t){if(t.headerToolbar&&t.headerToolbar.sortAscBtn&&t.headerToolbar.sortDescBtn){var e,i=t.headerToolbar.sortAscBtn.sortData.sortData.sortOrder,o=t.headerToolbar.sortDescBtn.sortData.sortData.sortOrder;"None"===i?e="asc":"None"===o&&(e="desc"),t.sortOrder=e}if(t.filtering&&t.filtering.filterData){var a=g.dataTypeToSchemaFormat[t.dataType],n=t.filtering.filterData.filterTypeOperator,r=t.filtering.filterData.fieldValueStart,s=t.filtering.filterData.fieldValueEnd;if(t.filtering.filterData.clearFilter||null==r&&null==n)t.filterOperator=void 0,t.filterValue=void 0;else{var l=g.operatorAndValueToSchemaFormat(n,a,r,s);t.filterOperator=l.operator,t.filterValue=l.value}}return t.headingsTabContent&&this.headingFormat.set(this.headingFormat.parse(t.headingsTabContent),{silent:!0}),t.valuesTabContent&&(this.detailsRowFormat.dataType=g.dataTypeToSchemaFormat[t.dataType],this.detailsRowFormat.set(this.detailsRowFormat.parse(t.valuesTabContent),{silent:!0})),t.conditionalFormattingData&&(this.conditions.dataType=g.dataTypeToSchemaFormat[t.dataType],this.conditions.reset(t.conditionalFormattingData.conditions,{silent:!0,parse:!0})),t},actions:{"change:sortOrder":function(){var t,e=this.get("headerToolbar").sortAscBtn.sortData.sortData.sortColumnName,i=this.get("headerToolbar").sortAscBtn.sortData.sortData.sortColumnType;switch(this.get("sortOrder")){case"asc":t="Asc";break;case"desc":t="Dsc";break;default:t="None"}return{actionName:"sort",sortData:{sortColumnName:e,sortColumnType:i,sortOrder:t,tableUuid:this.get("parentId")}}},"change:filter":function(){var t=null==this.get("filterValue")&&null==this.get("filterOperator"),e=this.get("filtering").filterData;return{actionName:"filter",filterData:{fieldValueStart:g.filterStartValue(this.get("filterOperator"),this.get("filterValue"),g.dataTypeToSchemaFormat[this.get("dataType")],this.parent&&this.parent.config?this.parent.config.genericProperties:void 0),fieldValueEnd:g.filterEndValue(this.get("filterOperator"),this.get("filterValue"),g.dataTypeToSchemaFormat[this.get("dataType")],this.parent&&this.parent.config?this.parent.config.genericProperties:void 0),filterTypeOperator:g.schemaFormatOperatorToFilterOperator(this.get("filterOperator"),this.get("filterValue"),g.dataTypeToSchemaFormat[this.get("dataType")]),clearFilter:t,filterPattern:e.filterPattern,fieldName:e.fieldName,localeCode:e.localeCode,timeZoneId:e.timeZoneId,isField:e.isField,tableUuid:this.get("parentId"),filterType:this.get("dataType")}}},"change:headingFormat":o,"change:columnLabel":o,"change:detailsRowFormat":function(){return{actionName:"editTextElement",editTextElementData:{applyTo:"detailrows",tableUuid:this.get("parentId"),columnIndex:this.get("columnIndex"),dataType:this.get("dataType"),headingName:this.get("columnLabel"),fontName:this.detailsRowFormat.get("font").name,fontSize:this.detailsRowFormat.get("font").size+"",fontBold:this.detailsRowFormat.get("font").bold,fontItalic:this.detailsRowFormat.get("font").italic,fontUnderline:this.detailsRowFormat.get("font").underline,fontColor:this.detailsRowFormat.get("font").color,formatPattern:this.detailsRowFormat.toJiveFormat(),fontHAlign:this.detailsRowFormat.get("align").charAt(0).toUpperCase()+this.detailsRowFormat.get("align").slice(1),fontBackColor:"transparent"===this.detailsRowFormat.get("backgroundColor")?"000000":this.detailsRowFormat.get("backgroundColor"),mode:"transparent"===this.detailsRowFormat.get("backgroundColor")?"Transparent":"Opaque"}}},"change:conditions":function(){var t=this.parent&&this.parent.config?this.parent.config.genericProperties:void 0;return{actionName:"conditionalFormatting",conditionalFormattingData:{applyTo:"detailrows",tableUuid:this.get("parentId"),columnIndex:this.get("columnIndex"),conditionPattern:this.get("conditionalFormattingData").conditionPattern,conditionType:this.get("conditionalFormattingData").conditionType,conditions:this.conditions.map(function(e){return e.toJiveFormat(t)})}}}},toReportComponentObject:function(){var t=this.parent.get("allColumnsData");if(t){var o=l.findWhere(t,{uuid:this.get("id")});if(o)return o.interactive?{id:this.get("id"),componentType:h.TABLE_COLUMN,dataType:g.dataTypeToSchemaFormat[this.get("dataType")],label:this.get("columnLabel"),name:this.get("name"),sort:e.call(this),filter:i.call(this),headingFormat:this.headingFormat.toJSON(),detailsRowFormat:this.detailsRowFormat.toJSON(),conditions:this.get("canFormatConditionally")?this.conditions.toJSON():void 0}:void 0}return void 0},updateFromReportComponentObject:function(t){var e={};t.label&&(e.columnLabel=t.label),this.get("canSort")&&t.sort&&("order"in t.sort?e.sortOrder=t.sort.order:0===l.keys(t.sort).length&&(e.sortOrder=void 0)),this.get("canFilter")&&t.filter&&(0===l.keys(t.filter).length||null==t.filter.operator||null==t.filter.value?(e.filterOperator=void 0,e.filterValue=void 0):(e.filterOperator=t.filter.operator,e.filterValue=t.filter.value)),t.headingFormat&&(t.headingFormat.font=l.extend({},this.headingFormat.get("font"),t.headingFormat.font||{}),t.headingFormat.backgroundColor&&"transparent"!==t.headingFormat.backgroundColor&&(t.headingFormat.backgroundColor=t.headingFormat.backgroundColor.toUpperCase()),t.headingFormat.font&&t.headingFormat.font.color&&(t.headingFormat.font.color=t.headingFormat.font.color.toUpperCase()),this.headingFormat.set(t.headingFormat)),t.detailsRowFormat&&(t.detailsRowFormat.font=l.extend({},this.detailsRowFormat.get("font"),t.detailsRowFormat.font||{}),l.isObject(this.detailsRowFormat.get("pattern"))&&(t.detailsRowFormat.pattern=l.extend({},this.detailsRowFormat.get("pattern"),t.detailsRowFormat.pattern||{})),t.detailsRowFormat.backgroundColor&&"transparent"!==t.detailsRowFormat.backgroundColor&&(t.detailsRowFormat.backgroundColor=t.detailsRowFormat.backgroundColor.toUpperCase()),t.detailsRowFormat.font&&t.detailsRowFormat.font.color&&(t.detailsRowFormat.font.color=t.detailsRowFormat.font.color.toUpperCase()),this.detailsRowFormat.set(t.detailsRowFormat)),this.get("canFormatConditionally")&&t.conditions&&0!==t.conditions.length&&this.conditions.reset(t.conditions),this.set(e)}})});