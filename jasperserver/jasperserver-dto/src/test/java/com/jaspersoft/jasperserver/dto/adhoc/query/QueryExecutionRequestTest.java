package com.jaspersoft.jasperserver.dto.adhoc.query;

import com.jaspersoft.jasperserver.dto.adhoc.filters.FilterTest;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxesQueryExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryExecution;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 2/2/16 8:16AM
 */
public class QueryExecutionRequestTest extends FilterTest {

    protected static final String DATASOURCE_URI = "/public/Samples/Ad_Hoc_Views/04__Product_Results_by_Store_Type";

    @Override
    protected String xml(Object value) throws Exception {
        StringWriter w = new StringWriter();
        getMarshaller(value.getClass()).marshal(value, w);
        return w.toString();
    }

    protected <T> String xmlForEntity(Object value, Class<T> entity) throws JAXBException {
        StringWriter w = new StringWriter();
        getMarshaller(entity).marshal(value, w);
        return w.toString();
    }

    @Override
    @Deprecated
    protected <T> T dto(String xml) throws IOException, JAXBException {
        return (T) getUnmarshaller(ClientMultiLevelQueryExecution.class, ClientMultiAxesQueryExecution.class).unmarshal(IOUtils.toInputStream(xml));
    }

    protected <T> T dtoForEntity(String xml, Class<T> entity) throws IOException, JAXBException {
        return (T) getUnmarshaller(entity).unmarshal(IOUtils.toInputStream(xml));
    }

    protected ClientMultiLevelQueryExecution request(ClientMultiLevelQuery cq) {
        return new ClientMultiLevelQueryExecution(cq, DATASOURCE_URI);
    }

    protected ClientMultiAxesQueryExecution request(ClientMultiAxisQuery cq) {
        return new ClientMultiAxesQueryExecution(cq, DATASOURCE_URI);
    }


}