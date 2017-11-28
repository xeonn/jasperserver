package com.jaspersoft.jasperserver.jaxrs.common;

import com.jaspersoft.jasperserver.remote.exception.xml.ErrorDescriptor;
import org.springframework.security.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author: Zakhar.Tomchenco
 */
@Provider
@Component
public class SpringSecurityAccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {

    public Response toResponse(AccessDeniedException exception) {
        ErrorDescriptor descriptor = new ErrorDescriptor.Builder()
                .setErrorCode(com.jaspersoft.jasperserver.remote.exception.AccessDeniedException.ERROR_CODE_ACCESS_DENIED)
                .setMessage(exception.getLocalizedMessage()).getErrorDescriptor();

        return Response.status(Response.Status.FORBIDDEN).entity(descriptor).build();
    }
}
