package com.example.myapplication2.api;

public class AdminAccount {
    private String adminID;
    private String adminPassword;
    public void signOut(){}
    private void getAccess(){}
    private void removeAccount(){}
    private void blockAccount(){}

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
