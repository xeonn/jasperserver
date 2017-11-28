package com.jaspersoft.jasperserver.remote.services.impl;

import com.jaspersoft.jasperserver.api.metadata.user.domain.ProfileAttribute;
import com.jaspersoft.jasperserver.api.metadata.user.domain.User;
import com.jaspersoft.jasperserver.api.metadata.user.domain.client.ProfileAttributeImpl;
import com.jaspersoft.jasperserver.api.metadata.user.service.ProfileAttributeService;
import com.jaspersoft.jasperserver.api.metadata.user.service.UserAuthorityService;
import com.jaspersoft.jasperserver.remote.ServiceException;
import com.jaspersoft.jasperserver.remote.exception.IllegalParameterValueException;
import com.jaspersoft.jasperserver.remote.services.AttributesRemoteService;
import com.jaspersoft.jasperserver.remote.services.GenericAttributesService;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Zakhar.Tomchenco
 */
@Component("userAttributesService")
public class UserAttributesServiceImpl implements GenericAttributesService<User> {

    @Resource(name = "attributesRemoteService")
    private AttributesRemoteService service;

    public void deleteAttribute(User principal, ProfileAttribute pa) {
        service.deleteAttribute(principal.getUsername(), pa);
    }

    public List<ProfileAttribute> getAttributes(User principal) throws ServiceException {
        return service.getAttributesOfUser(principal.getUsername());
    }

    public ProfileAttribute getAttribute(User principal, String name) throws ServiceException {
        List<ProfileAttribute> attributes = getAttributes(principal);
        for (ProfileAttribute p : attributes){
            if (p.getAttrName().equals(name)) return p;
        }
        return null;
    }

    public void putAttribute(User principal, ProfileAttribute pa) throws IllegalParameterValueException{
        try{
            service.putAttribute(principal.getUsername(), pa);
        } catch (DataIntegrityViolationException dive){
            throw new IllegalParameterValueException("value", pa.getAttrValue());
        }
    }
}
