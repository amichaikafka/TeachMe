package com.example.myapplication2.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;

public class Lesson {
    private static int ID=0;
    private int lessonID;
    private int teacherID;
    private LinkedList<Integer> studentsID;
    private Date date;
    private double duration;
    private String price;
    private boolean onlineLesson=false;
    private URL link;

    public Lesson(int teacherID, LinkedList<Integer> studentsID, Date date, double duration, String price, String link) throws MalformedURLException {
        this.teacherID = teacherID;
        this.studentsID = studentsID;
        this.date = date;
        this.duration = duration;
        this.price = price;
        this.link = new URL(link);
        onlineLesson=true;
        ID++;
        this.lessonID=ID;

    }

    public Lesson(int teacherID, LinkedList<Integer> studentsID, Date date, double duration, String price) {
        this.teacherID = teacherID;
        this.studentsID = studentsID;
        this.date = date;
        this.duration = duration;
        this.price = price;
        ID++;
        this.lessonID=ID;
    }
    public void startLesson(){}
    public void endLesson(){}

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        Lesson.ID = ID;
    }

    public int getLessonID() {
        return lessonID;
    }

    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public LinkedList<Integer> getStudentsID() {
        return studentsID;
    }

    public void setStudentsID(LinkedList<Integer> studentsID) {
        this.studentsID = studentsID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isOnlineLesson() {
        return onlineLesson;
    }

    public void setOnlineLesson(boolean onlineLesson) {
        this.onlineLesson = onlineLesson;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }
}
