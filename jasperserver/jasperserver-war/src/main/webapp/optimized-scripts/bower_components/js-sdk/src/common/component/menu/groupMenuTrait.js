define(["require","underscore","jquery"],function(e){var r=e("underscore"),n=(e("jquery"),{_onInitialize:function(){var e=this.$contentContainer.find("li"),n="groupId",t=this._getGroupNames(this.collection.models,n);r.each(t,function(r){var t=e.filter("[data-"+n+"='"+r+"']").first();t.index()&&t.before("<li class='leaf separator'></li>")},this)},_getGroupNames:function(e,n){return r.keys(r.groupBy(e,function(e){return e.get(n)}))}});return n});