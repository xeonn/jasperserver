package com.jaspersoft.jasperserver.dto.adhoc.component;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.*;

/**
 * @author Andriy Godovanets
 * @version $Id: ClientGenericComponent.java 64086 2016-07-27 12:25:21Z agodovan $
 */
public class ClientGenericComponent implements ClientComponent {
    private Map<String, Object> propertyMap;
    private String componentType;
    private List<? extends ClientComponent> components;

    public ClientGenericComponent() {
        propertyMap = new LinkedHashMap<String, Object>();
        components = new ArrayList<ClientComponent>();
    }

    public ClientGenericComponent(ClientGenericComponent component) {
        setComponentType(component.getComponentType());

        if (component.getComponents() != null) {
            List<ClientGenericComponent> children = new ArrayList<ClientGenericComponent>();

            for (ClientComponent c : component.getComponents()) {
                children.add(new ClientGenericComponent((ClientGenericComponent) c));
            }
            setComponents(children);
        }

        if (component.getProperties() != null) {
            setProperties(new LinkedHashMap<String, Object>(component.getProperties()));
        }
    }

    @Override
    public Map<String, Object> getProperties() {
        return propertyMap;
    }

    @SuppressWarnings("unchecked")
    public ClientGenericComponent setProperties(Map<String, Object> properties) {
        this.propertyMap = new LinkedHashMap<String, Object>(properties);
        return this;
    }

    @Override
    public Object getProperty(String key) {
        return propertyMap.get(key);
    }

    @SuppressWarnings("unchecked")
    protected ClientGenericComponent setProperty(String key, Object value) {
        propertyMap.put(key, value);
        return this;
    }

    @Override
    public String getComponentType() {
        return componentType;
    }

    @SuppressWarnings("unchecked")
    public ClientGenericComponent setComponentType(String type) {
        this.componentType = type;
        return this;
    }


    @XmlElementWrapper(name = "components")
    @XmlElement(type = ClientGenericComponent.class, name = "components")
    @Override
    public List<ClientComponent> getComponents() {
        return (List<ClientComponent>) components;
    }

    public ClientGenericComponent setComponents(List<ClientGenericComponent> components) {
        this.components = components;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientGenericComponent)) return false;
        ClientGenericComponent that = (ClientGenericComponent) o;
        return Objects.equals(propertyMap, that.propertyMap) &&
                Objects.equals(componentType, that.componentType) &&
                Objects.equals(components, that.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyMap, componentType, components);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClientGenericComponent{");
        sb.append("propertyMap=").append(propertyMap);
        sb.append(", componentType='").append(componentType).append('\'');
        sb.append(", components=").append(components);
        sb.append('}');
        return sb.toString();
    }


}
