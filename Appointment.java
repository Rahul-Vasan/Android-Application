package com.example.fbans.projecthm.utils;

public class Appointment {
    int id;
    String patname,appotime,mobile,visitType,date;

    public Appointment(int id,String patname, String appotime, String mobile, String visitType) {
        this.id = id;
        this.patname = patname;
        this.appotime = appotime;
        this.mobile = mobile;
        this.visitType = visitType;
    }

    public Appointment(String patname, String appotime, String visitType, String date) {
        this.patname = patname;
        this.appotime = appotime;
        this.visitType = visitType;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatname() {
        return patname;
    }

    public void setPatname(String patname) {
        this.patname = patname;
    }

    public String getAppotime() {
        return appotime;
    }

    public void setAppotime(String appotime) {
        this.appotime = appotime;
    }

    public String getAge() {
        return mobile;
    }

    public void setAge(String age) {
        mobile = age;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
