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
package com.jaspersoft.jasperserver.war.action;

import com.jaspersoft.jasperserver.api.JSDuplicateResourceException;
import com.jaspersoft.jasperserver.api.JSException;
import com.jaspersoft.jasperserver.api.common.domain.impl.ExecutionContextImpl;
import com.jaspersoft.jasperserver.api.metadata.common.domain.DataType;
import com.jaspersoft.jasperserver.api.metadata.common.domain.ResourceLookup;
import com.jaspersoft.jasperserver.api.metadata.common.service.RepositoryService;
import com.jaspersoft.jasperserver.api.metadata.view.domain.FilterCriteria;
import com.jaspersoft.jasperserver.war.cascade.InputControlValidationException;
import com.jaspersoft.jasperserver.war.cascade.handlers.converters.DataConverterService;
import com.jaspersoft.jasperserver.war.common.ConfigurationBean;
import com.jaspersoft.jasperserver.war.common.JasperServerUtil;
import com.jaspersoft.jasperserver.war.dto.BaseDTO;
import com.jaspersoft.jasperserver.war.dto.DataTypeWrapper;
import com.jaspersoft.jasperserver.war.util.CalendarFormatProvider;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.DataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.ScopeType;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author Ionut Nedelcu (ionutned@users.sourceforge.net)
 * @version $Id: EditDataTypeAction.java 38983 2013-10-17 12:32:51Z ztomchenco $
 */
public class EditDataTypeAction extends FormAction {
    private static final String ATTRIBUTE_RESOURCE_ID_NOT_SUPPORTED_SYMBOLS = "resourceIdNotSupportedSymbols";

	private static final String FORM_OBJECT_KEY = "dataType";
	private static final String PARENT_FOLDER_ATTR = "parentFolder";
	private static final String CURRENT_DATATYPE_ATTR = "currentDataType";
	private static final String IS_EDIT = "isEdit";//FIXME use wrapper to disable name in UI

	private RepositoryService repository;
	private CalendarFormatProvider calendarFormatProvider;
    private DataConverterService dataConverterService;

    protected MessageSource messages;
    private ConfigurationBean configuration;

	public RepositoryService getRepository() {
		return repository;
	}

    public void setDataConverterService(DataConverterService dataConverterService) {
        this.dataConverterService = dataConverterService;
    }

    public void setRepository(RepositoryService repository) {
		this.repository = repository;
	}

    public void setConfiguration(ConfigurationBean configuration) {
        this.configuration = configuration;
    }

    protected void initBinder(RequestContext context, DataBinder binder) {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}


	/**
	 *
	 */
	public EditDataTypeAction(){
		setFormObjectClass(DataTypeWrapper.class); //custom form backing object class
		setFormObjectName(FORM_OBJECT_KEY);
		setFormObjectScope(ScopeType.FLOW); 		//this is a multi-page wizard!
	}

    public void setMessages(MessageSource messages) {
        this.messages = messages;
    }


	/**
	 *
	 */
	public Object createFormObject(RequestContext context)
	{
		DataType dataType;
		DataTypeWrapper wrapper;
		ExecutionContextImpl executionContext = new ExecutionContextImpl();

		String isEdit = (String)context.getFlowScope().get(IS_EDIT);
		if (isEdit == null) {
			isEdit = (String)context.getRequestParameters().get("isEdit");
			context.getFlowScope().put(IS_EDIT, isEdit);
		}
		
		if (isEdit != null)
		{
			String currentDataType = (String) context.getFlowScope().get(CURRENT_DATATYPE_ATTR);
			if (currentDataType == null) {
				currentDataType = (String)context.getRequestParameters().get("resource");
				context.getFlowScope().put(CURRENT_DATATYPE_ATTR, currentDataType);
			}
			dataType = (DataType) repository.getResource(executionContext, currentDataType);
			if(dataType == null){
				context.getFlowScope().remove("prevForm");
				throw new JSException("jsexception.could.not.find.resource.with.uri", new Object[] {currentDataType});
			}
			wrapper = new DataTypeWrapper(dataType);
			wrapper.setMode(BaseDTO.MODE_STAND_ALONE_EDIT);
			byte type = dataType.getType();
			if (JasperServerUtil.isDateType(type)) {
				DateFormat df = getFormat(type);
				if(dataType.getMinValue() != null){
					wrapper.setMinValueText(df.format((Date)dataType.getMinValue()));
				}
				if(dataType.getMaxValue() != null){
					wrapper.setMaxValueText(df.format((Date)dataType.getMaxValue()));
				}
			}
		}
		else
		{
			dataType = (DataType) repository.newResource(executionContext, DataType.class);
			String parentFolder = (String) context.getFlowScope().get(PARENT_FOLDER_ATTR);
			if (parentFolder == null) {
				parentFolder = (String)context.getRequestParameters().get("ParentFolderUri");
				context.getFlowScope().put(PARENT_FOLDER_ATTR, parentFolder);
			}
			if (parentFolder == null || parentFolder.trim().length() == 0)
				parentFolder = "/";
			dataType.setParentFolder(parentFolder);
			wrapper = new DataTypeWrapper(dataType);
			wrapper.setMode(BaseDTO.MODE_STAND_ALONE_NEW);

			FilterCriteria criteria = FilterCriteria.createFilter();
			criteria.addFilterElement(FilterCriteria.createParentFolderFilter(parentFolder));
			ResourceLookup[] allDataTypes = repository.findResource(executionContext, criteria);
			wrapper.setAllDataTypes(allDataTypes);
		}

		return wrapper;
	}


	/**
	 *
	 */
	public Event saveDataType(RequestContext context) throws Exception
	{
		DataTypeWrapper wrapper = (DataTypeWrapper) getFormObject(context);

        DataType dataType = wrapper.getDataType();
        dataType.setMaxValue(extractMaxValue(wrapper));
        dataType.setMinValue(extractMinValue(wrapper));

        if (wrapper.isStandAloneMode())
			try {
				repository.saveResource(null, wrapper.getDataType());
                if (!wrapper.isEditMode()) {
                    context.getExternalContext().getSessionMap().put("repositorySystemConfirm",
                            messages.getMessage("resource.dataType.dataTypeAdded",
                                    new String[] {wrapper.getDataType().getName(),
                                    wrapper.getDataType().getParentFolder()},
                                    LocaleContextHolder.getLocale()));
                }
				return yes();
			}
			catch (JSDuplicateResourceException e) {
				getFormErrors(context).rejectValue("dataType.name", "DataTypeValidator.error.duplicate");
				return error();
			}

		return success();
	}

	/**
	 *
	 */
	public Event setupEditForm(RequestContext context) throws Exception {
		MutableAttributeMap rs = context.getRequestScope();
		rs.put(FORM_OBJECT_KEY, getFormObject(context));

        context.getFlowScope().put(ATTRIBUTE_RESOURCE_ID_NOT_SUPPORTED_SYMBOLS,
                configuration.getResourceIdNotSupportedSymbols());

		return success();
	}

	public CalendarFormatProvider getCalendarFormatProvider()
	{
		return calendarFormatProvider;
	}

	public void setCalendarFormatProvider(CalendarFormatProvider calendarFormatProvider)
	{
		this.calendarFormatProvider = calendarFormatProvider;
	}
	
	private DateFormat getFormat(byte type) {
        switch (type) {
            case DataType.TYPE_DATE:
                return getCalendarFormatProvider().getDateFormat();
            case DataType.TYPE_DATE_TIME:
                return getCalendarFormatProvider().getDatetimeFormat();
            case DataType.TYPE_TIME:
                return getCalendarFormatProvider().getTimeFormat();
            default:
                return getCalendarFormatProvider().getDateFormat();
        }
    }

    private Comparable extractMinValue(DataTypeWrapper wrapper) throws InputControlValidationException {
        Comparable minValue = null;
        DataType dataType = wrapper.getDataType();
        if (dataType.getType() == DataType.TYPE_NUMBER) {
            if (dataType.getMinValue() != null) {
                minValue = (Comparable) dataConverterService.convertSingleValue(dataType.getMinValue().toString(), dataType);
            }
        } else {
            if (wrapper.getMinValueText() != null && !"".equals(wrapper.getMinValueText())) {
                minValue = (Comparable) dataConverterService.convertSingleValue(wrapper.getMinValueText(), dataType);
            }
        }
        return minValue;
    }

    private Comparable extractMaxValue(DataTypeWrapper wrapper) throws InputControlValidationException {
        Comparable maxValue = null;
        DataType dataType = wrapper.getDataType();
        if (dataType.getType() == DataType.TYPE_NUMBER) {
            if (dataType.getMaxValue() != null) {
                maxValue = (Comparable) dataConverterService.convertSingleValue(dataType.getMaxValue().toString(), dataType);
            }
        } else {
            if (wrapper.getMaxValueText() != null && !"".equals(wrapper.getMaxValueText())) {
                maxValue = (Comparable) dataConverterService.convertSingleValue(wrapper.getMaxValueText(), dataType);
            }
        }
        return maxValue;
    }
}

