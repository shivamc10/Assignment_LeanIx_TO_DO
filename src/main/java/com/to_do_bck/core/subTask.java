package com.to_do_bck.core;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class subTask implements Task_Interface{
    @JsonProperty
    private String name;
    @JsonProperty
    private String desc;
    @JsonProperty
    private Date due_date;
    @JsonProperty
    private int id;
    private boolean Expire = false;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @JsonProperty
    public String getName() {
        return name;
    }
    @JsonProperty
    public void setName(String name) {
        this.name = name;
    }
    @JsonProperty
    public String getDesc() {
        return desc;
    }
    @JsonProperty
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean getExpire() {
        return Expire;
    }

    public void setExpire(boolean expire) {
        Expire = expire;
    }

    @JsonProperty
    public String getDue_date() {
        return dateFormat.format(this.due_date);
    }
    @JsonProperty
    public void setDue_date(String due_date) throws ParseException {
        this.due_date = this.dateFormat.parse(due_date);
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(this.getClass()))
            return false;
        if( this == obj)
            return true;
        subTask subtask = (subTask) obj;
        return this.name.equals(subtask.getName()) && this.desc.equals(subtask.getDesc()) &&
                this.getDue_date().equals(subtask.getDue_date());
    }

    @Override
    public String toString() {
        return this.getName()+' '+this.getDesc()+' '+this.getDue_date();
    }

}
