package com.jaspersoft.jasperserver.war.httpheaders;

import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.springframework.http.HttpMethod.GET;

/**
 * Created by schubar on 11/18/15.
 */
public class ThumbnailsCacheControlHeaderFilterTest extends BaseGetHttpHeaderFilterTest {

    @Test
    public void ensurePrivateIfReportHasBenExecuted() throws ServletException, IOException {
        givenConfig(
                rule(GET, "/rest_v2/thumbnails/.*",
                        header("Cache-Control", "private,no-cache,no-store,max-age=0,must-revalidate"),
                        header("Pragma", "no-cache")
                )
        );

        whenFilter(GET, "/rest_v2", "/thumbnails/public/Samples/Reports/01._Geographic_Results_by_Segment_Report");

        thenHeader("Cache-Control", "private,no-cache,no-store,max-age=0,must-revalidate");
        thenHeader("Pragma", "no-cache");
    }

    @Test
    public void ensurePrivateIfDefaultAllowedAndReportHasBenExecuted() throws ServletException, IOException {
        givenConfig(
                rule(GET, "/rest_v2/thumbnails/.+",
                        header("Cache-Control", "private,no-cache,no-store,max-age=0,must-revalidate"),
                        header("Pragma", "no-cache")
                )
        );

        whenFilter(GET, "/rest_v2", "/thumbnails/public/Samples/Reports/01._Geographic_Results_by_Segment_Report?defaultAllowed=true");

        thenHeader("Cache-Control", "private,no-cache,no-store,max-age=0,must-revalidate");
        thenHeader("Pragma", "no-cache");
    }

    @Test
    public void ensurePrivateIfDefaultNotAllowedAndReportHasBenExecuted() throws ServletException, IOException {
        givenConfig(
                rule(GET, "/rest_v2/thumbnails/.*\\?.*&?defaultAllowed=false",
                        header("Cache-Control", "private,no-cache,no-store,max-age=0,must-revalidate"),
                        header("Pragma", "no-cache")
                )
        );

        whenFilter(GET, "/rest_v2", "/thumbnails/public/Samples/Reports/01._Geographic_Results_by_Segment_Report?defaultAllowed=false");

        thenHeader("Cache-Control", "private,no-cache,no-store,max-age=0,must-revalidate");
        thenHeader("Pragma", "no-cache");
    }

    @Test
    public void ensurePrivateIfDefaultAllowedAndReportHasNotBenExecuted() throws ServletException, IOException {
        givenConfig(
                rule(GET, "/rest_v2/thumbnails/.*\\?.*&?defaultAllowed=true",
                        header("Cache-Control", "private,no-cache,no-store,max-age=0,must-revalidate"),
                        header("Pragma", "no-cache")
                )
        );

        whenFilter(GET, "/rest_v2", "/thumbnails/public/Samples/Reports/01._Geographic_Results_by_Segment_Report?defaultAllowed=true");

        thenHeader("Cache-Control", "private,no-cache,no-store,max-age=0,must-revalidate");
        thenHeader("Pragma", "no-cache");
    }

    @Test
    public void ensurePrivateIfDefaultNotAllowedAndReportHasNotBenExecuted() throws ServletException, IOException {
        // If no payload don't cache
        givenConfig(
                rule(GET, "/rest_v2/thumbnails/.*\\?.*&?defaultAllowed=false",
                        header("Cache-Control", "private,no-cache,no-store,max-age=0,must-revalidate"),
                        header("Pragma", "no-cache")
                )
        );

        whenFilter(GET, "/rest_v2", "/thumbnails/public/Samples/Reports/01._Geographic_Results_by_Segment_Report?defaultAllowed=false");

        thenHeader("Cache-Control", "private,no-cache,no-store,max-age=0,must-revalidate");
        thenHeader("Pragma", "no-cache");
    }

}
