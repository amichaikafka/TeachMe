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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }
}
