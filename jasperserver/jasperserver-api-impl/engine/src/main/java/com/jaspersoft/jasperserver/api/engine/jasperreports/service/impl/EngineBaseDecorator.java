/*
 * Copyright (C) 2005 - 2011 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com.
 * Licensed under commercial Jaspersoft Subscription License Agreement
 */
package com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl;

import com.jaspersoft.jasperserver.api.common.domain.ExecutionContext;
import com.jaspersoft.jasperserver.api.common.domain.ValidationResult;
import com.jaspersoft.jasperserver.api.engine.common.domain.Request;
import com.jaspersoft.jasperserver.api.engine.common.domain.Result;
import com.jaspersoft.jasperserver.api.engine.common.service.EngineService;
import com.jaspersoft.jasperserver.api.engine.common.service.ReportExecutionStatusInformation;
import com.jaspersoft.jasperserver.api.engine.common.service.ReportExecutionStatusSearchCriteria;
import com.jaspersoft.jasperserver.api.engine.common.service.ReportInputControlsInformation;
import com.jaspersoft.jasperserver.api.engine.common.service.SchedulerReportExecutionStatusSearchCriteria;
import com.jaspersoft.jasperserver.api.metadata.common.domain.InputControlsContainer;
import com.jaspersoft.jasperserver.api.metadata.common.domain.Resource;
import com.jaspersoft.jasperserver.api.metadata.common.domain.ResourceLookup;
import com.jaspersoft.jasperserver.api.metadata.common.domain.ResourceReference;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.ReportDataSource;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.ReportUnit;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.service.ReportDataSourceService;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.commons.collections.OrderedMap;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: EngineBaseDecorator.java 23440 2012-06-21 13:11:21Z afomin $
 */
public class EngineBaseDecorator implements EngineService
{

	private EngineService decoratedEngine;

	public void clearCaches(Class resourceItf, String resourceURI)
	{
		decoratedEngine.clearCaches(resourceItf, resourceURI);
	}

	public ReportDataSourceService createDataSourceService(ReportDataSource dataSource)
	{
		return decoratedEngine.createDataSourceService(dataSource);
	}

	public Result execute(ExecutionContext context, Request request)
	{
		return decoratedEngine.execute(context, request);
	}

	public OrderedMap executeQuery(ExecutionContext context, ResourceReference queryReference, String keyColumn, String[] resultColumns, ResourceReference defaultDataSourceReference)
	{
		return decoratedEngine.executeQuery(context, queryReference, keyColumn, resultColumns, defaultDataSourceReference);
	}

	public OrderedMap executeQuery(ExecutionContext context,
			ResourceReference queryReference, String keyColumn,
			String[] resultColumns,
			ResourceReference defaultDataSourceReference, Map parameterValues)
	{
		return decoratedEngine.executeQuery(context, 
				queryReference, keyColumn, 
				resultColumns, 
				defaultDataSourceReference, parameterValues);
	}

    public OrderedMap executeQuery(
            ExecutionContext context, ResourceReference queryReference, String keyColumn, String[] resultColumns,
            ResourceReference defaultDataSourceReference, Map parameterValues, Map<String, Class<?>> parameterTypes, boolean formatValueColumns) {
        return decoratedEngine.executeQuery(context,
                queryReference, keyColumn,
                resultColumns,
                defaultDataSourceReference, parameterValues, parameterTypes, formatValueColumns);
    }

    public void exportToPdf(ExecutionContext context, String reportUnitURI, Map exportParameters)
	{
		decoratedEngine.exportToPdf(context, reportUnitURI, exportParameters);
	}

	public Set getDataSourceTypes(ExecutionContext context, String queryLanguage)
	{
		return decoratedEngine.getDataSourceTypes(context, queryLanguage);
	}

	public ResourceLookup[] getDataSources(ExecutionContext context, String queryLanguage)
	{
		return decoratedEngine.getDataSources(context, queryLanguage);
	}

	public JasperReport getMainJasperReport(ExecutionContext context, String reportUnitURI)
	{
		return decoratedEngine.getMainJasperReport(context, reportUnitURI);
	}

    public JasperReport getMainJasperReport(ExecutionContext context, InputControlsContainer container) {
        return decoratedEngine.getMainJasperReport(context, container);
    }

    public String getQueryLanguage(ExecutionContext context, ResourceReference jrxmlResource)
	{
		return decoratedEngine.getQueryLanguage(context, jrxmlResource);
	}

	public Resource[] getResources(ResourceReference jrxmlReference)
	{
		return decoratedEngine.getResources(jrxmlReference);
	}

	public void release()
	{
		decoratedEngine.release();
	}

	public ValidationResult validate(ExecutionContext context, ReportUnit reportUnit)
	{
		return decoratedEngine.validate(context, reportUnit);
	}

	public Map getReportInputControlDefaultValues(ExecutionContext context, String reportURI, Map initialParameters)
	{
		return decoratedEngine.getReportInputControlDefaultValues(context, reportURI, initialParameters);
	}
	
	public ReportInputControlsInformation getReportInputControlsInformation(ExecutionContext context, InputControlsContainer icContainer, Map initialParameters){
        return decoratedEngine.getReportInputControlsInformation(context, icContainer, initialParameters);
    }

	public EngineService getDecoratedEngine()
	{
		return decoratedEngine;
	}
	
	public void setDecoratedEngine(EngineService decoratedEngine)
	{
		this.decoratedEngine = decoratedEngine;
	}

	public ReportInputControlsInformation getReportInputControlsInformation(
			ExecutionContext context, String reportURI, Map initialParameters) {
		return decoratedEngine.getReportInputControlsInformation(context, reportURI, initialParameters);
    }

    public ExecutionContext getRuntimeExecutionContext() {
    	return null;
    }
	public boolean cancelExecution(String requestId) {
		return decoratedEngine.cancelExecution(requestId);
	}

    public List<ReportExecutionStatusInformation> getReportExecutionStatusList() {
        return decoratedEngine.getReportExecutionStatusList();
    }

    public List<ReportExecutionStatusInformation> getReportExecutionStatusList(ReportExecutionStatusSearchCriteria searchCriteria) {
        return decoratedEngine.getReportExecutionStatusList(searchCriteria);
    }

    public List<ReportExecutionStatusInformation> getSchedulerReportExecutionStatusList() {
        return decoratedEngine.getSchedulerReportExecutionStatusList();
    }

    public List<ReportExecutionStatusInformation> getSchedulerReportExecutionStatusList(SchedulerReportExecutionStatusSearchCriteria searchCriteria) {
        return decoratedEngine.getSchedulerReportExecutionStatusList(searchCriteria);
    }
}
