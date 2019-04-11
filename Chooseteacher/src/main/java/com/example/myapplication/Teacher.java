package com.example.myapplication;

public class Teacher {
    String teacher_no;
    String course_name;
    int course_no;
    String description;
    String status;

    public Teacher(String teacher_no, String course_name, int course_no, String description, String status) {
        this.teacher_no = teacher_no;
        this.course_name = course_name;
        this.course_no = course_no;
        this.description = description;
        this.status = status;
    }

    public String getTeacher_no() {
        return teacher_no;
    }
    public void setTeacher_no(String teacher_no) {
        this.teacher_no = teacher_no;
    }
    public String getCourse_name() {
        return course_name;
    }
    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
    public int getCourse_no() {
        return course_no;
    }
    public void setCourse_no(int course_no) {
        this.course_no = course_no;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}

