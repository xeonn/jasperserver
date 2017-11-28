package com.jaspersoft.jasperserver.dto.query;

import com.jaspersoft.jasperserver.dto.common.TimeString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Paul Lysak
 *         Date: 11.02.13
 *         Time: 17:37
 */
@XmlSeeAlso({TimeString.class})
public class QueryResultRow {
    private List<Object> values = new ArrayList<Object>();

    public QueryResultRow() {

    }

    public QueryResultRow(Object... values) {
        this.values.addAll(Arrays.asList(values));
    }

    @XmlElement(name = "value")
    public List<Object> getValues() {
        return values;
    }

    public QueryResultRow setValues(List<Object> values) {
        this.values = values;
        return this;
    }

    @Override
    public String toString() {
        return "QueryResultRow{" +
                "values=" + values +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueryResultRow that = (QueryResultRow) o;

        if (values != null ? !values.equals(that.values) : that.values != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return values != null ? values.hashCode() : 0;
    }

}
