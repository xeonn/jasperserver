package com.jaspersoft.jasperserver.export.modules.mt.beans;

import com.jaspersoft.jasperserver.api.metadata.user.domain.ProfileAttribute;
import com.jaspersoft.jasperserver.api.metadata.user.domain.Tenant;
import com.jaspersoft.jasperserver.export.modules.BaseExporterModule;
import com.jaspersoft.jasperserver.export.modules.common.ProfileAttributeBean;

import java.util.Iterator;
import java.util.List;

/**
 * @author Voloyda Sabadosh
 * @version $Id: TenantBean.java 54590 2015-04-22 17:55:42Z vzavadsk $
 */
public class TenantBean {
    private String id = null;
    private String alias = null;
    private String parentId = null;
    private String tenantName = null;
    private String tenantDesc = null;
    private String tenantNote = null;
    private String tenantUri = null;
    private String tenantFolderUri = null;
    private String theme = null;
    private ProfileAttributeBean[] attributes;

    public void copyFrom(Tenant tenant) {
        setId(tenant.getId());
        setAlias(tenant.getAlias());
        setParentId(tenant.getParentId());
        setTenantName(tenant.getTenantName());
        setTenantDesc(tenant.getTenantDesc());
        setTenantNote(tenant.getTenantNote());
        setTenantUri(tenant.getTenantUri());
        setTenantFolderUri(tenant.getTenantFolderUri());
        setTheme(tenant.getTheme());
    }

    public void copyTo(Tenant tenant) {
        tenant.setId(getId());
        tenant.setAlias(getAlias());
        tenant.setParentId(getParentId());
        tenant.setTenantName(getTenantName());
        tenant.setTenantDesc(getTenantDesc());
        tenant.setTenantNote(getTenantNote());
        tenant.setTenantUri(getTenantUri());
        tenant.setTenantFolderUri(getTenantFolderUri());
        tenant.setTheme(getTheme());
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantDesc() {
        return tenantDesc;
    }

    public void setTenantDesc(String tenantDesc) {
        this.tenantDesc = tenantDesc;
    }

    public String getTenantNote() {
        return tenantNote;
    }

    public void setTenantNote(String tenantNote) {
        this.tenantNote = tenantNote;
    }

    public String getTenantUri() {
        return tenantUri;
    }

    public void setTenantUri(String tenantUri) {
        this.tenantUri = tenantUri;
    }

    public String getTenantFolderUri() {
        return tenantFolderUri;
    }

    public void setTenantFolderUri(String tenantFolderUri) {
        this.tenantFolderUri = tenantFolderUri;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public ProfileAttributeBean[] getAttributes() {
        return attributes;
    }

    public void setAttributes(ProfileAttributeBean[] attributes) {
        this.attributes = attributes;
    }
}
