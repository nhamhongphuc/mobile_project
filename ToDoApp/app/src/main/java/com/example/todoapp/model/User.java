package com.example.todoapp.model;

public class User {
    private String email;
    private String password;
    private String avt;

    public User() {
    }

    public User(String email, String password, String avt) {
        this.email = email;
        this.password = password;
        this.avt = avt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }
}
