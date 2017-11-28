define(["require","jquery","underscore","backbone","backbone.marionette","bundle!AttributesBundle","common/view/mixin/epoxyViewMixin"],function(t){var e=t("jquery"),i=t("underscore"),n=t("backbone"),r=t("backbone.marionette"),o=t("bundle!AttributesBundle"),l=t("common/view/mixin/epoxyViewMixin"),s="defaultFilter",c=["true","false"],a=r.CompositeView.extend({className:"filtersContainer",events:{"change select":"_onCurrentFilterChange"},templateHelpers:function(){return{i18n:o}},constructor:function(){this._initCurrentCriteria(),r.CompositeView.apply(this,arguments)},initialize:function(t){this.targetCollection=t&&t.targetCollection||[],this.epoxifyView(),this.initEvents()},initEvents:function(){this.listenTo(this.targetCollection,"add",this.addItemToFilterCollection),this.listenTo(this.targetCollection,"remove",this.removeItemFromFilterCollection),this.listenTo(this.targetCollection,"sync",this._initFilterCollection)},filter:function(t){var e=t||this.currentCriteria,i=e.defaultFilter?this.filterCollection.models:this.filterCollection.where(e);this.targetCollection.reset(i)},reset:function(){this.currentCriteria[s]||(this.$el.find("option[value='"+s+"::true']").prop("selected",!0),this._initCurrentCriteria(),this.filter())},addItemToFilterCollection:function(t){this.filterCollection.add(t)},removeItemFromFilterCollection:function(t){this.filterCollection.remove(t)},render:function(){return r.CompositeView.prototype.render.apply(this,arguments),this.applyEpoxyBindings(),this.delegateEvents(this.events),this},remove:function(){this.removeEpoxyBindings(),r.CompositeView.prototype.remove.apply(this,arguments)},_initCurrentCriteria:function(t,e){t=t||s,e=i.isUndefined(e)?!0:e,this.currentCriteria={},this.currentCriteria[t]=e},_initFilterCollection:function(){this.filterCollection=new n.Collection(this.targetCollection.models)},_onCurrentFilterChange:function(t){var n=e(t.target).val().split("::"),r=n[0],o=n[1];-1!==i.indexOf(c,o)&&(this._initCurrentCriteria(r,e.parseJSON(o)),this.filter())}});return i.extend(a.prototype,l),a});