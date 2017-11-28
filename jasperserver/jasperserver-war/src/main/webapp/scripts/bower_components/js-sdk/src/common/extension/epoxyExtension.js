define(function(require){

    var Epoxy = require("backbone.epoxy.original"),
        _ = require("underscore");

    Epoxy.binding.addHandler("validationErrorClass", {
        init: function( $element, value, bindings, context ) {
            var attr = $element.data("modelAttr");
            var model = this.view.model;

            this._onAttrValidated = function(model, attr, error) {
                $element[error ? "addClass" : "removeClass"]("error");
            };

            model.on("validate:" + attr, this._onAttrValidated);
        },
        get: function( $element, value, event ) {
            // Get data from the bound element...
            return $element.val();
        },
        set: function( $element, value ) {
            // Set data into the bound element...
            $element.val( value );
        },
        clean: function() {
            var attr = this.$el.data("modelAttr");
            this.view.model.off("validate:" + attr, this._onAttrValidated);
        }
    });

    Epoxy.binding.addHandler("validationErrorText", {
        init: function( $element, value, bindings, context ) {
            var attr = $element.data("modelAttr");
            var model = this.view.model;

            this._onAttrValidated = function(model, attr, error) {
                $element.text(error || "");
            };

            model.on("validate:" + attr, this._onAttrValidated);
        },
        get: function( $element, value, event ) {
            // Get data from the bound element...
            return $element.val();
        },
        set: function( $element, value ) {
            // Set data into the bound element...
            $element.val( value );
        },
        clean: function() {
            var attr = this.$el.data("modelAttr");
            this.view.model.off("validate:" + attr, this._onAttrValidated);
        }
    });

    Epoxy.binding.addFilter("escapeCharacters", {
        get: function( value ) {
            // model -> view
            return _.escape(value);
        },
        set: function( value ) {
            // view -> model
            return _.unescape(value);
        }
    });

    return Epoxy;
});