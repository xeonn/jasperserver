package com.jaspersoft.jasperserver.jaxrs.poc.hypermedia.common.activity;

import com.jaspersoft.jasperserver.jaxrs.poc.hypermedia.common.Relation;
import com.jaspersoft.jasperserver.jaxrs.poc.hypermedia.common.activity.GenericRequest;
import com.jaspersoft.jasperserver.jaxrs.poc.hypermedia.common.representation.HypermediaRepresentation;
import com.jaspersoft.jasperserver.jaxrs.poc.hypermedia.common.representation.embedded.EmbeddedElement;

/**
 * @author: Igor.Nesterenko
 * @version: ${Id}
 */
public interface Activity<R extends  HypermediaRepresentation, D> {


    EmbeddedElement proceed();

    D findData(GenericRequest request);

    D getData();

    Activity setData(D data);

    Activity setGenericRequest(GenericRequest genericRequest);

    GenericRequest getGenericRequest();

    R getRepresentation();

    Activity setRepresentation(R representation);

    R buildRepresentation();

    EmbeddedElement buildLink();

    Relation getOwnRelation();
}
