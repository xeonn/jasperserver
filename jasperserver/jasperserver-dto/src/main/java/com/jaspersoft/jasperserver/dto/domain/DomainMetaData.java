package com.jaspersoft.jasperserver.dto.domain;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Paul Lysak
 */
@XmlRootElement(name = "domainMetadata")
public class DomainMetaData {
    private Map<String, String> properties;

    private DomainMetaLevel rootLevel;

    @XmlElementWrapper(name = "properties")
    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DomainMetaData that = (DomainMetaData) o;

        if (properties != null ? !properties.equals(that.properties) : that.properties != null) return false;
        if (rootLevel != null ? !rootLevel.equals(that.rootLevel) : that.rootLevel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = properties != null ? properties.hashCode() : 0;
        result = 31 * result + (rootLevel != null ? rootLevel.hashCode() : 0);
        return result;
    }

    public DomainMetaData addProperty(String key, String value) {
        if(properties == null) {
            properties = new HashMap<String, String>();
        }
        properties.put(key, value);
        return this;
    }

    public DomainMetaData setProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    public DomainMetaLevel getRootLevel() {
        return rootLevel;
    }

    public DomainMetaData setRootLevel(DomainMetaLevel rootLevel) {
        this.rootLevel = rootLevel;
        return this;
    }

    @Override
    public String toString() {
        return "DomainMetaData{" +
                "properties=" + properties +
                ", rootLevel=" + rootLevel +
                '}';
    }

}
