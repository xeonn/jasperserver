package com.jaspersoft.jasperserver.dto.executions;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientMultiAxisQuery;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Vasyl Spachynskyi
 * @version $Id: ClientMultiAxesQueryExecution.java 62579 2016-04-17 18:01:20Z gbacon $
 * @since 15.02.2016
 */
@XmlRootElement(name = "queryExecution")
@XmlType(propOrder = {"query", "dataSourceUri"})
public class ClientMultiAxesQueryExecution extends ClientQueryExecution<ClientMultiAxisQuery,
        ClientMultiAxesQueryExecution> {
    @Valid
    private ClientMultiAxisQuery query;

    public ClientMultiAxesQueryExecution() {
    }

    public ClientMultiAxesQueryExecution(ClientMultiAxesQueryExecution clientExecution) {
        super(clientExecution);
        setQuery(new ClientMultiAxisQuery(clientExecution.getQuery()));
    }

    public ClientMultiAxesQueryExecution(ClientMultiAxisQuery clientQuery, String dataSourceUri) {
        setQuery(clientQuery);
        setDataSourceUri(dataSourceUri);
    }

    @XmlElement
    @Override
    public ClientMultiAxisQuery getQuery() {
        return query;
    }

    public ClientMultiAxesQueryExecution setQuery(ClientMultiAxisQuery query) {
        this.query = query;
        return this;
    }

    /**
     * This method is required for proper JAXB serialization in XML.
     *
     * @return
     */
    @XmlElement
    @Override
    public String getDataSourceUri() {
        return super.getDataSourceUri();
    }

    /**
     * This method is required for proper JAXB serialization in XML.
     *
     * @param dataSourceUri
     * @return
     */
    @Override
    public ClientMultiAxesQueryExecution setDataSourceUri(String dataSourceUri) {
        return super.setDataSourceUri(dataSourceUri);
    }
}
