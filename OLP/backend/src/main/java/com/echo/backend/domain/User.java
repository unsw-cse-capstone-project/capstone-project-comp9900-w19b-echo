package com.echo.backend.domain;

import java.util.Date;

public class User {

    private int uid;

    private String userName;

    private String password;

    private String role;

    private String email;

    // format: +xx_xxxxxxxxxxx
    private String phone;

    private Date registerTime;

    // 0: not verificated
    // 1: verificated
    private int verification = 0;

    public User(int uid, String userName, String password, String role, String email, String phone) {
        this.uid = uid;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public int getVerification() {
        return verification;
    }

    public void setVerification(int verification) {
        this.verification = verification;
    }
}
