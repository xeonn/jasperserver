package com.jaspersoft.jasperserver.jaxrs.common;

import com.jaspersoft.jasperserver.remote.exception.UnsupportedOperationRemoteException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author: Zakhar.Tomchenco
 */
@Provider
@Component
public class UnsupportedOperationExceptionMapper implements ExceptionMapper<UnsupportedOperationRemoteException> {
    public Response toResponse(UnsupportedOperationRemoteException exception) {
        return Response.status(501).entity(exception.getErrorDescriptor()).build();
    }
}
