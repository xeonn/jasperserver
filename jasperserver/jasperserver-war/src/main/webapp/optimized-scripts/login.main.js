define(["require","!domReady","login.form","jquery","components.loginBox","jrs.configs"],function(i){"use strict";var t=i("!domReady");i("login.form");var s=i("jquery"),n=i("components.loginBox"),o=i("jrs.configs");window.location.hash&&(window.localStorage.previousPageHash=window.location.hash),t(function(){if(isIPad()&&s("#frame").hide(),o.isProVersion&&(n._initVars=function(i){this._baseInitVars(i),this._organizationId=i.organizationId,this._singleOrganization=i.singleOrganization},n._processTemplate=function(){this._baseProcessTemplate(),this._organizationIdLabel=this._dom.select('label[for="orgId"]')[0],this._organizationIdInput=s("#orgId")},n.initialize=function(i){var t=s("#j_username");this._baseInitialize(i),this._singleOrganization||this._organizationId?this._organizationIdInput.val(this._organizationId):this._organizationIdLabel.removeClassName("hidden"),""===t.val()&&""===s("#j_password_pseudo").val()&&(this._singleOrganization?t.focus():""===this._organizationIdInput.val()&&this._organizationIdInput.focus())}),n.initialize(o.loginState),isIPad()){var i=window.orientation;switch(i){case 0:s("h2.textAccent").css("font-size","14px").parent().css("width","39%"),s("#copy").css("width","600px"),s("#loginForm").css({left:"524px",right:""});break;case 90:s("h2.textAccent").css("font-size","16px").parent().css("width","46%"),s("#copy").css("width","766px");break;case-90:s("h2.textAccent").css("font-size","16px").parent().css("width","46%"),s("#copy").css("width","766px")}s("#frame").show(),window.addEventListener("orientationchange",function(i){var t=window.orientation;switch(t){case 0:s("h2.textAccent").css("font-size","14px").parent().css("width","39%"),s("#copy").css("width","600px"),s("#loginForm").css({left:"524px",right:""});break;case 90:s("h2.textAccent").css("font-size","16px").parent().css("width","46%"),s("#copy").css("width","766px"),s("#loginForm").css({left:"",right:"-10px"});break;case-90:s("h2.textAccent").css("font-size","16px").parent().css("width","46%"),s("#copy").css("width","766px"),s("#loginForm").css({left:"",right:"-10px"})}})}})});