package com.jaspersoft.jasperserver.remote.resources.converters;

import com.jaspersoft.jasperserver.api.metadata.common.domain.InternalURI;
import com.jaspersoft.jasperserver.api.metadata.user.domain.ObjectPermission;
import com.jaspersoft.jasperserver.api.metadata.user.domain.ProfileAttribute;
import com.jaspersoft.jasperserver.api.metadata.user.domain.User;
import com.jaspersoft.jasperserver.api.metadata.user.domain.client.ObjectPermissionImpl;
import com.jaspersoft.jasperserver.api.metadata.user.domain.client.ProfileAttributeImpl;
import com.jaspersoft.jasperserver.api.metadata.user.domain.client.UserImpl;
import com.jaspersoft.jasperserver.api.metadata.user.service.ProfileAttributeService;
import com.jaspersoft.jasperserver.api.metadata.user.service.impl.AttributePathTransformer;
import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.remote.helpers.AttributesConfig;
import com.jaspersoft.jasperserver.remote.helpers.RecipientIdentityResolver;
import com.jaspersoft.jasperserver.remote.services.PermissionsService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * <p></p>
 *
 * @author Zakhar.Tomchenco
 * @author Volodya Sabadosh
 * @version $Id: UserAttributesConverterTest.java 54590 2015-04-22 17:55:42Z vzavadsk $
 */
public class UserAttributesConverterTest {
    private final ProfileAttribute server = new ProfileAttributeImpl();
    private final ClientUserAttribute client = new ClientUserAttribute();
    private final String holderUri = "/organizations/organization_1/users/joeuser";
    private final String holderId = "user:/organization_1/joeuser";
    private final User principal = new UserImpl();

    private UserAttributesConverter converter;
    @Mock
    private PermissionsService attributesPermissionService;
    @Mock
    protected RecipientIdentityResolver recipientIdentityResolver;
    @Mock
    private AttributePathTransformer attributePathTransformer;
    @Mock
    private AttributesConfig attributesConfig;
    @Mock
    private ProfileAttributeService profileAttributeService;

    private final ObjectPermission permission = new ObjectPermissionImpl();

    @BeforeMethod
    public void setUp() throws Exception {
        principal.setUsername("joeuser");
        principal.setTenantId("organization_1");

        converter = new UserAttributesConverter();
        permission.setPermissionMask(1);
        String attributePath = "/attributes/attr1";

        MockitoAnnotations.initMocks(this);
        converter.setProfileAttributeService(profileAttributeService);
        converter.setAttributesPermissionService(attributesPermissionService);
        converter.setRecipientIdentityResolver(recipientIdentityResolver);
        converter.setAttributePathTransformer(attributePathTransformer);
        converter.setAttributesConfig(attributesConfig);

        when(attributesConfig.getMaxLengthAttrName()).thenReturn(255);
        when(attributesConfig.getMaxLengthAttrValue()).thenReturn(2000);
        when(attributesConfig.getMaxLengthDescription()).thenReturn(255);

        when(attributePathTransformer.transformPath(any(String.class), any(Authentication.class))).thenReturn(attributePath);
                when(attributesPermissionService.getEffectivePermission(any(InternalURI.class), any(Authentication.class))).thenReturn(permission);

        when(recipientIdentityResolver.toRecipientUri(principal)).thenReturn("tenant:/organization_1");
        when(recipientIdentityResolver.resolveRecipientObject(holderId)).thenReturn(principal);

        when(profileAttributeService.generateAttributeHolderUri(principal)).thenReturn(holderUri);

        server.setAttrName("sname");
        server.setAttrValue("scalue");
        server.setPrincipal(principal);

        client.setName("cname");
        client.setValue("cvallue");
        client.setHolder(holderId);
    }

    @Test
    public void testToClient() throws Exception {
        ClientUserAttribute converted = converter.toClient(server, null);

        assertEquals(converted.getName(), server.getAttrName());
        assertEquals(converted.getValue(), server.getAttrValue());
        assertTrue(converted.getPermissionMask() == 1);
    }

    @Test
    public void testToServer() throws Exception {
        ProfileAttribute converted = converter.toServer(client, null);

        assertEquals(converted.getAttrName(), client.getName());
        assertEquals(converted.getAttrValue(), client.getValue());
        assertTrue(converted.getUri().startsWith(holderUri));
    }

    @Test
    public void testToServer_update() throws Exception {
        ProfileAttribute converted = converter.toServer(client, server, null);

        assertEquals(converted.getAttrName(), client.getName());
        assertEquals(converted.getAttrValue(), client.getValue());
        assertTrue(converted.getUri().startsWith(holderUri));
    }
}
