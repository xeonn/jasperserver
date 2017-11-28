/*
 * Copyright (C) 2005 - 2012 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com.
 * Licensed under commercial Jaspersoft Subscription License Agreement
 */

package com.jaspersoft.jasperserver.remote.services.async;

import com.jaspersoft.jasperserver.export.service.ImportExportService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.File;
import java.util.Locale;
import java.util.Map;

/**
 * @author  inesterenko
 */

abstract public class BaseImportExportTaskRunnable<T> implements TaskRunnable {

    protected Map<String, Boolean> parameters;
    protected File file;
    protected StateDto state;

    private MessageSource messageSource;
    protected ImportExportService service;
    protected Locale locale;

    public File getFile(){
            return file;
        }

    public StateDto getState(){
        return state;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public ImportExportService getService() {
        return service;
    }

    public void setService(ImportExportService service) {
        this.service = service;
    }

    protected String localize(String key){
        return messageSource.getMessage(key, null, key, locale);
    }

    abstract public void prepare();
}
