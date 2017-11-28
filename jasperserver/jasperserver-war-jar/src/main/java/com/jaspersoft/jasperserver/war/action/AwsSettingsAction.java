package com.jaspersoft.jasperserver.war.action;

import org.springframework.webflow.execution.RequestContext;

/**
 * Action for handling AWS Settings saving
 *
 * @author agodovanets@jaspersoft.com
 * @version $Id: AwsSettingsAction.java 28044 2013-01-25 13:27:11Z agodovanets $
 */
public class AwsSettingsAction extends BaseSettingsAction {
    @Override
    public void init(RequestContext context) {
        context.getRequestScope().put("aws.db.security.group.changes.enabled", getPropertiesManagementService().getProperty("aws.db.security.group.changes.enabled"));
        context.getRequestScope().put("aws.db.security.group.name", getPropertiesManagementService().getProperty("aws.db.security.group.name"));
        context.getRequestScope().put("aws.db.security.group.description", getPropertiesManagementService().getProperty("aws.db.security.group.description"));
        context.getRequestScope().put("aws.db.security.group.ingressPublicIp", getPropertiesManagementService().getProperty("aws.db.security.group.ingressPublicIp"));
        context.getRequestScope().put("aws.db.security.group.suppressEc2CredentialsWarnings", getPropertiesManagementService().getProperty("aws.db.security.group.suppressEc2CredentialsWarnings"));
    }

    // TODO: implement URI validation
    @Override
    protected String validate(String option, String value) {
        return null;
    }

    @Override
    protected String getSettingsContextName() {
        return "awsConfigurationSettingsUpdate";
    }
}
