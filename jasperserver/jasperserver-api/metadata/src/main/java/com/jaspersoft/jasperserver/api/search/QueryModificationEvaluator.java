package com.jaspersoft.jasperserver.api.search;

import com.jaspersoft.jasperserver.api.common.domain.ExecutionContext;

/**
 * User: carbiv
 * Date: 6/4/12
 * Time: 3:56 PM
 * to help decide if and how to update the hibernate query.
 */
public interface QueryModificationEvaluator {
    public static final String FORCE_REPO_RESOURCE = "forceRepoResource";

    public boolean useFullResource(ExecutionContext context);

}
