package com.example.myapplication2.model;

import java.util.Date;

public class Comment {
    private String subject;
    private String review;
    private Date date;
    private String reviewerName;
    private float rating;
    public Comment(){}
    public Comment(String subject, String review, Date date ,String name, float rating){
        this.subject = subject;
        this.review = review;
        this.date = date;
        this.reviewerName = name;
        this.rating = rating;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
