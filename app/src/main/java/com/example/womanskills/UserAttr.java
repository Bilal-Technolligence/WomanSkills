package com.example.womanskills;

public class UserAttr {

    String id, username, fullname, email, date, cnic,address, gender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserAttr(String id, String username, String fullname, String email, String date, String cnic, String address, String gender) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.date = date;
        this.cnic = cnic;
        this.address = address;
        this.gender = gender;
    }

    public UserAttr() {
    }
}
