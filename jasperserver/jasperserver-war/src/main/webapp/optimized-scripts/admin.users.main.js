define(["require","!domReady","underscore","org.user.mng.actions","jrs.configs","mng.common.actions","xregexp","json2","backbone","attributes.view","org.user.mng.components","common/util/encrypter","utils.common","csrf.guard"],function(e){var n=e("!domReady"),o=e("underscore"),r=e("org.user.mng.actions"),s=e("jrs.configs");e("mng.common.actions"),e("xregexp"),e("json2"),e("backbone"),e("attributes.view"),e("org.user.mng.components"),e("common/util/encrypter"),e("utils.common"),e("csrf.guard"),n(function(){"undefined"==typeof r.messages&&(r.messages={}),"undefined"==typeof r.Configuration&&(r.Configuration={}),o.extend(window.localContext,s.userManagement.localContext),o.extend(r.messages,s.userManagement.orgModule.messages),o.extend(r.Configuration,s.userManagement.orgModule.Configuration),r.userManager.initialize()})});