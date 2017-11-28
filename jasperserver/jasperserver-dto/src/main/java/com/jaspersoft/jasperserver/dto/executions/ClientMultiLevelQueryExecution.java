package com.jaspersoft.jasperserver.dto.executions;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientMultiLevelQuery;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Vasyl Spachynskyi
 * @version $Id: ClientMultiLevelQueryExecution.java 62579 2016-04-17 18:01:20Z gbacon $
 * @since 15.02.2016
 */
@XmlRootElement(name = "queryExecution")
@XmlType(propOrder = {"query", "dataSourceUri"})
public class ClientMultiLevelQueryExecution extends ClientQueryExecution<ClientMultiLevelQuery,
        ClientMultiLevelQueryExecution> {
    @Valid
    private ClientMultiLevelQuery query;

    public ClientMultiLevelQueryExecution() {
    }

    public ClientMultiLevelQueryExecution(ClientMultiLevelQueryExecution clientExecution) {
        super(clientExecution);
        setQuery(new ClientMultiLevelQuery(clientExecution.getQuery()));
    }

    public ClientMultiLevelQueryExecution(ClientMultiLevelQuery query, String dsUri) {
        setQuery(query);
        setDataSourceUri(dsUri);
    }

    @Override
    public ClientQueryParams getParams() {
        return super.getParams();
    }

    @XmlElement(type = ClientMultiLevelQuery.class)
    @Override
    public ClientMultiLevelQuery getQuery() {
        return query;
    }

    public ClientMultiLevelQueryExecution setQuery(ClientMultiLevelQuery query) {
        this.query = query;
        return this;
    }

    @XmlElement
    @Override
    public String getDataSourceUri() {
        return super.getDataSourceUri();
    }

    @Override
    public ClientMultiLevelQueryExecution setDataSourceUri(String dataSourceUri) {
        return super.setDataSourceUri(dataSourceUri);
    }
}
