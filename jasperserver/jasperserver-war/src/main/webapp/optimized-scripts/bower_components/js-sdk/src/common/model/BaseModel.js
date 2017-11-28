define(["require","underscore","backbone","common/enum/httpStatusCodes","common/enum/errorCodes","json3"],function(r){var e=r("underscore"),o=r("backbone"),n=r("common/enum/httpStatusCodes"),t=r("common/enum/errorCodes"),s=r("json3"),a=o.Model.extend({initialize:function(){this.on("error",a.unifyServerErrors)},serialize:function(){return e.clone(this.attributes)}},{unifyServerErrors:function(r,e){var o=n[e.status],t=a.createServerError(e);r.trigger("error:"+o,r,t,e),r.trigger("error:all",r,t,e)},createServerError:function(r){var e;try{e=s.parse(r.responseText)}catch(o){e={message:"Can't parse server response",errorCode:t.UNEXPECTED_ERROR,parameters:[]}}return e}});return a});