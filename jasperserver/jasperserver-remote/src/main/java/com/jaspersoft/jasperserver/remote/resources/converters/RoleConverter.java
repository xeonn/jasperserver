package com.jaspersoft.jasperserver.remote.resources.converters;

import com.jaspersoft.jasperserver.api.metadata.user.domain.Role;
import com.jaspersoft.jasperserver.api.metadata.user.domain.client.RoleImpl;
import com.jaspersoft.jasperserver.dto.authority.ClientRole;
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
public class RoleConverter implements ToServerConverter<ClientRole, Role>, ToClientConverter<Role, ClientRole> {
    @Override
    public ClientRole toClient(Role serverObject, ToClientConversionOptions options) {
        ClientRole clientRole = new ClientRole();

        clientRole.setName(serverObject.getRoleName());
        clientRole.setExternallyDefined(serverObject.isExternallyDefined());
        clientRole.setTenantId(serverObject.getTenantId());

        return clientRole;
    }

    @Override
    public String getClientResourceType() {
        return ClientRole.class.getName();
    }

    @Override
    public Role toServer(ClientRole clientObject, ToServerConversionOptions options) throws IllegalParameterValueException, MandatoryParameterNotFoundException {
        return toServer(clientObject, new RoleImpl(), null);
    }

    @Override
    public Role toServer(ClientRole clientObject, Role resultToUpdate, ToServerConversionOptions options) throws IllegalParameterValueException, MandatoryParameterNotFoundException {
        resultToUpdate.setRoleName(clientObject.getName());
        resultToUpdate.setTenantId(clientObject.getTenantId());
        resultToUpdate.setExternallyDefined(clientObject.isExternallyDefined());

        return resultToUpdate;
    }

    @Override
    public String getServerResourceType() {
        return Role.class.getName();
    }
}
