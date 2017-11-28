define("scheduler/model/job",["require","jquery","underscore","backbone","encoding","jrs.configs","moment","common/jquery/extension/timepickerExt","common/jquery/extension/datepickerExt"],function(e){"use strict";var t=",",r="YYYY-MM-DD HH:mm",i=!1,o=e("jquery"),s=e("underscore"),n=e("backbone"),a=e("encoding"),d=e("jrs.configs"),g=e("moment");e("common/jquery/extension/timepickerExt"),e("common/jquery/extension/datepickerExt");var l=function(e){var t,r=[{id:0,value:"dd",key:"day-of-month-2",result:"DD"},{id:1,value:"DD",key:"day-of-week",result:"dddd"},{id:2,value:"mm",key:"month-2",result:"MM"},{id:3,value:"MM",key:"month-name",result:"MMMM"},{id:4,value:"yy",key:"year-4",result:"YYYY"},{id:5,value:"d",key:"day-of-month-1",result:"D"},{id:6,value:"D",key:"day-of-week-abbr",result:"ddd"},{id:7,value:"m",key:"month-1",result:"M"},{id:8,value:"M",key:"month-name-abbr",result:"MMM"},{id:9,value:"y",key:"year-2",result:"YY"},{id:10,value:"oo",key:"day-of-year-2",result:"DDDD"},{id:11,value:"o",key:"day-of-year-1",result:"DDD"}];for(t=0;t<r.length;t++)e=e.replace(new RegExp(r[t].value,"g"),"{"+r[t].id+"}");for(t=0;t<r.length;t++)e=e.replace(new RegExp("\\{"+r[t].id+"\\}","g"),r[t].result);return e},u=l(d.calendar.timepicker.dateFormat),c=d.calendar.timepicker.timeFormat.replace(":ss",""),p=u+" "+c;return n.Model.extend({urlRoot:d.contextPath+"/rest_v2/jobs",isValid:function(e){return!this.validate(this.attributes,{validate:!0,editMode:e}).length},validate:function(e,t){if(t=t||{},t.validate===!0){var r,i=[];e.trigger&&(2==e.trigger.startType&&(e.trigger.startDate?g(e.trigger.startDate,p,!0).isValid()?!t.editMode&&this.ifPastDate(e.trigger.startDate,e.trigger.timezone)&&i.push({field:"startDate",errorCode:"error.before.current.date.trigger.startDate"}):i.push({field:"startDate",errorCode:"error.invalid.date"}):i.push({field:"startDate",errorCode:"report.scheduling.job.edit.specify.startdate"})),"simple"===e.trigger.type?(this.isNumeric(e.trigger.recurrenceInterval)?this.isNumeric(e.trigger.recurrenceInterval,{minValue:-2147483648,maxValue:2147483647})||i.push({field:"recurrenceInterval",errorCode:"typeMismatch.java.lang.Integer"}):i.push({field:"recurrenceInterval",errorCode:"report.scheduling.job.edit.specify.recurrenceinterval"}),"numberOfTimes"===e.trigger.radioEndDate&&(this.isNumeric(e.trigger.occurrenceCount)?this.isNumeric(e.trigger.occurrenceCount,{minValue:-2147483648,maxValue:2147483647})||i.push({field:"occurrenceCount",errorCode:"typeMismatch.java.lang.Integer"}):i.push({field:"occurrenceCount",errorCode:"report.scheduling.job.edit.specify.numberoftimestorunreport"})),"specificDate"===e.trigger.radioEndDate&&(e.trigger.endDate?g(e.trigger.endDate,p,!0).isValid()?(this.ifPastDate(e.trigger.endDate,e.trigger.timezone)&&i.push({field:"simpleEndDate",errorCode:"error.before.current.date.trigger.endDate"}),2==e.trigger.startType&&e.trigger.startDate&&+new Date(e.trigger.startDate)>=+new Date(e.trigger.endDate)&&i.push({field:"simpleEndDate",errorCode:"error.before.start.date.trigger.endDate"})):i.push({field:"simpleEndDate",errorCode:"error.invalid.date"}):i.push({field:"simpleEndDate",errorCode:"report.scheduling.job.edit.specify.enddate"}))):"calendar"===e.trigger.type&&("selectedMonths"===e.trigger.radioWhichMonth&&0===e.trigger.months.month.length&&i.push({field:"monthSelector",errorCode:"report.scheduling.job.edit.specify.monthswhenjobshouldrun"}),e.trigger.endDate&&(g(e.trigger.endDate,p,!0).isValid()?(this.ifPastDate(e.trigger.endDate,e.trigger.timezone)&&i.push({field:"calendarEndDate",errorCode:"error.before.current.date.trigger.endDate"}),2==e.trigger.startType&&e.trigger.startDate&&+new Date(e.trigger.startDate)>=+new Date(e.trigger.endDate)&&i.push({field:"calendarEndDate",errorCode:"error.before.start.date.trigger.endDate"})):i.push({field:"calendarEndDate",errorCode:"error.invalid.date"})),"selectedDays"===e.trigger.radioWhichDay&&0===e.trigger.weekDays.day.length&&i.push({field:"daySelector",errorCode:"report.scheduling.job.edit.specify.dayswhenjobshouldrun"}),"datesInMonth"===e.trigger.radioWhichDay&&(r=this.parseIntervals(e.trigger.monthDays,"daysInMonth"),r||i.push({field:"datesInMonth",errorCode:"report.scheduling.job.edit.specify.whenjobshouldrun"})),this.parseIntervals(e.trigger.hours,"hours")||i.push({field:"hours",errorCode:"error.not.empty.trigger.hours"}),this.parseIntervals(e.trigger.minutes,"minutes")||i.push({field:"minutes",errorCode:"error.not.empty.trigger.minutesshould"})));var o=e.repositoryDestination;if(o.outputDescription&&o.outputDescription.length>250&&i.push({field:"outputDescription",errorCode:"report.scheduling.job.output.long.description"}),o.sequentialFilenames&&!o.timestampPattern&&i.push({field:"timestampPattern",errorCode:"report.scheduling.job.output.timestamppattern"}),e.baseOutputFilename||i.push({field:"baseOutputFilename",errorCode:"error.not.empty.baseOutputFilename"}),e.baseOutputFilename&&!this.isValidFileName(e.baseOutputFilename)&&i.push({field:"baseOutputFilename",errorCode:"error.invalid.chars.baseOutputFilename"}),e.outputFormats.outputFormat&&e.outputFormats.outputFormat.length||i.push({field:"outputFormats",errorCode:"error.report.job.no.output.formats"}),o.saveToRepository&&!o.folderURI&&i.push({field:"outputRepository",errorCode:"error.not.empty.folderURI"}),""===o.outputLocalFolder&&i.push({field:"outputHostFileSystem",errorCode:"error.not.empty.folderURI"}),o.outputFTPInfo.enabled&&(o.outputFTPInfo.serverName?this.isHostName(o.outputFTPInfo.serverName)||i.push({field:"ftpAddress",errorCode:"error.report.scheduling.empty.ftp.server"}):i.push({field:"ftpAddress",errorCode:"error.report.scheduling.empty.ftp.server"}),o.outputFTPInfo.port&&this.isNumeric(o.outputFTPInfo.port)?(r=parseInt(o.outputFTPInfo.port,10),r>0&&65535>=r||i.push({field:"ftpPort",errorCode:"error.report.scheduling.empty.ftp.port"})):i.push({field:"ftpPort",errorCode:"error.report.scheduling.empty.ftp.port"})),e.mailNotification){var s=e.mailNotification.toAddresses&&e.mailNotification.toAddresses.address||"",n=e.mailNotification.ccAddresses&&e.mailNotification.ccAddresses.address||"",a=e.mailNotification.bccAddresses&&e.mailNotification.bccAddresses.address||"",d=e.mailNotification.subject||"",l=e.mailNotification.messageText||"";s&&!this.validateEmails(s)&&i.push({field:"to_suc",errorCode:"error.invalid.mailNotification.invalidEmailaddresses"}),n&&!this.validateEmails(n)&&i.push({field:"cc_suc",errorCode:"error.invalid.mailNotification.invalidEmailaddresses"}),a&&!this.validateEmails(a)&&i.push({field:"bcc_suc",errorCode:"error.invalid.mailNotification.invalidEmailaddresses"}),(s||d)&&(s||i.push({field:"to_suc",errorCode:"error.invalid.mailNotification.specify.oneaddresses"}),d||i.push({field:"subject_suc",errorCode:"report.scheduling.job.edit.specify.messagesubject"})),l&&(s||i.push({field:"to_suc",errorCode:"error.invalid.mailNotification.specify.oneaddresses"}),d||i.push({field:"subject_suc",errorCode:"report.scheduling.job.edit.specify.messagesubject"}))}if(e.alert){var u=e.alert.toAddresses&&e.alert.toAddresses.address||"",c=e.alert.subject||"";e.alert.messageText||"",e.alert.messageTextWhenJobFails||"";u&&!this.validateEmails(u)&&i.push({field:"job_status_to",errorCode:"error.invalid.mailNotification.invalidEmailaddresses"}),(u||c)&&(u||i.push({field:"job_status_to",errorCode:"error.invalid.mailNotification.specify.oneaddresses"}),c||i.push({field:"job_status_subject",errorCode:"report.scheduling.job.edit.specify.messagesubject"})),(-1!==e.alert.jobState.indexOf("SUCCESS_ONLY")||-1!==e.alert.jobState.indexOf("ALL"))&&(u||i.push({field:"job_status_to",errorCode:"error.invalid.mailNotification.specify.oneaddresses"}),c||i.push({field:"job_status_subject",errorCode:"report.scheduling.job.edit.specify.messagesubject"})),(-1!==e.alert.jobState.indexOf("FAIL_ONLY")||-1!==e.alert.jobState.indexOf("ALL"))&&(u||i.push({field:"job_status_to",errorCode:"error.invalid.mailNotification.specify.oneaddresses"}),c||i.push({field:"job_status_subject",errorCode:"report.scheduling.job.edit.specify.messagesubject"}))}return this.trigger("clearAllErrors"),i.length?this.trigger("invalid",this,i):this.trigger("valid",this,[]),i}},update:function(e,t){if("object"==typeof t){t=s.extend({},this.get(e),t);for(var r in t)t.hasOwnProperty(r)&&void 0===t[r]&&delete t[r]}this.set(e,t)},value:function(e){var t=this.attributes;e=e.split("/");for(var r=0,i=e.length-1;i>r;r++){if(!t)return void 0;t=t[e[r]]}return t?t[e[e.length-1]]:void 0},sync:function(e,t,r){return"update"===e?e="create":"create"===e&&(e="update"),r||(r={}),r.contentType="application/job+json",r.beforeSend=function(e){e.setRequestHeader("Accept","application/job+json")},n.sync(e,t,r)},parse:function(e){if(e.trigger&&("simpleTrigger"in e.trigger?e.trigger.type="simpleTrigger":"calendarTrigger"in e.trigger&&(e.trigger.type="calendarTrigger"),e.trigger.type&&(s.extend(e.trigger,e.trigger[e.trigger.type]),delete e.trigger[e.trigger.type]),"simpleTrigger"===e.trigger.type&&null===e.trigger.recurrenceInterval?e.trigger.type="none":"simpleTrigger"===e.trigger.type?e.trigger.type="simple":"calendarTrigger"===e.trigger.type?e.trigger.type="calendar":e.trigger.type="none","none"===e.trigger.type&&(e.trigger.occurrenceCount=""),"undefined"==typeof e.trigger.startType&&(e.trigger.startType=1),e.trigger.startDate&&(e.trigger.startDate=this.formatDate(e.trigger.startDate)),("undefined"==typeof e.trigger.timezone||null===e.trigger.timezone)&&(e.trigger.timezone=d.usersTimeZone||"America/Los_Angeles"),("simple"===e.trigger.type||"calendar"===e.trigger.type)&&("undefined"==typeof e.trigger.calendarName||null===e.trigger.calendarName)&&(e.trigger.calendarName=""),("undefined"==typeof e.trigger.recurrenceInterval||null===e.trigger.recurrenceInterval)&&(e.trigger.recurrenceInterval=1),("undefined"==typeof e.trigger.recurrenceIntervalUnit||null===e.trigger.recurrenceIntervalUnit)&&(e.trigger.recurrenceIntervalUnit="DAY"),("undefined"==typeof e.trigger.occurrenceCount||null===e.trigger.occurrenceCount)&&(e.trigger.occurrenceCount=""),"undefined"==typeof e.trigger.endDate||null===e.trigger.endDate?e.trigger.endDate="":e.trigger.endDate=this.formatDate(e.trigger.endDate),e.trigger.endDate?e.trigger.endDate&&(e.trigger.radioEndDate="specificDate",e.trigger.occurrenceCount=""):-1!=e.trigger.occurrenceCount&&e.trigger.occurrenceCount?e.trigger.radioEndDate="numberOfTimes":(e.trigger.radioEndDate="indefinitely",e.trigger.occurrenceCount=""),e.trigger.months||(e.trigger.months={}),s.isArray(e.trigger.months.month)||(e.trigger.months.month=[]),e.trigger.radioWhichMonth="everyMonth",0<e.trigger.months.month.length&&e.trigger.months.month.length<12?e.trigger.radioWhichMonth="selectedMonths":12==e.trigger.months.month.length&&(e.trigger.radioWhichMonth="everyMonth",e.trigger.months.month=[]),e.trigger.weekDays||(e.trigger.weekDays={}),s.isArray(e.trigger.weekDays.day)||(e.trigger.weekDays.day=[]),"undefined"==typeof e.trigger.monthDays||null===e.trigger.monthDays?e.trigger.monthDays="":e.trigger.monthDays=e.trigger.monthDays.toString().replace(/ /g,"").replace(/,/g,", "),"undefined"!=typeof e.trigger.daysType?"month"==e.trigger.daysType.toLowerCase()?(e.trigger.radioWhichDay="datesInMonth",e.trigger.weekDays.day=[]):"week"==e.trigger.daysType.toLowerCase()?(e.trigger.radioWhichDay="selectedDays",e.trigger.monthDays=""):(e.trigger.radioWhichDay="everyDay",e.trigger.weekDays.day=[],e.trigger.monthDays=""):0<e.trigger.weekDays.day.length&&e.trigger.weekDays.day.length<7?e.trigger.radioWhichDay="selectedDays":e.trigger.monthDays?e.trigger.radioWhichDay="datesInMonth":e.trigger.radioWhichDay="everyDay","undefined"==typeof e.trigger.hours||null===e.trigger.hours?e.trigger.hours="0":e.trigger.hours=e.trigger.hours.toString().replace(/ /g,"").replace(/,/g,", "),"undefined"==typeof e.trigger.minutes||null===e.trigger.minutes?e.trigger.minutes="0":e.trigger.minutes=e.trigger.minutes.toString().replace(/ /g,"").replace(/,/g,", ")),"undefined"==typeof e.repositoryDestination||null===e.repositoryDestination)e.repositoryDestination={overwriteFiles:!0,sequentialFilenames:!1,saveToRepository:!0,timestampPattern:"yyyyMMddHHmm",outputFTPInfo:{propertiesMap:{},type:"ftp",port:"21",implicit:!0,pbsz:0}};else{if("undefined"==typeof e.repositoryDestination.outputFTPInfo||null===e.repositoryDestination.outputFTPInfo)e.repositoryDestination.outputFTPInfo={};else{var r=e.repositoryDestination.outputFTPInfo;e.repositoryDestination.outputFTPInfo.type="ftp"==e.repositoryDestination.outputFTPInfo.type?"TYPE_FTP":"TYPE_FTPS",r.enabled=!!r.serverName&&!!r.userName}e.repositoryDestination.timestampPattern||(e.repositoryDestination.timestampPattern="yyyyMMddHHmm")}("undefined"==typeof e.repositoryDestination.outputFTPInfo.port||null===e.repositoryDestination.outputFTPInfo.port)&&(e.repositoryDestination.outputFTPInfo.port="21");var i="SEND";if(e.mailNotification=e.mailNotification||{toAddresses:{address:""},ccAddresses:{address:""},bccAddresses:{address:""},subject:"",messageText:"",resultSendType:i},e.alert=e.alert||{toAddresses:{address:""},subject:"",messageText:"",messageTextWhenJobFails:"",jobState:"NONE"},"undefined"==typeof e.mailNotification.resultSendType||null===e.mailNotification.resultSendType)e.mailNotification.resultSendType=i;else{var o=e.mailNotification.resultSendType;"SEND"!==o&&"SEND_ATTACHMENT"!==o&&"SEND_ATTACHMENT_NOZIP"!==o&&"SEND_EMBED"!==o&&"SEND_ATTACHMENT_ZIP_ALL"!==o&&"SEND_EMBED_ZIP_ALL_OTHERS"!==o&&(e.mailNotification.resultSendType=i)}var n,a=["toAddresses","ccAddresses","bccAddresses"];for(n=0;n<a.length;n++)"undefined"==typeof e.mailNotification[a[n]]||null===e.mailNotification[a[n]]?e.mailNotification[a[n]]={address:""}:("undefined"==typeof e.mailNotification[a[n]].address||null===e.mailNotification[a[n]].address)&&(e.mailNotification[a[n]].address=""),s.isArray(e.mailNotification[a[n]].address)&&e.mailNotification[a[n]].address.length>0?e.mailNotification[a[n]].address=e.mailNotification[a[n]].address.join(t+" "):e.mailNotification[a[n]].address="";return e.mailNotification.toAddresses.address||(e.mailNotification={toAddresses:{address:""},ccAddresses:{address:""},bccAddresses:{address:""},subject:"",messageText:"",resultSendType:i}),"undefined"==typeof e.alert.toAddresses||null===e.alert.toAddresses?e.alert.toAddresses={address:""}:("undefined"==typeof e.alert.toAddresses.address||null===e.alert.toAddresses.address)&&(e.alert.toAddresses.address=""),s.isArray(e.alert.toAddresses.address)&&e.alert.toAddresses.address.length>0?e.alert.toAddresses.address=e.alert.toAddresses.address.join(t+" "):e.alert={toAddresses:{address:""},subject:"",messageText:"",messageTextWhenJobFails:"",jobState:"NONE",includingReportJobInfo:!1,includingStackTrace:!1},e},toJSON:function(){var e=jQuery.extend(!0,{},this.attributes),i={};if(e.trigger&&(e.trigger.startType=parseInt(e.trigger.startType,10),1===e.trigger.startType?e.trigger.startDate=null:e.trigger.startDate=g(e.trigger.startDate,p,!0).format(r),"none"===e.trigger.type?(e.trigger.type="simpleTrigger",e.trigger.endDate=null,e.trigger.occurrenceCount=1,e.trigger.recurrenceInterval=null,e.trigger.recurrenceIntervalUnit=null,delete e.trigger.hours,delete e.trigger.minutes,delete e.trigger.months,delete e.trigger.daysType,delete e.trigger.weekDays,delete e.trigger.monthDays,delete e.trigger.calendarName):"simple"===e.trigger.type?(e.trigger.type="simpleTrigger","numberOfTimes"==e.trigger.radioEndDate&&(e.trigger.endDate=null),"specificDate"==e.trigger.radioEndDate&&(e.trigger.occurrenceCount=-1,e.trigger.endDate=g(e.trigger.endDate,p,!0).format(r)),"indefinitely"==e.trigger.radioEndDate&&(e.trigger.occurrenceCount=-1,e.trigger.endDate=null),e.trigger.recurrenceInterval=parseInt(e.trigger.recurrenceInterval,10),e.trigger.recurrenceIntervalUnit=e.trigger.recurrenceIntervalUnit.toUpperCase(),""===e.trigger.calendarName&&(e.trigger.calendarName=null),delete e.trigger.hours,delete e.trigger.minutes,delete e.trigger.months,delete e.trigger.daysType,delete e.trigger.weekDays,delete e.trigger.monthDays):"calendar"===e.trigger.type&&(e.trigger.type="calendarTrigger","everyMonth"===e.trigger.radioWhichMonth&&(e.trigger.months.month=[1,2,3,4,5,6,7,8,9,10,11,12]),"everyDay"===e.trigger.radioWhichDay||7==e.trigger.weekDays.day.length?(e.trigger.daysType="ALL",e.trigger.weekDays.day=[],e.trigger.monthDays=""):"selectedDays"===e.trigger.radioWhichDay?(e.trigger.daysType="WEEK",e.trigger.monthDays=""):"datesInMonth"===e.trigger.radioWhichDay&&(e.trigger.daysType="MONTH",e.trigger.weekDays.day=[]),e.trigger.monthDays=e.trigger.monthDays.replace(/ /g,""),""===e.trigger.endDate?delete e.trigger.endDate:e.trigger.endDate=g(e.trigger.endDate,p,!0).format(r),e.trigger.hours=e.trigger.hours.replace(/ /g,""),e.trigger.minutes=e.trigger.minutes.replace(/ /g,""),""===e.trigger.calendarName&&(e.trigger.calendarName=null),delete e.trigger.occurrenceCount,delete e.trigger.recurrenceInterval,delete e.trigger.recurrenceIntervalUnit),delete e.trigger.radioEndDate,delete e.trigger.radioWhichMonth,delete e.trigger.radioWhichDay,e.trigger&&e.trigger.type&&(i[e.trigger.type]=e.trigger,delete i[e.trigger.type].type,e.trigger=i)),e.repositoryDestination&&(e.repositoryDestination.outputFTPInfo&&(e.repositoryDestination.outputFTPInfo.enabled||(e.repositoryDestination.outputFTPInfo={type:"TYPE_FTP",port:21,folderPath:null,password:null,propertiesMap:{},serverName:null,userName:null}),e.repositoryDestination.outputFTPInfo.type&&(e.repositoryDestination.outputFTPInfo.type="TYPE_FTP"==e.repositoryDestination.outputFTPInfo.type?"ftp":"ftps"),"enabled"in e.repositoryDestination.outputFTPInfo&&delete e.repositoryDestination.outputFTPInfo.enabled),e.repositoryDestination.sequentialFilenames||(e.repositoryDestination.timestampPattern=null)),e.mailNotification){var o,s=["toAddresses","ccAddresses","bccAddresses"];for(o=0;o<s.length;o++)if(e.mailNotification[s[o]]&&"undefined"!=typeof e.mailNotification[s[o]].address){if(!e.mailNotification[s[o]].address){delete e.mailNotification[s[o]];continue}e.mailNotification[s[o]].address=e.mailNotification[s[o]].address.replace(/ /g,"").split(t)}e.mailNotification.toAddresses&&"undefined"!=typeof e.mailNotification.toAddresses.address||delete e.mailNotification}return e.alert&&(e.alert.toAddresses&&"undefined"!=typeof e.alert.toAddresses.address&&(e.alert.toAddresses.address?e.alert.toAddresses.address=e.alert.toAddresses.address.replace(/ /g,"").split(t):delete e.alert.toAddresses.address),e.alert.toAddresses&&"undefined"!=typeof e.alert.toAddresses.address||delete e.alert),e},state:function(e){var t={jobId:[this.id],toJSON:function(){return this},trigger:function(){return this}};return this.get("state").value=e?"NORMAL":"PAUSED",this.trigger("change"),n.sync.call(this,"update",t,{url:this.collection.url+"/"+(e?"resume":"pause"),type:"POST"})},parameters:function(e,t){e=e||this.get("source").reportUnitURI;var r=d.contextPath+"/rest_v2/reports"+e+"/inputControls/";return n.sync.call(this,"read",new n.Model,{url:r,type:"GET",success:function(e,r){"function"==typeof t&&t(void 0,e)},error:function(e){"function"==typeof t&&t(e)}})},save:function(e,t){var r,i=this,o=this,s=1,a=function(){n.Model.prototype.save.call(i,e,t)},d=function(e){return e?void o.trigger("saveValidationFailed"):(--s,r&&clearTimeout(r),void(r=setTimeout(function(){0===s&&a()})))},g=function(){return s++,d};this.testOutputLocalFolder(d),this.trigger("save",this,g)},testFTPConnection:function(e){if(i)return void("function"==typeof e&&e());var t=this.get("repositoryDestination");if(!(t&&t.outputFTPInfo&&t.outputFTPInfo.enabled))return void("function"==typeof e&&e());t=t.outputFTPInfo;var r=this,s={host:t.serverName,userName:t.userName,password:t.password,folderPath:t.folderPath,type:"TYPE_FTP"==t.type?"ftp":"ftps",protocol:t.protocol,port:t.port,implicit:t.implicit,prot:t.prot,pbsz:t.pbsz,toJSON:function(){return this},trigger:function(){return this}};return i=!0,o("#ftpTestButton").addClass("checking"),o("[data-field=ftpTest]").text(""),n.sync.call(r,"update",s,{url:d.contextPath+"/rest_v2/connections",contentType:"application/connections.ftp+json",headers:{Accept:"application/json"},type:"POST",success:function(t,s){i=!1,o("#ftpTestButton").removeClass("checking"),r.trigger("valid",r,[{field:"ftpTest",errorCode:"report.scheduling.connection.passed"}]),"function"==typeof e&&e(void 0)},error:function(t){i=!1,o("#ftpTestButton").removeClass("checking"),r.trigger("invalid",r,[{field:"ftpTest",errorCode:"report.scheduling.connection.failed"}]),"function"==typeof e&&e(t)}})},testOutputLocalFolder:function(e){var t=this;if(!t.get("repositoryDestination")||!t.get("repositoryDestination").outputLocalFolder)return"function"==typeof e&&e(),void t.trigger("saveValidationFailed");var r={path:t.get("repositoryDestination").outputLocalFolder,toJSON:function(){return this},trigger:function(){return this}};return n.sync.call(t,"update",r,{url:d.contextPath+"/rest_v2/connections",contentType:"application/connections.lfs+json",type:"POST",headers:{Accept:"application/json"},success:function(t,r){"function"==typeof e&&e(void 0)},error:function(r){t.trigger("invalid",t,[{field:"outputHostFileSystem",errorCode:"report.scheduling.output.localhostpath"}],{switchToErrors:!0}),"function"==typeof e&&e(r)}})},permission:function(e){var t=this.get("repositoryDestination").folderURI;return"ja"==d.userLocale&&isIE8()&&(t=a.convert(t,{to:"SJIS",from:"UNICODE",type:"string"})),n.sync.call(this,"read",new n.Model,{url:d.contextPath+"/rest_v2/resources"+t,headers:{Accept:"application/repository.folder+json"},type:"GET",success:function(t,r){"function"==typeof e&&e(void 0,t.permissionMask)},error:function(t){"function"==typeof e&&e(t)}})},resource:function(e,t){return n.sync.call(this,"read",new n.Model,{url:d.contextPath+"/rest_v2/resources"+this.get("source").reportUnitURI,headers:{Accept:"application/repository."+e+"+json"},type:"GET",success:function(e,r){"function"==typeof t&&t(void 0,e)},error:function(e){"function"==typeof t&&t(e)}})},parseUri:function(e){var t;return"/"!==e[0]&&(e="/"+e),e&&(t=e.match(/^(.*)\/([^\/]+)$/))?(""===t[1]&&(t[1]="/"),{full:t[0],folder:t[1],file:t[2]}):{}},createFromUri:function(e){this.trigger("clearAllErrors"),this.clear({silent:!0}),e=this.parseUri(e),this.set(this.parse({baseOutputFilename:e.file,outputFormats:{outputFormat:["PDF"]},source:{reportUnitURI:e.full},trigger:{timezone:d.usersTimeZone||"America/Los_Angeles"},outputTimeZone:d.usersTimeZone||"America/Los_Angeles",repositoryDestination:{overwriteFiles:!0,sequentialFilenames:!1,folderURI:e.folder,saveToRepository:!0,timestampPattern:"yyyyMMddHHmm",outputFTPInfo:{propertiesMap:{},type:"ftp",port:"21",implicit:!0,pbsz:0}}}))},formatDate:function(e){return e?g(e,r).format(p):""},parseIntervals:function(e,t){if(e=e||!1,!e)return!1;e=e.replace(/ /g,"");var r,i,o,s=e.split(","),n=[],a={},d=1,g=31;if("hours"===t&&(d=0,g=23),"minutes"===t&&(d=0,g=59),s.length<1)return!1;for(r=0;r<s.length;r++)if(-1!==s[r].indexOf("-")){if(o=s[r].split("-"),!this.isNumeric(o[0],{allowZero:!0}))return!1;if(!this.isNumeric(o[1],{allowZero:!0}))return!1;if(o[0]=parseInt(o[0],10),o[1]=parseInt(o[1],10),!(d<=o[0]&&o[0]<=g))return!1;if(!(d<=o[1]&&o[1]<=g))return!1;if(o[0]>=o[1])return!1;for(i=o[0];i<=o[1];i++)"undefined"==typeof a[i]&&(n.push(i),a[i]=1)}else{if(!this.isNumeric(s[r],{allowZero:!0}))return!1;if(s[r]=parseInt(s[r],10),!(d<=s[r]&&s[r]<=g))return!1;"undefined"==typeof a[s[r]]&&(n.push(s[r]),a[s[r]]=1)}return n},ifPastDate:function(e,t){if(!e||!t)return!1;var r,i,o,s,n;return r=getTZOffset(t),i=-1*g().zone()/60,o=i-r,s=+g().format("X"),n=+g(e,p,!0).add("minute",1).format("X")+3600*o,s>n},isHostName:function(e){if(!e)return!1;var t=/^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\-]*[a-zA-Z0-9])\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\-]*[A-Za-z0-9])$/;return t.test(e)},isEmail:function(e){if(!e)return!1;var t=/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;return t.test(e)},isValidFileName:function(e){if(!e)return!1;var t=/^(\w|\d|\_|\.|\-|[;@]|[^\x00-\x80])+$/;return t.test(e)},validateEmails:function(e){if(!e)return!1;e=e.replace(/ /g,""),e=e.split(t);for(var r=0;r<e.length;r++)if(!this.isEmail(e[r]))return!1;return!0},isNumeric:function(e,t){var r;if(!e)return!1;if(t=t||{},t.allowNegative=t.allowNegative||!1,t.allowZero=t.allowZero||!1,t.maxValue=t.maxValue||!1,t.minValue=t.minValue||!1,"string"==typeof e){if(e.match(/\D/))return!1;if(r=parseInt(e,10),s.isNaN(r))return!1}return t.allowNegative===!1&&0>r?!1:t.allowZero===!1&&0===r?!1:t.maxValue&&t.maxValue<r?!1:t.minValue&&r<t.minValue?!1:!0},buildDate:function(e,t){var r=getTZOffset(t)+4,i=r>0?"+":"-";return r=Math.abs(r),10>r&&(r="0"+r),e.replace(" ","T")+":00"+i+r+":00"}})});