package com.example.scotterfinaltest;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class User implements Serializable {

    String fullName;
    String mobile;
    String email;
    Date Date;

    public User(String fullName, String mobile, Date Date , String email){
        this.fullName = fullName;
        this.mobile = mobile;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}