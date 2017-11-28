/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl;

import org.apache.log4j.MDC;

import java.util.Hashtable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Yakiv Tymoshenko
 * @version $Id: Id $
 * @since 13.11.14
 */
class Log4jMdcCompatibleThreadPoolExecutor implements Executor {
    private ExecutorService threadPoolExecutor;

    public Log4jMdcCompatibleThreadPoolExecutor() {
        threadPoolExecutor = Executors.newCachedThreadPool();
    }

    @SuppressWarnings("unused")
    public Log4jMdcCompatibleThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    // Type safety maintained by LOG4J's MDC
    @SuppressWarnings("unchecked")
    public void execute(final Runnable command) {
        final Hashtable<String, String> mdcContextCopy = new Hashtable<String, String>(MDC.getContext());
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                MDC.getContext().putAll(mdcContextCopy);
                command.run();
            }
        });
    }
}
