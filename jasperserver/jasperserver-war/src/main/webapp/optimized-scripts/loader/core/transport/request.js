define(["require","jquery","xdm","fakeXhrFactory","logger"],function(e){"use strict";function r(){var e=[];return e.push({name:"logEnabled",value:s.get("enabled")}),s.get("level")&&e.push({name:"logLevel",value:s.get("level")}),n.param(e)}var t,n=e("jquery"),o=e("xdm"),a=n.Deferred,u=e("fakeXhrFactory"),s=e("logger"),c=function(e){t=new o.Rpc({remote:e+"?"+r(),container:document.body,props:{style:{display:"none"}}},{remote:{request:{},abort:{}}})},i=function(e,r){var n=new a,o=e.error;return r=r||e.success,t?(t.request(e,function(e){n.resolve(e.data,e.status,u(e.xhr))},function(e){n.reject(e.message,e.data)}),r&&n.done(r),o&&n.error(o)):n.rejectWith(new Error("RPC object is not initialized")),n.promise()};return i.rpc=c,i});