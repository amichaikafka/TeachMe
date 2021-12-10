package com.example.myapplication2.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;

public class Lesson {
    private static int ID=0;
    private int lessonID;
    private int teacerID;
    private LinkedList<Integer> studentsID;
    private Date date;
    private double duration;
    private String price;
    private boolean onlineLesson=false;
    private URL link;

    public Lesson(int teacerID, LinkedList<Integer> studentsID, Date date, double duration, String price, String link) throws MalformedURLException {
        this.teacerID = teacerID;
        this.studentsID = studentsID;
        this.date = date;
        this.duration = duration;
        this.price = price;
        this.link = new URL(link);
        onlineLesson=true;
        ID++;
        this.lessonID=ID;

    }

    public Lesson(int teacerID, LinkedList<Integer> studentsID, Date date, double duration, String price) {
        this.teacerID = teacerID;
        this.studentsID = studentsID;
        this.date = date;
        this.duration = duration;
        this.price = price;
        ID++;
        this.lessonID=ID;
    }
    public void startLesson(){}
    public void endLesson(){}
}
