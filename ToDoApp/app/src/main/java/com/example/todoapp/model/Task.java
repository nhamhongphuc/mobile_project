package com.example.todoapp.model;

import static java.text.DateFormat.getDateTimeInstance;

import com.google.firebase.database.ServerValue;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Task {
    private String title;
    private String description;
    private boolean isCompleted;
    private int priority;
    private Map<String, String> date;


    public Task() {
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.date = new HashMap();
        this.date.put("createdDate", String.valueOf(ServerValue.TIMESTAMP));
    }

    public Task(String title, String description, boolean isCompleted, int priority, Map<String, String> date) {
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.priority = priority;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Map<String, String> getDate() {
        return date;
    }

    public void setDate(Map<String, String> date) {
        this.date = date;
    }

    public static String getTimeDate(long timestamp){
        try{
            DateFormat dateFormat = getDateTimeInstance();
            Date netDate = (new Date(timestamp));
            return dateFormat.format(netDate);
        } catch(Exception e) {
            return "date";
        }
    }
}
