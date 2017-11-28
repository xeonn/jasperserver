package com.jaspersoft.jasperserver.remote.common;

import java.util.List;

/**
 * @author Volodya Sabadosh (vsabadosh@jaspersoft.com)
 * @version $Id $
 */
public class RoleSearchCriteria {

    private java.lang.String roleName;

    private java.lang.String tenantId;

    private java.lang.Boolean includeSubOrgs;

    private java.lang.Boolean hasAllUsers;

    private int maxRecords;

    private List<String> usersNames;

    public RoleSearchCriteria() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public int getMaxRecords() {
        return maxRecords;
    }

    public void setMaxRecords(int maxRecords) {
        this.maxRecords = maxRecords;
    }

    public Boolean getHasAllUsers() {
        return hasAllUsers;
    }

    public void setHasAllUsers(Boolean hasAllUsers) {
        this.hasAllUsers = hasAllUsers;
    }

    public List<String> getUsersNames() {
        return usersNames;
    }

    public void setUsersNames(List<String> usersNames) {
        this.usersNames = usersNames;
    }
}
