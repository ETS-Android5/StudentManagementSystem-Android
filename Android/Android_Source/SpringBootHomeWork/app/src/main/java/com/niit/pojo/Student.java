package com.niit.pojo;

import android.content.Intent;
import android.text.Editable;

public class Student {
    private Integer stuid;

    private String name;

    private String batch;

    private String age;

    private String address;

    private String contactnumber;

    private String email;

    public Integer getStuid() {
        return stuid;
    }

    public void setStuid(Integer stuid) {
        this.stuid = stuid;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuid=" + stuid +
                ", name='" + name + '\'' +
                ", batch='" + batch + '\'' +
                ", age='" + age + '\'' +
                ", address='" + address + '\'' +
                ", contactnumber='" + contactnumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatch() {
        return batch;
    }


    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}