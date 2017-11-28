dynamicTree.TreeSupport=function(e,r){if(dynamicTree.Tree.call(this,e,r),this.providerId=r.providerId,this.hideLoader=r.hideLoader,this.rootUri=Object.isUndefined(r.rootUri)?"/":r.rootUri,this.nodeClass=r.nodeClass&&Object.isFunction(r.nodeClass)?r.nodeClass:dynamicTree.TreeNode,r.rootObjectModifier&&(this.modifyRootObject=r.rootObjectModifier),this.resetStatesOnShow=Object.isUndefined(r.resetStatesOnShow)||r.resetStatesOnShow,this.inInit=!0,this.ajaxBufferId="ajaxbuffer",this.nodeTextId="treeNodeText",this.urlGetNode=r.urlGetNode?r.urlGetNode:this._getFlowUrl("getNode"),this.urlGetChildren=r.urlGetChildren?r.urlGetChildren:this._getFlowUrl("getChildren"),this.urlGetMultipleChildren=r.urlGetMultipleChildren?r.urlGetMultipleChildren:this._getFlowUrl("getMultipleChildren"),this.urlGetMessage=r.urlGetMessage?r.urlGetMessage:this._getFlowUrl("getMessage"),this.additionalParams=r.additionalParams?r.additionalParams:{},null==this.TREE_NN_ITEMS_SELECTED){var t=function(e){this.TREE_NN_ITEMS_SELECTED=e}.bind(this);this.getMessage("TREE_NN_ITEMS_SELECTED",t,null)}this._initOpenListener()},dynamicTree.TreeSupport.prototype=deepClone(dynamicTree.Tree.prototype),dynamicTree.TreeSupport.addMethod("_initOpenListener",function(){this.observe("node:open",function(e){var r=e.memo.node;r&&!r.isloaded&&this.getTreeNodeChildren(r)}.bindAsEventListener(this))}),dynamicTree.TreeSupport.addMethod("stopObserving",function(e,r){this._getElement().stopObserving(e,r),"node:open"!==e||r||this._initOpenListener()}),dynamicTree.TreeSupport.addMethod("_getFlowUrl",function(e){return __jrsConfigs__.contextPath+"/flow.html?_flowId=treeFlow&method="+e}),dynamicTree.TreeSupport.addMethod("showTree",function(e,r,t,d){var i=this.urlGetNode+"&provider="+this.providerId+"&uri="+this.rootUri+"&depth="+e;d&&(i+="&forceHtmlEscape=true"),i+="&"+this._evaluateAdditionalParams(),this._showTree(i,r,t)}),dynamicTree.TreeSupport.addMethod("_evaluateAdditionalParams",function(){var e=this.additionalParams;return _.isFunction(e)&&(e=e.call(null)),_.isObject(e)?Object.toQueryString(e):null}),dynamicTree.TreeSupport.addMethod("showTreePrefetchNodes",function(e,r,t){var d=this.urlGetNode+"&provider="+this.providerId+"&uri="+this.rootUri;e&&(d+="&prefetch="+encodeURIComponent(e)),d+="&"+this._evaluateAdditionalParams(),this._showTree(d,r,t)}),dynamicTree.TreeSupport.addMethod("_showTree",function(e,r,t){this.inInit=!0,this.wait();var d=function(e,r,t){return function(){return e.showTreeCallback(r,t)}}(this,r,t);ajaxTargettedUpdate(e,{fillLocation:this.ajaxBufferId,callback:d,errorHandler:baseErrorHandler,hideLoader:this.hideLoader})}),dynamicTree.TreeSupport.addMethod("showTreeCallback",function(e,r){var t=document.getElementById(this.nodeTextId);if(null==t)return void(r?r():this.fireServerErrorEvent());var d=xssUtil.unescape(jQuery(t).text()),i=d.evalJSON();this.modifyRootObject&&(i=this.modifyRootObject(i,!1)),t=document.getElementById(this.ajaxBufferId),t.innerHTML="",this.setRootNode(this.processNode(i)),this.resortTree(),this.resetStatesOnShow&&this.resetStates(),this.renderTree(),this.inInit=!1,e?e():this.fireTreeLoadedEvent({tree:this})}),dynamicTree.TreeSupport.addMethod("processNode",function(e){var r={};r.id=e.id,r.type=e.type,r.uri=e.uri,r.extra=deepClone(e.extra),e.cssClass&&(r.cssClass=e.cssClass);var t=this.nodeClass,d=new t({name:unescapeBackslash(e.label),param:r,orderNumber:e.order});e.tooltip&&(d.tooltip=e.tooltip);var i=e.children;if(null!=i){var o=i.length;if(0===o)d.setHasChilds(!1);else for(var n=0;o>n;n++){var a=i[n];if(a){var s=this.processNode(a);d.addChild(s)}}d.isloaded=!0}return d}),dynamicTree.TreeSupport.addMethod("getTreeNodeChildren",function(e,r,t){var d=e.param.uri,i=function(e,r,t,d){return function(){return e.getTreeNodeChildrenCallback(r,t,d)}}(this,e.id,r,t),o=function(r){(500==r.status||r.getResponseHeader("JasperServerError"))&&e.stopWaiting(),baseErrorHandler(r)};ajaxTargettedUpdate(this.urlGetChildren+"&provider="+this.providerId+"&uri="+encodeURIComponent(encodeURIComponent(d))+"&"+this._evaluateAdditionalParams(),{fillLocation:this.ajaxBufferId,callback:i,errorHandler:o,hideLoader:this.hideLoader}),this.inInit||e.wait()}),dynamicTree.TreeSupport.addMethod("getTreeNodeChildrenCallback",function(e,r,t){var d=document.getElementById(this.nodeTextId);if(null==d)return void(t?t():this.fireServerErrorEvent());var i=xssUtil.unescape(jQuery(d).text()).evalJSON(),o=dynamicTree.nodes[e];this.modifyRootObject&&(i=this.modifyRootObject(i,!0,o)),d=document.getElementById(this.ajaxBufferId),d.innerHTML="",o.resetChilds(),o.stopWaiting();var n=i.length;if(0===n)o.setHasChilds(!1);else{var a=o.getTreeId(),s=a?dynamicTree.trees[a]:null,l=s?s.sortNodes:null;s&&(s.sortNodes=!1);for(var h=0;n>h;h++){var u=this.processNode(i[h]);o.addChild(u)}s&&(s.sortNodes=l,o.resortChilds())}o.isloaded=!0,o.refreshNode(),r?r(o.childs):this.fireChildrenLoadedEvent(o.childs)}),dynamicTree.TreeSupport.addMethod("getTreeMultipleNodesChildren",function(e,r,t){var d,i="",o=[];if(e&&e.length)for(d=0;d<e.length;d++)d>0&&(i+=","),i+=encodeURIComponent(encodeURIComponent(e[d].param.uri)),o[d]=e[d].id;if(i.length){var n=function(e,r,t,d){return function(){return e.getTreeMultipleNodesChildrenCallback(r,t,d)}}(this,o,r,t);if(ajaxTargettedUpdate(this.urlGetMultipleChildren+"&provider="+this.providerId,{fillLocation:this.ajaxBufferId,callback:n,postData:"uris="+i,errorHandler:baseErrorHandler,hideLoader:this.hideLoader}),!this.inInit)for(d=0;d<e.length;d++)e[d].wait()}}),dynamicTree.TreeSupport.addMethod("getTreeMultipleNodesChildrenCallback",function(e,r,t){var d=document.getElementById(this.nodeTextId);if(null==d)return void(t?t():this.fireServerErrorEvent());var i=xssUtil.unescape(jQuery(d).text()).evalJSON();d=document.getElementById(this.ajaxBufferId),d.innerHTML="",r?r(e,i):(this.setMultipleNodesChilden(e,i),this.fireMultipleChildrenLoadedEvent(e,i))}),dynamicTree.TreeSupport.addMethod("setMultipleNodesChilden",function(e,r,t){if(e&&r)for(var d=0;d<r.length;d++){for(var i=r[d],o=i.children,n=null,a=0;a<e.length;a++){var s=dynamicTree.nodes[e[a]];if(s.param.uri==i.parentUri){n=s;break}}if(n){n.resetChilds(),n.stopWaiting();var l=o.length;if(0===l)n.setHasChilds(!1);else{var h=n.getTreeId(),u=h?dynamicTree.trees[h]:null,c=u?u.sortNodes:null;u&&(u.sortNodes=!1);for(var f=0;l>f;f++){var p=this.processNode(o[f]);n.addChild(p)}u&&(u.sortNodes=c,n.resortChilds())}n.isloaded=!0,t||n.refreshNode()}}}),dynamicTree.TreeSupport.addMethod("getTreeNodeChildrenPrefetched",function(e,r,t,d,i,o,n){var a=e.param.uri,s=this.urlGetNode+"&provider="+this.providerId+"&uri="+a,l="";r&&(l="&prefetch="+r),i&&(s+="&depth="+i);var h=function(e,r,t,d,i,o){return function(){return e.getTreeNodeChildrenPrefetchedCallback(r,t,d,i,o)}}(this,e.id,t,d,o,n);ajaxTargettedUpdate(s,{fillLocation:this.ajaxBufferId,callback:h,postData:l,errorHandler:baseErrorHandler,hideLoader:this.hideLoader}),this.inInit||e.wait()}),dynamicTree.TreeSupport.addMethod("getTreeNodeChildrenPrefetchedCallback",function(e,r,t,d,i){var o=document.getElementById(this.nodeTextId);if(null==o)return void(t?t():this.fireServerErrorEvent());var n=xssUtil.unescape(jQuery(o).text()).evalJSON();o=document.getElementById(this.ajaxBufferId),o.innerHTML="";var a=dynamicTree.nodes[e];if(a.resetChilds(),a.stopWaiting(),n.children){var s=a.getTreeId(),l=s?dynamicTree.trees[s]:null,h=l?l.sortNodes:null;l&&(l.sortNodes=!1);for(var u=0;u<n.children.length;u++){var c=this.processNode(n.children[u]);a.addChild(c)}l&&(l.sortNodes=h,d||a.resortChilds())}a.isloaded=!0,i||a.refreshNode(),r?r():this.fireChildredPrefetchedLoadedEvent(a.childs)}),dynamicTree.TreeSupport.addMethod("openAndSelectNode",function(e,r,t,d){var i=function(e){var r=dynamicTree.trees[e.getTreeId()];e.parent&&r&&r.rootNode!=e.parent&&r.getState(e.parent.id)==dynamicTree.TreeNode.State.CLOSED&&e.parent.handleNode(),e&&jQuery("#dataChooserSource").length&&(e.nofocus=!0),r._selectOrEditNode(void 0,e,!1,!1,!1,d)};this.processNodePath(e,i,t);var o=this.getSelectedNode();o&&o.scroll(),r&&r()}),dynamicTree.TreeSupport.addMethod("processNodePath",function(e,r,t){var d=this.getRootNode();if("/"===e)r(d);else{var i,o=e.split("/");for(i=0;i<o.length;i++)if(o[i]){var n=d;if(d=this.findNodeChildByMetaName(d,o[i]),!d){if(!t)return;if(d=this.findNodeFirstNodeChildByAlphabeticalOrder(n),!d)return}r(d)}}}),dynamicTree.TreeSupport.addMethod("findLastLoadedNode",function(e){var r={node:null},t=function(e){return function(r){e.node=r}}(r);return this.processNodePath(e,t),r.node}),dynamicTree.TreeSupport.addMethod("findNodeChildByMetaName",function(e,r){if(e.hasChilds())for(var t=0;t<e.childs.length;t++)if(e.childs[t].param.id==r)return e.childs[t];return null}),dynamicTree.TreeSupport.addMethod("findNodeFirstNodeChildByAlphabeticalOrder",function(e){var r=null,t=null;if(e.childs.length>0){r=e.childs[0].param.id,t=0;for(var d=null,i=null,o=1;o<e.childs.length;o++){d=e.childs[o].param.id,i=o;for(var n=d.length<r.length?d.length:r.length,a=0;n>a;a++){if(d.charCodeAt(a)<r.charCodeAt(a)){r=d,t=i;break}if(d.charCodeAt(a)>r.charCodeAt(a))break}}return e.childs[t]}return null}),dynamicTree.TreeSupport.addMethod("findNodeById",function(e,r){return function t(e,r){if(!r||!e)return null;if(r.param.id===e)return r;if(r.hasChilds())for(var d=0;d<r.childs.length;++d){var i=t(e,r.childs[d]);if(i)return i}return null}(e,r?r:this.getRootNode())}),dynamicTree.TreeSupport.addMethod("hasVisibleFolders",function(e){if(this.bShowRoot)return!0;var r=e.children;if(r)for(var t=0;t<r.length;t++){var d=e.children[t].children;if(d&&d.length>0)return!0}return!1}),dynamicTree.TreeSupport.addMethod("getMessage",function(e,r,t){var d=this.urlGetMessage+"&messageId="+e,i=function(e,r,t){return function(){return e.getMessageCallback(r,t)}}(this,r,t);ajaxTargettedUpdate(d,{fillLocation:this.ajaxBufferId,callback:i,errorHandler:baseErrorHandler,hideLoader:this.hideLoader})}),dynamicTree.TreeSupport.addMethod("getMessageCallback",function(e,r){var t=document.getElementById(this.ajaxBufferId);if(null==t)return void(r&&r());var d=trim(t.innerHTML);t.innerHTML="",e&&e(d)}),dynamicTree.TreeSupport.addMethod("fireServerErrorEvent",function(){this._getElement().fire("server:error",{})}),dynamicTree.TreeSupport.addMethod("fireTreeLoadedEvent",function(e){this._getElement().fire("tree:loaded",{tree:e})}),dynamicTree.TreeSupport.addMethod("fireChildrenLoadedEvent",function(e){this._getElement().fire("children:loaded",{nodes:e})}),dynamicTree.TreeSupport.addMethod("fireMultipleChildrenLoadedEvent",function(e,r){this._getElement().fire("multipleChildren:loaded",{parentNodeIds:e,metaNodes:r})}),dynamicTree.TreeSupport.addMethod("fireChildredPrefetchedLoadedEvent",function(e){this._getElement().fire("childredPrefetched:loaded",{nodes:e})});