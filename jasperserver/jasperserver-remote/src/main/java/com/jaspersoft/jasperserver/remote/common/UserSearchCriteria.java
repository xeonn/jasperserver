package com.jaspersoft.jasperserver.remote.common;

import com.jaspersoft.jasperserver.api.metadata.user.domain.Role;

import java.util.List;

/**
 * @author Volodya Sabadosh (vsabadosh@jaspersoft.com)
 * @version $Id $
 */
public class UserSearchCriteria {
    private java.lang.String name;

    private java.lang.String tenantId;

    private java.lang.Boolean includeSubOrgs;

    private java.lang.Boolean hasAllRequiredRoles = Boolean.TRUE;  //default

    private List<Role> requiredRoles;

    private int maxRecords;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Boolean getIncludeSubOrgs() {
        return includeSubOrgs;
    }

    public void setIncludeSubOrgs(Boolean includeSubOrgs) {
        this.includeSubOrgs = includeSubOrgs;
    }

    public List<Role> getRequiredRoles() {
        return requiredRoles;
    }

    public void setRequiredRoles(List<Role> requiredRoles) {
        this.requiredRoles = requiredRoles;
    }

    public int getMaxRecords() {
        return maxRecords;
    }

    public void setMaxRecords(int maxRecords) {
        this.maxRecords = maxRecords;
    }

    public Boolean getHasAllRequiredRoles() {
        return hasAllRequiredRoles;
    }

    public void setHasAllRequiredRoles(Boolean haveAllRequiredRoles) {
        this.hasAllRequiredRoles = haveAllRequiredRoles;
    }
}
