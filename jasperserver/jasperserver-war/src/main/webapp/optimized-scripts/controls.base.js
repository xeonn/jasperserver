var ControlsBase={INPUT_CONTROLS_FORM:"inputControlsForm",INPUT_CONTROLS_CONTAINER:"inputControlsContainer",INPUT_CONTROLS_DIALOG:"inputControls",SAVE_REPORT_OPTIONS_DIALOG:"saveValues",REPORT_OPTIONS_SELECTOR:"savedValues",TOOLBAR_CONTROLS_BUTTON:"ICDialog",DEFAULT_OPTION_TEXT:"Options",NULL_SUBSTITUTION_VALUE:null,NULL_SUBSTITUTION_LABEL:null,NOTHING_SUBSTITUTION_VALUE:null,_BUTTON_OK:"ok",_BUTTON_SAVE:"save",_BUTTON_REMOVE:"remove",_BUTTON_APPLY:"apply",_BUTTON_CANCEL:"cancel",TEMP_REPORT_CONTAINER:"tempReportContainer",WRAPPER_ERROR_FLAG_PREFIX_SELECTOR:"div[id^=error_]",WRAPPERS_ERRORS_DIV:"wrappersErrors",EVAL_SCRIPT_ELEMENT_NAME:"_evalScript",CALENDAR_ICON_SPAN:'<span class="button picker calTriggerWrapper" />',_messages:{},getMessage:function(o,t){var n=this._messages[o];return n?new Template(n).evaluate(t?t:{}):""},setControlError:function(o,t){o&&(o=$(o),t?(o.up().addClassName(layoutModule.ERROR_CLASS),o.nextSiblings()[0].update(t)):(o.up().removeClassName(layoutModule.ERROR_CLASS),o.nextSiblings()[0].update("")))},setControlsErrors:function(o){var t=$$(ControlsBase.WRAPPER_ERROR_FLAG_PREFIX_SELECTOR),n=0,i=-1;t.each(function(t){var e=!1;o&&o.each(function(o){if(t.id.indexOf(o[0])>-1)throw ControlsBase.setControlError(t,o[1]),0>i&&(jQuery("#inputControlsForm .leaf").each(function(o,t){n>o&&(i+=t.clientHeight)}),jQuery(".groupBox .content .body")[0].scrollTop=i),e=!0,$break;n++}),e||ControlsBase.setControlError(t,"")})},buildParams:function(o){var t="";if(_.isObject(o)&&!_.isEmpty(o)){var n=function(o){return o.length>0&&"&"!=o[o.length-1]&&(o+="&"),o};_.each(o,function(o,i){t=n(t),_.isArray(o)?_.each(o,function(o){t=n(t),t+=i+"="+encodeURIComponent(o)}):t+=i+"="+encodeURIComponent(o)})}return t},buildSelectedDataUri:function(o,t){var n=ControlsBase.buildParams(o);if(_.isObject(t)&&!_.isEmpty(t)){var i=ControlsBase.buildParams(t);return n+"&"+i}return n}},ControlDialog=function(o){var t=this;this._dom=$(ControlsBase.INPUT_CONTROLS_DIALOG),this.buttonActions=o,jQuery("#"+ControlsBase.INPUT_CONTROLS_DIALOG).on("mouseup touchend","button",function(o){if(this.id&&!this.id.empty()){var n=t.buttonActions["button#"+this.id];n&&n()}})};ControlDialog.addMethod("_dialogClickHandler",function(o){var t=o.element();for(var n in this.buttonActions)if(matchAny(t,[n],!0))return this.buttonActions[n](),void o.stop()}),ControlDialog.addMethod("show",function(){dialogs.popup.show(this._dom)}),ControlDialog.addMethod("hide",function(){dialogs.popup.hide(this._dom)});var OptionsDialog=function(o){this._dom=$(ControlsBase.SAVE_REPORT_OPTIONS_DIALOG),this.input=this._dom.select("input#savedValuesName")[0],this.overwrite=!1,this.buttonActions=o,this._dom.observe("click",this._dialogClickHandler.bindAsEventListener(this))};OptionsDialog.addMethod("_dialogClickHandler",function(o){var t=o.element();for(var n in this.buttonActions)if(matchAny(t,[n],!0))return this.buttonActions[n](),void o.stop()}),OptionsDialog.addMethod("show",function(){this.input.setValue(ControlsBase.DEFAULT_OPTION_TEXT),dialogs.popup.show(this._dom)}),OptionsDialog.addMethod("hide",function(){dialogs.popup.hide(this._dom),this.hideWarning()}),OptionsDialog.addMethod("showWarning",function(o){var t=jQuery("#"+ControlsBase.SAVE_REPORT_OPTIONS_DIALOG).find(".warning");o&&t.text(o),t.show()}),OptionsDialog.addMethod("hideWarning",function(){var o=jQuery("#"+ControlsBase.SAVE_REPORT_OPTIONS_DIALOG).find(".warning");o.text(""),o.hide()});