/**
 * Package-level schema mapping needed to avoid repeating of schema URI for every xs:anyType element.
 */
@XmlSchema(xmlns = {@XmlNs(prefix = "xsi", namespaceURI = XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI),
        @XmlNs(prefix = "xs", namespaceURI = XMLConstants.W3C_XML_SCHEMA_NS_URI)})
package com.jaspersoft.jasperserver.dto.query;

import javax.xml.XMLConstants;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlSchema;
