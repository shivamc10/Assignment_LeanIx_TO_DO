package com.to_do_bck.api;


import com.to_do_bck.core.Task;
import com.to_do_bck.core.subTask;
import com.to_do_bck.db.DataStore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SubTaskCheckingService implements Runnable{
    private DataStore dataStore;
    public SubTaskCheckingService(DataStore dataStore){
        this.dataStore = dataStore;
    }
    @Override
    public void run() {
        checkForSubTask();
    }

    public void checkForSubTask(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dt = dateFormat.format(date);
        List<Integer> keys = dataStore.getAllSubTasks();
        for(Integer key: keys){
            subTask subtask = dataStore.getSubTaskById(key);
            if(subtask.getDue_date().equals((dt)) && !subtask.getExpire()) {
                System.out.println("subtask Task Expire " + subtask);
                subtask.setExpire(true);
            }
        }
    }
}
