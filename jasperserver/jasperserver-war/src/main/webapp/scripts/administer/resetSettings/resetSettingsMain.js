/*
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */


/**
 * @version: $Id: resetSettingsMain.js 10082 2016-04-20 18:28:47Z inestere $
 */

define(function(require) {
    "use strict";

    var domReady = require("!domReady"),
        $ = require("jquery"),
        i18n = require("bundle!CommonBundle"),
        i18n2 = require("bundle!EditSettingsBundle"),
        logging = require("administer.logging"),
        Administer = require("administer.base"),
        jrsConfigs = require("jrs.configs"),
        buttonsTrait = require("serverSettingsCommon/view/traits/buttonsTrait"),
        ResetSettingsCollectionView = require("./view/ResetSettingsCollectionView").extend(buttonsTrait),
        ResetSettingsItemView = require("./view/ResetSettingsItemView"),
        ResetSettingsEmptyView = require("./view/ResetSettingsEmptyView"),
        ResetSettingsCollection = require("./collection/ResetSettingsCollection"),
        ResetSettingsModel = require("./model/ResetSettingsModel"),
        tooltipTemplate = require("text!./templates/tooltipTemplate.htm");

    domReady(function() {
        Administer.urlContext = jrsConfigs.urlContext;

        logging.initialize();

        var collection = new ResetSettingsCollection([], {
                model: ResetSettingsModel
            }),
            resetSettingsView = new ResetSettingsCollectionView({
                el: $(".resetSettings"),
                tooltip: {
                    template: tooltipTemplate,
                    i18n: i18n2
                },
                collection: collection,
                childViewContainer: ".tbody",
                childView: ResetSettingsItemView,
                emptyView: ResetSettingsEmptyView,
                buttons: [
                    { label: i18n["button.save"], action: "save", primary: true },
                    { label: i18n["button.cancel"], action: "cancel", primary: false }
                ],
                buttonsContainer: ".buttonsContainer"
            });

        resetSettingsView.fetchData()
            .done(resetSettingsView.render);
    });
});