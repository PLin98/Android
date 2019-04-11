package com.example.myapplication;

public class Choose {
    String student_no;
    String choose_time;

    public Choose(String student_no, String choose_time) {
        super();
        this.student_no = student_no;
        this.choose_time = choose_time;
    }
    public String getStudent_no() {
        return student_no;
    }
    public void setStudent_no(String student_no) {
        this.student_no = student_no;
    }
    public String getChoose_time() {
        return choose_time;
    }
    public void setChoose_time(String choose_time) {
        this.choose_time = choose_time;
    }
}
