/**
 * The OWASP CSRFGuard Project, BSD License
 * Eric Sheridan (eric@infraredsecurity.com), Copyright (c) 2011 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *    2. Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *    3. Neither the name of OWASP nor the names of its contributors may be used
 *       to endorse or promote products derived from this software without specific
 *       prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
(function($) {
    "use strict";

    var DELAY_AJAX_TILL_CSRF_TOKEN_LOADS_MILLIS = 1000;
    var MAX_NUMBER_OF_TRIES = 5;
    var isCSRFTokenLoaded = false;

	/** hook using standards based prototype **/
	function hijackStandard() {
		XMLHttpRequest.prototype._open = XMLHttpRequest.prototype.open;
		XMLHttpRequest.prototype.open = function(method, url, async, user, pass) {
			this.url = url;
            this.ajaxTryCntr = 0;   //number of ajax request tries (waiting for CSRF token to load)

			this._open.apply(this, arguments);
		};
		
		XMLHttpRequest.prototype._send = XMLHttpRequest.prototype.send;
		XMLHttpRequest.prototype.send = function(data) {
            if (this.url.indexOf("%SERVLET_PATH%") >= 0 || isCSRFTokenLoaded) {
            if(this.onsend != null) {
                this.onsend.apply(this, arguments);
            }

            this._send.apply(this, arguments);
            }
            else {
                //wait till csrf token gets loaded for all ajax requests (except request for csrf token itself).
                // Hopefully, this won't take long!
                ajaxWaitForCsrfTokenToLoad(this, data);
            }
		};
	}

	/** ie does not properly support prototype - wrap completely **/
	function hijackExplorer() {
		var _XMLHttpRequest = window.XMLHttpRequest;
		
		function alloc_XMLHttpRequest() {
			this.base = _XMLHttpRequest ? new _XMLHttpRequest : new window.ActiveXObject("Microsoft.XMLHTTP");
		}
		
		function init_XMLHttpRequest() {
			return new alloc_XMLHttpRequest;
		}
		
		init_XMLHttpRequest.prototype = alloc_XMLHttpRequest.prototype;
		
		/** constants **/
		init_XMLHttpRequest.UNSENT = 0;
		init_XMLHttpRequest.OPENED = 1;
		init_XMLHttpRequest.HEADERS_RECEIVED = 2;
		init_XMLHttpRequest.LOADING = 3;
		init_XMLHttpRequest.DONE = 4;
		
		/** properties **/
		init_XMLHttpRequest.prototype.status = 0;
		init_XMLHttpRequest.prototype.statusText = "";
		init_XMLHttpRequest.prototype.readyState = init_XMLHttpRequest.UNSENT;
		init_XMLHttpRequest.prototype.responseText = "";
		init_XMLHttpRequest.prototype.responseXML = null;
		init_XMLHttpRequest.prototype.onsend = null;
		
		init_XMLHttpRequest.url = null;
		init_XMLHttpRequest.onreadystatechange = null;

		/** methods **/
		init_XMLHttpRequest.prototype.open = function(method, url, async, user, pass) {
			var self = this;
			this.url = url;
            this.ajaxTryCntr = 0;

			this.base.onreadystatechange = function() {
				try { self.status = self.base.status; } catch (e) { }
				try { self.statusText = self.base.statusText; } catch (e) { }
				try { self.readyState = self.base.readyState; } catch (e) { }
				try { self.responseText = self.base.responseText; } catch(e) { }
				try { self.responseXML = self.base.responseXML; } catch(e) { }
				
				if(self.onreadystatechange != null) {
					self.onreadystatechange.apply(this, arguments);
				}
			}
			
			this.base.open(method, url, async, user, pass);
		};
		
		init_XMLHttpRequest.prototype.send = function(data) {
            if (this.url.indexOf("%SERVLET_PATH%") >= 0 || isCSRFTokenLoaded) {
            if(this.onsend != null) {
                this.onsend.apply(this, arguments);
            }

            this.base.send(data);
            }
            else {
                //wait till csrf token gets loaded for all ajax requests (except request for csrf token itself).
                // Hopefully, this won't take long!
                ajaxWaitForCsrfTokenToLoad(this, data);
            }
		};
		
		init_XMLHttpRequest.prototype.abort = function() {
			this.base.abort();
		};
		
		init_XMLHttpRequest.prototype.getAllResponseHeaders = function() {
			return this.base.getAllResponseHeaders();
		};
		
		init_XMLHttpRequest.prototype.getResponseHeader = function(name) {
			return this.base.getResponseHeader(name);
		};
		
		init_XMLHttpRequest.prototype.setRequestHeader = function(name, value) {
			return this.base.setRequestHeader(name, value);
		};
		
		/** hook **/
		window.XMLHttpRequest = init_XMLHttpRequest;
	}

    function ajaxWaitForCsrfTokenToLoad(xhr, data) {
        if (xhr.ajaxTryCntr < MAX_NUMBER_OF_TRIES) {
            window.setTimeout(function () {
                xhr.ajaxTryCntr++;
                xhr.send(data);
            }, DELAY_AJAX_TILL_CSRF_TOKEN_LOADS_MILLIS);
        }
        else {
            $(document).trigger('systemDialogWarn', ['Connection error! Try reloading!', 60000]);
            console.error('Failed to retrieve CSRF token');
        }
    }

	/** check if valid domain based on domainStrict **/
	function isValidDomain(pageDomain, serverDomain) {
		var result = false;
        var checkDomain = function(page, server) {
            return page.match(server+"$") == server;
        };

		/** check exact or subdomain match **/
		if(pageDomain == serverDomain) {
			result = true;
		} else if(%DOMAIN_STRICT% == false) {
			if(serverDomain.charAt(0) == '.') {
				result = checkDomain(pageDomain, serverDomain);
			} else {
				result = checkDomain(pageDomain, '.' + serverDomain);
			}
		}
		
		return result;
	}

	/** determine if uri/url points to valid domain **/
	function isValidUrl(src) {
		var result = false;
		
		/** parse out domain to make sure it points to our own **/
		if(src.substring(0, 7) == "http://" || src.substring(0, 8) == "https://") {
			var token = "://";
			var index = src.indexOf(token);
			var part = src.substring(index + token.length);
			var domain = "";
			
			/** parse up to end, first slash, or anchor **/
			for(var i=0; i<part.length; i++) {
				var character = part.charAt(i);
				
				if(character == '/' || character == ':' || character == '#') {
					break;
				} else {
					domain += character;
				}
			}
			
			result = isValidDomain(document.domain, domain);
			/** explicitly skip anchors **/
		} else if(src.charAt(0) == '#') {
			result = false;
			/** ensure it is a local resource without a protocol **/
		} else if(!(src.indexOf("//") === 0) && (src.charAt(0) == '/' || src.indexOf(':') == -1)) {
			result = true;
		}

		return result;
	}

	/** parse uri from url **/
	function parseUri(url) {
		var uri = "";
		var token = "://";
		var index = url.indexOf(token);
		var part = "";
		
		/**
		 * ensure to skip protocol and prepend context path for non-qualified
		 * resources (ex: "protect.html" vs
		 * "/Owasp.CsrfGuard.Test/protect.html").
		 */
		if(index > 0) {
			part = url.substring(index + token.length);
		} else if(url.charAt(0) != '/') {
			part = "%CONTEXT_PATH%/" + url;
		} else {
			part = url;
		}
		
		/** parse up to end or query string **/
		var uriContext = (index == -1);
		
		for(var i=0; i<part.length; i++) {
			var character = part.charAt(i);
			
			if(character == '/') {
				uriContext = true;
			} else if(uriContext == true && (character == '?' || character == '#')) {
				uriContext = false;
				break;
			}
			
			if(uriContext == true) {
				uri += character;
			}
		}
		
		return uri;
	}

	/** inject tokens as hidden fields into forms **/
	function injectTokenForm(form, tokenName, tokenValue, pageTokens,injectGetForms) {
	  
		if (!injectGetForms) {
			var method = form.getAttribute("method");
	  
			if ((typeof method != 'undefined') && method != null && method.toLowerCase() == "get") {
				return;
			}
		}

		var value = tokenValue;
		var action = form.getAttribute("action");

		if(action != null && isValidUrl(action)) {
			var uri = parseUri(action);
            //JRS: pageTokens is not working because form action could be set dynamically before form submit (e.g. pageForm
            // launching domain designer). To fix this, attach submit events to the forms looking up action uri token
            // and setting the hidden token input value.
			value = pageTokens[uri] != null ? pageTokens[uri] : tokenValue;
		}

		var hidden = document.createElement("input");

		hidden.setAttribute("type", "hidden");
		hidden.setAttribute("name", tokenName);
		hidden.setAttribute("value", value);
		
		form.appendChild(hidden);
	}

	/** inject tokens as query string parameters into url **/
	function injectTokenAttribute(element, attr, tokenName, tokenValue, pageTokens) {
		var location = element.getAttribute(attr);
		
		if(location != null && isValidUrl(location)) {
			var uri = parseUri(location);
			var value = (pageTokens[uri] != null ? pageTokens[uri] : tokenValue);
			
			if(location.indexOf('?') != -1) {
				location = location + '&' + tokenName + '=' + value;
			} else {
				location = location + '?' + tokenName + '=' + value;
			}

			try {
				element.setAttribute(attr, location);
			} catch (e) {
				// attempted to set/update unsupported attribute
			}
		}
	}

	/** inject csrf prevention tokens throughout dom **/
	function injectTokens(tokenName, tokenValue) {
		/** obtain reference to page tokens if enabled **/
		var pageTokens = {};
		
		if(%TOKENS_PER_PAGE% == true) {
			pageTokens = requestPageTokens();
		}
		
		/** iterate over all elements and injection token **/
		var all = document.all ? document.all : document.getElementsByTagName('*');
		var len = all.length;

		//these are read from the csrf guard config file(s)
		var injectForms = %INJECT_FORMS%;
		var injectGetForms = %INJECT_GET_FORMS%;
		var injectFormAttributes = %INJECT_FORM_ATTRIBUTES%;
		var injectAttributes = %INJECT_ATTRIBUTES%;
		
		for(var i=0; i<len; i++) {
			var element = all[i];
			
			/** inject into form **/
			if(element.tagName.toLowerCase() == "form") {
				if(injectForms) {
					injectTokenForm(element, tokenName, tokenValue, pageTokens,injectGetForms);
				}
				if (injectFormAttributes) {
					injectTokenAttribute(element, "action", tokenName, tokenValue, pageTokens);
				}
				/** inject into attribute **/
			} else if(injectAttributes) {
				injectTokenAttribute(element, "src", tokenName, tokenValue, pageTokens);
				injectTokenAttribute(element, "href", tokenName, tokenValue, pageTokens);
			}
		}
	}

	/** obtain array of page specific tokens **/
    //JRS: Not used (TokenPerPage=false).  This is broken anyways as a form on a page
    // could have an action uri different from the page uri.
	function requestPageTokens() {
		var xhr = window.XMLHttpRequest ? new window.XMLHttpRequest : new window.ActiveXObject("Microsoft.XMLHTTP");
		var pageTokens = {};
		
		xhr.open("POST", "%SERVLET_PATH%", false);
		xhr.send(null);
		
		var text = xhr.responseText;
		var name = "";
		var value = "";
		var nameContext = true;
		
		for(var i=0; i<text.length; i++) {
			var character = text.charAt(i);
			
			if(character == ':') {
				nameContext = false;
			} else if(character != ',') {
				if(nameContext == true) {
					name += character;
				} else {
					value += character;
				}
			}
			
			if(character == ',' || (i + 1) >= text.length) {
				pageTokens[name] = value;
				name = "";
				value = "";
				nameContext = true;
			}
		}
		
		return pageTokens;
	}
	
	/**
	 * Only inject the tokens if the JavaScript was referenced from HTML that
	 * was served by us. Otherwise, the code was referenced from malicious HTML
	 * which may be trying to steal tokens using JavaScript hijacking techniques.
	 * The token is now removed and fetched using another POST request to solve,
	 * the token hijacking problem.
	 */
	if(isValidDomain(document.domain, "%DOMAIN_ORIGIN%")) {
		/** optionally include Ajax CSRF support **/
        if(%INJECT_XHR% == true) {
            if (navigator.appName == "Microsoft Internet Explorer") {
                hijackExplorer();
            } else {
                hijackStandard();
            }
        }

        $.ajax({
            method: "POST",
            url: "%SERVLET_PATH%",
            headers: {"FETCH-CSRF-TOKEN": "1"}
        })
        .done(function(tokenResp) {
                var token_pair = tokenResp.split(":");
                var token_name = token_pair[0];
                var token_value = token_pair[1];

                if(%INJECT_XHR% == true) {
                    XMLHttpRequest.prototype.onsend = function (data) {
                        if (isValidUrl(this.url)) {
                            this.setRequestHeader("X-Requested-With", "%X_REQUESTED_WITH%");
                            this.setRequestHeader(token_name, token_value);
                        }
                    };
                }

                if ( %INJECT_FORMS% == true) {
                    //inject CSRF token into dynamically created post forms
                    HTMLFormElement.prototype._submit = HTMLFormElement.prototype.submit;
                    HTMLFormElement.prototype.submit = function (data) {
                        // The forms are submitted synchronously; not likely to be submitted during page load.
                        if (!this.elements[token_name]) {
                            var pageTokens = {};
                            if (%TOKENS_PER_PAGE% == true)
                                pageTokens = requestPageTokens();
                            var injectGetForms = %INJECT_GET_FORMS%;
                            injectTokenForm(this, token_name, token_value, pageTokens, injectGetForms);
                        }
                        this._submit.apply(this, arguments);
                    };
                }

                $( document ).ready(function() {
                    /** update nodes (with csrf token) in DOM after load **/
                    injectTokens(token_name, token_value);
                });

                isCSRFTokenLoaded = true;
        })
        .fail(function(error) {
            $(document).trigger('systemDialogWarn', ['Connection error! Try reloading!', 60000]);
            console.error('Failed to retrieve CSRF token');
        });
	} else {
		console.error("OWASP CSRFGuard JavaScript was included from within an unauthorized domain!");
	}

})(jQuery);
