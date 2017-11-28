package com.jaspersoft.jasperserver.remote.services.impl;

import com.jaspersoft.jasperserver.api.common.domain.ExecutionContext;
import com.jaspersoft.jasperserver.api.metadata.user.domain.ProfileAttribute;
import com.jaspersoft.jasperserver.api.metadata.user.domain.User;
import com.jaspersoft.jasperserver.api.metadata.user.domain.client.ProfileAttributeImpl;
import com.jaspersoft.jasperserver.api.metadata.user.domain.client.UserImpl;
import com.jaspersoft.jasperserver.api.metadata.user.service.AttributesSearchCriteria;
import com.jaspersoft.jasperserver.api.metadata.user.service.AttributesSearchResult;
import com.jaspersoft.jasperserver.api.metadata.user.service.ProfileAttributeService;
import com.jaspersoft.jasperserver.api.metadata.user.service.impl.AttributesSearchResultImpl;
import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.remote.helpers.RecipientIdentity;
import com.jaspersoft.jasperserver.remote.helpers.RecipientIdentityResolver;
import com.jaspersoft.jasperserver.remote.resources.converters.PermissionConverter;
import com.jaspersoft.jasperserver.remote.resources.converters.UserAttributesConverter;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * An unit-test for the AttributesServiceImpl class
 *
 * @author askorodumov
 * @version $Id: AttributesServiceImplTest.java 54590 2015-04-22 17:55:42Z vzavadsk $
 */
public class AttributesServiceImplTest {
    private static final String ANONYMOUS_USER_URI = "user:/anonymousUser";

    private final AttributesServiceImpl service = new AttributesServiceImpl();

    @Mock
    ProfileAttributeService profileAttributeService;
    @Mock
    RecipientIdentityResolver recipientIdentityResolver;
    @Mock
    UserAttributesConverter attributesConverter;
    @Mock
    AttributesPermissionServiceImpI attributesPermissionService;
    @Mock
    PermissionConverter permissionConverter;

    private final User anonymousUser = new UserImpl();

    private final ProfileAttribute attributeA = new ProfileAttributeImpl();
    private final ProfileAttribute attributeB = new ProfileAttributeImpl();

    private final ClientUserAttribute clientUserAttributeA = new ClientUserAttribute();
    private final ClientUserAttribute clientUserAttributeB = new ClientUserAttribute();

    private final RecipientIdentity recipientIdentityAnonymousUser = new RecipientIdentity(User.class, "anonymousUser");

    @BeforeClass
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);

        anonymousUser.setUsername("anonymousUser");

        attributeA.setAttrName("A");
        attributeA.setAttrValue("AAA");

        clientUserAttributeA.setName(attributeA.getAttrName());
        clientUserAttributeA.setValue(attributeA.getAttrValue());

        attributeB.setAttrName("B");
        attributeB.setAttrValue("BBB");

        clientUserAttributeB.setName(attributeB.getAttrName());
        clientUserAttributeB.setValue(attributeB.getAttrValue());

        // Mockito stubbing
        when(recipientIdentityResolver.resolveRecipientObject(recipientIdentityAnonymousUser))
                .thenReturn(anonymousUser);
        when(recipientIdentityResolver.toRecipientUri(recipientIdentityAnonymousUser))
                .thenReturn(ANONYMOUS_USER_URI);
        when(recipientIdentityResolver.resolveRecipientObject(ANONYMOUS_USER_URI))
                .thenReturn(anonymousUser);

        when(profileAttributeService.getProfileAttributesForPrincipal(
                any(ExecutionContext.class),
                eq(anonymousUser),
                eq(new AttributesSearchCriteria.Builder().setNames(Collections.singleton(attributeB.getAttrName())).build())
        )).thenReturn(new AttributesSearchResultImpl<ProfileAttribute>());

        AttributesSearchResult<ProfileAttribute> searchResult = new AttributesSearchResultImpl<ProfileAttribute>();
        searchResult.setList(Arrays.asList(attributeA, attributeB));
        searchResult.setTotalCount(2);
        when(profileAttributeService.getProfileAttributesForPrincipal(
                any(ExecutionContext.class),
                eq(anonymousUser),
                eq(new AttributesSearchCriteria.Builder().setHolder(ANONYMOUS_USER_URI).build())
        )).thenReturn(searchResult);
        when(profileAttributeService.getProfileAttributesForPrincipal(
                any(ExecutionContext.class),
                eq(anonymousUser),
                eq(new AttributesSearchCriteria.Builder().setNames(Collections.<String>emptySet()).build())
        )).thenReturn(searchResult);
        when(profileAttributeService.getProfileAttributesForPrincipal(
                any(ExecutionContext.class),
                eq(anonymousUser)
        )).thenReturn(Arrays.asList(attributeA, attributeB));

        when(attributesConverter.toServer(clientUserAttributeB, null))
                .thenReturn(attributeB);
        when(attributesConverter.toClient(attributeA, null))
                .thenReturn(clientUserAttributeA);
        when(attributesConverter.toClient(attributeB, null))
                .thenReturn(clientUserAttributeB);

        // set mockito beans to the service
        service.profileAttributeService = profileAttributeService;
        service.recipientIdentityResolver = recipientIdentityResolver;
        service.attributesPermissionService = attributesPermissionService;
        service.permissionConverter = permissionConverter;

        // Use reflection for setting private field "attributesConverter"
        Field fieldAttributesConverter = service.getClass().getDeclaredField("attributesConverter");
        fieldAttributesConverter.setAccessible(true);
        fieldAttributesConverter.set(service, attributesConverter);
    }

    @Test
    public void getAttributes_recipientIdentityAnonymousUser_inSetB_false_successNotFound() throws Exception {
        List<ClientUserAttribute> list
                = service.getAttributes(recipientIdentityAnonymousUser, Collections.singleton("B"), false);
        assertNotNull("Result should not be null", list);
        assertTrue("Size of the result should be zero", list.size() == 0);
    }

    @Test
    public void getAttributes_recipientIdentityAnonymousUser_emptySet_false_successFound() throws Exception {
        List<ClientUserAttribute> list
                = service.getAttributes(recipientIdentityAnonymousUser, Collections.<String>emptySet(), false);
        assertNotNull("Result should not be null", list);
        assertTrue("Size of the result should be two", list.size() == 2);
    }

    @Test
    public void getAttributes_searchCriteriaWithHolderAnonymousUser_false_success() throws Exception {
        AttributesSearchResult<ClientUserAttribute> searchResult = service.getAttributes(
                new AttributesSearchCriteria.Builder().setHolder(ANONYMOUS_USER_URI).build(),
                false);
        assertNotNull("Result should not be null", searchResult);
        assertNotNull("The list in the result should not be null", searchResult.getList());
        assertTrue("Size of the result should be two", searchResult.getList().size() == 2);
    }

    @Test
    public void putAttributes_recipientIdentityAnonymousUser_attributeBList_BSet_false_success() throws Exception {
        List<ClientUserAttribute> list = service.putAttributes(
                recipientIdentityAnonymousUser,
                Collections.singletonList(clientUserAttributeB),
                Collections.singleton("B"),
                false);
        assertNotNull("Result should not be null", list);
        assertTrue("Size of the result should be one", list.size() == 1);
        assertEquals("Attribute name does not match the original", list.get(0).getName(), clientUserAttributeB.getName());
    }

    @Test
    public void deleteAttribute_recipientIdentityAnonymousUser_B_success() throws Exception {
        AttributesSearchResult<ProfileAttribute> searchResult = new AttributesSearchResultImpl<ProfileAttribute>();
        ProfileAttribute profileAttribute = new ProfileAttributeImpl();
        profileAttribute.setAttrName("B");
        searchResult.setList(Collections.singletonList(profileAttribute));

        when(profileAttributeService.getProfileAttributesForPrincipal((ExecutionContext) isNull(),
                eq(anonymousUser),
                eq(new AttributesSearchCriteria.Builder().setNames(Collections.singleton("B")).setSkipServerSettings(true).build())
        )).thenReturn(searchResult);

        service.deleteAttributes(recipientIdentityAnonymousUser, Collections.singleton("B"));

        verify(profileAttributeService, times(1)).deleteProfileAttribute(null, profileAttribute);
    }
}
