package com.to_do_bck.api;

import com.to_do_bck.db.DataStore;
import io.dropwizard.lifecycle.Managed;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskCheckingExecutionService implements Managed {
    private final ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
    DataStore dataStore;
    public TaskCheckingExecutionService(DataStore dataStore){
        this.dataStore = dataStore;
    }
    @Override
    public void start() throws Exception {
        System.out.println("Starting jobs");
        service.scheduleAtFixedRate(new TaskCheckingService(dataStore), 1, 1, TimeUnit.MINUTES);
        service.scheduleAtFixedRate(new SubTaskCheckingService(dataStore), 1, 1, TimeUnit.MINUTES);
    }

    @Override
    public void stop() throws Exception {    }
}
