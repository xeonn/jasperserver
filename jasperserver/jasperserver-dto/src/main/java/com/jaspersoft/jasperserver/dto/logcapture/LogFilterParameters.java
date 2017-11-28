/*
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 * Licensed under commercial Jaspersoft Subscription License Agreement
 */
package com.jaspersoft.jasperserver.dto.logcapture;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Yakiv Tymoshenko
 * @version $Id: Id $
 * @since 07.10.14
 */
@XmlRootElement
@XmlType(propOrder = {})
public class LogFilterParameters {
    private String userId;
    private String sessionId;
    private ResourceAndSnapshotFilter resourceAndSnapshotFilter;

    public LogFilterParameters() {
        resourceAndSnapshotFilter = new ResourceAndSnapshotFilter();
    }

    public LogFilterParameters(LogFilterParameters other) {
        this.userId = other.getUserId();
        this.sessionId = other.getSessionId();
        this.resourceAndSnapshotFilter = new ResourceAndSnapshotFilter(other.getResourceAndSnapshotFilter());
    }

    public String getUserId() {
        return userId;
    }

    public LogFilterParameters setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getSessionId() {
        return sessionId;
    }

    public LogFilterParameters setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    @XmlElement(name = "resource")
    public ResourceAndSnapshotFilter getResourceAndSnapshotFilter() {
        return resourceAndSnapshotFilter;
    }

    public LogFilterParameters setResourceAndSnapshotFilter(ResourceAndSnapshotFilter resourceAndSnapshotFilter) {
        this.resourceAndSnapshotFilter = resourceAndSnapshotFilter;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogFilterParameters)) return false;

        LogFilterParameters that = (LogFilterParameters) o;

        if (getUserId() != null ? !getUserId().equals(that.getUserId()) : that.getUserId() != null) return false;
        if (getSessionId() != null ? !getSessionId().equals(that.getSessionId()) : that.getSessionId() != null)
            return false;
        return !(getResourceAndSnapshotFilter() != null ? !getResourceAndSnapshotFilter().equals(that.getResourceAndSnapshotFilter()) : that.getResourceAndSnapshotFilter() != null);

    }

    @Override
    public int hashCode() {
        int result = getUserId() != null ? getUserId().hashCode() : 0;
        result = 31 * result + (getSessionId() != null ? getSessionId().hashCode() : 0);
        result = 31 * result + (getResourceAndSnapshotFilter() != null ? getResourceAndSnapshotFilter().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LogFilterParameters{" +
                "userId='" + userId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", resourceAndSnapshotFilter=" + resourceAndSnapshotFilter +
                '}';
    }
}
