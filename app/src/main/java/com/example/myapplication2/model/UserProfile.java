package com.example.myapplication2.model;

import java.util.Date;

import android.graphics.Point;

public abstract class UserProfile {
    protected String userID;
    protected String firstName;
    protected String lastName;
    protected Date dateOfBirth;
    protected String emailAddress;
    protected String gender;
    protected String aboutMe;
    protected Point location;

    public UserProfile(String userID, String firstName, String lastName, Date dateOfBirth, String emailAddress, String gender, String aboutMe, Point location) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.emailAddress = emailAddress;
        this.gender = gender;
        this.aboutMe = aboutMe;
        this.location = location;
    }

    public void uploadPhoto(){}
    public void updateProfile(){}
    public void editProfile(){}
}
