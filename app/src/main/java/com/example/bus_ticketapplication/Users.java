package com.example.bus_ticketapplication;

public class Users {

    public String firstName, lastName, emailId, passWord,confPass, phoneNo;

    public Users(){

    }

    public Users(String firstName, String lastName, String emailId, String passWord, String confPass,String phoneNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.passWord = passWord;
        this.phoneNo = phoneNo;
        this.confPass=confPass;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getConfPass() {
        return confPass;
    }

    public void setConfPass(String confPass) {
        this.confPass = confPass;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
