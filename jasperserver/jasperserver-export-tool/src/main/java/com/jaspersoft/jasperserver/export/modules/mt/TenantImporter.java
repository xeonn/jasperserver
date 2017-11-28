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
package com.jaspersoft.jasperserver.export.modules.mt;

import com.jaspersoft.jasperserver.api.metadata.user.domain.ProfileAttribute;
import com.jaspersoft.jasperserver.api.metadata.user.domain.Tenant;
import com.jaspersoft.jasperserver.api.metadata.user.service.ProfileAttributeService;
import com.jaspersoft.jasperserver.api.metadata.user.service.TenantService;
import com.jaspersoft.jasperserver.export.modules.BaseImporterModule;
import com.jaspersoft.jasperserver.export.modules.common.ProfileAttributeBean;
import com.jaspersoft.jasperserver.export.modules.mt.beans.TenantBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: TenantImporter.java 16193 2009-03-11 08:18:19Z andy21ca $
 */
public class TenantImporter extends BaseImporterModule
{
	private static final Log log = LogFactory.getLog(TenantImporter.class);
	
	private TenantModuleConfiguration moduleConfiguration;
    private String updateArg;
    private String includeServerSettings;

    public List<String> process()
	{
        initProcess();

		for (Iterator it = indexElement.elementIterator(moduleConfiguration.getTenantIndexElement());
				it.hasNext(); ) {
			Element tenantElement = (Element) it.next();
			String tenantId = tenantElement.getText();
            process(tenantId);
		}
        return null;
	}

    protected Boolean isMultitenancyFeatureSupported() {
        /* For CE deciding to make it true or false? */
        return true;
    }

    protected void process(String tenantId) {
        Tenant existingTenant = getTenantService().getTenant(executionContext, tenantId);
        if (existingTenant != null) {
            if (hasParameter(getUpdateArg())) {
                TenantBean tenantBean = (TenantBean) deserialize(getModuleConfiguration().getTenantsDirectory(),
                        getTenantFileName(tenantId),
                        getModuleConfiguration().getTenantSerializer());
                updateTenant(existingTenant, tenantBean);
            } else {
                commandOut.info("Tenant " + tenantId + " already exists, skipping");
            }
        } else {
            /* Checking if now we have a multi tenant environment. */
            Integer tenantsCount = getTenantService().getNumberOfTenants(executionContext);

            Boolean canImportOrganization = true;

            if (tenantsCount >= 1) {
                /* We already have organizations and at least tenant under it. */
                /* Should check if multitenancy is enabled. */
                if (!isMultitenancyFeatureSupported()) {
                    canImportOrganization = false;
                }
            }

            if (canImportOrganization) {
                if (log.isDebugEnabled()) {
                    log.debug("Deserializing tenant " + tenantId);
                }

                TenantBean tenantBean = (TenantBean) deserialize(getModuleConfiguration().getTenantsDirectory(),
                        getTenantFileName(tenantId),
                        getModuleConfiguration().getTenantSerializer());
                createTenant(tenantBean);
                commandOut.info("Imported tenant " + tenantId);
            } else {
                commandOut.warn("This feature is unavailable under the current license. " +
                        "Please contact support at Jaspersoft.com for help.");
            }
        }
    }

    protected String getTenantFileName(String tenantId) {
        return tenantId + ".xml";
    }

    protected TenantService getTenantService() {
        return getModuleConfiguration().getTenantService();
    }

    private void createTenant(TenantBean tenantBean) {
        Tenant tenant = (Tenant)moduleConfiguration.getObjectMappingFactory().newObject(Tenant.class);
        tenantBean.copyTo(tenant);
        // In previous versions of JasperServer we did not have tenant alias and theme.
        // So, setting alias with tenant id value and default theme for such versions.
        if (tenant.getAlias() == null) {
            tenant.setAlias(tenant.getId());
        }
        if (tenant.getTheme() == null) {
            tenant.setTheme(getModuleConfiguration().getTenantExportConfiguration().getDefaultThemeName());
        }
        getTenantService().putTenant(executionContext, tenant);
        saveTenantAttributes(tenant, tenantBean.getAttributes());
    }

    private void updateTenant(Tenant existing, TenantBean newTenant) {
        existing.setParentId(newTenant.getParentId());
        existing.setTenantDesc(newTenant.getTenantDesc());
        existing.setTenantFolderUri(newTenant.getTenantFolderUri());
        existing.setTenantName(newTenant.getTenantName());
        existing.setTenantNote(newTenant.getTenantNote());
        existing.setTenantUri(existing.getTenantUri());

        if (newTenant.getAlias() != null) {
            existing.setAlias(newTenant.getAlias());
        }
        if (newTenant.getTheme() != null) {
            existing.setTheme(newTenant.getTheme());
        }
        getTenantService().putTenant(executionContext, existing);

        ProfileAttributeBean[] profileAttributes = newTenant.getAttributes();
        if (profileAttributes != null &&
                !hasParameter(includeServerSettings) &&
                (newTenant.getId() == null ||
                        newTenant.getId().equals(TenantService.ORGANIZATIONS))) {
            profileAttributes = getCustomProfileAttributes(profileAttributes);
        }

        saveTenantAttributes(existing, profileAttributes);

        commandOut.info("Updated tenant " + existing.getId());
    }

    protected void saveTenantAttributes(Tenant tenant, ProfileAttributeBean[] attributes) {
        if (attributes != null && attributes.length > 0) {
            for (ProfileAttributeBean profileAttributeBean : attributes) {
                saveTenantAttribute(tenant, profileAttributeBean);
            }
        }
    }

    protected void saveTenantAttribute(Tenant tenant, ProfileAttributeBean attributeBean) {
        ProfileAttributeService attributeService = moduleConfiguration.getAttributeService();
        ProfileAttribute attribute = attributeService.newProfileAttribute(executionContext);
        attribute.setPrincipal(tenant);
        attributeBean.copyTo(attribute);
        attribute.setUri(attribute.getAttrName(), attributeService.generateAttributeHolderUri(attribute.getPrincipal()));
        setPermissions(attribute, attributeBean.getPermissions(), false);
        attributeService.putProfileAttribute(executionContext, attribute);
    }

    protected ProfileAttributeBean[] getCustomProfileAttributes(ProfileAttributeBean[] profileAttributes) {
        ProfileAttributeService attributeService = moduleConfiguration.getAttributeService();
        List<ProfileAttributeBean> profileAttributesToSave = new ArrayList<ProfileAttributeBean>();
        for (ProfileAttributeBean profileAttributeBean : profileAttributes) {
            // TODO: re-implement without getting changer names, remove "getChangerName" method
            String changerName = attributeService.getChangerName(profileAttributeBean.getName());
            if (changerName == null || changerName.equals("custom")) {
                    profileAttributesToSave.add(profileAttributeBean);
            }
        }

        return profileAttributesToSave.toArray(new ProfileAttributeBean[profileAttributesToSave.size()]);
    }

    public TenantModuleConfiguration getModuleConfiguration() {
        return moduleConfiguration;
    }

    public void setModuleConfiguration(TenantModuleConfiguration moduleConfiguration) {
        this.moduleConfiguration = moduleConfiguration;
    }

    public String getUpdateArg() {
        return updateArg;
    }

    public void setUpdateArg(String updateArg) {
        this.updateArg = updateArg;
    }

    public void setIncludeServerSettings(String includeServerSettings) {
        this.includeServerSettings = includeServerSettings;
    }
}
