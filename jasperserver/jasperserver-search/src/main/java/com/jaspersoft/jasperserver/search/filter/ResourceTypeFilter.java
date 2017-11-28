package com.jaspersoft.jasperserver.search.filter;

import com.jaspersoft.jasperserver.api.common.domain.ExecutionContext;
import com.jaspersoft.jasperserver.api.metadata.common.domain.Folder;
import com.jaspersoft.jasperserver.api.metadata.common.domain.ResourceLookup;
import com.jaspersoft.jasperserver.api.metadata.common.service.ResourceFactory;
import com.jaspersoft.jasperserver.api.search.SearchCriteria;
import com.jaspersoft.jasperserver.search.common.SearchAttributes;
import com.jaspersoft.jasperserver.search.service.RepositorySearchCriteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Filters resources by resourceType field.</p>
 *
 * @author Yuriy Plakosh
 * @version $Id: ResourceTypeFilter.java 38151 2013-09-26 09:30:32Z vsabadosh $
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
                    Criterion criterion = Restrictions.in("resourceType", types);

                    if (addFolders && ResourceLookup.class.getName().equals(type)) {
                        Criterion folderCriterion = Restrictions.isNull("resourceType");
                        criteria.add(types.isEmpty() ? folderCriterion : Restrictions.or(folderCriterion, criterion));
                    } else {
                        criteria.add(criterion);
                    }
                }
            } else {
                throw new RuntimeException("Resource type filter not found in the custom filters map.");
            }
        }
    }
}
