/*
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.jasperserver.search.filter;

import com.jaspersoft.jasperserver.api.common.domain.ExecutionContext;
import com.jaspersoft.jasperserver.api.metadata.common.domain.Folder;
import com.jaspersoft.jasperserver.api.metadata.common.domain.ResourceLookup;
import com.jaspersoft.jasperserver.api.metadata.common.service.ResourceFactory;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.ReportUnit;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.impl.RepoReportUnit;
import com.jaspersoft.jasperserver.api.search.SearchCriteria;
import com.jaspersoft.jasperserver.search.common.SearchAttributes;
import com.jaspersoft.jasperserver.search.service.RepositorySearchCriteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.*;

/**
 * <p>Filters resources by resourceType field.</p>
 *
 * @author Yuriy Plakosh
 * @version $Id: ResourceTypeFilter.java 51441 2014-11-14 14:01:06Z bkolesnikov $
 */
public class ResourceTypeFilter extends BaseSearchFilter implements Serializable {
    private Map<String, List<String>> filterOptionToResourceTypes;
    private ResourceFactory persistentClassMappings;
    private Map<String, List<String>> persistentResourceTypesCache =
            Collections.synchronizedMap(new HashMap<String, List<String>>());

    public void setFilterOptionToResourceTypes(Map<String, List<String>> filterOptionToResourceTypes) {
        this.filterOptionToResourceTypes = filterOptionToResourceTypes;
    }

    public void setPersistentClassMappings(ResourceFactory persistentClassMappings) {
        this.persistentClassMappings = persistentClassMappings;
    }

    public void applyRestrictions(String type, ExecutionContext context, SearchCriteria criteria) {
        SearchAttributes searchAttributes = getSearchAttributes(context);

        if (searchAttributes != null && searchAttributes.getState() != null) {
            String resourceTypeFilterOption =
                    searchAttributes.getState().getCustomFiltersMap().get("resourceTypeFilter");

            List<String> resourceTypes = null;
            if (resourceTypeFilterOption != null) {
                resourceTypes = filterOptionToResourceTypes.get(resourceTypeFilterOption);
            }

            if (resourceTypes != null) {
                criteria.add(Restrictions.in("resourceType", resourceTypes));
            }
        } else {
            final RepositorySearchCriteria repositorySearchCriteria = getTypedAttribute(context, RepositorySearchCriteria.class);
            if (repositorySearchCriteria != null) {
                if (repositorySearchCriteria.getResourceTypes() != null && !repositorySearchCriteria.getResourceTypes().isEmpty()) {
                    List<String> types = new ArrayList<String>(repositorySearchCriteria.getResourceTypes());
                    boolean addFolders = types.remove(Folder.class.getName());
                    boolean extractTopics = types.remove("com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.Topic");

                    Criterion criterion = Restrictions.in("resourceType", types);

                    if (addFolders && ResourceLookup.class.getName().equals(type)) {
                        Criterion folderCriterion = Restrictions.isNull("resourceType");
                        criterion = types.isEmpty() ? folderCriterion : Restrictions.or(folderCriterion, criterion);
                    }

                    if(extractTopics){

                        SearchCriteria notAdhocReportsCriteria = SearchCriteria.forClass(RepoReportUnit.class);
                        notAdhocReportsCriteria
                                .createAlias("parent", "p")
                                .createAlias("dataSource", "rds", CriteriaSpecification.LEFT_JOIN);

                        Criterion isNotAdHoc = Restrictions.or(
                                Restrictions.isNull("rds.resourceType"),
                                Restrictions.ne("rds.resourceType", "com.jaspersoft.ji.adhoc.AdhocDataView"));

                        Criterion hasPublicTopicsFolderUri = Restrictions.like("p.URI", "/public/adhoc/topics%");
                        Criterion hasOrganizationTopicsFolderUri = Restrictions.like("p.URI", "/organizations/%/adhoc/topics%");

                        Criterion topicsCriterion = Restrictions.and(isNotAdHoc,
                                Restrictions.or(hasOrganizationTopicsFolderUri, hasPublicTopicsFolderUri));

                        notAdhocReportsCriteria
                                .add(topicsCriterion)
                                .setProjection(Projections.property("id"));

                        List<Long> topicIds = getHibernateTemplate().findByCriteria(notAdhocReportsCriteria);
                        Criterion isReportUnit = Restrictions.eq("resourceType", ReportUnit.class.getName());
                        Criterion isTopic = Restrictions.and(isReportUnit, Restrictions.in("id", topicIds));

                        criterion = Restrictions.or(criterion, isTopic);
                    }

                    criteria.add(criterion);
                }
            } else {
                throw new RuntimeException("Resource type filter not found in the custom filters map.");
            }
        }
    }
}
