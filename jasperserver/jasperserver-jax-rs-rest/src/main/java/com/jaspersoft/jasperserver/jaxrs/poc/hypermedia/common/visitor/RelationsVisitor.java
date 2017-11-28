package com.jaspersoft.jasperserver.jaxrs.poc.hypermedia.common.visitor;

import com.amazonaws.services.autoscaling.model.Activity;

/**
 * @author: Igor.Nesterenko
 * @version: ${Id}
 */
public class RelationsVisitor<R> {

    protected R representation;

    /**
     * @deprecated should't be a state of visitor
     */
    public R getRepresentation() {
        return representation;
    }

    /**
     * @deprecated should't be a state of visitor
     */
    public void setRepresentation(R representation) {
        this.representation = representation;
    }

}
