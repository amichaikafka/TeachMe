package com.example.myapplication2.api;

import android.graphics.Point;

import java.util.Date;
import java.util.LinkedList;

public class StudentProfile extends UserProfile{
    private LinkedList<String> filledOfStudies;
    public StudentProfile(String userId, String firstName, String lastName, Date dateOfBirth, String emailAddress, String gender, String aboutMe, Point location) {
        super(userId, firstName, lastName, dateOfBirth, emailAddress, gender, aboutMe, location);
    }

    public StudentProfile(String userId, String firstName, String lastName, Date dateOfBirth, String emailAddress, String gender, String aboutMe, Point location, LinkedList<String> filledOfStudies) {
        super(userId, firstName, lastName, dateOfBirth, emailAddress, gender, aboutMe, location);
        this.filledOfStudies = filledOfStudies;
    }
    public void scheduleLesson(){}
    public LinkedList<String> searchForTeacher(Fillter fillter){
        return null;
    }
    public void sortTeacherBy(String sortBy){}
}