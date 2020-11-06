package com.to_do_bck.core;
import static io.dropwizard.testing.FixtureHelpers.*;
import static org.junit.Assert.assertEquals;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import io.dropwizard.jackson.Jackson;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class Test_Task  {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        final Task task = new Task("Shopping", "Shopping in mall for clothes", "2020-11-06");

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/task.json"), Task.class));
        assertEquals(MAPPER.writeValueAsString(task),expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final Task task = new Task("Shopping", "Shopping in mall for clothes", "2020-11-06");
        System.out.println(task);
        System.out.println(MAPPER.readValue(fixture("fixtures/task.json"), Task.class));

        assertEquals(MAPPER.readValue(fixture("fixtures/task.json"), Task.class), task);
    }
}

