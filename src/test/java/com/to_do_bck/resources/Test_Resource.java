package com.to_do_bck.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.to_do_bck.core.Task;
import com.to_do_bck.db.DataStore;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.InstanceOfAssertFactories.ARRAY;
import static org.junit.Assert.assertEquals;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.stream.Collectors;

@RunWith(JUnitPlatform.class)
@ExtendWith(DropwizardExtensionsSupport.class)
public class Test_Resource {
    private static final DataStore datastore = mock(DataStore.class);
    private static final ResourceExtension EXT = ResourceExtension.builder()
            .addResource(new ToDoResource(datastore))
            .build();
    private Task task;
    private Task task2;
    @BeforeEach
    public void setup() throws Exception{
        task = new Task("Shopping", "do something", "2020-11-06");
        task.setId(1);

        task2 = new Task("Shopping", "shopping from mall", "2020-11-06");
        task2.setId(1);

    }

    @AfterEach
    public void tearDown() {
        reset(datastore);
    }

    @Test
    public void getTaskSuccess() throws JsonProcessingException {
        when(datastore.getTaskById(1)).thenReturn(task);
        Response response = EXT.target("todos/1").request().get();
        String result = new BufferedReader(
                new InputStreamReader((InputStream) response.getEntity(), StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));

        Task found = new ObjectMapper().readValue(result, Task.class);
        assertEquals(found.getId(), task.getId());
        verify(datastore).getTaskById(1);
    }

    @Test
    public void postTaskSuccess() throws Exception {
        when(datastore.addTask("Shopping", "do something", "2020-11-06")).thenReturn(task);
        String input = "{\"name\":\"Shopping\",\"desc\":\"do something\",\"due_date\":\"2020-11-06\"}";
        Response response = EXT.target("todos").request().header("Content-Type", "application/json")
                .post(Entity.json(input));
        String result = new BufferedReader(
                new InputStreamReader((InputStream) response.getEntity(), StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));

        Task found = new ObjectMapper().readValue(result, Task.class);
        assertEquals(found.getId(), task.getId());
        verify(datastore).addTask("Shopping", "do something", "2020-11-06");
    }

    @Test
    public void getAllTasks() throws Exception{
        List<Integer> keys = new ArrayList<Integer>();
        keys.add(1);
        when(datastore.getAllTasks()).thenReturn(keys);
        when(datastore.getTaskById(1)).thenReturn(task);
        Response response = EXT.target("todos/").request().get();
        String result = new BufferedReader(
                new InputStreamReader((InputStream) response.getEntity(), StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));
        int size = result.length();
        result = result.substring(1,size-1);

        Task found = new ObjectMapper().readValue(result, Task.class);
        assertEquals(found.getId(), task.getId());
        verify(datastore).getTaskById(1);
    }

    @Test
    public void updateTask() throws Exception{
        doNothing().when(datastore).updateTask(1,"Shopping", "shopping from mall", "2020-11-06");
        when(datastore.getTaskById(1)).thenReturn(task2);
        String input = "{\"name\":\"Shopping\",\"desc\":\"shopping from mall\",\"due_date\":\"2020-11-06\"}";
        EXT.target("todos/1").request().header("Content-Type", "application/json")
                .put(Entity.json(input));
        Response response = EXT.target("todos/1").request().get();

        String result = new BufferedReader(
                new InputStreamReader((InputStream) response.getEntity(), StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));

        Task found = new ObjectMapper().readValue(result, Task.class);
        assertEquals(found.getDesc(), task2.getDesc());
    }

    @Test
    public void deleteTask() throws Exception{
        when(datastore.getTaskById(1)).thenThrow(new NotFoundException("no user found"));
        doNothing().when(datastore).deleteTaskById(1);
        EXT.target("todos/1").request().delete();
        Response response = EXT.target("todos/1").request().get();
        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void getTaskNotFound() {
        when(datastore.getTaskById(2)).thenThrow(new NotFoundException("no user found"));
        final Response response = EXT.target("/todos/2").request().get();
        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(datastore).getTaskById(2);
    }

}
