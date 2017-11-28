package com.jaspersoft.jasperserver.jaxrs.report;

import com.jaspersoft.jasperserver.remote.exception.xml.ErrorDescriptor;
import com.jaspersoft.jasperserver.remote.services.ExecutionStatus;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: ReportExecutionStatusEntity.java 26599 2012-12-10 13:04:23Z ykovalchyk $
 */
@XmlRootElement(name = "status")
public class ReportExecutionStatusObject {

    private ExecutionStatus value;
    private ErrorDescriptor errorDescriptor;

    public ReportExecutionStatusObject(){}
    public ReportExecutionStatusObject(ReportExecutionStatusObject source){
        value = source.getValue();
        errorDescriptor = source.getErrorDescriptor();
    }

    public ExecutionStatus getValue() {
        return value;
    }

    public ReportExecutionStatusObject setValue(ExecutionStatus value) {
        this.value = value;
        return this;
    }

    public ErrorDescriptor getErrorDescriptor() {
        return errorDescriptor;
    }

    public ReportExecutionStatusObject setErrorDescriptor(ErrorDescriptor errorDescriptor) {
        this.errorDescriptor = errorDescriptor;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportExecutionStatusObject that = (ReportExecutionStatusObject) o;

        if (errorDescriptor != null ? !errorDescriptor.equals(that.errorDescriptor) : that.errorDescriptor != null)
            return false;
        if (value != that.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (errorDescriptor != null ? errorDescriptor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReportExecutionStatusObject{" +
                "value=" + value +
                ", errorDescriptor=" + errorDescriptor +
                '}';
    }
}
