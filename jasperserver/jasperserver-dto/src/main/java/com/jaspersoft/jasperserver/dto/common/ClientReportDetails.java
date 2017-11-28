package com.jaspersoft.jasperserver.dto.common;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author: Zakhar.Tomchenco
 */

@XmlRootElement(name = "report")
public class ClientReportDetails {
    private String dataSourceUri;
    private String label;
    private String location;
    private String template;

    public String getDataSourceUri() {
        return dataSourceUri;
    }

    public void setDataSourceUri(String dataSourceUri) {
        this.dataSourceUri = dataSourceUri;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
