package com.jaspersoft.jasperserver.remote.resources.converters;

import com.jaspersoft.jasperserver.api.metadata.user.domain.ProfileAttribute;
import com.jaspersoft.jasperserver.api.metadata.user.domain.client.ProfileAttributeImpl;
import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.remote.exception.IllegalParameterValueException;
import com.jaspersoft.jasperserver.remote.exception.MandatoryParameterNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <p></p>
 *
 * @author Zakhar.Tomchenco
 * @version $Id$
 */
@Service
public class UserAttributesConverter implements ToServerConverter<ClientUserAttribute, ProfileAttribute>, ToClientConverter<ProfileAttribute, ClientUserAttribute> {
    @Override
    public ClientUserAttribute toClient(ProfileAttribute serverObject, ToClientConversionOptions options) {
        ClientUserAttribute client = new ClientUserAttribute();
        client.setName(serverObject.getAttrName());
        client.setValue(serverObject.getAttrValue());

        return client;
    }

    @Override
    public ProfileAttribute toServer(ClientUserAttribute clientObject, ToServerConversionOptions options) throws IllegalParameterValueException, MandatoryParameterNotFoundException {
        return toServer(clientObject, new ProfileAttributeImpl(), null);
    }

    @Override
    public ProfileAttribute toServer(ClientUserAttribute clientObject, ProfileAttribute resultToUpdate, ToServerConversionOptions options) throws IllegalParameterValueException, MandatoryParameterNotFoundException {
        resultToUpdate.setAttrName(clientObject.getName());
        resultToUpdate.setAttrValue(clientObject.getValue());
        return resultToUpdate;
    }

    @Override
    public String getServerResourceType() {
        return ProfileAttribute.class.getName();
    }

    @Override
    public String getClientResourceType() {
        return ClientUserAttribute.class.getName();
    }
}
