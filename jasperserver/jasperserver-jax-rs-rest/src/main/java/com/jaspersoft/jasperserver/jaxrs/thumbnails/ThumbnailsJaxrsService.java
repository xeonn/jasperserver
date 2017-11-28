package com.jaspersoft.jasperserver.jaxrs.thumbnails;

import com.jaspersoft.jasperserver.api.JSException;
import com.jaspersoft.jasperserver.api.metadata.common.domain.Folder;
import com.jaspersoft.jasperserver.api.metadata.common.service.impl.hibernate.HibernateRepositoryService;
import com.jaspersoft.jasperserver.api.metadata.common.service.impl.hibernate.ReportThumbnailService;
import com.jaspersoft.jasperserver.api.metadata.user.domain.User;
import com.jaspersoft.jasperserver.dto.thumbnails.ResourceThumbnail;
import com.jaspersoft.jasperserver.dto.thumbnails.ResourceThumbnailsListWrapper;
import com.jaspersoft.jasperserver.remote.exception.RemoteException;
import com.jaspersoft.jasperserver.remote.resources.converters.ResourceConverterProvider;
import com.jaspersoft.jasperserver.remote.services.SingleRepositoryService;
import com.jaspersoft.jasperserver.war.httpheaders.JRSExpiresHeader;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Providers;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * This service exposes thumbnail items from the DB via REST services
 *
 * @author Grant Bacon <gbacon@jaspersoft.com>
 * @version
 */
@Service
@Path("/thumbnails")
public class ThumbnailsJaxrsService {

    public static final String PATH_PARAM_URI = "uri";
    public static final String QUERY_PARAM_URI = "uri";
    public static final String PATH_PARAM_DEFAULT_ALLOWED = "defaultAllowed";

    @Resource(name = "${bean.thumbnailService}")
    private ReportThumbnailService thumbnailService;
    @Resource
    private HibernateRepositoryService hibernateRepositoryService;
    @Resource
    private SingleRepositoryService singleRepositoryService;
    @Resource
    private ResourceConverterProvider resourceConverterProvider;
    @Context
    private Providers providers;
    @Context
    private HttpHeaders httpHeaders;

    @Resource(name = "thumbnailsExpiresHeader")
    private JRSExpiresHeader expiresHeader;

    @Context
    javax.servlet.http.HttpServletResponse response;

    public ThumbnailsJaxrsService() {
    }

    /**
     * Obtain the thumbnail of one resource by URI in either binary or base64 encoded format
     *
     * @param uri PathParam: Location of the resource in the repository
     * @param accept HeaderParam: "image/png" or "text/plain" to choose between binary and base64, respectively
     * @return Response
     */
    @GET
    @Produces({MediaType.TEXT_PLAIN, "image/jpeg"})
    @Path("/{uri: .+}")
    public Response getThumbnail(
            @PathParam(ThumbnailsJaxrsService.PATH_PARAM_URI) String uri,
            @QueryParam(ThumbnailsJaxrsService.PATH_PARAM_DEFAULT_ALLOWED) Boolean defaultAllowed,
            @HeaderParam(HttpHeaders.ACCEPT) String accept) {

        if (defaultAllowed == null)
            defaultAllowed = false;
        Response.ResponseBuilder response = Response.ok();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.jaspersoft.jasperserver.api.metadata.common.domain.Resource resource;

        try{
            resource = singleRepositoryService.getResource(Folder.SEPARATOR + uri.replaceAll("/$", ""));
        } catch (AccessDeniedException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        ByteArrayInputStream thumbnailStream;

        if (resource == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            thumbnailStream = thumbnailService.getReportThumbnail(user, resource);
            if (thumbnailStream == null) {
                if (!defaultAllowed) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                } else {
                    thumbnailStream = thumbnailService.getDefaultThumbnail();

                    cacheDefaultThumbnail(response);
                }
            }
        }

        if (MediaType.TEXT_PLAIN.equals(accept) || accept == null) {
            response.entity(encodeByteArrayInputStreamBase64(thumbnailStream));
        } else {
            try {
                response.entity(IOUtils.toByteArray(thumbnailStream));
            } catch (IOException e) {
                throw new JSException("js.unexpected.io.error.occured");
            }
        }

        return response.build();
    }

    /**
     * we want to cache default thumbnails
     * @param responseBuilder
     */
    public void cacheDefaultThumbnail(Response.ResponseBuilder responseBuilder) {
        CacheControl storeCacheControl = new CacheControl();
        storeCacheControl.setMaxAge((int) this.expiresHeader.getMaxAge());

        this.response.setHeader(HttpHeaders.CACHE_CONTROL, ""); // Manually clean up previous value because jersey is adding new header
        responseBuilder.cacheControl(storeCacheControl).expires(this.expiresHeader.getDate());
    }

    /**
     * Obtain a list of ResourceThumbnail DTOs to be set as an entity in an HTTP response
     *
     * @param uris
     * @param authenticatedUser
     * @return Set<ResourceThumbnail>
     */
    protected List<ResourceThumbnail> getBatchThumbnails(final Set<String> uris, User authenticatedUser, boolean defaultAllowed)
        throws JSException
    {
        List<ResourceThumbnail> thumbnailEntities = new ArrayList<ResourceThumbnail>();

        for (String uri : uris) {
            ByteArrayInputStream thumbnailData;
            try {
                thumbnailData = thumbnailService.getReportThumbnail(authenticatedUser, uri);
            } catch (JSException e) {
                if (e.getMessage().contains("invalid.resource")) {
                    throw e;
                }
                continue;   // invalid user, remove from result set.
            }
            if (thumbnailData == null) {
                if (!defaultAllowed) {
                    thumbnailData = null;
                } else {
                    thumbnailData = thumbnailService.getDefaultThumbnail();
                }
            }
                ResourceThumbnail resThumbnail = new ResourceThumbnail();
                resThumbnail.setUri(uri);
                String encoded = (thumbnailData == null) ? "" : encodeByteArrayInputStreamBase64(thumbnailData);
                resThumbnail.setThumbnailData(encoded);
                thumbnailEntities.add(resThumbnail);
        }

        return thumbnailEntities;
    }

    /**
     * Get a collection of Thumbnail records for a provided list of URIs
     *
     * @param accept HeaderParam: "application/json" or "application/xml"
     * @param uris QueryParam: URIs to fetch thumbnails for
     * @return Response
     * @throws RemoteException
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getThumbnails(
            @HeaderParam(HttpHeaders.ACCEPT) String accept,
            @QueryParam(ThumbnailsJaxrsService.PATH_PARAM_DEFAULT_ALLOWED) Boolean defaultAllowed,
            @QueryParam(QUERY_PARAM_URI) List<String> uris) throws RemoteException
    {

        if (defaultAllowed == null)
            defaultAllowed = false;

        Set<String> uriSet = new HashSet<String>(uris);
        if (uriSet.isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).build();

        // Remove illegal URIs
        Iterator<String> uriIterator = uriSet.iterator();
        do {
            String uri = uriIterator.next();
            if (uri.isEmpty()) {
                uriIterator.remove();
                continue; // Don't try to match pattern if empty
            }
            if ((!Pattern.matches("^/[\\w\\._/]+", uri))) {
                // If any URI is not in the proper format, it is invalid.
                // Inform user of bad request, return early.
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

        } while (uriIterator.hasNext());

        if (uriSet.isEmpty()) return Response.status(Response.Status.BAD_REQUEST).build();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ResourceThumbnail> thumbnailEntities;
        try {
             thumbnailEntities = getBatchThumbnails(uriSet, user, defaultAllowed);
        } catch (JSException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (thumbnailEntities.isEmpty()) return Response.status(Response.Status.NO_CONTENT).build();
        ResourceThumbnailsListWrapper thumbnailsListWrapper = new ResourceThumbnailsListWrapper(thumbnailEntities);
        return Response.status(Response.Status.OK).entity(thumbnailsListWrapper).build();
    }

    /**
     * Get a collection of Thumbnail records for a provided list of URIs
     *
     * If a list of URIs exceeds the recommended 2000 character limit for a URL, this method is to be used. Additionally,
     * the header "Content-Type: application/x-www-form-urlencoded" must be set.
     *
     * @param accept HeaderParam: "application/json" or "application/xml"
     * @param uris QueryParam: URIs to fetch thumbnails for
     * @return Response
     * @throws RemoteException
     */
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getThumbnailsFormEncoded(
            @HeaderParam(HttpHeaders.ACCEPT) String accept,
            @QueryParam(ThumbnailsJaxrsService.PATH_PARAM_DEFAULT_ALLOWED) Boolean defaultAllowed,
            @FormParam(QUERY_PARAM_URI) List<String> uris) throws RemoteException
    {
        return getThumbnails(accept, defaultAllowed, uris);
    }

    /**
     * Encode a ByteArrayInputStream as a Base64 string
     * Useful to share resources over HTTP, and for embedding in websites using data protocol.
     *
     * @param stream Image stream
     * @return String
     */
    private String encodeByteArrayInputStreamBase64(ByteArrayInputStream stream)
    {
        String encodedThumbnail;
        if (stream == null) return null;
        try {
            byte[] byteArray = IOUtils.toByteArray(stream);
            encodedThumbnail = Base64.toBase64String(byteArray);
        } catch (IOException e) {
            encodedThumbnail = null;
        }
        return encodedThumbnail;
    }

}
