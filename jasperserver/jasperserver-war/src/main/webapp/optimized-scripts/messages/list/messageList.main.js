define(["require","!domReady","messages/list/messageList","jrs.configs"],function(s){"use strict";var e=s("!domReady"),i=s("messages/list/messageList"),t=s("jrs.configs");e(function(){i.initialize(t.messagesListInitOptions)})});