/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights reserved.
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
 * @author: Kostiantyn Tsaregradskyi
 * @version: $Id: ReportController.js 2889 2014-12-12 14:44:48Z tbidyuk $
 */

define(function (require) {
    "use strict";

    var Backbone = require("backbone"),
        _ = require("underscore"),
        $ = require("jquery"),
        ReportView = require("./view/ReportView"),
        ReportStateStack = require("./model/ReportStateStack"),
        ReportComponentCollection = require("./jive/collection/ReportComponentCollection"),
        ReportModel = require("./model/ReportModel"),
        ExportModel = require("./model/ReportExportModel"),
        biComponentErrorCodes = require("common/bi/error/enum/biComponentErrorCodes"),
        biComponentErrorFactoryReportProxy = require("./error/biComponentErrorFactoryReportProxy"),
        log =  require("logger").register("Report"),
        reportEvents = require("./enum/reportEvents"),
        reportStatuses = require("./enum/reportStatuses"),
        reportOutputFormats = require("./enum/reportOutputFormats"),
        request = require("common/transport/request");

    function runAction(action) {
        var dfd = new $.Deferred(),
            self = this,
            actionObj;

        actionObj = action.options && action.options.showErrorDialog ? _.omit(action, "options") : action;

        this.view.showOverlay();
        this.model
            .runAction(actionObj)
            .then(_.bind(self.model.updateStatus, self.model), dfd.reject)
            .then(function() {
                if (self.model.isFailed() || self.model.isCancelled()) {
                    dfd.reject({
                        source: "execution",
                        status: self.model.get("status"),
                        errorDescriptor: self.model.get("errorDescriptor")
                    });
                } else {
                    self.trigger(reportEvents.AFTER_REPORT_EXECUTION);

                    self.fetchReportHtmlExportAndJiveComponents()
                        .done(dfd.resolve)
                        .fail(dfd.reject);
                }
            }, dfd.reject);

            dfd.fail(function(response) {
                action = _.isArray(action) ? _.reduce(action) : action;
                var actionName = action.actionName + "Data";
                var actionObject = action[actionName];
                var showErrorDialog = action.options && action.options.showErrorDialog;

                if (response.readyState === 4 &&
                    response.status === 500) {
                    showErrorDialog && self.components.get(actionObject[actionObject.chartComponentUuid ? "chartComponentUuid" : "tableUuid"]).
                            handleServerError(response.responseJSON.result);
                }
                self.view.hideOverlay();
            });

        return dfd;
    }

    function ReportController(stateModel) {
        var self = this;

        this.model = new ReportModel();
        this.components = new ReportComponentCollection([], {
            report: this.model ,
	        stateModel: stateModel
        });
        this.view = new ReportView({
            model: this.model,
            collection: this.components,
	        stateModel: stateModel
        });
        this.stateStack = new ReportStateStack();

        // dirty hack to make JIVE work now
        this.model.components = this.components;
        this.model.config = {
            container: this.view.$el
        };

        if (!this.model.getExport(reportOutputFormats.HTML)) {
            this.model.addExport({ options: { outputFormat: reportOutputFormats.HTML } });
        }

        this.listenTo(this.model.getExport(reportOutputFormats.HTML), "change:outputFinal", function(){
            self.trigger(reportEvents.PAGE_FINAL, this.model.getExport(reportOutputFormats.HTML).getHTMLOutput());
        });

        this.listenTo(this.components, reportEvents.ACTION, this.runReportAction);

        this.listenTo(this.model, "change:status", function() {
            log.info("Report status changed to '" + self.model.get("status") + "'");

            if (self.model.isReady()) {
                self.model.update()
                    .done(function() {
                        log.info("Report total pages number is " + self.model.get("totalPages"));
                        if(self.model.getExport(reportOutputFormats.HTML).get("outputFinal")){
                            // HTML is final, don't need to reload.
                            self.trigger(reportEvents.REPORT_COMPLETED, self.model.get("status"));
                        } else {
                            self.fetchReportHtmlExportAndJiveComponents().fail(function () {
                                    var args = Array.prototype.slice.call(arguments);
                                    args.unshift(reportStatuses.FAILED);
                                    args.unshift(reportEvents.REPORT_COMPLETED);

                                    self.trigger.apply(self, args);
                                });
                        }
                    });
            } else if (self.model.isCancelled() || self.model.isFailed()) {
                self.trigger(reportEvents.REPORT_COMPLETED, self.model.get("status"), {
                    source: "execution",
                    status: self.model.get("status"),
                    errorDescriptor: self.model.get("errorDescriptor")
                });
            }
        });

        this.once(reportEvents.REQUESTED_PAGES_READY, function() {
            if (!self.model.isCompleted()) {
                self.model.waitForExecution();
            }

            // first REQUESTED_PAGES_READY event differs a bit from consequent events
            self.on(reportEvents.REQUESTED_PAGES_READY, function() {
                if (!self.model.isCompleted()) {
                    self.model.waitForExecution();
                }

                if (self._reportRendered) {
                    self.view.renderReport();
                    self.view.renderJive()
                        .done(function() {
                            if (self.model.isReady()) {
                                self.trigger(reportEvents.REPORT_COMPLETED, self.model.get("status"));
                            }
                        });
                }
            });
        });

        this.on(reportEvents.AFTER_REPORT_EXECUTION, function() {
            self.model.execution.set("pages", 1);
            _.extend(self.model.getExport(reportOutputFormats.HTML).get("options"), { "pages": 1 });
        });
    }

    _.extend(ReportController.prototype, Backbone.Events, {
        undoReportAction: function() {
            var self = this;

            return runAction.call(this, { actionName: "undo" })
                .done(function() {
                    self.stateStack.previousState();
                });
        },

        undoAllReportAction: function() {
            var self = this;

            return runAction.call(this, { actionName: "undoAll" })
                .done(function() {
                    self.stateStack.firstState();
                });
        },

        redoReportAction: function() {
            var self = this;

            return runAction.call(this, { actionName: "redo" })
                .done(function() {
                    self.stateStack.nextState();
                });
        },

        runReportAction: function(action) {
            var self = this;

            return runAction.call(this, action)
                .done(function() {
                    self.stateStack.newState();
                });
        },

        executeReport: function(refresh) {
            var dfd = new $.Deferred(),
                self = this;

            this.model
                .execute({freshData: !!refresh})
                .then(_.bind(self.fetchReportHtmlExportAndJiveComponents, self), dfd.reject)
                .then(function() {
                    self.stateStack.newState();
                    dfd.resolve.apply(dfd, arguments);
                }, dfd.reject);

            return dfd;
        },

        cancelReportExecution: function() {
            this.fetchExportDfd && this.fetchExportDfd.state() === "pending" && this.fetchExportDfd.reject(
                {source: "execution",
                status: "cancelled"});
            return this.model.cancel();
        },

        applyReportParameters: function(refresh) {
            var dfd = new $.Deferred(),
                self = this;
            this.fetchExportDfd && this.fetchExportDfd.state() === "pending" && this.fetchExportDfd.reject(
                {source: "execution",
                    status: "cancelled"});

            this.model
                .applyParameters(refresh)
                .then(_.bind(self.model.updateStatus, self.model), dfd.reject)
                .then(function() {
                    self.trigger(reportEvents.AFTER_REPORT_EXECUTION);
                    self.fetchExportDfd = self.fetchReportHtmlExportAndJiveComponents();
                    return self.fetchExportDfd;
                }, dfd.reject)
                .then(dfd.resolve, dfd.reject);

            return dfd;
        },

        fetchReportHtmlExportAndJiveComponents: function() {
            var dfd = new $.Deferred(),
                exportDfd,
                outputDfd;

            dfd.fail(function(error){
                exportDfd && exportDfd.state() === "pending" && (exportDfd.reject ? exportDfd.reject(error) : exportDfd.abort(error));
                outputDfd && outputDfd.state() === "pending" && (outputDfd.reject ? outputDfd.reject(error) : outputDfd.abort(error));
            });

            if (this.model.isFailed() || this.model.isCancelled()) {
                dfd.reject({
                    source: "execution",
                    status: this.model.get("status"),
                    errorDescriptor: this.model.get("errorDescriptor")
                });
            } else {
                var self = this,
                    htmlExport = this.model.getExport(reportOutputFormats.HTML);

                exportDfd = htmlExport.run();
                exportDfd.then(function () {
                        if (htmlExport.isFailed() || htmlExport.isCancelled()) {
                            dfd.reject({
                                source: "export",
                                format: reportOutputFormats.HTML,
                                status: htmlExport.get("status"),
                                errorDescriptor: htmlExport.get("errorDescriptor")
                            });
                        } else {
                            outputDfd = htmlExport.fetchOutput();
                            outputDfd
                                .then(_.bind(self.components.fetch, self.components), dfd.reject)
                                .then(function () {
                                    dfd.resolve();
                                    self.trigger(reportEvents.REQUESTED_PAGES_READY, self);
                                }, function(error){
                                    dfd.state() === "pending" && dfd.reject(error);
                                });
                        }
                    }, function(error){
                        dfd.state() === "pending" && dfd.reject(error);
                    });
            }

            return dfd;
        },

        renderReport: function() {
            return this.view.render().done(_.bind(function() { this._reportRendered = true; }, this));
        },

        exportReport: function(options) {
            var dfd = new $.Deferred();
            if (this.model.isFailed() || this.model.isCancelled()) {
                var err = biComponentErrorFactoryReportProxy.reportStatus({
                    source: "execution",
                    status: this.model.get("status"),
                    errorDescriptor: this.model.get("errorDescriptor")
                });

                dfd.reject(err);
            } else {
                // DO NOT REMOVE NEXT LINE: 'pages' must present in any case to override report properties
                options.pages = options.pages;

                var exportModel = new ExportModel({options: options}, {report: this.model}), wait = _.bind(exportModel.waitForExport, exportModel);

                exportModel.run().then(wait, dfd.reject).then(function() {
                    if (exportModel.isFailed() || exportModel.isCancelled()) {
                        var err = biComponentErrorFactoryReportProxy.reportStatus({
                            source: "export",
                            format: options.outputFormat,
                            status: exportModel.get("status"),
                            errorDescriptor: exportModel.get("errorDescriptor")
                        });

                        dfd.reject(err);
                    } else {
                        //TODO: extend link with export mime-type info, resource name
                        dfd.resolve({href: exportModel.urlOutput()}, function(options){
                            options = _.defaults(options || {}, {
                                url: exportModel.urlOutput(),
                                type: "GET",
                                headers: {
                                    "Accept": "text/plain, application/json",
                                    "x-jrs-base-url" : exportModel.report.contextPath
                                },
                                dataType: "text",
                                data: {
                                    suppressContentDisposition: true
                                }
                            });
                            return request(options);
                        });
                    }
                });
            }
            return dfd;
        },

        destroy: function() {
            return this.cancelReportExecution().done(_.bind(this.view.remove, this.view));
        }
    });

    return ReportController;
});