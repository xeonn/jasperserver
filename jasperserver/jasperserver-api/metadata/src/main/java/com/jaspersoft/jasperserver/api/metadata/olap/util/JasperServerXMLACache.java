package com.jaspersoft.jasperserver.api.metadata.olap.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.olap4j.driver.xmla.cache.XmlaOlap4jInvalidStateException;
import org.olap4j.driver.xmla.cache.XmlaOlap4jNamedMemoryCache;

/**
 * this class overwrites XmlaOlap4jNamedMemoryCache to fix bug 29966
 * by enforcing UTF-8 characterset for cached responses
 * Created by IntelliJ IDEA. User: ichan Date: 11/27/12 Time: 2:47 PM
 */
public class JasperServerXMLACache extends XmlaOlap4jNamedMemoryCache {

	protected static final Log log = LogFactory.getLog(JasperServerXMLACache.class);

    private static String USER = "USER";

    public JasperServerXMLACache() {
		super();
	}

    @Override
    public byte[] get(String id, URL url, byte[] request)
            throws RuntimeException {
        log.debug("Intercepted XMLA cache get\nid: "+id+"\nurl: "+url+"\nrequest: " + request);
        try {
            byte[] response = super.get(id, url, request);
            if (response != null) {
                return new String(response, "UTF-8").getBytes("UTF-8");
            } else {
                return null;
            }
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Failed to convert XMLA cached response to UTF-8", ex);
        }  catch (XmlaOlap4jInvalidStateException ex) {
            throw new RuntimeException("Xmla olap4j invalid state exception", ex);
        }
    }

    @Override
    public String setParameters(
            Map<String, String> config,
            Map<String, String> props) {
        if (props.containsKey(XmlaOlap4jNamedMemoryCache.Property.NAME.name()) && config.containsKey(USER)) {
            String refId = props.get(XmlaOlap4jNamedMemoryCache.Property.NAME.name()) + "_" + config.get(USER);
            //Put cache info per authorized user.
            props.put(XmlaOlap4jNamedMemoryCache.Property.NAME.name(), refId);
        }
        return super.setParameters(config, props);
    }

}
