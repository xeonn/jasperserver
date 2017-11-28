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

package com.jaspersoft.jasperserver.export;

import com.jaspersoft.jasperserver.api.common.domain.ExecutionContext;
import com.jaspersoft.jasperserver.api.common.domain.impl.ExecutionContextImpl;
import com.jaspersoft.jasperserver.api.metadata.user.service.ObjectPermissionService;
import com.jaspersoft.jasperserver.export.io.ExportImportIOFactory;
import com.jaspersoft.jasperserver.export.io.ImportInput;
import com.jaspersoft.jasperserver.export.util.CommandOut;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: ImportCommandImpl.java 51947 2014-12-11 14:38:38Z ogavavka $
 */
public class ImportCommandImpl implements CommandBean, ApplicationContextAware {
	
	private static final CommandOut commandOut = CommandOut.getInstance();

	private ApplicationContext ctx;
	
	private ExportImportIOFactory exportImportIOFactory;
	private String importerPrototypeBeanName;

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}
	
	public void process(Parameters parameters) {
		ImportTask task = createTask(parameters);
		Importer importer = createPrototypeImporter(parameters);
		importer.setTask(task);
		importer.performImport();
	}

	protected ImportTask createTask(Parameters parameters) {
		ImportTaskImpl task = new ImportTaskImpl();
		task.setParameters(parameters);
		task.setExecutionContext(getExecutionContext(parameters));
		task.setInput(getImportInput(parameters));
        task.setApplicationContext(this.ctx);
		return task;
	}

	protected ImportInput getImportInput(Parameters parameters) {
		return getExportImportIOFactory().createInput(parameters);
	}

	protected ExecutionContext getExecutionContext(Parameters parameters) {
		ExecutionContextImpl context = new ExecutionContextImpl();
		context.setLocale(getLocale(parameters));
        if(ObjectPermissionService.PRIVILEGED_OPERATION.equals(parameters.getParameterValue(ObjectPermissionService.PRIVILEGED_OPERATION))){
            context.getAttributes().add(ObjectPermissionService.PRIVILEGED_OPERATION);
        }
		return context;
	}

	protected Locale getLocale(Parameters parameters) {
		return LocaleContextHolder.getLocale();
	}

	protected Importer createPrototypeImporter(Parameters parameters) {
		String importerBeanName = getImporterPrototypeBeanName(parameters);
		
		commandOut.debug("Using " + importerBeanName + " importer prototype bean.");
		
		return (Importer) ctx.getBean(importerBeanName, Importer.class);
	}

	protected String getImporterPrototypeBeanName(Parameters parameters) {
		return getImporterPrototypeBeanName();
	}

	public String getImporterPrototypeBeanName() {
		return importerPrototypeBeanName;
	}

	public void setImporterPrototypeBeanName(String importerPrototypeBeanName) {
		this.importerPrototypeBeanName = importerPrototypeBeanName;
	}

	public ExportImportIOFactory getExportImportIOFactory() {
		return exportImportIOFactory;
	}

	public void setExportImportIOFactory(ExportImportIOFactory exportImportIOFactory) {
		this.exportImportIOFactory = exportImportIOFactory;
	}

}
