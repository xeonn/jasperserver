define(["require","underscore","attributes/enum/roleEnum","attributes/enum/permissionMasksEnum"],function(e){{var i=e("underscore"),s=e("attributes/enum/roleEnum");e("attributes/enum/permissionMasksEnum")}return{computeds:{permissionEmbedded:{deps:["_embedded","permissionMask"],get:function(e,i){var s=this.getPermission(e);return s?s.mask:i},set:function(e){var n=i.cloneDeep(this.get("_embedded")),t=this.getPermission(n);return t?t.mask=e:n.permission.push({mask:e,recipient:s.ROLE_ADMINISTRATOR}),{_embedded:n}}}},_initModelWithPermissionDefaults:function(){this.defaults=i.extend({},this.defaults,{_embedded:{permission:[{recipient:s.ROLE_ADMINISTRATOR,mask:"1"}]}})},getPermission:function(e){return e=e||this.get("_embedded"),e?i.find(e.permission,function(e){return e.recipient===s.ROLE_ADMINISTRATOR}):void 0}}});