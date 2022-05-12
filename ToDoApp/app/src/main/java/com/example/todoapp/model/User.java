package com.example.todoapp.model;

public class User {
    private String email;
    private String avt;

    public User() {
    }

    public User(String email, String avt) {
        this.email = email;
        this.avt = avt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }
}
