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
    private int price;
    private boolean onlineLesson=false;
    private URL link;
    private String teacherName;
    private String studentName;



    private String studyField;

    public Lesson(int teacherID, LinkedList<Integer> studentsID, Date date, double duration, int price, String link) throws MalformedURLException {
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
    // use for lesson box.
    public Lesson(String teacherName, String studentName, String studyField, Date date, int price) {
        this.teacherName = teacherName;
        this.studentName = studentName;
        this.studyField = studyField;
        this.date = date;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudyField(String studyField) {
        this.studyField = studyField;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudyField() {
        return studyField;
    }
}
