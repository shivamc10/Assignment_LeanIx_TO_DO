package com.to_do_bck.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ToDoClient {
    public  void getRequest() {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:8080/todos");
            Response response = target.request().header("Content-Type", "application/json").get();
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            String output = new BufferedReader(
                    new InputStreamReader((InputStream) response.getEntity(), StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining("\n"));
            System.out.println("Output from Server .... \n");
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postRequest(){
        try {

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:8080/todos");
            String input = "{\"name\":\"Metallica\",\"desc\":\"Fade To Black\"}";
            Response response = target.request().header("Content-Type", "application/json")
                    .post(Entity.json(input));
            if (response.getStatus() != 201) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            System.out.println("Output from Server .... \n");
            String output = new BufferedReader(
                    new InputStreamReader((InputStream) response.getEntity(), StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining("\n"));
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
