/*
 * Copyright (C) 2005 - 2011 Jaspersoft Corporation. All rights reserved.
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
package com.jaspersoft.jasperserver.api.common.properties;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Volodya Sabadosh
 * @version $Id:$
 */
public class Log4jSettingsService extends ReloadableResourceBundleMessageSource{
    private List<String> loggerDescriptionFiles;
    private MessageSource messageSource;

    private Map<Locale, Map<String, String>> loggerDescriptionsByLocale =
            new ConcurrentHashMap<Locale, Map<String, String>>();
    private Map<String, String> loggers = new ConcurrentHashMap<String, String>();

    @PostConstruct
    public void init() {
        initLoggersFromPropFiles();
    }

    public Map<String, String> getLoggers() {
        return loggers;
    }

    public Map<String, String> getLoggerDescriptionsByLocale(Locale locale) {
        if (!loggerDescriptionsByLocale.containsKey(locale)) {
            Map<String, String> loggerDescriptions = new LinkedHashMap<String, String>();

            for (String key : loggers.keySet()) {
                loggerDescriptions.put(key, messageSource.getMessage(key, null, "", locale));
            }

            loggerDescriptionsByLocale.put(locale, loggerDescriptions);
        }
        return loggerDescriptionsByLocale.get(locale);
    }

    public void updateLogger(String key, String value) {
        loggers.put(key, value);
    }

    public void setLoggerDescriptionFiles(List<String> loggerDescriptionFiles) {
        this.loggerDescriptionFiles = loggerDescriptionFiles;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Loads the loggers list with the keys (leaves levels blank)
     */
    private void initLoggersFromPropFiles() {
        if (loggers.isEmpty()) {
            if (loggerDescriptionFiles == null || loggerDescriptionFiles.isEmpty()) {
                throw new IllegalStateException("No logger description files specified.");
            }

            //just putting in the logger keys here
            for (String file : loggerDescriptionFiles) {
                    PropertiesHolder propHolder = getProperties(file);
                    if (propHolder.getProperties() != null) {
                        for (Object key : propHolder.getProperties().keySet()) {
                            Logger log = Logger.getLogger((String)key);
                            String level = log.getEffectiveLevel().toString();
                            loggers.put((String)key, level);
                    }
                }
            }
        }
    }

}
