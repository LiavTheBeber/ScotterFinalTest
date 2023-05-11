package com.example.scotterfinaltest;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class User implements Serializable {

    private String fullName;
    private String mobile;
    private String email;
    private Date birthdate;

    public User(String fullName, String mobile, Date birthdate, String email) {
        this.fullName = fullName;
        this.mobile = mobile;
        this.birthdate = birthdate;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}