define(["require","underscore","request","jquery"],function(e){function r(e){var r,o=null;return e.firstChild.innerText&&""!==e.firstChild.innerText&&e.body?(r=new window.ActiveXObject("Microsoft.XMLDOM"),r.async=!1,r.loadXML(e.firstChild.innerText.replace("\r\n-","\r\n"))):r=e,r.firstChild&&(o=r.firstChild.nodeName.toLowerCase()),o&&"html"!==o&&"#comment"!==o?t("xml"===r.firstChild.nodeName.toLowerCase()?r.firstChild.nextSibling:r.firstChild):{errorCode:"unexpected.error"}}function t(e){var r,o;if(e.children)r=e.children;else{r=[];for(var n=0,i=e.childNodes.length;i>n;n++)null===e.childNodes[n].nodeValue&&r.push(e.childNodes[n])}if(0===r.length&&1===e.childNodes.length)return e.childNodes[0].nodeValue;if(r.length>1&&r[0].nodeName===r[1].nodeName){o=[];for(var n=0,i=r.length;i>n;n++)o[n]=t(r[n])}else{o={};for(var n=0,i=r.length;i>n;n++)o[r[n].nodeName]=t(r[n])}return o}function o(e){var r=new d.Deferred;return e.submit(function(t){t.preventDefault(),a({url:e.attr("action"),type:e.attr("method"),data:new FormData(e[0]),cache:!1,contentType:!1,processData:!1,headers:{Accept:"application/json"}}).done(function(e){r.resolve(e)}).fail(function(e){e=e.responseJSON||e||{},i.defaults(e,{errorCode:"error.load.error"}),r.reject(e)}),e.unbind("submit")}),e.submit(),r}function n(e,t){var o=d.Deferred(),n=d("<iframe style='display:none' name='"+t+"'></iframe>");return e.append(n).attr("target",t),n.load(function(){try{o.resolve(r(this.contentWindow.document))}catch(e){o.reject({errorCode:"error.invalid.response"})}n.remove()}),n.on("abort",function(){o.reject({errorCode:"error.load.aborted"}),n.remove()}),n.on("error",function(){o.reject({errorCode:"error.load.error"}),n.remove()}),e.submit(),o}var i=e("underscore"),a=e("request"),d=e("jquery"),s=function(e,t,o,n){this.name=i.uniqueId("uploadTarget"),this.form=d(e),t&&this.form.attr("action",t),t&&this.form.attr("method",o),t&&this.form.attr("enctype",n),this.parceXmlDocToObject=r};return s.prototype.submit=function(){return window.FormData?o(this.form):n(this.form,this.name)},s});