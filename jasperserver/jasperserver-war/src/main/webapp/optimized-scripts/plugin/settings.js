define(["require","request","requestSettings","underscore","jrs.configs"],function(e){"use strict";var t=e("request"),n=e("requestSettings"),r=e("underscore"),s=e("jrs.configs"),i=s.contextPath+"/rest_v2/settings/",u=function(e,s){var u=r.extend({},n,{type:"GET",dataType:"json",url:i+e});t(u).fail(function(){s(null)}).then(function(e){s(e)})};return u.load=function(e,t,n,r){return r.isBuild?void n():void u(e,n)},u});