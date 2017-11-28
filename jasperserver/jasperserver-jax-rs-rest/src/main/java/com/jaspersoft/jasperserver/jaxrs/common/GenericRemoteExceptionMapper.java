package com.jaspersoft.jasperserver.jaxrs.common;

import com.jaspersoft.jasperserver.remote.exception.RemoteException;
import com.jaspersoft.jasperserver.remote.exception.builders.LocalizedErrorDescriptorBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author: Zakhar.Tomchenco
 */

@Provider
@Component
public class GenericRemoteExceptionMapper implements ExceptionMapper<RemoteException> {
    private static final Log log = LogFactory.getLog(GenericRemoteExceptionMapper.class);

    @Resource(name= "localizedErrorDescriptorBuilder")
    private LocalizedErrorDescriptorBuilder builder;

    public Response toResponse(RemoteException exception) {
        Response.Status status = Response.Status.BAD_REQUEST;

        if (exception.isUnexpected()){
            status = Response.Status.INTERNAL_SERVER_ERROR;
            log.error("Unexpected error occurs", exception);
        }

        return Response.status(status)
                       .entity(builder.localizeDescriptor(exception.getErrorDescriptor()))
                       .build();
    }
}
