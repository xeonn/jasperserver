define(["require","./Tree","jquery","underscore","json3","./TreeDataLayer","./plugin/TooltipPlugin","./plugin/ContextMenuTreePlugin","./plugin/SearchPlugin","common/enum/repositoryResourceTypes","text!./template/repositoryFoldersTreeLevelTemplate.htm","bundle!CommonBundle","jrs.configs"],function(e){"use strict";function t(e){var t=2;return e.isWritable&&(t=4|t),e.isRemovable&&(t=16|t),e.isAdministrable&&(t=1),t}var r=e("./Tree"),o=e("jquery"),n=e("underscore"),i=e("json3"),a=e("./TreeDataLayer"),l=e("./plugin/TooltipPlugin"),s=e("./plugin/ContextMenuTreePlugin"),u=e("./plugin/SearchPlugin"),c=e("common/enum/repositoryResourceTypes"),d=e("text!./template/repositoryFoldersTreeLevelTemplate.htm"),p=e("bundle!CommonBundle"),m=e("jrs.configs"),f={folderTreeProcessor:{processItem:function(e){return e._node=!0,e._readonly=!(1==e.value.permissionMask||4&e.value.permissionMask),e}},filterPublicFolderProcessor:{processItem:function(e){return e.value.uri!==m.publicFolderUri&&e.value.uri!==m.tempFolderUri?e:void 0}},i18n:{processItem:function(e){return e.i18n=p,e}},tenantProcessor:{processItem:function(e){return e._node=!0,e.value.label=e.value.tenantName,e.value.uri=e.value.tenantUri,e}}};return{repositoryFoldersTree:function(e){return r.use(l,{i18n:p,contentTemplate:e.tooltipContentTemplate}).create().instance({additionalCssClasses:"folders",dataUriTemplate:e.contextPath+"/rest_v2/resources?{{= id != '@fakeRoot' ? 'folderUri=' + id : ''}}&recursive=false&type="+c.FOLDER+"&offset={{= offset }}&limit={{= limit }}",levelDataId:"uri",itemsTemplate:d,collapsed:!0,lazyLoad:!0,rootless:!0,selection:{allowed:!0,multiple:!1},customDataLayers:{"/":n.extend(new a({dataUriTemplate:e.contextPath+"/flow.html?_flowId=searchFlow&method=getNode&provider=repositoryExplorerTreeFoldersProvider&uri=/&depth=1",processors:n.chain(f).omit("filterPublicFolderProcessor","tenantProcessor").values().value(),getDataArray:function(e){e=i.parse(o(e).text());var r=n.find(e.children,function(e){return"/public"===e.uri}),a=[{id:"@fakeRoot",label:e.label,uri:"/",resourceType:"folder",permissionMask:t(e.extra),_links:{content:"@fakeContentLink"}}];return r&&a.push({id:"/public",label:r.label,uri:"/public",resourceType:"folder",permissionMask:t(r.extra),_links:{content:"@fakeContentLink"}}),a}}),{accept:"text/html",dataType:"text"})},processors:[f.i18n,f.folderTreeProcessor,f.filterPublicFolderProcessor],getDataArray:function(e){return e?e[c.RESOURCE_LOOKUP]:[]}})},tenantFoldersTree:function(e){return r.use(l).use(u).use(s,{contextMenu:e.contextMenu}).create().instance(n.extend({},{itemsTemplate:d,selection:{allowed:{left:!0,right:!0},multiple:!1},rootless:!0,collapsed:!0,lazyLoad:!0,dataUriTemplate:m.contextPath+"/rest_v2/organizations?{{= id != 'organizations' ? 'rootTenantId=' + id : ''}}&offset={{= offset }}&limit={{= limit }}&maxDepth=1",levelDataId:"id",getDataArray:function(e){return e?e[c.ORGANIZATION]:[]},processors:[f.tenantProcessor],customDataLayers:{"/":n.extend(new a({dataUriTemplate:m.contextPath+"/flow.html?_flowId=treeFlow&method=getNode&provider=tenantTreeFoldersProvider&uri=/&prefetch=%2F",processors:[f.tenantProcessor],getDataArray:function(e){return e=i.parse(o(e).text()),[{id:e.id,tenantName:e.label,tenantUri:"/",resourceType:"folder",_links:{content:"@fakeContentLink"}}]}}),{accept:"text/html",dataType:"text"})}}))}}});