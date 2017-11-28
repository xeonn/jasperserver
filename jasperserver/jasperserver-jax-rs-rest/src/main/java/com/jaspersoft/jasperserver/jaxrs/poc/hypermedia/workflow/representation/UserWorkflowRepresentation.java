package com.jaspersoft.jasperserver.jaxrs.poc.hypermedia.workflow.representation;


import com.jaspersoft.jasperserver.jaxrs.poc.hypermedia.common.representation.HypermediaRepresentation;
import com.jaspersoft.jasperserver.jaxrs.poc.hypermedia.workflow.dto.UserWorkflow;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Igor.Nesterenko
 * @version $Id: UserWorkflowRepresentation.java 37101 2013-09-10 15:43:02Z inesterenko $
 */

@XmlRootElement(name = "workflow")
public class UserWorkflowRepresentation extends HypermediaRepresentation {

    private UserWorkflow userWorkflow;

    public UserWorkflowRepresentation(UserWorkflow userWorkflow){
        this.userWorkflow = userWorkflow;
    }

    public String getName() {
        return userWorkflow.getName();
    }

    public String getLabel() {
        return userWorkflow.getLabel();
    }

    public String getDescription() {
        return userWorkflow.getDescription();
    }

    public String getParentName() {
        return userWorkflow.getParentName();
    }

    public String getContentReferenceId() {
        return userWorkflow.getContentReferenceId();
    }

    @XmlTransient
    public UserWorkflow getBody(){
        return userWorkflow;
    }

    @Override
    public int hashCode() {
        return (new HashCodeBuilder()
                .append(this.getName())
                .append(this.getDescription())
                .append(this.getLabel())
                .append(this.getEmbedded().hashCode())
                .append(this.getLinks().hashCode())
        ).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof UserWorkflowRepresentation)) {
            return false;
        }
        UserWorkflowRepresentation that = (UserWorkflowRepresentation)obj;
        return new EqualsBuilder()
                .append(this.getName(), that.getName())
                .append(this.getDescription(), that.getDescription())
                .append(this.getLabel(), that.getLabel())
                .append(this.getEmbedded(), that.getEmbedded())
                .append(this.getLinks(), that.getLinks())
                .isEquals();
    }
}
