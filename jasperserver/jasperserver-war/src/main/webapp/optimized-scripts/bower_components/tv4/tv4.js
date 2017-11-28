!function(e,r){"function"==typeof define&&define.amd?define([],r):"undefined"!=typeof module&&module.exports?module.exports=r():e.tv4=r()}(this,function(){function e(r,t){if(r===t)return!0;if("object"==typeof r&&"object"==typeof t){if(Array.isArray(r)!==Array.isArray(t))return!1;if(Array.isArray(r)){if(r.length!==t.length)return!1;for(var i=0;i<r.length;i++)if(!e(r[i],t[i]))return!1}else{var n;for(n in r)if(void 0===t[n]&&void 0!==r[n])return!1;for(n in t)if(void 0===r[n]&&void 0!==t[n])return!1;for(n in r)if(!e(r[n],t[n]))return!1}return!0}return!1}function r(e){var r=String(e).replace(/^\s+|\s+$/g,"").match(/^([^:\/?#]+:)?(\/\/(?:[^:@]*(?::[^:@]*)?@)?(([^:\/?#]*)(?::(\d*))?))?([^?#]*)(\?[^#]*)?(#[\s\S]*)?/);return r?{href:r[0]||"",protocol:r[1]||"",authority:r[2]||"",host:r[3]||"",hostname:r[4]||"",port:r[5]||"",pathname:r[6]||"",search:r[7]||"",hash:r[8]||""}:null}function t(e,t){function i(e){var r=[];return e.replace(/^(\.\.?(\/|$))+/,"").replace(/\/(\.(\/|$))+/g,"/").replace(/\/\.\.$/,"/../").replace(/\/?[^\/]*/g,function(e){"/.."===e?r.pop():r.push(e)}),r.join("").replace(/^\//,"/"===e.charAt(0)?"/":"")}return t=r(t||""),e=r(e||""),t&&e?(t.protocol||e.protocol)+(t.protocol||t.authority?t.authority:e.authority)+i(t.protocol||t.authority||"/"===t.pathname.charAt(0)?t.pathname:t.pathname?(e.authority&&!e.pathname?"/":"")+e.pathname.slice(0,e.pathname.lastIndexOf("/")+1)+t.pathname:e.pathname)+(t.protocol||t.authority||t.pathname?t.search:t.search||e.search)+t.hash:null}function i(e){return e.split("#")[0]}function n(e,r){if(e&&"object"==typeof e)if(void 0===r?r=e.id:"string"==typeof e.id&&(r=t(r,e.id),e.id=r),Array.isArray(e))for(var i=0;i<e.length;i++)n(e[i],r);else{"string"==typeof e.$ref&&(e.$ref=t(r,e.$ref));for(var a in e)"enum"!==a&&n(e[a],r)}}function a(e,r,t,i,n){if(Error.call(this),void 0===e)throw new Error("No code supplied for error: "+r);this.message=r,this.code=e,this.dataPath=t||"",this.schemaPath=i||"",this.subErrors=n||null;var a=new Error(this.message);if(this.stack=a.stack||a.stacktrace,!this.stack)try{throw a}catch(a){this.stack=a.stack||a.stacktrace}}function o(e,r){if(r.substring(0,e.length)===e){var t=r.substring(e.length);if(r.length>0&&"/"===r.charAt(e.length-1)||"#"===t.charAt(0)||"?"===t.charAt(0))return!0}return!1}function s(e){var r=new l,a=e||"en",o={addFormat:function(){r.addFormat.apply(r,arguments)},language:function(e){return e?(c[e]||(e=e.split("-")[0]),c[e]?(a=e,e):!1):a},addLanguage:function(e,r){var t;for(t in h)r[t]&&!r[h[t]]&&(r[h[t]]=r[t]);var i=e.split("-")[0];if(c[i]){c[e]=Object.create(c[i]);for(t in r)"undefined"==typeof c[i][t]&&(c[i][t]=r[t]),c[e][t]=r[t]}else c[e]=r,c[i]=r;return this},freshApi:function(e){var r=s();return e&&r.language(e),r},validate:function(e,t,i,n){var o=new l(r,!1,c[a],i,n);"string"==typeof t&&(t={$ref:t}),o.addSchema("",t);var s=o.validateAll(e,t,null,null,"");return!s&&n&&(s=o.banUnknownProperties()),this.error=s,this.missing=o.missing,this.valid=null===s,this.valid},validateResult:function(){var e={};return this.validate.apply(e,arguments),e},validateMultiple:function(e,t,i,n){var o=new l(r,!0,c[a],i,n);"string"==typeof t&&(t={$ref:t}),o.addSchema("",t),o.validateAll(e,t,null,null,""),n&&o.banUnknownProperties();var s={};return s.errors=o.errors,s.missing=o.missing,s.valid=0===s.errors.length,s},addSchema:function(){return r.addSchema.apply(r,arguments)},getSchema:function(){return r.getSchema.apply(r,arguments)},getSchemaMap:function(){return r.getSchemaMap.apply(r,arguments)},getSchemaUris:function(){return r.getSchemaUris.apply(r,arguments)},getMissingUris:function(){return r.getMissingUris.apply(r,arguments)},dropSchemas:function(){r.dropSchemas.apply(r,arguments)},defineKeyword:function(){r.defineKeyword.apply(r,arguments)},defineError:function(e,r,t){if("string"!=typeof e||!/^[A-Z]+(_[A-Z]+)*$/.test(e))throw new Error("Code name must be a string in UPPER_CASE_WITH_UNDERSCORES");if("number"!=typeof r||r%1!==0||1e4>r)throw new Error("Code number must be an integer > 10000");if("undefined"!=typeof h[e])throw new Error("Error already defined: "+e+" as "+h[e]);if("undefined"!=typeof u[r])throw new Error("Error code already used: "+u[r]+" as "+r);h[e]=r,u[r]=e,p[e]=p[r]=t;for(var i in c){var n=c[i];n[e]&&(n[r]=n[r]||n[e])}},reset:function(){r.reset(),this.error=null,this.missing=[],this.valid=!0},missing:[],error:null,valid:!0,normSchema:n,resolveUrl:t,getDocumentUri:i,errorCodes:h};return o}Object.keys||(Object.keys=function(){var e=Object.prototype.hasOwnProperty,r=!{toString:null}.propertyIsEnumerable("toString"),t=["toString","toLocaleString","valueOf","hasOwnProperty","isPrototypeOf","propertyIsEnumerable","constructor"],i=t.length;return function(n){if("object"!=typeof n&&"function"!=typeof n||null===n)throw new TypeError("Object.keys called on non-object");var a=[];for(var o in n)e.call(n,o)&&a.push(o);if(r)for(var s=0;i>s;s++)e.call(n,t[s])&&a.push(t[s]);return a}}()),Object.create||(Object.create=function(){function e(){}return function(r){if(1!==arguments.length)throw new Error("Object.create implementation only accepts one parameter.");return e.prototype=r,new e}}()),Array.isArray||(Array.isArray=function(e){return"[object Array]"===Object.prototype.toString.call(e)}),Array.prototype.indexOf||(Array.prototype.indexOf=function(e){if(null===this)throw new TypeError;var r=Object(this),t=r.length>>>0;if(0===t)return-1;var i=0;if(arguments.length>1&&(i=Number(arguments[1]),i!==i?i=0:0!==i&&1/0!==i&&i!==-1/0&&(i=(i>0||-1)*Math.floor(Math.abs(i)))),i>=t)return-1;for(var n=i>=0?i:Math.max(t-Math.abs(i),0);t>n;n++)if(n in r&&r[n]===e)return n;return-1}),Object.isFrozen||(Object.isFrozen=function(e){for(var r="tv4_test_frozen_key";e.hasOwnProperty(r);)r+=Math.random();try{return e[r]=!0,delete e[r],!1}catch(t){return!0}});var l=function(e,r,t,i,n){if(this.missing=[],this.missingMap={},this.formatValidators=e?Object.create(e.formatValidators):{},this.schemas=e?Object.create(e.schemas):{},this.collectMultiple=r,this.errors=[],this.handleError=r?this.collectError:this.returnError,i&&(this.checkRecursive=!0,this.scanned=[],this.scannedFrozen=[],this.scannedFrozenSchemas=[],this.scannedFrozenValidationErrors=[],this.validatedSchemasKey="tv4_validation_id",this.validationErrorsKey="tv4_validation_errors_id"),n&&(this.trackUnknownProperties=!0,this.knownPropertyPaths={},this.unknownPropertyPaths={}),this.errorMessages=t,this.definedKeywords={},e)for(var a in e.definedKeywords)this.definedKeywords[a]=e.definedKeywords[a].slice(0)};l.prototype.defineKeyword=function(e,r){this.definedKeywords[e]=this.definedKeywords[e]||[],this.definedKeywords[e].push(r)},l.prototype.createError=function(e,r,t,i,n){var o=this.errorMessages[e]||p[e];if("string"!=typeof o)return new a(e,"Unknown error code "+e+": "+JSON.stringify(r),t,i,n);var s=o.replace(/\{([^{}]*)\}/g,function(e,t){var i=r[t];return"string"==typeof i||"number"==typeof i?i:e});return new a(e,s,t,i,n)},l.prototype.returnError=function(e){return e},l.prototype.collectError=function(e){return e&&this.errors.push(e),null},l.prototype.prefixErrors=function(e,r,t){for(var i=e;i<this.errors.length;i++)this.errors[i]=this.errors[i].prefixWith(r,t);return this},l.prototype.banUnknownProperties=function(){for(var e in this.unknownPropertyPaths){var r=this.createError(h.UNKNOWN_PROPERTY,{path:e},e,""),t=this.handleError(r);if(t)return t}return null},l.prototype.addFormat=function(e,r){if("object"==typeof e){for(var t in e)this.addFormat(t,e[t]);return this}this.formatValidators[e]=r},l.prototype.resolveRefs=function(e,r){if(void 0!==e.$ref){if(r=r||{},r[e.$ref])return this.createError(h.CIRCULAR_REFERENCE,{urls:Object.keys(r).join(", ")},"","");r[e.$ref]=!0,e=this.getSchema(e.$ref,r)}return e},l.prototype.getSchema=function(e,r){var t;if(void 0!==this.schemas[e])return t=this.schemas[e],this.resolveRefs(t,r);var i=e,n="";if(-1!==e.indexOf("#")&&(n=e.substring(e.indexOf("#")+1),i=e.substring(0,e.indexOf("#"))),"object"==typeof this.schemas[i]){t=this.schemas[i];var a=decodeURIComponent(n);if(""===a)return this.resolveRefs(t,r);if("/"!==a.charAt(0))return void 0;for(var o=a.split("/").slice(1),s=0;s<o.length;s++){var l=o[s].replace(/~1/g,"/").replace(/~0/g,"~");if(void 0===t[l]){t=void 0;break}t=t[l]}if(void 0!==t)return this.resolveRefs(t,r)}void 0===this.missing[i]&&(this.missing.push(i),this.missing[i]=i,this.missingMap[i]=i)},l.prototype.searchSchemas=function(e,r){if(e&&"object"==typeof e){"string"==typeof e.id&&o(r,e.id)&&void 0===this.schemas[e.id]&&(this.schemas[e.id]=e);for(var t in e)if("enum"!==t)if("object"==typeof e[t])this.searchSchemas(e[t],r);else if("$ref"===t){var n=i(e[t]);n&&void 0===this.schemas[n]&&void 0===this.missingMap[n]&&(this.missingMap[n]=n)}}},l.prototype.addSchema=function(e,r){if("string"!=typeof e||"undefined"==typeof r){if("object"!=typeof e||"string"!=typeof e.id)return;r=e,e=r.id}(e=i(e)+"#")&&(e=i(e)),this.schemas[e]=r,delete this.missingMap[e],n(r,e),this.searchSchemas(r,e)},l.prototype.getSchemaMap=function(){var e={};for(var r in this.schemas)e[r]=this.schemas[r];return e},l.prototype.getSchemaUris=function(e){var r=[];for(var t in this.schemas)(!e||e.test(t))&&r.push(t);return r},l.prototype.getMissingUris=function(e){var r=[];for(var t in this.missingMap)(!e||e.test(t))&&r.push(t);return r},l.prototype.dropSchemas=function(){this.schemas={},this.reset()},l.prototype.reset=function(){this.missing=[],this.missingMap={},this.errors=[]},l.prototype.validateAll=function(e,r,t,i,n){var o;if(r=this.resolveRefs(r),!r)return null;if(r instanceof a)return this.errors.push(r),r;var s,l=this.errors.length,h=null,u=null;if(this.checkRecursive&&e&&"object"==typeof e){if(o=!this.scanned.length,e[this.validatedSchemasKey]){var f=e[this.validatedSchemasKey].indexOf(r);if(-1!==f)return this.errors=this.errors.concat(e[this.validationErrorsKey][f]),null}if(Object.isFrozen(e)&&(s=this.scannedFrozen.indexOf(e),-1!==s)){var p=this.scannedFrozenSchemas[s].indexOf(r);if(-1!==p)return this.errors=this.errors.concat(this.scannedFrozenValidationErrors[s][p]),null}if(this.scanned.push(e),Object.isFrozen(e))-1===s&&(s=this.scannedFrozen.length,this.scannedFrozen.push(e),this.scannedFrozenSchemas.push([])),h=this.scannedFrozenSchemas[s].length,this.scannedFrozenSchemas[s][h]=r,this.scannedFrozenValidationErrors[s][h]=[];else{if(!e[this.validatedSchemasKey])try{Object.defineProperty(e,this.validatedSchemasKey,{value:[],configurable:!0}),Object.defineProperty(e,this.validationErrorsKey,{value:[],configurable:!0})}catch(c){e[this.validatedSchemasKey]=[],e[this.validationErrorsKey]=[]}u=e[this.validatedSchemasKey].length,e[this.validatedSchemasKey][u]=r,e[this.validationErrorsKey][u]=[]}}var d=this.errors.length,m=this.validateBasic(e,r,n)||this.validateNumeric(e,r,n)||this.validateString(e,r,n)||this.validateArray(e,r,n)||this.validateObject(e,r,n)||this.validateCombinations(e,r,n)||this.validateFormat(e,r,n)||this.validateDefinedKeywords(e,r,n)||null;if(o){for(;this.scanned.length;){var v=this.scanned.pop();delete v[this.validatedSchemasKey]}this.scannedFrozen=[],this.scannedFrozenSchemas=[]}if(m||d!==this.errors.length)for(;t&&t.length||i&&i.length;){var y=t&&t.length?""+t.pop():null,E=i&&i.length?""+i.pop():null;m&&(m=m.prefixWith(y,E)),this.prefixErrors(d,y,E)}return null!==h?this.scannedFrozenValidationErrors[s][h]=this.errors.slice(l):null!==u&&(e[this.validationErrorsKey][u]=this.errors.slice(l)),this.handleError(m)},l.prototype.validateFormat=function(e,r){if("string"!=typeof r.format||!this.formatValidators[r.format])return null;var t=this.formatValidators[r.format].call(null,e,r);return"string"==typeof t||"number"==typeof t?this.createError(h.FORMAT_CUSTOM,{message:t}).prefixWith(null,"format"):t&&"object"==typeof t?this.createError(h.FORMAT_CUSTOM,{message:t.message||"?"},t.dataPath||null,t.schemaPath||"/format"):null},l.prototype.validateDefinedKeywords=function(e,r){for(var t in this.definedKeywords)for(var i=this.definedKeywords[t],n=0;n<i.length;n++){var a=i[n],o=a(e,r[t],r);if("string"==typeof o||"number"==typeof o)return this.createError(h.KEYWORD_CUSTOM,{key:t,message:o}).prefixWith(null,"format");if(o&&"object"==typeof o){var s=o.code||h.KEYWORD_CUSTOM;if("string"==typeof s){if(!h[s])throw new Error("Undefined error code (use defineError): "+s);s=h[s]}var l="object"==typeof o.message?o.message:{key:t,message:o.message||"?"},u=o.schemaPath||"/"+t.replace(/~/g,"~0").replace(/\//g,"~1");return this.createError(s,l,o.dataPath||null,u)}}return null},l.prototype.validateBasic=function(e,r,t){var i;return(i=this.validateType(e,r,t))?i.prefixWith(null,"type"):(i=this.validateEnum(e,r,t))?i.prefixWith(null,"type"):null},l.prototype.validateType=function(e,r){if(void 0===r.type)return null;var t=typeof e;null===e?t="null":Array.isArray(e)&&(t="array");var i=r.type;"object"!=typeof i&&(i=[i]);for(var n=0;n<i.length;n++){var a=i[n];if(a===t||"integer"===a&&"number"===t&&e%1===0)return null}return this.createError(h.INVALID_TYPE,{type:t,expected:i.join("/")})},l.prototype.validateEnum=function(r,t){if(void 0===t["enum"])return null;for(var i=0;i<t["enum"].length;i++){var n=t["enum"][i];if(e(r,n))return null}return this.createError(h.ENUM_MISMATCH,{value:"undefined"!=typeof JSON?JSON.stringify(r):r})},l.prototype.validateNumeric=function(e,r,t){return this.validateMultipleOf(e,r,t)||this.validateMinMax(e,r,t)||null},l.prototype.validateMultipleOf=function(e,r){var t=r.multipleOf||r.divisibleBy;return void 0===t?null:"number"==typeof e&&e%t!==0?this.createError(h.NUMBER_MULTIPLE_OF,{value:e,multipleOf:t}):null},l.prototype.validateMinMax=function(e,r){if("number"!=typeof e)return null;if(void 0!==r.minimum){if(e<r.minimum)return this.createError(h.NUMBER_MINIMUM,{value:e,minimum:r.minimum}).prefixWith(null,"minimum");if(r.exclusiveMinimum&&e===r.minimum)return this.createError(h.NUMBER_MINIMUM_EXCLUSIVE,{value:e,minimum:r.minimum}).prefixWith(null,"exclusiveMinimum")}if(void 0!==r.maximum){if(e>r.maximum)return this.createError(h.NUMBER_MAXIMUM,{value:e,maximum:r.maximum}).prefixWith(null,"maximum");if(r.exclusiveMaximum&&e===r.maximum)return this.createError(h.NUMBER_MAXIMUM_EXCLUSIVE,{value:e,maximum:r.maximum}).prefixWith(null,"exclusiveMaximum")}return null},l.prototype.validateString=function(e,r,t){return this.validateStringLength(e,r,t)||this.validateStringPattern(e,r,t)||null},l.prototype.validateStringLength=function(e,r){return"string"!=typeof e?null:void 0!==r.minLength&&e.length<r.minLength?this.createError(h.STRING_LENGTH_SHORT,{length:e.length,minimum:r.minLength}).prefixWith(null,"minLength"):void 0!==r.maxLength&&e.length>r.maxLength?this.createError(h.STRING_LENGTH_LONG,{length:e.length,maximum:r.maxLength}).prefixWith(null,"maxLength"):null},l.prototype.validateStringPattern=function(e,r){if("string"!=typeof e||void 0===r.pattern)return null;var t=new RegExp(r.pattern);return t.test(e)?null:this.createError(h.STRING_PATTERN,{pattern:r.pattern}).prefixWith(null,"pattern")},l.prototype.validateArray=function(e,r,t){return Array.isArray(e)?this.validateArrayLength(e,r,t)||this.validateArrayUniqueItems(e,r,t)||this.validateArrayItems(e,r,t)||null:null},l.prototype.validateArrayLength=function(e,r){var t;return void 0!==r.minItems&&e.length<r.minItems&&(t=this.createError(h.ARRAY_LENGTH_SHORT,{length:e.length,minimum:r.minItems}).prefixWith(null,"minItems"),this.handleError(t))?t:void 0!==r.maxItems&&e.length>r.maxItems&&(t=this.createError(h.ARRAY_LENGTH_LONG,{length:e.length,maximum:r.maxItems}).prefixWith(null,"maxItems"),this.handleError(t))?t:null},l.prototype.validateArrayUniqueItems=function(r,t){if(t.uniqueItems)for(var i=0;i<r.length;i++)for(var n=i+1;n<r.length;n++)if(e(r[i],r[n])){var a=this.createError(h.ARRAY_UNIQUE,{match1:i,match2:n}).prefixWith(null,"uniqueItems");if(this.handleError(a))return a}return null},l.prototype.validateArrayItems=function(e,r,t){if(void 0===r.items)return null;var i,n;if(Array.isArray(r.items)){for(n=0;n<e.length;n++)if(n<r.items.length){if(i=this.validateAll(e[n],r.items[n],[n],["items",n],t+"/"+n))return i}else if(void 0!==r.additionalItems)if("boolean"==typeof r.additionalItems){if(!r.additionalItems&&(i=this.createError(h.ARRAY_ADDITIONAL_ITEMS,{}).prefixWith(""+n,"additionalItems"),this.handleError(i)))return i}else if(i=this.validateAll(e[n],r.additionalItems,[n],["additionalItems"],t+"/"+n))return i}else for(n=0;n<e.length;n++)if(i=this.validateAll(e[n],r.items,[n],["items"],t+"/"+n))return i;return null},l.prototype.validateObject=function(e,r,t){return"object"!=typeof e||null===e||Array.isArray(e)?null:this.validateObjectMinMaxProperties(e,r,t)||this.validateObjectRequiredProperties(e,r,t)||this.validateObjectProperties(e,r,t)||this.validateObjectDependencies(e,r,t)||null},l.prototype.validateObjectMinMaxProperties=function(e,r){var t,i=Object.keys(e);return void 0!==r.minProperties&&i.length<r.minProperties&&(t=this.createError(h.OBJECT_PROPERTIES_MINIMUM,{propertyCount:i.length,minimum:r.minProperties}).prefixWith(null,"minProperties"),this.handleError(t))?t:void 0!==r.maxProperties&&i.length>r.maxProperties&&(t=this.createError(h.OBJECT_PROPERTIES_MAXIMUM,{propertyCount:i.length,maximum:r.maxProperties}).prefixWith(null,"maxProperties"),this.handleError(t))?t:null},l.prototype.validateObjectRequiredProperties=function(e,r){if(void 0!==r.required)for(var t=0;t<r.required.length;t++){var i=r.required[t];if(void 0===e[i]){var n=this.createError(h.OBJECT_REQUIRED,{key:i}).prefixWith(null,""+t).prefixWith(null,"required");if(this.handleError(n))return n}}return null},l.prototype.validateObjectProperties=function(e,r,t){var i;for(var n in e){var a=t+"/"+n.replace(/~/g,"~0").replace(/\//g,"~1"),o=!1;if(void 0!==r.properties&&void 0!==r.properties[n]&&(o=!0,i=this.validateAll(e[n],r.properties[n],[n],["properties",n],a)))return i;if(void 0!==r.patternProperties)for(var s in r.patternProperties){var l=new RegExp(s);if(l.test(n)&&(o=!0,i=this.validateAll(e[n],r.patternProperties[s],[n],["patternProperties",s],a)))return i}if(o)this.trackUnknownProperties&&(this.knownPropertyPaths[a]=!0,delete this.unknownPropertyPaths[a]);else if(void 0!==r.additionalProperties){if(this.trackUnknownProperties&&(this.knownPropertyPaths[a]=!0,delete this.unknownPropertyPaths[a]),"boolean"==typeof r.additionalProperties){if(!r.additionalProperties&&(i=this.createError(h.OBJECT_ADDITIONAL_PROPERTIES,{}).prefixWith(n,"additionalProperties"),this.handleError(i)))return i}else if(i=this.validateAll(e[n],r.additionalProperties,[n],["additionalProperties"],a))return i}else this.trackUnknownProperties&&!this.knownPropertyPaths[a]&&(this.unknownPropertyPaths[a]=!0)}return null},l.prototype.validateObjectDependencies=function(e,r,t){var i;if(void 0!==r.dependencies)for(var n in r.dependencies)if(void 0!==e[n]){var a=r.dependencies[n];if("string"==typeof a){if(void 0===e[a]&&(i=this.createError(h.OBJECT_DEPENDENCY_KEY,{key:n,missing:a}).prefixWith(null,n).prefixWith(null,"dependencies"),this.handleError(i)))return i}else if(Array.isArray(a))for(var o=0;o<a.length;o++){var s=a[o];if(void 0===e[s]&&(i=this.createError(h.OBJECT_DEPENDENCY_KEY,{key:n,missing:s}).prefixWith(null,""+o).prefixWith(null,n).prefixWith(null,"dependencies"),this.handleError(i)))return i}else if(i=this.validateAll(e,a,[],["dependencies",n],t))return i}return null},l.prototype.validateCombinations=function(e,r,t){return this.validateAllOf(e,r,t)||this.validateAnyOf(e,r,t)||this.validateOneOf(e,r,t)||this.validateNot(e,r,t)||null},l.prototype.validateAllOf=function(e,r,t){if(void 0===r.allOf)return null;for(var i,n=0;n<r.allOf.length;n++){var a=r.allOf[n];if(i=this.validateAll(e,a,[],["allOf",n],t))return i}return null},l.prototype.validateAnyOf=function(e,r,t){if(void 0===r.anyOf)return null;var i,n,a=[],o=this.errors.length;this.trackUnknownProperties&&(i=this.unknownPropertyPaths,n=this.knownPropertyPaths);for(var s=!0,l=0;l<r.anyOf.length;l++){this.trackUnknownProperties&&(this.unknownPropertyPaths={},this.knownPropertyPaths={});var u=r.anyOf[l],f=this.errors.length,p=this.validateAll(e,u,[],["anyOf",l],t);if(null===p&&f===this.errors.length){if(this.errors=this.errors.slice(0,o),this.trackUnknownProperties){for(var c in this.knownPropertyPaths)n[c]=!0,delete i[c];for(var d in this.unknownPropertyPaths)n[d]||(i[d]=!0);s=!1;continue}return null}p&&a.push(p.prefixWith(null,""+l).prefixWith(null,"anyOf"))}return this.trackUnknownProperties&&(this.unknownPropertyPaths=i,this.knownPropertyPaths=n),s?(a=a.concat(this.errors.slice(o)),this.errors=this.errors.slice(0,o),this.createError(h.ANY_OF_MISSING,{},"","/anyOf",a)):void 0},l.prototype.validateOneOf=function(e,r,t){if(void 0===r.oneOf)return null;var i,n,a=null,o=[],s=this.errors.length;this.trackUnknownProperties&&(i=this.unknownPropertyPaths,n=this.knownPropertyPaths);for(var l=0;l<r.oneOf.length;l++){this.trackUnknownProperties&&(this.unknownPropertyPaths={},this.knownPropertyPaths={});var u=r.oneOf[l],f=this.errors.length,p=this.validateAll(e,u,[],["oneOf",l],t);if(null===p&&f===this.errors.length){if(null!==a)return this.errors=this.errors.slice(0,s),this.createError(h.ONE_OF_MULTIPLE,{index1:a,index2:l},"","/oneOf");if(a=l,this.trackUnknownProperties){for(var c in this.knownPropertyPaths)n[c]=!0,delete i[c];for(var d in this.unknownPropertyPaths)n[d]||(i[d]=!0)}}else p&&o.push(p.prefixWith(null,""+l).prefixWith(null,"oneOf"))}return this.trackUnknownProperties&&(this.unknownPropertyPaths=i,this.knownPropertyPaths=n),null===a?(o=o.concat(this.errors.slice(s)),this.errors=this.errors.slice(0,s),this.createError(h.ONE_OF_MISSING,{},"","/oneOf",o)):(this.errors=this.errors.slice(0,s),null)},l.prototype.validateNot=function(e,r,t){if(void 0===r.not)return null;var i,n,a=this.errors.length;this.trackUnknownProperties&&(i=this.unknownPropertyPaths,n=this.knownPropertyPaths,this.unknownPropertyPaths={},this.knownPropertyPaths={});var o=this.validateAll(e,r.not,null,null,t),s=this.errors.slice(a);return this.errors=this.errors.slice(0,a),this.trackUnknownProperties&&(this.unknownPropertyPaths=i,this.knownPropertyPaths=n),null===o&&0===s.length?this.createError(h.NOT_PASSED,{},"","/not"):null};var h={INVALID_TYPE:0,ENUM_MISMATCH:1,ANY_OF_MISSING:10,ONE_OF_MISSING:11,ONE_OF_MULTIPLE:12,NOT_PASSED:13,NUMBER_MULTIPLE_OF:100,NUMBER_MINIMUM:101,NUMBER_MINIMUM_EXCLUSIVE:102,NUMBER_MAXIMUM:103,NUMBER_MAXIMUM_EXCLUSIVE:104,STRING_LENGTH_SHORT:200,STRING_LENGTH_LONG:201,STRING_PATTERN:202,OBJECT_PROPERTIES_MINIMUM:300,OBJECT_PROPERTIES_MAXIMUM:301,OBJECT_REQUIRED:302,OBJECT_ADDITIONAL_PROPERTIES:303,OBJECT_DEPENDENCY_KEY:304,ARRAY_LENGTH_SHORT:400,ARRAY_LENGTH_LONG:401,ARRAY_UNIQUE:402,ARRAY_ADDITIONAL_ITEMS:403,FORMAT_CUSTOM:500,KEYWORD_CUSTOM:501,CIRCULAR_REFERENCE:600,UNKNOWN_PROPERTY:1e3},u={};for(var f in h)u[h[f]]=f;var p={INVALID_TYPE:"invalid type: {type} (expected {expected})",ENUM_MISMATCH:"No enum match for: {value}",ANY_OF_MISSING:'Data does not match any schemas from "anyOf"',ONE_OF_MISSING:'Data does not match any schemas from "oneOf"',ONE_OF_MULTIPLE:'Data is valid against more than one schema from "oneOf": indices {index1} and {index2}',NOT_PASSED:'Data matches schema from "not"',NUMBER_MULTIPLE_OF:"Value {value} is not a multiple of {multipleOf}",NUMBER_MINIMUM:"Value {value} is less than minimum {minimum}",NUMBER_MINIMUM_EXCLUSIVE:"Value {value} is equal to exclusive minimum {minimum}",NUMBER_MAXIMUM:"Value {value} is greater than maximum {maximum}",NUMBER_MAXIMUM_EXCLUSIVE:"Value {value} is equal to exclusive maximum {maximum}",STRING_LENGTH_SHORT:"String is too short ({length} chars), minimum {minimum}",STRING_LENGTH_LONG:"String is too long ({length} chars), maximum {maximum}",STRING_PATTERN:"String does not match pattern: {pattern}",OBJECT_PROPERTIES_MINIMUM:"Too few properties defined ({propertyCount}), minimum {minimum}",OBJECT_PROPERTIES_MAXIMUM:"Too many properties defined ({propertyCount}), maximum {maximum}",OBJECT_REQUIRED:"Missing required property: {key}",OBJECT_ADDITIONAL_PROPERTIES:"Additional properties not allowed",OBJECT_DEPENDENCY_KEY:"Dependency failed - key must exist: {missing} (due to key: {key})",ARRAY_LENGTH_SHORT:"Array is too short ({length}), minimum {minimum}",ARRAY_LENGTH_LONG:"Array is too long ({length}), maximum {maximum}",ARRAY_UNIQUE:"Array items are not unique (indices {match1} and {match2})",ARRAY_ADDITIONAL_ITEMS:"Additional items not allowed",FORMAT_CUSTOM:"Format validation failed ({message})",KEYWORD_CUSTOM:"Keyword failed: {key} ({message})",CIRCULAR_REFERENCE:"Circular $refs: {urls}",UNKNOWN_PROPERTY:"Unknown property (not in schema)"};a.prototype=Object.create(Error.prototype),a.prototype.constructor=a,a.prototype.name="ValidationError",a.prototype.prefixWith=function(e,r){if(null!==e&&(e=e.replace(/~/g,"~0").replace(/\//g,"~1"),this.dataPath="/"+e+this.dataPath),null!==r&&(r=r.replace(/~/g,"~0").replace(/\//g,"~1"),this.schemaPath="/"+r+this.schemaPath),null!==this.subErrors)for(var t=0;t<this.subErrors.length;t++)this.subErrors[t].prefixWith(e,r);return this};var c={},d=s();return d.addLanguage("en-gb",p),d.tv4=d,d});