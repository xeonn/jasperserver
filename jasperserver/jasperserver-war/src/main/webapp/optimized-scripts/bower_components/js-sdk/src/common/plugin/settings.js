define(["require","common/transport/request","common/config/requestSettings","underscore","jrs.configs"],function(n){"use strict";var t=n("common/transport/request"),e=n("common/config/requestSettings"),o=n("underscore"),r=n("jrs.configs"),s=r.contextPath+"/rest_v2/settings/",i=function(n,r){var i=o.extend({},e,{type:"GET",dataType:"json",url:s+n});t(i).fail(function(){r(null)}).then(function(n){r(n)})};return i.load=function(n,t,e,o){return o.isBuild?void e():void i(n,e)},i});