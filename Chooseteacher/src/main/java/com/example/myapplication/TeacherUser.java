package com.example.myapplication;

import android.support.annotation.NonNull;

public class TeacherUser {
    private String name;
    private String password;

    public TeacherUser(String name, String password) {
        this.name=name;
        this.password=password;
    }

    @NonNull
    @Override
    public String toString() {
        return "radio=teacher&username=" + name +"&password=" + password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
