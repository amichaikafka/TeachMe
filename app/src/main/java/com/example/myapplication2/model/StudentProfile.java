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
}
