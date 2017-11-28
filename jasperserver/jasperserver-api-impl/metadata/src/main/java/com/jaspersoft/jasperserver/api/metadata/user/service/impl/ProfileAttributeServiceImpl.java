/*
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.jasperserver.api.metadata.user.service.impl;

import com.jaspersoft.jasperserver.api.JSException;
import com.jaspersoft.jasperserver.api.common.domain.ExecutionContext;
import com.jaspersoft.jasperserver.api.common.properties.PropertiesManagementService;
import com.jaspersoft.jasperserver.api.metadata.common.domain.Resource;
import com.jaspersoft.jasperserver.api.metadata.common.domain.impl.IdedObject;
import com.jaspersoft.jasperserver.api.metadata.common.service.ResourceFactory;
import com.jaspersoft.jasperserver.api.metadata.common.service.impl.HibernateDaoImpl;
import com.jaspersoft.jasperserver.api.common.crypto.PasswordCipherer;
import com.jaspersoft.jasperserver.api.metadata.common.service.impl.hibernate.HibernateRepositoryService;
import com.jaspersoft.jasperserver.api.metadata.common.service.impl.hibernate.PersistentObjectResolver;
import com.jaspersoft.jasperserver.api.metadata.common.service.impl.hibernate.persistent.RepoResource;
import com.jaspersoft.jasperserver.api.metadata.tenant.service.TenantPersistenceResolver;
import com.jaspersoft.jasperserver.api.metadata.user.domain.ProfileAttribute;
import com.jaspersoft.jasperserver.api.metadata.user.domain.Role;
import com.jaspersoft.jasperserver.api.metadata.user.domain.Tenant;
import com.jaspersoft.jasperserver.api.metadata.user.domain.User;
import com.jaspersoft.jasperserver.api.metadata.user.domain.impl.client.MetadataUserDetails;
import com.jaspersoft.jasperserver.api.metadata.user.domain.impl.hibernate.RepoProfileAttribute;
import com.jaspersoft.jasperserver.api.metadata.user.service.ProfileAttributeService;
import com.jaspersoft.jasperserver.api.metadata.user.service.TenantService;
import com.jaspersoft.jasperserver.api.metadata.user.service.UserAuthorityService;
import com.jaspersoft.jasperserver.api.metadata.user.domain.impl.hibernate.RepoTenant;
import com.jaspersoft.jasperserver.api.metadata.user.domain.impl.hibernate.RepoUser;
import com.jaspersoft.jasperserver.api.metadata.user.service.ProfileAttributeCategory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Manages attributes for principals - Users, Roles.
 *
 * @author sbirney
 * @version $Id: ProfileAttributeServiceImpl.java 51947 2014-12-11 14:38:38Z ogavavka $
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProfileAttributeServiceImpl extends HibernateDaoImpl
        implements ProfileAttributeService, PersistentObjectResolver {

    protected static final Log log = LogFactory.getLog(ProfileAttributeServiceImpl.class);

    private HibernateRepositoryService repoService;
    private UserAuthorityService userService;
    private TenantPersistenceResolver tenantPersistenceResolver;
    private List<ProfileAttributeCategory> profileAttributeCategories;

    private ResourceFactory objectFactory;
    private ResourceFactory persistentClassFactory;
    private PropertiesManagementService propertiesManagementService;

    /**
     * @return Returns the repoService.
     */
    public HibernateRepositoryService getRepositoryService() {
        return repoService;
    }

    /**
     * @param repoService The repoService to set.
     */
    public void setRepositoryService(HibernateRepositoryService repoService) {
        this.repoService = repoService;
    }

    /**
     * @return Returns the userService.
     */
    public UserAuthorityService getUserAuthorityService() {
        return userService;
    }

    /**
     * @param userService The userService to set.
     */
    public void setUserAuthorityService(UserAuthorityService userService) {
        this.userService = userService;
    }

    /**
     * @return Returns the propertiesManagement service
     */
    public PropertiesManagementService getPropertiesManagementService() {
        return propertiesManagementService;
    }

    /**
     * @param propertiesManagementService The propertiesManagement service to set
     */
    public void setPropertiesManagementService(PropertiesManagementService propertiesManagementService) {
        this.propertiesManagementService = propertiesManagementService;
    }

    /**
     * @return The tenant persistence resolver service
     */
    public TenantPersistenceResolver getTenantPersistenceResolver() {
        return tenantPersistenceResolver;
    }

    /**
     * @param tenantPersistenceResolver The tenant persistence resolver service
     */
    public void setTenantPersistenceResolver(TenantPersistenceResolver tenantPersistenceResolver) {
        this.tenantPersistenceResolver = tenantPersistenceResolver;
    }

    /**
     * @param profileAttributeCategories An ordered List of profile attribute categories to set
     */
    public void setProfileAttributeCategories(List<ProfileAttributeCategory> profileAttributeCategories) {
        this.profileAttributeCategories = profileAttributeCategories;
    }

    public ResourceFactory getObjectMappingFactory() {
        return objectFactory;
    }

    public void setObjectMappingFactory(ResourceFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    public ResourceFactory getPersistentClassFactory() {
        return persistentClassFactory;
    }

    public void setPersistentClassFactory(ResourceFactory persistentClassFactory) {
        this.persistentClassFactory = persistentClassFactory;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProfileAttribute newProfileAttribute(ExecutionContext context) {
        return (ProfileAttribute) getObjectMappingFactory().newObject(ProfileAttribute.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void putProfileAttribute(ExecutionContext context, ProfileAttribute attr) {
        if (isServerPrincipal(attr.getPrincipal())) {
            String attrValue = attr.getAttrValue();
            if (attr.isSecure()) {
                attrValue = PasswordCipherer.getInstance().encryptSecureAttribute(attrValue);
            }
            propertiesManagementService.setProperty(attr.getAttrName(), attrValue);
        } else {
            RepoProfileAttribute existingAttr = getRepoProfileAttribute(attr);
            if (existingAttr == null) {
                existingAttr = (RepoProfileAttribute) getPersistentClassFactory().newObject(ProfileAttribute.class);
            }
            existingAttr.copyFromClient(attr, this);
            getHibernateTemplate().saveOrUpdate(existingAttr);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteProfileAttribute(ExecutionContext context, ProfileAttribute attr) {
        if (isServerPrincipal(attr.getPrincipal())) {
            propertiesManagementService.remove(attr.getAttrName());
        } else {
            RepoProfileAttribute existingAttr = getRepoProfileAttribute(attr);
            getHibernateTemplate().delete(existingAttr);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteProfileAttributes(Collection<ProfileAttribute> attributesToDelete) {
        Map<String, ProfileAttribute> paMapToDel = new HashMap<String, ProfileAttribute>(attributesToDelete.size());
        for (ProfileAttribute pa : attributesToDelete)
            paMapToDel.put(pa.getAttrName(), pa);
        deleteProfileAttributes(paMapToDel);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteProfileAttributes(Map<String, ProfileAttribute> attributeMapToDelete) {
        List<RepoProfileAttribute> paListToDelete = new ArrayList<RepoProfileAttribute>();
        List<RepoProfileAttribute> paList = getRepoProfileAttributes(getCurrentUserDetails());
        for (RepoProfileAttribute pa : paList) {
            if (attributeMapToDelete.containsKey(pa.getAttrName()))
                paListToDelete.add(pa);
        }
        getHibernateTemplate().deleteAll(paListToDelete);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProfileAttribute getProfileAttribute(ExecutionContext context, ProfileAttribute attr) {
        // Given the object and the recipient, find the permission
        RepoProfileAttribute existingPerm = getRepoProfileAttribute(attr);
        if (existingPerm == null) {
            return null;
        } else {
            return (ProfileAttribute) existingPerm.toClient(getObjectMappingFactory());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED)
    public List<ProfileAttribute> getCurrentUserProfileAttributes(ProfileAttributeCategory category) {
        Object principal;
        boolean effectiveAttributes = false;

        if (category != null && category != ProfileAttributeCategory.HIERARCHICAL) {
            if (!profileAttributeCategories.contains(category)) {
                throw new IllegalArgumentException("Provided \"" + category + "\" category is not supported");
            }
            principal = category.getPrincipal(getObjectMappingFactory());
        } else {
            principal = ProfileAttributeCategory.USER.getPrincipal(getObjectMappingFactory());
            effectiveAttributes = true;
        }

        if (principal != null) {
            return getProfileAttributesForPrincipal(null, principal, effectiveAttributes);
        }

        return new ArrayList<ProfileAttribute>();
    }

    private RepoProfileAttribute getRepoProfileAttribute(ProfileAttribute attr) {
        List objList = getRepoProfileAttributes(attr.getPrincipal(), new String[]{attr.getAttrName()});
        return (objList.size() == 0) ? null : (RepoProfileAttribute) objList.get(0);
    }

    private Class<?> getObjectClass(Class<?> classObj) {
        if (classObj.toString().indexOf("_$$_") > 0) return classObj.getSuperclass();
        return classObj;
    }

    private List<RepoProfileAttribute> getRepoProfileAttributes(final Class<?> principalClass,
                                                                final List<Long> principalIds,
                                                                final String[] attrNames) {
        final String idKey = "principalId";
        final String classKey = "principalClass";
        final String attrNameKey = "principalAttrName";
        final String queryName = (attrNames == null) ? "JIProfileAttributeFindByClassAndIds" : "JIProfileAttributeFindByClassAndIdsAndNames";

        List objList = getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.getNamedQuery(queryName);
                query.setParameterList(idKey, principalIds, Hibernate.LONG);
                query.setParameter(classKey, getObjectClass(principalClass), Hibernate.CLASS);

                if (attrNames != null) {
                    query.setParameterList(attrNameKey, attrNames, Hibernate.STRING);
                }

                return query.list();
            }
        });

        return objList;
    }

    /**
     * Fetches from database and returns the list of repo profile attributes entities.
     *
     * @param principal The principalObject object used to search attributes by its "class" and "id".
     * @param attrNames The attributes names.
     * @return The list of repo profile attributes.
     */
    private List<RepoProfileAttribute> getRepoProfileAttributes(Object principal, String[] attrNames) {
        final IdedObject principalObject = (IdedObject) getPersistentObject(principal);

        if (principalObject == null) {
            throw new JSException("jsexception.no.principal");
        }

        List<Long> principalId = new ArrayList<Long>() {{
            add(principalObject.getId());
        }};
        Class<?> principalClass = principalObject.getClass();
        return getRepoProfileAttributes(principalClass, principalId, attrNames);
    }

    /**
     * Fetches from database and returns the list of repo profile attributes entities.
     *
     * @param principal The principalObject object used to search attributes by its "class" and "id"
     * @return The list of repo profile attributes.
     * @return The list of repo profile attributes.
     */
    protected List<RepoProfileAttribute> getRepoProfileAttributes(Object principal) {
        return getRepoProfileAttributes(principal, null);
    }

    protected List<ProfileAttribute> getClientProfileAttributes(Object principal, boolean effectiveAttributes) {
        List<ProfileAttribute> profileAttributes = new ArrayList<ProfileAttribute>();
        boolean isServerPrincipal = isServerPrincipal(principal);

        if (!isServerPrincipal) {
            // Given the principalObject to find the attributes
            final IdedObject principalObject = (IdedObject) getPersistentObject(principal);
            List<RepoProfileAttribute> repoProfileAttributes = getRepoProfileAttributes(principalObject);

            if (effectiveAttributes) {
                repoProfileAttributes.addAll(getInheritedRepoProfileAttributes(principalObject));
            }

            // Convert Repo to Client profile attributes
            profileAttributes.addAll(makeProfileAttributeClientList(repoProfileAttributes, principalObject));
        }

        if (isServerPrincipal || effectiveAttributes) {
            profileAttributes.addAll(getServerProfileAttributes());
        }

        // Remove all profile attributes that have the same name, but locates on higher level.
        // E.g. if User have attribute with the name "attr1", and his parent Tenant also have attribute with "attr1" name
        // then tenant's attribute will be removed
        return overrideProfileAttributesHierarchically(profileAttributes);
    }

    private List<ProfileAttribute> overrideProfileAttributesHierarchically(List<ProfileAttribute> orderedProfileAttributes) {
        Map<String, ProfileAttribute> profileAttributeMap = new LinkedHashMap<String, ProfileAttribute>();
        for (ProfileAttribute profileAttribute : orderedProfileAttributes) {
            if (!profileAttributeMap.containsKey(profileAttribute.getAttrName())) {
                profileAttributeMap.put(profileAttribute.getAttrName(), profileAttribute);
            }
        }
        return new ArrayList<ProfileAttribute>(profileAttributeMap.values());
    }

    protected List<RepoProfileAttribute> getInheritedRepoProfileAttributes(IdedObject principalObject) {
        List<RepoProfileAttribute> inheritedAttributes = new ArrayList<RepoProfileAttribute>();
        // Get all parent Tenant ids (e.g. org_1, org_2, ...)
        List<String> parentTenantIds = getAllParentTenantIds(principalObject);
        // Get all parent RepoTenant database ids
        List<Long> parentsIds = getAllParentIds(parentTenantIds);

        if (parentsIds != null && parentsIds.size() > 0) {
            inheritedAttributes = getRepoProfileAttributes(RepoTenant.class, parentsIds, null);
            // Sort profile attributes by tenantUri
            sortRepoProfileAttributes(inheritedAttributes);
        }

        return inheritedAttributes;
    }

    private List<String> getAllParentTenantIds(Object principalObject) {
        if (principalObject instanceof RepoUser || principalObject instanceof RepoTenant) {
            RepoTenant repoTenant;
            boolean isPrincipalInstanceOfRepoTenant = false;

            if (principalObject instanceof RepoUser) {
                repoTenant = ((RepoUser) principalObject).getTenant();
            } else {
                isPrincipalInstanceOfRepoTenant = true;
                repoTenant = (RepoTenant) principalObject;
            }

            // Remove 1-st slash
            String tenantUri = repoTenant.getTenantUri().substring(1);

            if (isPrincipalInstanceOfRepoTenant) {
                // Remove this principal
                int indexOfTenantId = tenantUri.lastIndexOf("/" + repoTenant.getTenantId());
                if (indexOfTenantId != -1) {
                    tenantUri = tenantUri.substring(0, indexOfTenantId);
                }
            }

            return Arrays.asList(tenantUri.split("/"));
        }

        return null;
    }

    private List<Long> getAllParentIds(List<String> tenantIds) {
        List<RepoTenant> tenants = getTenantPersistenceResolver().getPersistentTenants(tenantIds);
        List<Long> parentIds = new ArrayList<Long>();

        if (tenants != null) {
            for (RepoTenant tenant : tenants) {
                parentIds.add(tenant.getId());
            }
        }

        return parentIds;
    }

    protected List<ProfileAttribute> getServerProfileAttributes() {
        List<ProfileAttribute> resultList = new ArrayList<ProfileAttribute>();
        for (Map.Entry<String, String> entry : propertiesManagementService.getProperties().entrySet()) {
            ProfileAttribute clientProfileAttr = (ProfileAttribute) getObjectMappingFactory().newObject(ProfileAttribute.class);
            clientProfileAttr.setAttrName(entry.getKey());
            String value = entry.getValue();
            if (PasswordCipherer.getInstance().isEncrypted(value)) {
                value = PasswordCipherer.getInstance().decryptSecureAttribute(value);
                clientProfileAttr.setSecure(true);
            }
            clientProfileAttr.setAttrValue(value);
            clientProfileAttr.setCategory(ProfileAttributeCategory.SERVER);

            resultList.add(clientProfileAttr);
        }

        return resultList;
    }

    private boolean isServerPrincipal(Object principal) {
        return (principal instanceof Tenant &&
                (((Tenant) principal).getId() == null || ((Tenant) principal).getId().equals(TenantService.ORGANIZATIONS)));
    }

    private void sortRepoProfileAttributes(List<RepoProfileAttribute> profileAttributes) {
        Collections.sort(profileAttributes, new Comparator<RepoProfileAttribute>() {
            @Override
            public int compare(RepoProfileAttribute attr1, RepoProfileAttribute attr2) {
                String tenantUri1 = ((RepoTenant) attr1.getPrincipal()).getTenantUri();
                String tenantUri2 = ((RepoTenant) attr2.getPrincipal()).getTenantUri();
                // Sort by tenantUri length. First should go child tenant because parent tenant
                // will also have lesser Uri
                return Integer.valueOf(tenantUri2.length()).compareTo(tenantUri1.length());
            }
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List getProfileAttributesForPrincipal(ExecutionContext context, Object principal) {
        return getClientProfileAttributes(principal, false);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List getProfileAttributesForPrincipal(ExecutionContext context, Object principal, boolean effectiveAttributes) {
        return getClientProfileAttributes(principal, effectiveAttributes);
    }

    public List getProfileAttributesForPrincipal(ExecutionContext context) {
        User user = getCurrentUserDetails();
        return getProfileAttributesForPrincipal(context, user);
    }

    public List getProfileAttributesForPrincipal() {
        return getProfileAttributesForPrincipal(null);
    }

    // basePrincipal is needed to find the category of the attribute
    protected List<ProfileAttribute> makeProfileAttributeClientList(List<RepoProfileAttribute> profileAttributes,
                                                                  Object basePrincipal) {
        List<ProfileAttribute> clientProfileAttributes = new ArrayList<ProfileAttribute>();

        for (RepoProfileAttribute profileAttribute : profileAttributes) {
            ProfileAttribute clientProfileAttribute = (ProfileAttribute) profileAttribute.toClient(getObjectMappingFactory());
            ProfileAttributeCategory category = null;
            Object profileAttributePrincipal = profileAttribute.getPrincipal();

            // TODO: improve
            if (profileAttributePrincipal.equals(basePrincipal)) {
                if (profileAttributePrincipal instanceof RepoUser) {
                    category = ProfileAttributeCategory.USER;
                } else {
                    category = ProfileAttributeCategory.TENANT;
                }
            } else if (basePrincipal instanceof RepoUser) {
                Object parentPrincipal = ((RepoUser) basePrincipal).getTenant();
                if (profileAttributePrincipal.equals(parentPrincipal)) {
                    category = ProfileAttributeCategory.TENANT;
                }
            }

            clientProfileAttribute.setCategory(category);
            clientProfileAttributes.add(clientProfileAttribute);
        }

        return clientProfileAttributes;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Object getPersistentObject(Object clientObject) {
        // If already persisted, just return it
        if (clientObject == null) {
            return null;
        } else if (clientObject instanceof IdedObject) {
            return clientObject;
        } else if (clientObject instanceof Role || clientObject instanceof User || clientObject instanceof Tenant) {
            return ((PersistentObjectResolver) userService).getPersistentObject(clientObject);
        } else if (clientObject instanceof Resource) {
            //TODO Hack! Make it an interface!
            String uri = ((Resource) clientObject).getPath();
            return repoService.findByURI(RepoResource.class, uri, false);
        } else if (clientObject instanceof ProfileAttribute) {
            return getRepoProfileAttribute((ProfileAttribute) clientObject);
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String getCurrentUserPreferenceValue(String attrName) {
        String attrValue = null;
        User user = getCurrentUserDetails();
        ProfileAttribute attr = newProfileAttribute(null);
        attr.setPrincipal(user);
        attr.setAttrName(attrName);
        ProfileAttribute savedAttr = getProfileAttribute(null, attr);
        if (savedAttr != null) {
            attrValue = savedAttr.getAttrValue();
        }
        return attrValue;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void setCurrentUserPreferenceValue(String attrName, String attrValue) {
        User user = getCurrentUserDetails();
        ProfileAttribute attr = newProfileAttribute(null);
        attr.setPrincipal(user);
        attr.setAttrName(attrName);
        ProfileAttribute savedAttr = getProfileAttribute(null, attr);
        if (savedAttr == null) {
            savedAttr = attr;
        }
        if (!equalsWithNull(savedAttr.getAttrValue(), attrValue)) {
            savedAttr.setAttrValue(attrValue);
            putProfileAttribute(null, savedAttr);
        }
    }

    public static boolean equalsWithNull(Object o1, Object o2) {
        return (o1 == null && o2 == null) || (o1 != null && o1.equals(o2));
    }

    public MetadataUserDetails getCurrentUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return (MetadataUserDetails) auth.getPrincipal();
        }
        return null;
    }
}
