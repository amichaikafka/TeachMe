package com.example.myapplication2.model;

import java.util.Date;
import java.util.LinkedList;

public class Lesson {
    private static int ID=0;
    private Date date;
    private int price;
    private boolean onlineLesson=false;
    private String teacherName;
    private String studentName;
    private String studyField;

    public Lesson(){ }

    public Lesson(String teacherName, String studentName, String studyField, Date date, int price) {
        this.teacherName = teacherName;
        this.studentName = studentName;
        this.studyField = studyField;
        this.date = date;
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudyField() {
        return studyField;
    }
}
