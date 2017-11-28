package com.jaspersoft.jasperserver.jaxrs.common;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

import javax.ws.rs.ext.Provider;
import java.util.List;

/**
 * @author serhii.blazhyievskyi
 * @version $Id: JasperServerContainerResponseFilter.java 61296 2016-02-25 21:53:37Z mchan $
 */
@Provider
public class JasperServerContainerResponseFilter implements ContainerResponseFilter {

    @Override
    public ContainerResponse filter(ContainerRequest containerRequest, ContainerResponse containerResponse) {
        if (containerResponse != null) {
            String mediaType = "xml";
            if(containerRequest.getRequestHeader("accept") != null && containerRequest.getRequestHeader("accept").size() > 0) {
                String requestMediaType = containerRequest.getRequestHeader("accept").get(0);
                if(requestMediaType.indexOf("json") > -1) {
                    mediaType = "json";
                }
            }
            if (containerResponse.getEntity() instanceof com.jaspersoft.jasperserver.dto.common.ErrorDescriptor) {
                containerResponse.getHttpHeaders().add("Content-Type", "application/errorDescriptor+" + mediaType);
            } else if (containerResponse.getEntity() instanceof java.util.List
                    && ((List)containerResponse.getEntity()).get(0) instanceof com.jaspersoft.jasperserver.dto.common.ErrorDescriptor) {
                containerResponse.getHttpHeaders().add("Content-Type", "application/collection.errorDescriptor+" + mediaType);
            }
        }
        return containerResponse;
    }
}
