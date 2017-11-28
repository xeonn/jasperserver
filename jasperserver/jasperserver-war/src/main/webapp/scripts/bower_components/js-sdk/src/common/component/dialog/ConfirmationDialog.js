/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com.
 * Licensed under commercial Jaspersoft Subscription License Agreement
 */


/**
 * @author: Olesya Bobruyko
 * @version: $Id: ConfirmationDialog.js 1154 2015-04-25 17:52:53Z ktsaregr $
 */

define(function (require) {
    "use strict";

    var _ = require('underscore'),
        Dialog = require("./Dialog"),
        confirmDialogTemplate = require("text!./template/confirmDialogTemplate.htm"),
        i18n = require('bundle!CommonBundle');

    return Dialog.extend({

        constructor: function(options) {
            options || (options = {});

            this.confirmDialogTemplate = _.template(confirmDialogTemplate);

            Dialog.prototype.constructor.call(this, {
                modal: true,
                additionalCssClasses: "confirmationDialog",
                title: options.title || i18n["dialog.confirm.title"],
                content: this.confirmDialogTemplate({ text: options.text }),
                buttons: [
                    { label: i18n["button.yes"], action: "yes", primary: true },
                    { label: i18n["button.no"], action: "no", primary: false }
                ]
            });
        },

        initialize: function() {
            Dialog.prototype.initialize.apply(this, arguments);

            this.on("button:yes", this.close);
            this.on("button:no", this.close);
        },

        setContent: function(content) {
            Dialog.prototype.setContent.call(this, this.confirmDialogTemplate({text: content}));
        }
    });
});
