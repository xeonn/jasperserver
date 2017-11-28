package com.jaspersoft.jasperserver.jaxrs.common;

import com.jaspersoft.jasperserver.remote.exception.SqlErrorDescriptorBuilder;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.springframework.stereotype.Component;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id: SqlExceptionMapper.java 64791 2016-10-12 15:08:37Z ykovalch $
 * @see
 */
@Provider
@Component
public class SqlExceptionMapper implements ExceptionMapper<SQLException> {
    @Resource
    private SqlErrorDescriptorBuilder errorDescriptorBuilder;

    @Override
    public Response toResponse(SQLException exception) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity(errorDescriptorBuilder.build(exception))
                .build();
    }
}
