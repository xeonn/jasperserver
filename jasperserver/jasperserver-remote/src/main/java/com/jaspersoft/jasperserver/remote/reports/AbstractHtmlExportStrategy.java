/*
* Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
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
* along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
*/
package com.jaspersoft.jasperserver.remote.reports;

import com.jaspersoft.jasperserver.api.engine.jasperreports.util.HtmlExportUtil;
import com.jaspersoft.jasperserver.remote.exception.RemoteException;
import com.jaspersoft.jasperserver.remote.exception.xml.ErrorDescriptor;
import com.jaspersoft.jasperserver.remote.services.ExportExecution;
import com.jaspersoft.jasperserver.remote.services.ExportExecutionOptions;
import com.jaspersoft.jasperserver.remote.services.ReportExecution;
import com.jaspersoft.jasperserver.remote.services.ReportExecutionOptions;
import com.jaspersoft.jasperserver.remote.services.ReportOutputPages;
import com.jaspersoft.jasperserver.remote.services.ReportOutputResource;
import com.jaspersoft.jasperserver.remote.services.RunReportService;
import com.jaspersoft.jasperserver.remote.utils.AuditHelper;
import com.jaspersoft.jasperserver.war.util.JRHtmlExportUtils;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.SimpleJasperReportsContext;
import net.sf.jasperreports.engine.export.AbstractHtmlExporter;
import net.sf.jasperreports.engine.export.HtmlResourceHandler;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.MapHtmlResourceHandler;
import net.sf.jasperreports.engine.util.JRTypeSniffer;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id: AbstractHtmlExportStrategy.java 51369 2014-11-12 13:59:41Z sergey.prilukin $
 */
public abstract class AbstractHtmlExportStrategy implements HtmlExportStrategy {
    private final static Log log = LogFactory.getLog(FullHtmlExportStrategy.class);
    @Resource(name = "jasperReportsRemoteContext")
    private JasperReportsContext jasperReportsContext;
    @Resource
    private AuditHelper auditHelper;
    @Value("${deploy.base.url:}")
    private String deployBaseUrl;

    @Override
    public void export(ReportExecution reportExecution, ExportExecution exportExecution, JasperPrint jasperPrint) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final String contextPath;
        String baseUrl = exportExecution.getOptions().getBaseUrl();
        final ReportExecutionOptions reportExecutionOptions = reportExecution.getOptions();
        if (baseUrl != null) {
            // if baseUrl is specified fro this export execution, then use it first
            contextPath = baseUrl;
        } else if (deployBaseUrl != null && !deployBaseUrl.isEmpty()) {
            // no baseUrl is specified fro this export execution, but it is specified for JRS
            contextPath = deployBaseUrl;
        } else {
            // no baseUrl is specified, use contextPath from request or no prefix at all
            contextPath = (reportExecutionOptions.getContextPath() != null ? reportExecutionOptions.getContextPath() : "");
        }
        final AbstractHtmlExporter exporter = prepareExporter(reportExecution, exportExecution, contextPath);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        // collecting the images into a map
        Map<String, byte[]> imagesMap = new LinkedHashMap<String, byte[]>();
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);

        String attachmentsPrefix = exportExecution.getOptions().getAttachmentsPrefix() != null ?
                exportExecution.getOptions().getAttachmentsPrefix() : reportExecutionOptions.getDefaultAttachmentsPrefixTemplate();
        if (attachmentsPrefix != null) {
            attachmentsPrefix = attachmentsPrefix
                    .replace(RunReportService.CONTEXT_PATH_ATTACHMENTS_PREFIX_TEMPLATE_PLACEHOLDER, reportExecutionOptions.getContextPath() != null ? reportExecutionOptions.getContextPath() : "")
                    .replace(RunReportService.REPORT_EXECUTION_ID_ATTACHMENTS_PREFIX_TEMPLATE_PLACEHOLDER, reportExecution.getRequestId())
                    .replace(RunReportService.EXPORT_EXECUTION_ID_ATTACHMENTS_PREFIX_TEMPLATE_PLACEHOLDER, exportExecution.getId());
        }

        String resourcePattern = ((attachmentsPrefix != null) ? attachmentsPrefix : "images/") + "{0}";
        HtmlResourceHandler resourceHandler =
                new MapHtmlResourceHandler(
                        new WebHtmlResourceHandler(resourcePattern),
                        imagesMap
                );
        exporter.setImageHandler(resourceHandler);
        exporter.setResourceHandler(resourceHandler);
        try {
            exporter.exportReport();
        } catch (RemoteException e) {
            auditHelper.addExceptionToAllAuditEvents(e);
            throw e;
        } catch (JRRuntimeException e) {
            if ("net.sf.jasperreports.engine.JRAbstractExporter.start.page.index.out.of.range".equals(e.getMessageKey())
                    || e.getMessage().contains("index out of range : ") || e.getMessage().contains("out.of.range")) {
                throw new RemoteException(new ErrorDescriptor.Builder().setMessage(
                        "Page out of range. Requested: "
                                + exportExecution.getOptions().getPages().toString()
                                + (reportExecution.getTotalPages() != null ? " Total pages: " + reportExecution.getTotalPages() : ""))
                        .setErrorCode("export.pages.out.of.range").getErrorDescriptor());
            } else {
                throw new RemoteException(e);
            }
        } catch (Exception e) {
            log.debug("Error exporting report", e);
            auditHelper.addExceptionToAllAuditEvents(e);
            throw new RemoteException(
                    new ErrorDescriptor.Builder()
                            .setErrorCode("webservices.error.errorExportingReportUnit").setParameters(e.getMessage())
                            .getErrorDescriptor(), e
            );
        } finally {
            try {
                outputStream.close();
            } catch (IOException ex) {
                log.error("caught exception: " + ex.getMessage(), ex);
            }
        }
        exportExecution.setOutputResource(new ReportOutputResource("text/html", outputStream.toByteArray()));
        putImages(exporter.getParameters(), exportExecution.getAttachments());
    }

    protected AbstractHtmlExporter prepareExporter(ReportExecution reportExecution, ExportExecution exportExecution, final String contextPath) {
        // use new instance of jasper reports context to allow modifications
        SimpleJasperReportsContext context = new SimpleJasperReportsContext();
        context.setParent(jasperReportsContext);
        final AbstractHtmlExporter exporter = HtmlExportUtil.getHtmlExporter(context);
        final ExportExecutionOptions exportExecutionOptions = exportExecution.getOptions();
        ReportOutputPages pages = exportExecutionOptions.getPages();
        if(pages != null) {
            if (pages.getPage() != null) {
                exporter.setParameter(JRExporterParameter.PAGE_INDEX, pages.getPage() - 1);
            } else if (pages.getStartPage() != null && pages.getEndPage() != null) {
                exporter.setParameter(JRExporterParameter.START_PAGE_INDEX, pages.getStartPage() - 1);
                exporter.setParameter(JRExporterParameter.END_PAGE_INDEX, pages.getEndPage() - 1);
            }
        }
        // JR requires HttpServletRequest instance to get contextPath from it.
        // Seems it's the only field queried from the request object.
        // We need to do the trick with proxy to send contextPath to JR without having real request object.
        // We can't just inject HttpServletRequest, because in case of asynchronous export this class is invoked
        // from a different thread without valid request bound to it.
        HttpServletRequest proxy = (HttpServletRequest) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class<?>[]{HttpServletRequest.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object result = null;
                        if ("getContextPath".equals(method.getName())) {
                            result = contextPath;
                        }
                        return result;
                    }
                });
        exporter.setParameter(JRHtmlExportUtils.PARAMETER_HTTP_REQUEST, proxy);
        exporter.setFontHandler(new WebHtmlResourceHandler(contextPath + "/reportresource?&font={0}"));
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.TRUE);
        return exporter;
    }


    /**
     * Place images to output container.
     *
     * @param exportParameters - export result, contains images
     * @param outputContainer  - output container to fill with images
     * @throws com.jaspersoft.jasperserver.remote.exception.RemoteException if any error occurs
     */
    protected void putImages(Map<JRExporterParameter, Object> exportParameters, Map<String, ReportOutputResource> outputContainer) throws RemoteException {
        try {
            // cast is safe because of known parameter key
            @SuppressWarnings("unchecked")
            Map<String, byte[]> imagesMap = (Map<String, byte[]>) exportParameters.get(JRHtmlExporterParameter.IMAGES_MAP);
            if (imagesMap != null && !imagesMap.isEmpty()) {
                if (log.isDebugEnabled()) {
                    log.debug("imagesMap : " + Arrays.asList(imagesMap.keySet().toArray()));
                }
                for (String name : imagesMap.keySet()) {
                    byte[] data = imagesMap.get(name);
                    if (log.isDebugEnabled()) {
                        log.debug("Adding image for HTML: " + name);
                    }
                    outputContainer.put(name, new ReportOutputResource(JRTypeSniffer.getImageTypeValue(data).getMimeType(), data, name));
                }
            }
        } catch (Throwable e) {
            log.error(e);
            throw new RemoteException(new ErrorDescriptor.Builder()
                    .setErrorCode("webservices.error.errorAddingImage").setParameters(e.getMessage()).getErrorDescriptor(), e);
        }
    }
}