package com.jaspersoft.jasperserver.dto.adhoc.query;

import com.jaspersoft.jasperserver.dto.adhoc.filters.FilterTest;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryExecution;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 2/2/16 8:16AM
 */
public class QueryTest extends FilterTest {

    @Override
    protected String xml(Object value) throws Exception {
        StringWriter w = new StringWriter();
        getMarshaller(ClientMultiLevelQuery.class, ClientMultiAxisQuery.class).marshal(value, w);
        return w.toString().replace("\r\n", "\n").replace("\r", "\n");
    }

    @Override
    protected <T> T dto(String xml) throws IOException, JAXBException {
        return (T) getUnmarshaller(ClientMultiLevelQuery.class, ClientMultiAxisQuery.class).unmarshal(IOUtils.toInputStream(xml));
    }

    protected <T> T dtoForEntity(String xml, Class<T> entity) throws IOException, JAXBException {
        return (T) getUnmarshaller(entity).unmarshal(IOUtils.toInputStream(xml));
    }

    protected ClientMultiLevelQueryExecution request(ClientMultiLevelQuery cq) {
        return new ClientMultiLevelQueryExecution(cq, "/uri");
    }
}