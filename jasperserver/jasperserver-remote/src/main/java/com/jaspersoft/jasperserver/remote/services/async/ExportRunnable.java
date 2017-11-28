/*
* Copyright (C) 2005 - 2012 Jaspersoft Corporation. All rights reserved.
* http://www.jaspersoft.com.
* Licensed under commercial Jaspersoft Subscription License Agreement.
*/

package com.jaspersoft.jasperserver.remote.services.async;

import com.jaspersoft.jasperserver.remote.exception.NoResultException;
import com.jaspersoft.jasperserver.remote.exception.NotReadyResultException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/*
*  @author inesterenko
*/
public class ExportRunnable extends BaseImportExportTaskRunnable<InputStream> {

    protected final static Log log = LogFactory.getLog(ExportRunnable.class);

    private List<String> urisOfResources;
    private List<String> urisOfScheduledJobs;
    private List<String> rolesToExport;
    private List<String> usersToExport;
    private FileOutputStream fileOutputStream;

    public ExportRunnable(Map<String, Boolean> exportParams, List<String> urisOfResources, List<String> urisOfScheduledJobs, List<String> rolesToExport, List<String> usersToExport){
        this(exportParams, urisOfResources, urisOfScheduledJobs, rolesToExport, usersToExport, LocaleContextHolder.getLocale());
    }

    public ExportRunnable(Map<String, Boolean> exportParams, List<String> urisOfResources, List<String> urisOfScheduledJobs, List<String> rolesToExport, List<String> usersToExport, Locale locale){
        this.parameters = exportParams;
        this.urisOfResources = urisOfResources;
        this.urisOfScheduledJobs = urisOfScheduledJobs;
        this.rolesToExport = rolesToExport;
        this.usersToExport = usersToExport;
        this.state = new StateDto();
        this.locale = locale;
    }

    @Override
    public void run(){
        if (state.getPhase() != null && state.getPhase().equals(Task.FAILED)) return;
        try {
            if (file == null){
                file = File.createTempFile("export_", null);
                fileOutputStream = new FileOutputStream(file);
            }
            state.setMessage(localize("export.in.progress"));
            service.doExport(fileOutputStream, parameters, urisOfResources, urisOfScheduledJobs, rolesToExport, usersToExport, this.locale);
            fileOutputStream.close();
            state.setPhase(Task.FINISHED);
            state.setMessage(localize("export.finished"));
        } catch (Exception e) {
            log.error("Export failed: ", e);

            state.setPhase(Task.FAILED);
            state.setMessage(localize("export.failed"));

            if (file != null){
                try{
                    fileOutputStream.close();
                }
                catch (IOException ioe){
                    log.error("Can't close output stream "+file.getAbsolutePath());
                }

                if (!file.delete()){
                    log.error("Can't delete temp file "+file.getAbsolutePath());
                }
            }
        }
    }

    @Override
    public InputStream getResult() throws NotReadyResultException, NoResultException {
        FileInputStream inputStream = null;
        if (getState().getPhase().equals(Task.FINISHED)) {
            try {
                inputStream = new FileInputStream(file);
                return inputStream;
            } catch (IOException io) {
                String message = "Can't read temp file" + file.getAbsolutePath();
                log.error(message);
                throw new NoResultException("Can't get export result");
            }
        } else {
            throw new NotReadyResultException("Export hasn't completed yet");
        }
    }

    @Override
    public void prepare() {
        state.setMessage(localize("export.in.progress"));
    }
}
