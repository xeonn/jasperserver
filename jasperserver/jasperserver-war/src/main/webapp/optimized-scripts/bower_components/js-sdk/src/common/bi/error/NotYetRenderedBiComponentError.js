define(["require","./BiComponentError","./enum/biComponentErrorCodes","./enum/biComponentErrorMessages"],function(r){"use strict";var o=r("./BiComponentError"),e=r("./enum/biComponentErrorCodes"),n=r("./enum/biComponentErrorMessages");return o.extend({constructor:function(){o.prototype.constructor.call(this,e.NOT_YET_RENDERED_ERROR,n[e.NOT_YET_RENDERED_ERROR])}})});