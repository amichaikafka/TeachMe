package com.example.myapplication2.model;

public class UserNotification {
    private String userId;
    private int notificationType;

    public UserNotification(String userId, int notificationType) {
        this.userId = userId;
        this.notificationType = notificationType;
    }
    public  void displayNotification(){}
    public void requestLesson(){}
    public void acceptLesson(){}
    public void denyLesson(){}
}
