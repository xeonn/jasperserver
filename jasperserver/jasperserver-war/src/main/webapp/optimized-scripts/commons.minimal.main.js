define(["require","commons.bare.main","!domReady","components.heartbeat","jrs.configs","components.about","components.webHelp","jquery","attributes/collection/AttributesCollection","attributes/model/AttributeModel","stdnav","stdnavPluginAnchor","stdnavPluginButton","stdnavPluginForms","stdnavPluginGrid","stdnavPluginList","stdnavPluginTable","stdnavPluginActionMenu","stdnavPluginDynamicList","config/dateAndTimeSettings"],function(t){"use strict";t("commons.bare.main");var n=t("!domReady"),i=t("components.heartbeat"),e=t("jrs.configs"),a=t("components.about"),s=t("components.webHelp"),o=t("jquery");t("attributes/collection/AttributesCollection"),t("attributes/model/AttributeModel");if("true"==__jrsConfigs__.enableAccessibility)var l=t("stdnav"),c=t("stdnavPluginAnchor"),u=t("stdnavPluginButton"),d=t("stdnavPluginForms"),r=t("stdnavPluginGrid"),v=t("stdnavPluginList"),b=t("stdnavPluginTable"),g=t("stdnavPluginActionMenu"),m=t("stdnavPluginDynamicList");t("config/dateAndTimeSettings"),n(function(){i.initialize(e.heartbeatInitOptions),i.start(),e.initAdditionalUIComponents&&a.initialize();var t=o("#helpLink");t&&t.on("click",function(t){t.preventDefault(),s.displayWebHelp()}),"true"==__jrsConfigs__.enableAccessibility&&(l.activate(),c.activate(l),u.activate(l),d.activate(l),r.activate(l),v.activate(l),b.activate(l),g.activate(l),m.activate(l),l.start())})});