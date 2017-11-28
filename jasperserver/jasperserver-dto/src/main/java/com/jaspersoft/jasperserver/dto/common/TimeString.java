package com.jaspersoft.jasperserver.dto.common;

import javax.xml.XMLConstants;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Paul Lysak
 */
@XmlType(name = "time", namespace = XMLConstants.W3C_XML_SCHEMA_NS_URI)
public class TimeString {
    private String time;

    public TimeString() {
    }

    public TimeString(String time) {
        this.time = time;
    }

    @XmlValue
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeString that = (TimeString) o;

        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return time != null ? time.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TimeString{" +
                "time='" + time + '\'' +
                '}';
    }
}
