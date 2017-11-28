package com.jaspersoft.jasperserver.remote.exception;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.dto.common.ErrorDescriptorBuilder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id: SqlErrorDescriptorBuilder.java 64791 2016-10-12 15:08:37Z ykovalch $
 * @see
 */
@Component
public class SqlErrorDescriptorBuilder implements ErrorDescriptorBuilder<SQLException> {
    @Override
    public ErrorDescriptor build(SQLException e) {

        ErrorDescriptor errorDescriptor = new ErrorDescriptor();
        errorDescriptor.setErrorCode("sql.exception");
        StringBuilder stringBuilder = new StringBuilder("SQL error.");
        List<String> parameters = new ArrayList<String>(3);
        String message = e.getMessage();
        if (message != null && !message.isEmpty()) {
            stringBuilder.append(" Reason: " + message + ".");
            parameters.add(message);
        }
        String sqlState = e.getSQLState();
        if (sqlState != null && !sqlState.isEmpty()) {
            stringBuilder.append(" SQL State: " + sqlState + ".");
            parameters.add(sqlState);
        }
        int errorCode = e.getErrorCode();
        if (errorCode != 0) {
            stringBuilder.append(" Vendor code: " + errorCode + ".");
            parameters.add(String.valueOf(errorCode));
        }
        errorDescriptor.setMessage(stringBuilder.toString());
        errorDescriptor.setParameters(parameters);

        return errorDescriptor;
    }
}
