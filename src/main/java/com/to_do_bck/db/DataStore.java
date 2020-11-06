package com.to_do_bck.db;

import com.to_do_bck.core.Task;
import com.to_do_bck.core.subTask;
import javax.ws.rs.NotFoundException;
import java.text.ParseException;
import java.util.*;

public class DataStore {
    static DataStore instance = null;
    Map<Integer, Task> dataStore;
    Map<Integer, subTask> subTaskMap;
    Map<Integer, Integer> subTaskToTaskMapping;
    int taskId;
    int subtaskId;
    public static DataStore getInstance(){
        if(instance == null)
            instance = new DataStore();
        return instance;
    }
    private DataStore(){
        dataStore = new HashMap<>();
        subTaskMap = new HashMap<>();
        subTaskToTaskMapping = new HashMap<>();
        taskId = 1;
        subtaskId = 1;
    }

    public List<Integer> getAllTasks(){
        return new ArrayList<>(dataStore.keySet());
    }
    public List<Integer> getAllSubTasks(){
        return new ArrayList<>(subTaskMap.keySet());
    }
    public Task addTask(String name, String desc, String due_date) throws Exception{
        try {
            Task task = new Task(name, desc, due_date);
            task.setId(taskId);
            dataStore.put(taskId++, task);
            return task;
        }
        catch(Exception e){
            throw e;
        }
    }
    public Task duplicateTask(int id) throws ParseException {
        try {
            Task task = dataStore.get(id).duplicateTask();
            task.setId(id);
            dataStore.put(id++, task);
            return task;
        }
        catch (Exception e){
            if(!dataStore.containsKey(id))
                throw new NotFoundException("No such Task.");
            throw e;
        }
    }
    public subTask addSubTask(int id, subTask subtask) throws Exception{
        try {
            subtask.setId(subtaskId);
            dataStore.get(id).addSubTask(subtask);
            subTaskMap.put(subtaskId, subtask);
            subTaskToTaskMapping.put(subtaskId, id);
            subtaskId++;
            return subtask;
        }
        catch (Exception e){
            if(!dataStore.containsKey(id))
                throw new NotFoundException("No such Task.");
            throw e;
        }
    }

    public subTask getSubTask(int id, int subId) throws Exception{
        try{
            return dataStore.get(id).getSubtask(subId);
        }
        catch(Exception e){
            if(!dataStore.containsKey(id))
                throw new NotFoundException("No such Task.");
            throw e;
        }
    }
    public subTask getSubTaskById(int id) throws NotFoundException{
        if(subTaskMap.containsKey(id))
            return  subTaskMap.get(id);
        else
            throw new NotFoundException("No such Task.");

    }
    public List<subTask> getAllSubTasks(int id) throws Exception{
        try{
            return dataStore.get(id).getAllSubTasks();
        }
        catch(Exception e){
            if(!dataStore.containsKey(id))
                throw new NotFoundException("No such Task.");
            throw e;
        }
    }

    public void updateTask(int id, String name, String desc, String due_date) throws Exception{
        try{
            Task task = dataStore.get(id);
            task.setName(name);
            task.setDesc(desc);
            task.setDue_date(due_date);
        }
        catch (Exception e){
            if(!dataStore.containsKey(id))
                throw new NotFoundException("No such Task.");
            throw e;
        }
    }

    public Task getTaskById(int id) throws NotFoundException{
        if(dataStore.containsKey(id))
            return dataStore.get(id);
        else
            throw new NotFoundException("No such Task.");
    }

    public void deleteTaskById(int id) throws Exception {
        List<subTask> subtasks = getAllSubTasks(id);
        for(subTask subtask:subtasks) {
            subTaskMap.remove(subtask.getId());
            subTaskToTaskMapping.remove(subtask.getId());
        }
        dataStore.remove(id);
    }

    public void deleteSubTaskById(int id, int subId) throws Exception{
        try {
            Task task = dataStore.get(id);
            task.deleteSubTaskById(subId);
            subTaskMap.remove(subId);
            subTaskToTaskMapping.remove(subId);
        }
        catch (Exception e){
            if(!dataStore.containsKey(id))
                throw new NotFoundException("No such Task.");
            else
                throw e;
        }
    }
}
