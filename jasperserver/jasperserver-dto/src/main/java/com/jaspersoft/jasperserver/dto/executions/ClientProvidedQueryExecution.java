package com.jaspersoft.jasperserver.dto.executions;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Vasyl Spachynskyi
 * @version $Id: ClientProvidedQueryExecution.java 62579 2016-04-17 18:01:20Z gbacon $
 * @since 15.02.2016
 */
@XmlRootElement(name = "queryExecution")
public class ClientProvidedQueryExecution extends AbstractClientExecution<ClientProvidedQueryExecution> {

    public ClientProvidedQueryExecution() {
    }

    public ClientProvidedQueryExecution(String dataSourceUri) {
        setDataSourceUri(dataSourceUri);
    }

    public ClientProvidedQueryExecution(ClientProvidedQueryExecution clientExecution) {
        super(clientExecution);
        setParams(new ClientQueryParams(clientExecution.getParams()));
    }

    @Override
    public ClientQueryParams getParams() {
        return super.getParams();
    }

    @Override
    public ClientProvidedQueryExecution setDataSourceUri(String dataSourceUri) {
        return super.setDataSourceUri(dataSourceUri);
    }

    @XmlElement
    @Override
    public String getDataSourceUri() {
        return super.getDataSourceUri();
    }
}
