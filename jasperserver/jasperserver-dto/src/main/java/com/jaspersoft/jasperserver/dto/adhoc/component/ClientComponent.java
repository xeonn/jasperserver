package com.jaspersoft.jasperserver.dto.adhoc.component;

import java.util.List;
import java.util.Map;

/**
 * @author Andriy Godovanets
 * @version $Id: ClientComponent.java 63685 2016-06-29 09:56:06Z agodovan $
 */
public interface ClientComponent {

    Map<String, Object> getProperties();

    Object getProperty(String key);

    String getComponentType();

    List<ClientComponent> getComponents();

}
