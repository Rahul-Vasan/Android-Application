package com.example.fbans.projecthm.utils;

public class Staff {
    String name,age,gender,mno;


    public Staff(String name, String age, String gender, String mno) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.mno = mno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }
}
