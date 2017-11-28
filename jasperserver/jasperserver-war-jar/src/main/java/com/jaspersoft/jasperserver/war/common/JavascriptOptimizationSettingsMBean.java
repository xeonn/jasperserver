package com.jaspersoft.jasperserver.war.common;

/**
 * MBean interface to expose JMX methods and attributes
 *
 * Created by asokolnikov on 6/10/16.
 */
public interface JavascriptOptimizationSettingsMBean {

    /**
     * Regenerates runtime hash
     */
    public void reGenerateRuntimeHash();

}
