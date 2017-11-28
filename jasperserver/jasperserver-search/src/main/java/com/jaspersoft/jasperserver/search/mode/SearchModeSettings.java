package com.jaspersoft.jasperserver.search.mode;

import java.io.Serializable;

import com.jaspersoft.jasperserver.search.common.RepositorySearchConfiguration;
import com.jaspersoft.jasperserver.search.state.InitialStateResolver;
import com.jaspersoft.jasperserver.search.util.JSONConverter;

/**
 * <p>Settings for SearchMode</p>
 *
 * @author Yuriy Plakosh
 * @version $Id: SearchModeSettings.java 38151 2013-09-26 09:30:32Z vsabadosh $
 */
public class SearchModeSettings  implements Serializable{
    private RepositorySearchConfiguration repositorySearchConfiguration;
    private JSONConverter jsonConverter;
    private InitialStateResolver initialStateResolver;

    public RepositorySearchConfiguration getRepositorySearchConfiguration() {
        return repositorySearchConfiguration;
    }

    public void setRepositorySearchConfiguration(RepositorySearchConfiguration repositorySearchConfiguration) {
        this.repositorySearchConfiguration = repositorySearchConfiguration;
    }

    public JSONConverter getJsonConverter() {
        return jsonConverter;
    }

    public void setJsonConverter(JSONConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    public InitialStateResolver getInitialStateResolver() {
        return initialStateResolver;
    }

    public void setInitialStateResolver(InitialStateResolver initialStateResolver) {
        this.initialStateResolver = initialStateResolver;
    }
}
