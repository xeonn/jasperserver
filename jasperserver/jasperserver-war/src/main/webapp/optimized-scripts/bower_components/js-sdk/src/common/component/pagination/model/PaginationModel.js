define(["require","backbone","backbone.validation","common/validation/ValidationErrorMessage","underscore"],function(e){"use strict";var r=e("backbone"),n=e("backbone.validation"),t=e("common/validation/ValidationErrorMessage"),o=e("underscore"),a=r.Model.extend({validation:{step:[{min:1,msg:new t("error.pagination.property.min.value","step",1)}],current:[{integerNumber:!0,msg:new t("error.pagination.property.integer.value","current",1)},{min:1,msg:new t("error.pagination.property.min.value","current",1)},{fn:function(e){return e>this.get("total")?new t("error.pagination.property.max.value","current",e):void 0}}],total:[{min:1,msg:new t("error.pagination.property.min.value","total",1)}]},defaults:{step:1,current:1,total:1}});return o.extend(a.prototype,n.mixin),a});