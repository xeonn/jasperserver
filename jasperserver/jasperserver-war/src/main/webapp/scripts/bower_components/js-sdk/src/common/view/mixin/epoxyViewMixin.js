define(function(require){

    var Epoxy = require("common/extension/epoxyExtension"),
        epoxyCustomBindingHandlers = require("./epoxyCustomBindingHandlers"),
        epoxyCustomBindingFilters = require("./epoxyCustomBindingFilters");

    return {
        bindingHandlers: epoxyCustomBindingHandlers,

        bindingFilters: epoxyCustomBindingFilters,

        epoxifyView: function(){
            Epoxy.View.mixin(this);
        },

        applyEpoxyBindings: function(){
            this.applyBindings();
        },

        removeEpoxyBindings: function(){
            this.removeBindings();
        }
    };
});