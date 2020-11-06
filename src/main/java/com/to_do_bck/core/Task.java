package com.to_do_bck.core;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.NotFoundException;

public class Task extends subTask{
    @JsonProperty
    private int id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String desc;
    @JsonProperty
    private Date due_date;
    private boolean Expire = false;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final Map<Integer, subTask> subtaskMap = new HashMap<>();
    public Task(){}
    public Task(String name, String desc, String due_date) throws ParseException {
        this.name = name;
        this.desc = desc;
        this.due_date = this.dateFormat.parse(due_date);
    }

    @Override
    public boolean getExpire() {
        return Expire;
    }

    @Override
    public void setExpire(boolean expire) {
        Expire = expire;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String getDue_date() {
        return this.dateFormat.format(this.due_date);
    }

    @Override
    public void setDue_date(String due_date) throws ParseException {
        this.due_date = this.dateFormat.parse(due_date);
    }

    public void addSubTask(subTask subtask){
        subtaskMap.put(subtask.getId(),subtask);
    }
    public subTask getSubtask(int id) throws NotFoundException{
        if(subtaskMap.containsKey(id)) {
            return subtaskMap.get(id);
        }
        else{
            throw new NotFoundException("no such subtask.");
        }
    }
    public List<subTask> getAllSubTasks() {
        List<subTask> subTasks = new ArrayList<>();
        for(Map.Entry<Integer,subTask> entry: subtaskMap.entrySet())
            subTasks.add(entry.getValue());
        return subTasks;
    }
    public void deleteSubTaskById(int id)  throws NotFoundException{
        if(subtaskMap.containsKey(id)) {
            subtaskMap.remove(id);
        }
        else{
            throw new NotFoundException("no such subtask.");
        }
    }

    public Task duplicateTask() throws ParseException {
        Task res = new Task(this.getName(), this.getDesc(), this.getDue_date());
        res.subtaskMap.putAll(this.subtaskMap);
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(this.getClass()))
            return false;
        if( this == obj)
            return true;
        Task task = (Task) obj;
        return this.name.equals(task.getName()) && this.desc.equals(task.getDesc()) &&
                this.getDue_date().equals(task.getDue_date());
    }
}
