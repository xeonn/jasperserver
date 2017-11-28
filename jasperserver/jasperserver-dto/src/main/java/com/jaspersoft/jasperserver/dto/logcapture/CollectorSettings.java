/*
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 * Licensed under commercial Jaspersoft Subscription License Agreement
 */
package com.jaspersoft.jasperserver.dto.logcapture;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;

/**
 * DTO for Diagnostic Collector's settings.
 *
 * @author Yakiv Tymoshenko
 * @version $Id: CollectorSettings.java 61296 2016-02-25 21:53:37Z mchan $
 * @since 11.08.14
 */
@XmlRootElement
@XmlType(propOrder = {"id", "name", "verbosity", "logFilterParameters", "status"})
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CollectorSettings {

    public static void marshall(CollectorSettings collectorSettings, String filePath) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CollectorSettings.class);
        Marshaller xmlMarshaller = jaxbContext.createMarshaller();
        xmlMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        xmlMarshaller.marshal(collectorSettings, new File(filePath));
    }

    public static CollectorSettings unMarshall(String filePath) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CollectorSettings.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (CollectorSettings) unmarshaller.unmarshal(new File(filePath));
    }

    private LogFilterParameters logFilterParameters;
    private String id;
    private String name;
    private String verbosity;
    private String status;

    public CollectorSettings() {
        logFilterParameters = new LogFilterParameters();
    }

    // Cloning constructor required by Jaspersoft REST DTo convention.
    @SuppressWarnings("unused")
    public CollectorSettings(CollectorSettings other) {
        this.id = other.getId();
        this.name = other.getName();
        this.verbosity = other.getVerbosity();
        this.status = other.getStatus();
        this.logFilterParameters = new LogFilterParameters(other.getLogFilterParameters());
    }

    /*
        Don't name it "isExportEnabled" because it would look like a property in resulting JSON/XML.
     */
    public boolean exportEnabled() {
        if (logFilterParameters == null) {
            return false;
        }
        ResourceAndSnapshotFilter resourceAndSnapshotFilter = logFilterParameters.getResourceAndSnapshotFilter();
        return resourceAndSnapshotFilter != null
                && resourceAndSnapshotFilter.exportDatasnapshotEnabled();
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollectorSettings)) return false;

        CollectorSettings that = (CollectorSettings) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (logFilterParameters != null ? !logFilterParameters.equals(that.logFilterParameters) : that.logFilterParameters != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (!verbosity.equals(that.verbosity)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = logFilterParameters != null ? logFilterParameters.hashCode() : 0;
        result = 31 * result + id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + verbosity.hashCode();
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @XmlElement(name = "filterBy")
    public LogFilterParameters getLogFilterParameters() {
        return logFilterParameters;
    }

    // Getter required for correct json/xml transformation by jaxb.
    @SuppressWarnings("unused")
    public CollectorSettings setLogFilterParameters(LogFilterParameters logFilterParameters) {
        this.logFilterParameters = logFilterParameters;
        return  this;
    }

    public String getId() {
        return id;
    }

    public CollectorSettings setId(String id) {
        this.id = id;
        return  this;
    }

    public String getName() {
        return name;
    }

    public CollectorSettings setName(String name) {
        this.name = name;
        return this;
    }

    public String getVerbosity() {
        return verbosity;
    }

    // Getter required for correct json/xml transformation by jaxb
    @SuppressWarnings("unused")
    public CollectorSettings setVerbosity(String verbosity) {
        this.verbosity = verbosity;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public CollectorSettings setStatus(String status) {
        this.status = status;
        return this;
    }


}
