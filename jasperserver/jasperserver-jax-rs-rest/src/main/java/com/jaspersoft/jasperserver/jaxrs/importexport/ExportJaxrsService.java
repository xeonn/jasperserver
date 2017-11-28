package com.jaspersoft.jasperserver.jaxrs.importexport;

import com.jaspersoft.jasperserver.export.service.ImportExportService;
import com.jaspersoft.jasperserver.remote.exception.RemoteException;
import com.jaspersoft.jasperserver.remote.services.async.ExportRunnable;
import com.jaspersoft.jasperserver.remote.services.async.ImportExportTask;
import com.jaspersoft.jasperserver.remote.services.async.TasksManager;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Zakhar.Tomchenco
 */
@Component
@Path("/export")
public class ExportJaxrsService  {

    @Resource
    private ImportExportService synchImportExportService;

    @Resource
    private TasksManager basicTaskManager;

    @Resource(name="messageSource")
    private MessageSource messageSource;

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createNewTask(final ExportTaskDto data) throws RemoteException{

        Map<String, Boolean> params = new HashMap<String, Boolean>();
        if (data.getParameters() != null){
            for (String par : data.getParameters()) {
                params.put(par, true);
            }
        }

        ExportRunnable exportRunnable = new ExportRunnable(params, data.getUris(), data.getScheduledJobs(), data.getRoles(), data.getUsers(), LocaleContextHolder.getLocale());
        exportRunnable.setService(synchImportExportService);
        exportRunnable.setMessageSource(messageSource);
        basicTaskManager.startTask(new ImportExportTask<InputStream>(exportRunnable));

        return Response.ok(exportRunnable.getState()).build();
    }

    @GET
    @Path("/{id}/state")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getStateOfTheTask(@PathParam("id") final String taskId) throws RemoteException{
        return Response.ok(basicTaskManager.getTaskState(taskId)).build();
    }

    @GET
    @Path("/{id}/{name}")
    @Produces("application/zip")
    public Response downloadFile(@PathParam("id") final String taskId, @PathParam("name") String name) throws RemoteException{
        Response response = Response.ok(basicTaskManager.getTask(taskId).getResult()).build();
        basicTaskManager.finishTask(taskId);
        return response;
    }

}
