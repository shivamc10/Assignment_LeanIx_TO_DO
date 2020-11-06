package com.to_do_bck.resources;

import com.to_do_bck.core.Task;
import com.to_do_bck.core.subTask;
import com.to_do_bck.db.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/todos")
public class ToDoResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToDoResource.class);
    private DataStore dataStore;
    private String template;
    private String defaultName;
    public ToDoResource(DataStore dataStore){
        this.dataStore = dataStore;
    }
    public ToDoResource(String template, String defaultName, DataStore dataStore) {
        this.defaultName = defaultName;
        this.template = template;
        this.dataStore = dataStore;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskByID(@PathParam("id") int id){
        try {
            Task result = dataStore.getTaskById(id);
            return Response.status(201).entity(result).build();
        }
        catch (Exception e){
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTasks(){
        try{
            List<Task> allTask = new ArrayList<>();
            List<Integer> keys = dataStore.getAllTasks();
            for(Integer key:keys)
                allTask.add(dataStore.getTaskById(key));
            return Response.status(201).entity(allTask).build();
        }
        catch (Exception e){
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/subTask/{id}/{subId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubTask(@PathParam("id") int id, @PathParam("name") int subId){
        try{
            subTask sb = dataStore.getSubTask(id, subId);
            return Response.status(201).entity(sb).build();
        }
        catch (Exception e){
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/subTask/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSubTasks(@PathParam("id") int id){
        try{
            List<subTask> subTaskList = dataStore.getAllSubTasks(id);
            return Response.status(201).entity(subTaskList).build();
        }
        catch (Exception e){
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTask(@org.jetbrains.annotations.NotNull Task task){
        try {
            Task result =  dataStore.addTask(task.getName(), task.getDesc(), task.getDue_date());
            return Response.status(201).entity(result).build();
        }
        catch (Exception e){
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/duplicate/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response duplicateTask(@PathParam("id") int id){
        try {
            Task result =  dataStore.duplicateTask(id);
            return Response.status(201).entity(result).build();
        }
        catch (Exception e){
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/subTask/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSubTask(@PathParam("id") int id, @org.jetbrains.annotations.NotNull subTask subtask){
        try{
            subTask sub = dataStore.addSubTask(id, subtask);
            return Response.status(201).entity(sub).build();
        }
        catch (Exception e){
            return Response.status(404).entity(e.getMessage()).build();
        }
    }
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTaskById(@PathParam("id") int id, Task task){
        try {
            dataStore.updateTask(id, task.getName(), task.getDesc(), task.getDue_date());
            return Response.status(201).build();
        }
        catch (Exception e){
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTaskById(@PathParam("id") int id){
        try {
            dataStore.deleteTaskById(id);
            return Response.status(201).build();
        }
        catch (Exception e){
            return Response.status(404).entity(e.getMessage()).build();
        }
    }
    @DELETE
    @Path("/subTask/{id}/{subid}")
    public Response deleteSubTaskById(@PathParam("id") int id, @PathParam("id") int subid){
        try {
            dataStore.deleteSubTaskById(id,subid);
            return Response.status(201).build();
        }
        catch (Exception e){
            return Response.status(404).entity(e.getMessage()).build();
        }
    }
}
