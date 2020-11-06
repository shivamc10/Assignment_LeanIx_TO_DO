package com.to_do_bck.api;

import com.to_do_bck.core.Task;
import com.to_do_bck.db.DataStore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TaskCheckingService implements Runnable {
    private DataStore dataStore;
    public TaskCheckingService(DataStore dataStore){
        this.dataStore = dataStore;
    }
    @Override
    public void run() {
        checkForTasks();
    }
    public void checkForTasks(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dt = dateFormat.format(date);
        List<Integer> keys = dataStore.getAllTasks();
        for(Integer key: keys){
            Task task = dataStore.getTaskById(key);
            if(task.getDue_date().equals((dt))) {
                System.out.println("Task Expire " + task);
                task.setExpire(true);
            }
        }
    }
}
