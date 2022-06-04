package com.example.todoapp.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
    private String title;
    private String description;
    private String createdDate;
    private String content;

    public Note() {
    }

    public Note(String title, String description, String content) {
        this.title = title;
        this.description = description;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        this.createdDate = dateFormat.format(date).toString();
        this.content = content;

    }

    public Note(String title, String description, String createdDate, String content) {
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.content = content;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
