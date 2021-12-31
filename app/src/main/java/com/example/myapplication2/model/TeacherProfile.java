package com.example.myapplication2.model;

import android.graphics.Bitmap;

import java.util.List;

public class TeacherProfile extends UserProfile {
    private String fieldsOfTeaching = "";
    private String listOfStudents = "";
    private String phoneNumber;
    private double price;
    private float rating;
    private int numOfReviews;
    private float sumOfReviews;

    public TeacherProfile() {}

    public TeacherProfile(String firstName, String lastName, String emailAddress, String gender) {
        super(firstName, lastName, emailAddress, gender);
    }
    public float getSumOfReviews() {
        return sumOfReviews;
    }

    public void setSumOfReviews(float sumOfReviews) {
        this.sumOfReviews = sumOfReviews;
    }

    public String getFieldsOfTeaching() {
        return fieldsOfTeaching;
    }

    public void setFieldsOfTeaching(String fieldsOfTeaching) {
        this.fieldsOfTeaching = fieldsOfTeaching;
    }

    public String getListOfStudents() {
        return listOfStudents;
    }

    public void setListOfStudents(String listOfStudents) {
        this.listOfStudents = listOfStudents;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float r) {
        this.rating = r;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getNumOfReviews() {
        return numOfReviews;
    }

    public void setNumOfReviews(int numOfReviews) {
        this.numOfReviews = numOfReviews;
    }
}
