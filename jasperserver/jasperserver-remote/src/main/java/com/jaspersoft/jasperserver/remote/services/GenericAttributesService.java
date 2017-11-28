package com.jaspersoft.jasperserver.remote.services;

import com.jaspersoft.jasperserver.api.metadata.user.domain.ProfileAttribute;
import com.jaspersoft.jasperserver.api.metadata.user.domain.User;
import com.jaspersoft.jasperserver.remote.ServiceException;
import com.jaspersoft.jasperserver.remote.exception.IllegalParameterValueException;

import java.util.List;

/**
 * @author: Zakhar.Tomchenco
 */
public interface GenericAttributesService<T> {

    public void deleteAttribute(T principal, ProfileAttribute pa);

    public List<ProfileAttribute> getAttributes(T principal) throws ServiceException;

    public ProfileAttribute getAttribute(T principal, String name) throws ServiceException;

    public void putAttribute(T principal, ProfileAttribute pa) throws IllegalParameterValueException;

}
