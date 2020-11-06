package com.to_do_bck.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.to_do_bck.core.Task;
import com.to_do_bck.db.DataStore;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
public class Test_DB {
    private static DataStore datastore;
    private Task task1;
    private Task task2;
    @BeforeEach
    public void setup() throws Exception{
        datastore = DataStore.getInstance();
        task1 = new Task("Shopping", "do something", "2020-11-06");
        task1.setId(1);

        task2 = new Task("Shopping", "shopping from mall", "2020-11-06");
        task2.setId(1);

    }

    @AfterEach
    public void tearDown() {
        datastore = null;
    }

    @Test
    public void addAndGetTaskSuccess() throws Exception {
        datastore.addTask(task1.getName(), task1.getDesc(), task1.getDue_date());
        Task found = datastore.getTaskById(1);
        assertEquals(found.getId(), task1.getId());
    }

    @Test
    public void getAllTasks() throws Exception{
        datastore.addTask(task1.getName(), task1.getDesc(), task1.getDue_date());
        List<Integer> expected = datastore.getAllTasks();
        List<Integer> keys = new ArrayList<>();
        keys.add(1);
        assertThat(expected).hasSameElementsAs(keys);
    }

    @Test
    public void updateTask() throws Exception{
        datastore.addTask(task1.getName(), task1.getDesc(), task1.getDue_date());
        datastore.updateTask(1, task2.getName(), task2.getDesc(), task2.getDue_date());
        Task found = datastore.getTaskById(1);
        assertEquals(found.getDesc(), task2.getDesc());
    }

    @Test
    public void deleteTask() throws Exception{
        datastore.addTask(task1.getName(), task1.getDesc(), task1.getDue_date());
        datastore.deleteTaskById(1);
        String message = "";
        try{
            datastore.getTaskById(1);
        }
        catch (Exception e){
            message = e.getMessage();
        }
        assertEquals(message,"No such Task.");
    }

}
