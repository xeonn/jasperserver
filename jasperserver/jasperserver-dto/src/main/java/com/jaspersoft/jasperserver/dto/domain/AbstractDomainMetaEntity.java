package com.jaspersoft.jasperserver.dto.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Paul Lysak
 */
abstract public class AbstractDomainMetaEntity {

    private String id;

    private String label;

    private Map<String, String> properties;

    public String getId() {
        return id;
    }

    public AbstractDomainMetaEntity setId(String id) {
        this.id = id;
        return this;
    }

    @XmlElement(name = "label", required = false)
    public String getLabel() {
        return label;
    }

    public AbstractDomainMetaEntity setLabel(String label) {
        this.label = label;
        return this;
    }

    @XmlElementWrapper(name = "properties")
    public Map<String, String> getProperties() {
        return properties;
    }

    public AbstractDomainMetaEntity setProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    public AbstractDomainMetaEntity addProperty(String key, String value) {
        if(properties == null) {
           properties = new HashMap<String, String>();
        }
        properties.put(key, value);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractDomainMetaEntity that = (AbstractDomainMetaEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        if (properties != null ? !properties.equals(that.properties) : that.properties != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }
}
