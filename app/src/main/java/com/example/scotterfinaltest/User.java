package com.example.scotterfinaltest;
import java.io.Serializable;

public class User implements Serializable {

    String fullName;
    String mobile;
    String email;

    public User(String fullName, String mobile, String email){
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}