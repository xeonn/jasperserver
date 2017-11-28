package com.jaspersoft.jasperserver.api.engine.jasperreports.util;

import com.jaspersoft.jasperserver.api.common.domain.ExecutionContext;
import com.jaspersoft.jasperserver.api.common.domain.impl.ExecutionContextImpl;
import com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl.ReportLoadingService;
import com.jaspersoft.jasperserver.api.metadata.common.domain.InputControlsContainer;
import com.jaspersoft.jasperserver.api.metadata.common.domain.Resource;
import com.jaspersoft.jasperserver.api.metadata.common.domain.ResourceReference;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.ReportDataSource;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.ReportUnit;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @deprecated use ReportLoadingService.getRuntimeInputControls
 * @author Paul Lysak (paul.lysak@globallogic.com)
 */
@Deprecated
public class DataSourceInputControlsRetriever implements AfterReturningAdvice {
    private ReportLoadingService reportLoader;

    public void setReportLoader(ReportLoadingService reportLoader) {
        this.reportLoader = reportLoader;
    }

    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        if(!(returnValue instanceof ReportUnit)) {
            return;
        }
        ExecutionContext context;
        if( args.length >= 1 || args[0] instanceof ExecutionContext)  {
            context = (ExecutionContext)args[0];
        } else {
            context = ExecutionContextImpl.getRuntimeExecutionContext();
        }
        ReportUnit report = (ReportUnit) returnValue;
        if(report.getDataSource() == null) {
            return;
        }
        Resource dsResource = reportLoader.getFinalResource(context, report.getDataSource(), null);
        if( !(dsResource instanceof InputControlsContainer)) {
            return;
        }
        List<ResourceReference> dsInputControls = ((InputControlsContainer) dsResource).getInputControls();
        report.getInputControls().clear();
        //TODO decide if we need to merge with existing input controls
        //There is an issue in Spring which makes this advice run twice on each method call: http://forum.springsource.org/showthread.php?50377-ProxyFactoryBean-duplicates-interceptors-on-circular-dependency
        report.getInputControls().addAll(dsInputControls);
    }
}
