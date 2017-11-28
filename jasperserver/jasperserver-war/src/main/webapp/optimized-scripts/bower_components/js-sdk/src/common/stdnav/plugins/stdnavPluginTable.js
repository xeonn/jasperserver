define(["require","exports","module","jquery","underscore","logger","stdnav"],function(t,e,n){"use strict";var i=t("jquery"),s=(t("underscore"),t("logger").register(n)),r=t("stdnav"),a=0,o=function(){a++,this.serial=a};i.extend(o.prototype,{zinit:function(t){return s.debug("stdnavPluginTable.init("+t+")\n"),this},activate:function(){this.behavior={ariaprep:[this,this._ariaPrep,null],ariarefresh:[this,this._ariaRefresh,null],down:[this,this._onDown,null],fixfocus:[this,this._fixFocus,null],fixsuperfocus:[this,this._fixSuperfocus,null],inherit:!0,inheritable:!0,left:[this,this._onLeft,null],mousedown:[r,r.basicMouseDown],right:[this,this._onRight,null],up:[this,this._onUp,null],superfocusin:[r,r.basicSuperfocusIn,{maxdepth:4,focusSelector:"td",ghostfocus:!1}],superfocusout:[r,r.basicSuperfocusOut,{ghostfocus:!1}]},r.registerNavtype(this.navtype,this.behavior,this.navtype_tags)},deactivate:function(){r.unregisterNavtype(this.navtype,this.behavior)},_ariaPrep:function(t){this._ariaRefresh(t)},_ariaRefresh:function(t){var e=i(t);e.attr("role","application");var n=e.attr("aria-label"),s=e.attr("aria-labelledby"),a=e.find("td,th"),o=!1;if(r.nullOrUndefined(n)&&r.nullOrUndefined(s)){var l=a.find("a");l.length===a.length?(e.attr("aria-label","Table of "+a.length+" links."),o=!0):e.attr("aria-label","Table of "+a.length+" cells.")}return i.each(a,function(t,e){var n=i(e),s=n.find("a");if(s.length>0){n.attr("role","link");var a=n.attr("aria-label"),o=n.attr("aria-labelledby");if(r.nullOrUndefined(a)&&r.nullOrUndefined(o)){var l=n.text();i(s[0]).text();a=l,n.attr("aria-label",a)}}}),null},_findSubfocus:function(t){var e=i(t).closest("table"),n=e.find(".subfocus");return void 0!==n?i(n[0]):void 0},_getPreviousSection:function(t){var e,n=i(t).closest("thead,tbody,tfoot");if(void 0!==n){switch(n.prop("nodeType")){case"TFOOT":e=n.parent().children("TBODY");break;case"TBODY":e=n.parent().children("THEAD");break;case"THEAD":}return void 0!==e&&e.length>0?e[0]:void 0}},_getNextSection:function(t){var e,n=i(t).closest("thead,tbody,tfoot");if(void 0!==n){switch(n.prop("nodeType")){case"THEAD":e=n.parent().children("TBODY");break;case"TBODY":e=n.parent().children("TFOOT");break;case"TFOOT":}return void 0!==e&&e.length>0?e[0]:void 0}},_fixSuperfocus:function(t){var e,n=i(t).closest("table");return e=n.length>0?n[0]:null},_fixFocus:function(t){var e;switch(i(t).prop("nodeName")){case"TH":case"TD":e=t;break;case"TR":e=r.closestDescendant(t,"td,th .ghostfocus",null,1),void 0===e&&(e=r.closestDescendant(t,"td,th",null,1),void 0===e&&(e=i(t).prev("tr"),void 0===e&&(e=this._fixFocus(i(t).parent()))));break;case"THEAD":case"TBODY":case"TFOOT":if(e=r.closestDescendant(t,"td,th .ghostfocus",null,2),void 0===e&&(e=r.closestDescendant(t,"td,th",null,2),void 0===e)){var n=this._getNextSection(t);e=void 0===n?this._fixFocus(i(t).closest("table")):this._fixFocus(n)}break;case"COLGROUP":case"COL":e=this._fixFocus(i(t).closest("table"));break;case"CAPTION":e=this._fixFocus(i(t).closest("table"));break;case"TABLE":e=r.closestDescendant(t,"td,th .ghostfocus",null,5),void 0===e&&(e=r.closestDescendant(t,"td,th",null,5),void 0===e&&(e=t));break;default:e=this._fixFocus(i(t).closest("td,th,table"))}return e},_onSubfocusIn:function(t){var e=t;i(t).is("td")===!1&&(e=this._fixSubfocus(i(t)),r.setSubfocus(e,!1)),i.call(this,r.basicSubfocusIn,e)},_onLeft:function(t){var e=this._findSubfocus(t),n=i(!1);return e.is("td")&&(n=e.prev("td")),1===n.length?n:t},_onRight:function(t){var e=this._findSubfocus(t),n=i(!1);return e.is("td")&&(n=e.next("td")),1===n.length?n:t},_onUp:function(t){var e=this._findSubfocus(t),n=i(!1),s=i(!1),r=i(!1);if(e.is("td")&&(n=e.closest("tr"),1===n.length&&(s=n.prev("tr")),1==s.length)){var a=e,o=-1;do o++,a=i(a.prev("td")[0]);while(a.length>0);for(r=i(s.find("td")[0]);o>0;)a=r.next("td"),a.length>0&&(r=i(a[0])),o--}return 1===r.length?r:t},_onDown:function(t){var e=this._findSubfocus(t),n=i(!1),s=i(!1),r=i(!1);if(e.is("td")&&(n=e.closest("tr"),n.length>0&&(s=n.next("tr")),s.length>0)){var a=e,o=-1;do o++,a=i(a.prev("td")[0]);while(a.length>0);for(r=i(s.find("td")[0]);o>0;)a=r.next("td"),a.length>0&&(r=i(a[0])),o--}return 1===r.length?r:t}}),i.extend(o.prototype,{navtype:"table",navtype_tags:["TABLE"]});var l=new o;return l});