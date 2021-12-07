package com.example.myapplication2.model;

import android.graphics.Point;

import java.util.Date;
import java.util.LinkedList;

public class TeacherProfile extends UserProfile{
    private LinkedList<String> filledsOfTeaching;
    private LinkedList<String> listOfStudents;
    private double price;
    private int level;
    public TeacherProfile(String userId, String firstName, String lastName, Date dateOfBirth, String emailAddress, String gender, String aboutMe, Point location) {
        super(userId, firstName, lastName, dateOfBirth, emailAddress, gender, aboutMe, location);
    }
    public String getStats(){ return "";}
    public void scheduleLesson(){}
    private void creatLesson(){}
}
