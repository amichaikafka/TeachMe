package com.example.myapplication2.model;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class TeacherProfile extends UserProfile {
    //    private HashSet<String> fieldsOfTeaching;
    private String fieldsOfTeaching="";
    private HashSet<String> listOfStudents;
    private String phoneNumber;
    private double price;
    private int rating;
    private int numOfReviews;
    private List<String> reviews;           //TODO class Review
    private String profilePicture;

    public TeacherProfile() {
    }

    public TeacherProfile(String firstName, String lastName, String emailAddress, String gender) {
        super(firstName, lastName, emailAddress, gender);
    }

    public TeacherProfile(String userId, String firstName, String lastName, Date dateOfBirth,
                          String emailAddress, String gender, String aboutMe, Point location,
                          String fieldsOfTeaching, String phoneNumber,
                          double price, String iconPath) {
        super(userId, firstName, lastName, dateOfBirth, emailAddress, gender, aboutMe, location);
        this.fieldsOfTeaching = fieldsOfTeaching;
        this.listOfStudents = new HashSet<String>();
        this.phoneNumber = phoneNumber;
        this.price = price;
        this.listOfStudents = new HashSet<String>();
        this.rating = 0;
        this.profilePicture = iconPath;
        this.numOfReviews = 0;
        this.reviews = new ArrayList<>();
    }

    public String getStats() {
        return "";
    }

    public void scheduleLesson() {
    }

    private void createLesson() {
    }

    public String getFieldsOfTeaching() {
        return fieldsOfTeaching;
    }

    public void setFieldsOfTeaching(String fieldsOfTeaching) {
        this.fieldsOfTeaching = fieldsOfTeaching;
    }

    public HashSet<String> getListOfStudents() {
        return listOfStudents;
    }

    public void setListOfStudents(HashSet<String> listOfStudents) {
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

    public int getNumOfReviews() {
        return numOfReviews;
    }

    public void setNumOfReviews(int numOfReviews) {
        this.numOfReviews = numOfReviews;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }
}
