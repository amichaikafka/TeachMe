package com.example.myapplication2.model;

import java.util.LinkedList;

public  class UserAccount {
    private String userID;
    private String userPassword;
    private String hintQuestion;
    private String hintAnswer;
    private UserProfile userProfile;
    private LinkedList<Lesson> lessons=new LinkedList<>();

    public UserAccount(String userID, String userPassword, String hintQuestion, String hintAnswer) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.hintQuestion = hintQuestion;
        this.hintAnswer = hintAnswer;
    }
    public void registerUserAccount(){

    }
    public void signOut(){

    }
    public void deleteAccount(){

    }
    public void forgetPassword(){

    }
    public void recoveryPassword(){}
    public void changePassword(){}
}