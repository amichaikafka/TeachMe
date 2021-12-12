package com.example.myapplication2.api;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TeacherProfile extends UserProfile{
    private List<String> fieldsOfTeaching;
    private List<String> listOfStudents;
    private String phoneNumber;
    private double price;
    private int rating;
    private String profilePicture;
    public TeacherProfile(String userId, String firstName, String lastName, Date dateOfBirth,
                          String emailAddress, String gender, String aboutMe, Point location,
                          List<String> fieldsOfTeaching, String phoneNumber,
                        double price, String iconPath) {
        super(userId, firstName, lastName, dateOfBirth, emailAddress, gender, aboutMe, location);
        this.fieldsOfTeaching = fieldsOfTeaching;
        this.listOfStudents = listOfStudents;
        this.phoneNumber = phoneNumber;
        this.price = price;
        this.listOfStudents = new ArrayList<>();
        this.rating = 0;
        this.profilePicture = iconPath;
    }
    public String getStats(){ return "";}
    public void scheduleLesson(){}
    private void createLesson(){}

    public List<String> getFieldsOfTeaching() {
        return fieldsOfTeaching;
    }

    public void setFieldsOfTeaching(LinkedList<String> fieldsOfTeaching) {
        this.fieldsOfTeaching = fieldsOfTeaching;
    }

    public List<String> getListOfStudents() {
        return listOfStudents;
    }

    public void setListOfStudents(LinkedList<String> listOfStudents) {
        this.listOfStudents = listOfStudents;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int r) {
        this.rating = r;
    }

    public void setFieldsOfTeaching(List<String> fieldsOfTeaching) {
        this.fieldsOfTeaching = fieldsOfTeaching;
    }

    public void setListOfStudents(List<String> listOfStudents) {
        this.listOfStudents = listOfStudents;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
