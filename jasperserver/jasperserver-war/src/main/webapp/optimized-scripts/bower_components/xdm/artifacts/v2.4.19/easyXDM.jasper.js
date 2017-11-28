!function(e){"use strict";define(["json3","logger"],e)}(function(e,n){function t(e,n){var t=typeof e[n];return"function"==t||!("object"!=t||!e[n])||"unknown"==t}function o(e,n){return!("object"!=typeof e[n]||!e[n])}function r(e){return"[object Array]"===Object.prototype.toString.call(e)}function a(){var e="Shockwave Flash",n="application/x-shockwave-flash";if(!g(navigator.plugins)&&"object"==typeof navigator.plugins[e]){var t=navigator.plugins[e].description;t&&!g(navigator.mimeTypes)&&navigator.mimeTypes[n]&&navigator.mimeTypes[n].enabledPlugin&&(M=t.match(/\d+/g))}if(!M){var o;try{o=new ActiveXObject("ShockwaveFlash.ShockwaveFlash"),M=Array.prototype.slice.call(o.GetVariable("$version").match(/(\d+),(\d+),(\d+),(\d+)/),1),o=null}catch(r){}}if(!M)return!1;var a=parseInt(M[0],10),i=parseInt(M[1],10);return O=a>9&&i>0,!0}function i(){if(!q){q=!0,L("firing dom_onReady");for(var e=0;e<U.length;e++)U[e]();U.length=0}}function c(e,n){return q?void e.call(n):void U.push(function(){e.call(n)})}function s(){var e=parent;if(""!==X)for(var n=0,t=X.split(".");n<t.length;n++)e=e[t[n]];return e.easyXDM}function l(e){return L("Settings namespace to '"+e+"'"),window.easyXDM=B,X=e,X&&(I="easyXDM_"+X.replace(".","_")+"_"),j}function u(e){return e.match(N)[3]}function d(e){return e.match(N)[4]||""}function p(e){var n=e.toLowerCase().match(N),t=n[2],o=n[3],r=n[4]||"";return("http:"==t&&":80"==r||"https:"==t&&":443"==r)&&(r=""),t+"//"+o+r}function f(e){if(e=e.replace(H,"$1/"),!e.match(/^(http||https):\/\//)){var n="/"===e.substring(0,1)?"":location.pathname;"/"!==n.substring(n.length-1)&&(n=n.substring(0,n.lastIndexOf("/")+1)),e=location.protocol+"//"+location.host+n+e}for(;P.test(e);)e=e.replace(P,"");return L("resolved url '"+e+"'"),e}function m(e,n){var t="",o=e.indexOf("#");-1!==o&&(t=e.substring(o),e=e.substring(0,o));var r=[];for(var a in n)n.hasOwnProperty(a)&&r.push(a+"="+encodeURIComponent(n[a]));return e+(J?"#":-1==e.indexOf("?")?"?":"&")+r.join("&")+t}function g(e){return"undefined"==typeof e}function h(e,n,t){var o;for(var r in n)n.hasOwnProperty(r)&&(r in e?(o=n[r],"object"==typeof o?h(e[r],o,t):t||(e[r]=n[r])):e[r]=n[r]);return e}function v(){var e=document.body.appendChild(document.createElement("form")),n=e.appendChild(document.createElement("input"));n.name=I+"TEST"+F,x=n!==e.elements[n.name],document.body.removeChild(e),L("HAS_NAME_PROPERTY_BUG: "+x)}function w(e){L("creating frame: "+e.props.src),g(x)&&v();var n;x?n=document.createElement('<iframe name="'+e.props.name+'"/>'):(n=document.createElement("IFRAME"),n.name=e.props.name),n.id=n.name=e.props.name,delete e.props.name,"string"==typeof e.container&&(e.container=document.getElementById(e.container)),e.container||(h(n.style,{position:"absolute",top:"-2000px",left:"0px"}),e.container=document.body);var t=e.props.src;if(e.props.src="javascript:false",h(n,e.props),n.border=n.frameBorder=0,n.allowTransparency=!0,e.container.appendChild(n),e.onLoad&&T(n,"load",e.onLoad),e.usePost){var o,r=e.container.appendChild(document.createElement("form"));if(r.target=n.name,r.action=t,r.method="POST","object"==typeof e.usePost)for(var a in e.usePost)e.usePost.hasOwnProperty(a)&&(x?o=document.createElement('<input name="'+a+'"/>'):(o=document.createElement("INPUT"),o.name=a),o.value=e.usePost[a],r.appendChild(o));r.submit(),r.parentNode.removeChild(r)}else n.src=t;return e.props.src=t,n}function y(e,n){"string"==typeof e&&(e=[e]);for(var t,o=e.length;o--;)if(t=e[o],t=new RegExp("^"==t.substr(0,1)?t:"^"+t.replace(/(\*)/g,".$1").replace(/\?/g,".")+"$"),t.test(n))return!0;return!1}function b(e){var n,o=e.protocol;if(e.isHost=e.isHost||g(z.xdm_p),J=e.hash||!1,L("preparing transport stack"),e.props||(e.props={}),e.isHost)e.remote=f(e.remote),e.channel=e.channel||"default"+F++,e.secret=Math.random().toString(16).substring(2),g(o)?(o=p(location.href)==p(e.remote)?"4":t(window,"postMessage")||t(document,"postMessage")?"1":e.swf&&t(window,"ActiveXObject")&&a()?"6":"Gecko"===navigator.product&&"frameElement"in window&&-1==navigator.userAgent.indexOf("WebKit")?"5":e.remoteHelper?"2":"0",L("selecting protocol: "+o)):L("using protocol: "+o);else if(L("using parameters from query"),e.channel=z.xdm_c.replace(/["'<>\\]/g,""),e.secret=z.xdm_s,e.remote=z.xdm_e.replace(/["'<>\\]/g,""),o=z.xdm_p,e.acl&&!y(e.acl,e.remote))throw new Error("Access denied for "+e.remote);switch(e.protocol=o,o){case"0":if(h(e,{interval:100,delay:2e3,useResize:!0,useParent:!1,usePolling:!1},!0),e.isHost){if(!e.local){L("looking for image to use as local");for(var r,i=location.protocol+"//"+location.host,c=document.body.getElementsByTagName("img"),s=c.length;s--;)if(r=c[s],r.src.substring(0,i.length)===i){e.local=r.src;break}e.local||(L("no image found, defaulting to using the window"),e.local=window)}var l={xdm_c:e.channel,xdm_p:0};e.local===window?(e.usePolling=!0,e.useParent=!0,e.local=location.protocol+"//"+location.host+location.pathname+location.search,l.xdm_e=e.local,l.xdm_pa=1):l.xdm_e=f(e.local),e.container&&(e.useResize=!1,l.xdm_po=1),e.remote=m(e.remote,l)}else h(e,{channel:z.xdm_c,remote:z.xdm_e,useParent:!g(z.xdm_pa),usePolling:!g(z.xdm_po),useResize:e.useParent?!1:e.useResize});n=[new j.stack.HashTransport(e),new j.stack.ReliableBehavior({}),new j.stack.QueueBehavior({encode:!0,maxLength:4e3-e.remote.length}),new j.stack.VerifyBehavior({initiate:e.isHost})];break;case"1":n=[new j.stack.PostMessageTransport(e)];break;case"2":e.isHost&&(e.remoteHelper=f(e.remoteHelper)),n=[new j.stack.NameTransport(e),new j.stack.QueueBehavior,new j.stack.VerifyBehavior({initiate:e.isHost})];break;case"3":n=[new j.stack.NixTransport(e)];break;case"4":n=[new j.stack.SameOriginTransport(e)];break;case"5":n=[new j.stack.FrameElementTransport(e)];break;case"6":M||a(),n=[new j.stack.FlashTransport(e)]}return n.push(new j.stack.QueueBehavior({lazy:e.lazy,remove:!0})),n}function k(e){for(var n,t={incoming:function(e,n){this.up.incoming(e,n)},outgoing:function(e,n){this.down.outgoing(e,n)},callback:function(e){this.up.callback(e)},init:function(){this.down.init()},destroy:function(){this.down.destroy()}},o=0,r=e.length;r>o;o++)n=e[o],h(n,t,!0),0!==o&&(n.down=e[o-1]),o!==r-1&&(n.up=e[o+1]);return n}function _(e){e.up.down=e.down,e.down.up=e.up,e.up=e.down=null}if("undefined"==typeof document)return{};var x,M,O,T,R,S=window.setTimeout,D=n.register("EasyXDM"),E=this,F=Math.floor(1e4*Math.random()),C=Function.prototype,N=/^((http.?:)\/\/([^:\/\s]+)(:\d+)*)/,P=/[\-\w]+\/\.\.\//,H=/([^:])\/\//g,X="",j={},B=window.easyXDM,I="easyXDM_",J=!1,L=C;if(t(window,"addEventListener"))T=function(e,n,t){L("adding listener "+n),e.addEventListener(n,t,!1)},R=function(e,n,t){L("removing listener "+n),e.removeEventListener(n,t,!1)};else{if(!t(window,"attachEvent"))throw new Error("Browser not supported");T=function(e,n,t){L("adding listener "+n),e.attachEvent("on"+n,t)},R=function(e,n,t){L("removing listener "+n),e.detachEvent("on"+n,t)}}var A,q=!1,U=[];if("readyState"in document?(A=document.readyState,q="complete"==A||~navigator.userAgent.indexOf("AppleWebKit/")&&("loaded"==A||"interactive"==A)):q=!!document.body,!q){if(t(window,"addEventListener"))T(document,"DOMContentLoaded",i);else if(T(document,"readystatechange",function(){"complete"==document.readyState&&i()}),document.documentElement.doScroll&&window===top){var W=function(){if(!q){try{document.documentElement.doScroll("left")}catch(e){return void S(W,1)}i()}};W()}T(window,"load",i)}var z=function(e){e=e.substring(1).split("&");for(var n,t={},o=e.length;o--;)n=e[o].split("="),t[n[0]]=decodeURIComponent(n[1]);return t}(/xdm_e=/.test(location.search)?location.search:location.hash),V=function(){var e={},n={a:[1,2,3]},t='{"a":[1,2,3]}';return"undefined"!=typeof JSON&&"function"==typeof JSON.stringify&&JSON.stringify(n).replace(/\s/g,"")===t?JSON:(Object.toJSON&&Object.toJSON(n).replace(/\s/g,"")===t&&(e.stringify=Object.toJSON),"function"==typeof String.prototype.evalJSON&&(n=t.evalJSON(),n.a&&3===n.a.length&&3===n.a[2]&&(e.parse=function(e){return e.evalJSON()})),e.stringify&&e.parse?(V=function(){return e},e):null)};h(j,{version:"2.4.19.1",query:z,stack:{},apply:h,getJSONObject:V,whenReady:c,noConflict:l});var Q={_deferred:[],flush:function(){this.trace("... deferred messages ...");for(var e=0,n=this._deferred.length;n>e;e++)this.trace(this._deferred[e]);this._deferred.length=0,this.trace("... end of deferred messages ...")},getTime:function(){var e=new Date,n=e.getHours()+"",t=e.getMinutes()+"",o=e.getSeconds()+"",r=e.getMilliseconds()+"",a="000";return 1==n.length&&(n="0"+n),1==t.length&&(t="0"+t),1==o.length&&(o="0"+o),r=a.substring(r.length)+r,n+":"+t+":"+o+"."+r},log:function(e){D.debug(location.host+(X?":"+X:"")+": "+e)},getTracer:function(e){var t=n.register(e);return function(e){t.debug(e)}}};return Q.log("easyXDM present on '"+location.href),j.Debug=Q,L=Q.getTracer("EasyXDM.{Private}"),j.DomHelper={on:T,un:R,requiresJSON:function(e){o(window,"JSON")?Q.log("native JSON found"):(Q.log("loading external JSON"),document.write('<script type="text/javascript" src="'+e+'"></script>'))}},function(){var e={};j.Fn={set:function(n,t){this._trace("storing function "+n),e[n]=t},get:function(n,t){if(this._trace("retrieving function "+n),e.hasOwnProperty(n)){var o=e[n];return o||this._trace(n+" not found"),t&&delete e[n],o}}},j.Fn._trace=Q.getTracer("easyXDM.Fn")}(),j.Socket=function(e){var n=Q.getTracer("easyXDM.Socket");n("constructor");var t=k(b(e).concat([{incoming:function(n,t){e.onMessage(n,t)},callback:function(n){e.onReady&&e.onReady(n)}}])),o=p(e.remote);this.origin=p(e.remote),this.destroy=function(){t.destroy()},this.postMessage=function(e){t.outgoing(e,o)},t.init()},j.Rpc=function(e,n){var t=Q.getTracer("easyXDM.Rpc");if(t("constructor"),n.local)for(var o in n.local)if(n.local.hasOwnProperty(o)){var r=n.local[o];"function"==typeof r&&(n.local[o]={method:r})}var a=k(b(e).concat([new j.stack.RpcBehavior(this,n),{callback:function(n){e.onReady&&e.onReady(n)}}]));this.origin=p(e.remote),this.destroy=function(){a.destroy()},a.init()},j.stack.SameOriginTransport=function(e){var n=Q.getTracer("easyXDM.stack.SameOriginTransport");n("constructor");var t,o,r,a;return t={outgoing:function(e,n,t){r(e),t&&t()},destroy:function(){n("destroy"),o&&(o.parentNode.removeChild(o),o=null)},onDOMReady:function(){n("init"),a=p(e.remote),e.isHost?(h(e.props,{src:m(e.remote,{xdm_e:location.protocol+"//"+location.host+location.pathname,xdm_c:e.channel,xdm_p:4}),name:I+e.channel+"_provider"}),o=w(e),j.Fn.set(e.channel,function(e){return r=e,S(function(){t.up.callback(!0)},0),function(e){t.up.incoming(e,a)}})):(r=s().Fn.get(e.channel,!0)(function(e){t.up.incoming(e,a)}),S(function(){t.up.callback(!0)},0))},init:function(){c(t.onDOMReady,t)}}},j.stack.FlashTransport=function(e){function n(e){S(function(){o("received message"),r.up.incoming(e,i)},0)}function t(n){o("creating factory with SWF from "+n);var t=e.swf+"?host="+e.isHost,r="easyXDM_swf_"+Math.floor(1e4*Math.random());j.Fn.set("flash_loaded"+n.replace(/[\-.]/g,"_"),function(){j.stack.FlashTransport[n].swf=s=l.firstChild;for(var e=j.stack.FlashTransport[n].queue,t=0;t<e.length;t++)e[t]();e.length=0}),e.swfContainer?l="string"==typeof e.swfContainer?document.getElementById(e.swfContainer):e.swfContainer:(l=document.createElement("div"),h(l.style,O&&e.swfNoThrottle?{height:"20px",width:"20px",position:"fixed",right:0,top:0}:{height:"1px",width:"1px",position:"absolute",overflow:"hidden",right:0,top:0}),document.body.appendChild(l));var a="callback=flash_loaded"+encodeURIComponent(n.replace(/[\-.]/g,"_"))+"&proto="+E.location.protocol+"&domain="+encodeURIComponent(u(E.location.href))+"&port="+encodeURIComponent(d(E.location.href))+"&ns="+encodeURIComponent(X);a+="&log=true",l.innerHTML="<object height='20' width='20' type='application/x-shockwave-flash' id='"+r+"' data='"+t+"'><param name='allowScriptAccess' value='always'></param><param name='wmode' value='transparent'><param name='movie' value='"+t+"'></param><param name='flashvars' value='"+a+"'></param><embed type='application/x-shockwave-flash' FlashVars='"+a+"' allowScriptAccess='always' wmode='transparent' src='"+t+"' height='1' width='1'></embed></object>"}var o=Q.getTracer("easyXDM.stack.FlashTransport");o("constructor");var r,a,i,s,l;return r={outgoing:function(n,t,o){s.postMessage(e.channel,n.toString()),o&&o()},destroy:function(){o("destroy");try{s.destroyChannel(e.channel)}catch(n){}s=null,a&&(a.parentNode.removeChild(a),a=null)},onDOMReady:function(){o("init"),i=e.remote,j.Fn.set("flash_"+e.channel+"_init",function(){S(function(){o("firing onReady"),r.up.callback(!0)})}),j.Fn.set("flash_"+e.channel+"_onMessage",n),e.swf=f(e.swf);var c=u(e.swf),l=function(){j.stack.FlashTransport[c].init=!0,s=j.stack.FlashTransport[c].swf,s.createChannel(e.channel,e.secret,p(e.remote),e.isHost),e.isHost&&(O&&e.swfNoThrottle&&h(e.props,{position:"fixed",right:0,top:0,height:"20px",width:"20px"}),h(e.props,{src:m(e.remote,{xdm_e:p(location.href),xdm_c:e.channel,xdm_p:6,xdm_s:e.secret}),name:I+e.channel+"_provider"}),a=w(e))};j.stack.FlashTransport[c]&&j.stack.FlashTransport[c].init?l():j.stack.FlashTransport[c]?j.stack.FlashTransport[c].queue.push(l):(j.stack.FlashTransport[c]={queue:[l]},t(c))},init:function(){c(r.onDOMReady,r)}}},j.stack.PostMessageTransport=function(e){function n(e){if(e.origin)return p(e.origin);if(e.uri)return p(e.uri);if(e.domain)return location.protocol+"//"+e.domain;throw"Unable to retrieve the origin of the event"}function t(t){var a=n(t);o("received message '"+t.data+"' from "+a),a==s&&t.data.substring(0,e.channel.length+1)==e.channel+" "&&r.up.incoming(t.data.substring(e.channel.length+1),a)}var o=Q.getTracer("easyXDM.stack.PostMessageTransport");o("constructor");var r,a,i,s;return r={outgoing:function(n,t,o){i.postMessage(e.channel+" "+n,t||s),o&&o()},destroy:function(){o("destroy"),R(window,"message",t),a&&(i=null,a.parentNode.removeChild(a),a=null)},onDOMReady:function(){if(o("init"),s=p(e.remote),e.isHost){var n=function(c){c.data==e.channel+"-ready"&&(o("firing onReady"),i="postMessage"in a.contentWindow?a.contentWindow:a.contentWindow.document,R(window,"message",n),T(window,"message",t),S(function(){r.up.callback(!0)},0))};T(window,"message",n),h(e.props,{src:m(e.remote,{xdm_e:p(location.href),xdm_c:e.channel,xdm_p:1}),name:I+e.channel+"_provider"}),a=w(e)}else T(window,"message",t),i="postMessage"in window.parent?window.parent:window.parent.document,i.postMessage(e.channel+"-ready",s),S(function(){r.up.callback(!0)},0)},init:function(){c(r.onDOMReady,r)}}},j.stack.FrameElementTransport=function(e){var n=Q.getTracer("easyXDM.stack.FrameElementTransport");n("constructor");var t,o,r,a;return t={outgoing:function(e,n,t){r.call(this,e),t&&t()},destroy:function(){n("destroy"),o&&(o.parentNode.removeChild(o),o=null)},onDOMReady:function(){n("init"),a=p(e.remote),e.isHost?(h(e.props,{src:m(e.remote,{xdm_e:p(location.href),xdm_c:e.channel,xdm_p:5}),name:I+e.channel+"_provider"}),o=w(e),o.fn=function(e){return delete o.fn,r=e,S(function(){t.up.callback(!0)},0),function(e){t.up.incoming(e,a)}}):(document.referrer&&p(document.referrer)!=z.xdm_e&&(window.top.location=z.xdm_e),r=window.frameElement.fn(function(e){t.up.incoming(e,a)}),t.up.callback(!0))},init:function(){c(t.onDOMReady,t)}}},j.stack.NameTransport=function(e){function n(n){var t=e.remoteHelper+(s?"#_3":"#_2")+e.channel;a("sending message "+n),a("navigating to  '"+t+"'"),l.contentWindow.sendMessage(n,t)}function t(){s?2!==++d&&s||i.up.callback(!0):(n("ready"),a("calling onReady"),i.up.callback(!0))}function o(e){a("received message "+e),i.up.incoming(e,y)}function r(){v&&S(function(){v(!0)},0)}var a=Q.getTracer("easyXDM.stack.NameTransport");a("constructor"),e.isHost&&g(e.remoteHelper)&&a("missing remoteHelper");var i,s,l,u,d,v,y,b;return i={outgoing:function(e,t,o){v=o,n(e)},destroy:function(){a("destroy"),l.parentNode.removeChild(l),l=null,s&&(u.parentNode.removeChild(u),u=null)},onDOMReady:function(){a("init"),s=e.isHost,d=0,y=p(e.remote),e.local=f(e.local),s?(j.Fn.set(e.channel,function(n){a("received initial message "+n),s&&"ready"===n&&(j.Fn.set(e.channel,o),t())}),b=m(e.remote,{xdm_e:e.local,xdm_c:e.channel,xdm_p:2}),h(e.props,{src:b+"#"+e.channel,name:I+e.channel+"_provider"}),u=w(e)):(e.remoteHelper=e.remote,j.Fn.set(e.channel,o));var n=function(){var o=l||this;R(o,"load",n),j.Fn.set(e.channel+"_load",r),function a(){"function"==typeof o.contentWindow.sendMessage?t():S(a,50)}()};l=w({props:{src:e.local+"#_4"+e.channel},onLoad:n})},init:function(){c(i.onDOMReady,i)}}},j.stack.HashTransport=function(e){function n(n){if(a("sending message '"+(f+1)+" "+n+"' to "+y),!g)return void a("no caller window");var t=e.remote+"#"+f++ +"_"+n;(s||!v?g.contentWindow:g).location=t}function t(e){d=e,a("received message '"+d+"' from "+y),i.up.incoming(d.substring(d.indexOf("_")+1),y)}function o(){if(m){var e=m.location.href,n="",o=e.indexOf("#");-1!=o&&(n=e.substring(o)),n&&n!=d&&(a("poll: new message"),t(n))}}function r(){a("starting polling"),l=setInterval(o,u)}var a=Q.getTracer("easyXDM.stack.HashTransport");a("constructor");var i,s,l,u,d,f,m,g,v,y;return i={outgoing:function(e){n(e)},destroy:function(){window.clearInterval(l),(s||!v)&&g.parentNode.removeChild(g),g=null},onDOMReady:function(){if(s=e.isHost,u=e.interval,d="#"+e.channel,f=0,v=e.useParent,y=p(e.remote),s){if(h(e.props,{src:e.remote,name:I+e.channel+"_provider"}),v)e.onLoad=function(){m=window,r(),i.up.callback(!0)};else{var n=0,t=e.delay/50;!function o(){if(++n>t)throw a("unable to get reference to _listenerWindow, giving up"),new Error("Unable to reference listenerwindow");try{m=g.contentWindow.frames[I+e.channel+"_consumer"]}catch(c){}m?(r(),a("got a reference to _listenerWindow"),i.up.callback(!0)):S(o,50)}()}g=w(e)}else m=window,r(),v?(g=parent,i.up.callback(!0)):(h(e,{props:{src:e.remote+"#"+e.channel+new Date,name:I+e.channel+"_consumer"},onLoad:function(){i.up.callback(!0)}}),g=w(e))},init:function(){c(i.onDOMReady,i)}}},j.stack.ReliableBehavior=function(){var e=Q.getTracer("easyXDM.stack.ReliableBehavior");e("constructor");var n,t,o=0,r=0,a="";return n={incoming:function(i,c){e("incoming: "+i);var s=i.indexOf("_"),l=i.substring(0,s).split(",");i=i.substring(s+1),l[0]==o&&(e("message delivered"),a="",t&&t(!0)),i.length>0&&(e("sending ack, and passing on "+i),n.down.outgoing(l[1]+","+o+"_"+a,c),r!=l[1]&&(r=l[1],n.up.incoming(i,c)))},outgoing:function(e,i,c){a=e,t=c,n.down.outgoing(r+","+ ++o+"_"+e,i)}}},j.stack.QueueBehavior=function(e){function n(){if(e.remove&&0===a.length)return t("removing myself from the stack"),void _(o);if(!i&&0!==a.length&&!r){t("dispatching from queue"),i=!0;var c=a.shift();o.down.outgoing(c.data,c.origin,function(e){i=!1,c.callback&&S(function(){c.callback(e)},0),n()})}}var t=Q.getTracer("easyXDM.stack.QueueBehavior");t("constructor");var o,r,a=[],i=!0,c="",s=0,l=!1,u=!1;return o={init:function(){g(e)&&(e={}),e.maxLength&&(s=e.maxLength,u=!0),e.lazy?l=!0:o.down.init()},callback:function(e){i=!1;var t=o.up;n(),t.callback(e)},incoming:function(n,r){if(u){var a=n.indexOf("_"),i=parseInt(n.substring(0,a),10);c+=n.substring(a+1),0===i?(t("received the last fragment"),e.encode&&(c=decodeURIComponent(c)),o.up.incoming(c,r),c=""):t("waiting for more fragments, seq="+n)}else o.up.incoming(n,r)},outgoing:function(r,i,c){e.encode&&(r=encodeURIComponent(r));var d,p=[];if(u){for(;0!==r.length;)d=r.substring(0,s),r=r.substring(d.length),p.push(d);for(;d=p.shift();)t("enqueuing"),a.push({data:p.length+"_"+d,origin:i,callback:0===p.length?c:null})}else a.push({data:r,origin:i,callback:c});l?o.down.init():n()},destroy:function(){t("destroy"),r=!0,o.down.destroy()}}},j.stack.VerifyBehavior=function(e){function n(){t("requesting verification"),r=Math.random().toString(16).substring(2),o.down.outgoing(r)}var t=Q.getTracer("easyXDM.stack.VerifyBehavior");t("constructor");var o,r,a;return o={incoming:function(i,c){var s=i.indexOf("_");-1===s?i===r?(t("verified, calling callback"),o.up.callback(!0)):a||(t("returning secret"),a=i,e.initiate||n(),o.down.outgoing(i)):i.substring(0,s)===a&&o.up.incoming(i.substring(s+1),c)},outgoing:function(e,n,t){o.down.outgoing(r+"_"+e,n,t)},callback:function(){e.initiate&&n()}}},j.stack.RpcBehavior=function(e,n){function t(e){e.jsonrpc="2.0",i.down.outgoing(s.stringify(e))}function o(e,n){var o=Array.prototype.slice;return c("creating method "+n),function(){c("executing method "+n);var r,a=arguments.length,i={method:n};return a>0&&"function"==typeof arguments[a-1]?(a>1&&"function"==typeof arguments[a-2]?(r={success:arguments[a-2],error:arguments[a-1]},i.params=o.call(arguments,0,a-2)):(r={success:arguments[a-1]},i.params=o.call(arguments,0,a-1)),u[""+ ++l]=r,i.id=l):i.params=o.call(arguments,0),e.namedParams&&1===i.params.length&&(i.params=i.params[0]),t(i),i.id}}function a(e,n,o,a){if(!o)return c("requested to execute non-existent procedure "+e),void(n&&t({id:n,error:{code:-32601,message:"Procedure not found."}}));c("requested to execute procedure "+e);var i,s;n?(i=function(e){i=C,t({id:n,result:e})},s=function(e,o){s=C;var r={id:n,error:{code:-32099,message:e}};o&&(r.error.data=o),t(r)}):i=s=C,r(a)||(a=[a]);try{var l=o.method.apply(o.scope,a.concat([i,s]));g(l)||i(l)}catch(u){s(u.message)}}var i,c=Q.getTracer("easyXDM.stack.RpcBehavior"),s=n.serializer||V(),l=0,u={};return i={incoming:function(e){var o=s.parse(e);if(o.method)c("received request to execute method "+o.method+(o.id?" using callback id "+o.id:"")),o&&o.params&&o.params[0]&&"object"==typeof o.params[0]&&(o.params[0].id=o.id),n.handle?n.handle(o,t):a(o.method,o.id,n.local[o.method],o.params);else{c("received return value destined to callback with id "+o.id);var r=u[o.id];o.error?r.error?r.error(o.error):c("unhandled error returned."):r.success&&r.success(o.result),delete u[o.id]}},init:function(){if(c("init"),n.remote){c("creating stubs");for(var t in n.remote)n.remote.hasOwnProperty(t)&&(e[t]=o(n.remote[t],t))}i.down.init()},destroy:function(){c("destroy");for(var t in n.remote)n.remote.hasOwnProperty(t)&&e.hasOwnProperty(t)&&delete e[t];i.down.destroy()}}},j});