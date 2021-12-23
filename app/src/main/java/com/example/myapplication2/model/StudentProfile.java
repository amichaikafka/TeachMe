package com.example.myapplication2.model;

import android.graphics.Point;

import java.util.Date;
import java.util.LinkedList;

public class StudentProfile extends UserProfile{
    private LinkedList<String> filledOfStudies;

    public StudentProfile() {
    }

    public StudentProfile(String firstName, String lastName, String emailAddress, String gender) {
        super(firstName, lastName, emailAddress, gender);
    }

    public StudentProfile(String userId, String firstName, String lastName, Date dateOfBirth, String emailAddress, String gender, String aboutMe, Point location) {
        super(userId, firstName, lastName, dateOfBirth, emailAddress, gender, aboutMe, location);
    }

    public StudentProfile(String userId, String firstName, String lastName, Date dateOfBirth, String emailAddress, String gender, String aboutMe, Point location, LinkedList<String> filledOfStudies) {
        super(userId, firstName, lastName, dateOfBirth, emailAddress, gender, aboutMe, location);
        this.filledOfStudies = filledOfStudies;
    }
    public void scheduleLesson(){}

    public void sortTeacherBy(String sortBy){}

    public LinkedList<String> getFilledOfStudies() {
        return filledOfStudies;
    }

    public void setFilledOfStudies(LinkedList<String> filledOfStudies) {
        this.filledOfStudies = filledOfStudies;
    }
}
