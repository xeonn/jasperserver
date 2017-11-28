package com.jaspersoft.jasperserver.remote.resources.converters;

import com.jaspersoft.jasperserver.api.metadata.user.domain.Role;
import com.jaspersoft.jasperserver.api.metadata.user.domain.User;
import com.jaspersoft.jasperserver.api.metadata.user.domain.client.UserImpl;
import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.remote.exception.IllegalParameterValueException;
import com.jaspersoft.jasperserver.remote.exception.MandatoryParameterNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <p></p>
 *
 * @author Zakhar.Tomchenco
 * @version $Id$
 */
@Service
public class UserConverter implements ToServerConverter<ClientUser, User>, ToClientConverter<User, ClientUser> {

    @Resource
    private RoleConverter roleConverter;

    @Override
    public ClientUser toClient(User user, ToClientConversionOptions options) {
        ClientUser clientUser = new ClientUser();

        clientUser.setFullName(user.getFullName());
        clientUser.setEmailAddress(user.getEmailAddress());
        clientUser.setExternallyDefined(user.isExternallyDefined());
        clientUser.setEnabled(user.isEnabled());
        clientUser.setPreviousPasswordChangeTime(user.getPreviousPasswordChangeTime());
        clientUser.setTenantId(user.getTenantId());
        clientUser.setUsername(user.getUsername());

        if (user.getRoles() != null){
            Set<ClientRole> roleSet = new HashSet<ClientRole>();
            // core class uses raw Set. Cast is safe
            @SuppressWarnings("unchecked")
            final Set<Role> roles = (Set<Role>) user.getRoles();
            for (Role role: roles){
                roleSet.add(roleConverter.toClient(role, null));
            }
            clientUser.setRoleSet(roleSet);
        }
        return clientUser;
    }

    @Override
    public User toServer(ClientUser clientObject, User user, ToServerConversionOptions options) throws IllegalParameterValueException, MandatoryParameterNotFoundException {
        if (clientObject.getTenantId() != null) user.setTenantId(clientObject.getTenantId());
        if (clientObject.getUsername() != null) user.setUsername(clientObject.getUsername());
        if (clientObject.getEmailAddress() != null) user.setEmailAddress(clientObject.getEmailAddress());
        if (clientObject.getFullName() != null) user.setFullName(clientObject.getFullName());
        if (clientObject.isEnabled() != null) user.setEnabled(clientObject.isEnabled());
        if (clientObject.isExternallyDefined() != null) user.setExternallyDefined(clientObject.isExternallyDefined());
        if (clientObject.getPassword() != null) {
            if (user.getPassword()!= null && !clientObject.getPassword().equals(user.getPassword())){
                user.setPreviousPasswordChangeTime(new Date());
            }
            user.setPassword(clientObject.getPassword());
        }
        if (clientObject.getRoleSet() != null) {
            Set<Role> newRoles = new HashSet<Role>();
            for (ClientRole role: clientObject.getRoleSet()){
                newRoles.add(roleConverter.toServer(role, null));
            }
            user.setRoles(newRoles);
        }

        return user;
    }

    @Override
    public User toServer(ClientUser clientObject, ToServerConversionOptions options) throws IllegalParameterValueException, MandatoryParameterNotFoundException {
        return toServer(clientObject, new UserImpl(), null);
    }

    @Override
    public String getServerResourceType() {
        return User.class.getName();
    }

    @Override
    public String getClientResourceType() {
        return ClientUser.class.getName();
    }
}
