define(["require","jquery","underscore","common/util/datetime/RelativeDate","common/util/datetime/RelativeTime","common/util/datetime/RelativeTimestamp","text!./template/calendar2Template.htm","bundle!calendar","jquery.ui","common/jquery/extension/datepickerExt","common/jquery/extension/timepickerExt"],function(e){"use strict";var t=e("jquery"),n=e("underscore"),a=e("common/util/datetime/RelativeDate"),i=e("common/util/datetime/RelativeTime"),r=e("common/util/datetime/RelativeTimestamp"),d=e("text!./template/calendar2Template.htm"),o=e("bundle!calendar");e("jquery.ui"),e("common/jquery/extension/datepickerExt"),e("common/jquery/extension/timepickerExt");var l={evaluate:/\{\{([\s\S]+?)\}\}/g,interpolate:/\{\{=([\s\S]+?)\}\}/g,escape:/\{\{-([\s\S]+?)\}\}/g},s=function(e){return e=null==e?"":String(e),e=e.toLowerCase(),e.charAt(0).toUpperCase()+e.slice(1)},c=n.template(d,null,l),u={counter:0,activeMark:"activeCalendar2",hasActiveCalendar:function(e){return t(e).hasClass(this.activeMark)},instance:function(e){e=e||{};var d=this,l="hidden",u="calendar2_"+ ++this.counter,h={calContainer:!1,tabs:[],tabButtons:[],rdFunction:void 0,build:function(){e.calendarType=e.calendarType||"datetime",e.disabled=e.disabled||!1,e.relativeTime=e.relativeTime||!1,e.inputField=t(e.inputField),"date"==e.calendarType?h.rdFunction=a:"datetime"==e.calendarType?h.rdFunction=r:"time"==e.calendarType&&(h.rdFunction=i);var d=n.map(n.keys(h.rdFunction.PATTERNS),function(e){return{label:s(e)+"(s)",value:e}});e.inputField.addClass("hasCalendar2"),h.calContainer=t(c({uniqueId:u,i18n:o,rdWords:d,calendarType:e.calendarType})),h.calContainer.appendTo("body"),t("<button type='button' class='ui-datepicker-trigger button picker'></button>").insertAfter(e.inputField),h.initTabs(),h.attachListeners(),e.disabled&&h.calContainer.addClass("disabled"),this.RD.init(h.rdFunction),this.Calendar.init()},attachListeners:function(){h.tabButtons.each(function(e,n){t(n).bind("click",function(){h.activateTab(e)})}),e.inputField.next(".ui-datepicker-trigger").bind("click",h.show),h.calContainer.find(".closeButton").bind("click",h.hide),h.calContainer.find(".nowButton").bind("click",n.bind(h.Calendar.setCurrent,h.Calendar)),t(document).bind("mousedown",h.checkExternalClick)},removeListeners:function(){h.tabButtons.each(function(e,n){t(n).unbind("click",function(){h.activateTab(e)})}),e.inputField.next(".ui-datepicker-trigger").unbind("click",h.show),h.calContainer.find(".closeButton").unbind("click",h.hide),h.calContainer.find(".nowButton").unbind("click",n.bind(h.Calendar.setCurrent,h.Calendar)),t(document).unbind("mousedown",h.checkExternalClick)},checkExternalClick:function(e){"shown"==l&&0===t(e.target).parents("#"+u).length&&h.hide()},initTabs:function(){h.tabs=h.calContainer.find(".tabs > div"),h.tabButtons=h.calContainer.find(".tabsControl > .tabSelect"),"time"!==e.calendarType||e.relativeTime||(h.tabs[1].remove(),h.tabButtons[1].remove(),h.calContainer.find(".tabsControl").hide()),h.activateTab(0)},activateTab:function(e){e>=h.tabs.length||(h.tabButtons.removeClass("opened"),h.tabs.removeClass("opened"),t(h.tabs[e]).addClass("opened"),t(h.tabButtons[e]).addClass("opened"),h.calContainer.find(".nowButton")[1===e?"hide":"show"]())},destroy:function(){e.inputField.removeClass("hasCalendar2"),h.removeListeners(),h.RD.destroy(),h.calContainer.remove()},show:function(){e.inputField.attr("readonly","readonly");var t=e.inputField.val(),n=h.rdFunction.isValid(t);h.activateTab(n?1:0),h.calContainer.show(),h.Calendar.create(),n?h.RD.setValue(t):h.Calendar.setValue(t),h.adjustPosition(),l="shown";var a=e.inputField;a.length&&a.addClass(d.activeMark)},adjustPosition:function(){var n=e.inputField,a=n.offset(),i=h.calContainer.width(),r=h.calContainer.height(),d=t(window),o=d.width(),l=d.height();a.top+25+r<=l||a.top-r-5<0?a.top+=25:a.top=a.top-r-5,a.left+i>o&&(a.left=Math.max(o-(i+20),50)),h.calContainer.offset(a)},hide:function(){e.inputField.removeAttr("readonly"),h.calContainer.hide(),h.Calendar.destroy(),l="hidden";var t=e.inputField;t.length&&t.removeClass(d.activeMark)},RD:{possibleValues:[],holder:!1,date:{},init:function(e){this.date=new e("","+",""),this.holder=h.calContainer.find(".relativeDates > .dates"),this.attachEventListeners()},attachEventListeners:function(){this.holder.find(".measure select, .sign select").bind("change",this.onSelect),this.holder.find(".amount input").bind("keyup",this.onSelect)},removeEventListeners:function(){this.holder.find(".measure select, .sign select").unbind("change",this.onSelect),this.holder.find(".amount input").unbind("keyup",this.onSelect)},onSelect:function(){e.inputField.val(h.RD.getValue()),e.inputField.trigger("change")},destroy:function(){this.removeEventListeners()},getValue:function(){return this.date.setKeyword(this.holder.find(".measure select").val()),this.date.setSign(this.holder.find(".sign select").val()),this.date.setNumber(this.holder.find(".amount input").val()),this.date.toString()},setValue:function(e){var t=h.rdFunction.parse(e);t?this.date=t:(this.date.setKeyword(""),this.date.setSign("+"),this.date.setNumber("")),this.holder.find(".measure select").val(this.date.keyword),this.holder.find(".sign select").val(this.date.sign),this.holder.find(".amount input").val(this.date.number)}},Calendar:{jqueryCalendar:!1,jqueryCalendarConfig:{},jqueryCalendarType:!1,init:function(){this.jqueryCalendarType="datetimepicker","time"==e.calendarType?this.jqueryCalendarType="timepicker":"date"==e.calendarType&&(this.jqueryCalendarType="datepicker"),this.jqueryCalendarConfig={dateFormat:"yy-mm-dd",timeFormat:"HH:mm:ss",showHour:!1,showMinute:!1,showSecond:!1,showTime:!1,constrainInput:!1,showButtonPanel:!1,onSelect:function(t,n){e.inputField.val(t),e.inputField.trigger("change")}},("datetime"==e.calendarType||"date"==e.calendarType)&&(this.jqueryCalendarConfig=n.extend(this.jqueryCalendarConfig,{changeYear:!0,changeMonth:!0})),("datetime"==e.calendarType||"time"==e.calendarType)&&(this.jqueryCalendarConfig=n.extend(this.jqueryCalendarConfig,{showTime:!0,showHour:!0,showMinute:!0,showSecond:!0})),this.jqueryCalendarConfig=n.extend(this.jqueryCalendarConfig,e.jqueryPickerOptions||{}),this.jqueryCalendarConfig.disabled=e.disabled,this.jqueryCalendar=h.calContainer.find("#"+u+"_calendar")},destroy:function(){this.jqueryCalendar[this.jqueryCalendarType]("destroy")},create:function(){this.jqueryCalendar[this.jqueryCalendarType](this.jqueryCalendarConfig)},getValue:function(){return this.jqueryCalendar[this.jqueryCalendarType]("getDate")},setCurrent:function(){var t=new Date;this.jqueryCalendar[this.jqueryCalendarType]("time"===e.calendarType?"setTime":"setDate",t),this.jqueryCalendar.find(".ui-datepicker-today").click()},setValue:function(a){if("time"==e.calendarType)try{n.isString(a)?(a=t.datepicker.parseTime(this.jqueryCalendarConfig.timeFormat,a),a&&this.jqueryCalendar[this.jqueryCalendarType]("setTime",a)):(a=new Date(a.getTime()),"Invalid Date"!==a.toString()&&this.jqueryCalendar[this.jqueryCalendarType]("setTime",a))}catch(i){}else{if(!(a instanceof Date))try{"datetime"==e.calendarType?a=t.datepicker.parseDateTime(this.jqueryCalendarConfig.dateFormat,this.jqueryCalendarConfig.timeFormat,a):"date"==e.calendarType&&(a=t.datepicker.parseDate(this.jqueryCalendarConfig.dateFormat,a))}catch(i){}if(a instanceof Date&&"Invalid Date"!==a.toString())return this.jqueryCalendar[this.jqueryCalendarType]("setDate",a)}}}};return h.build(),h}};return window.Calendar2=u,u});