package com.example.fbans.projecthm.utils;

public class Instructions {
    String patname,staffname,roomnum,instruction,id;

    public Instructions(String staffname) {
        this.staffname = staffname;
    }

    public Instructions(String patname, String roomnum, String instruction) {
        this.patname = patname;
        this.roomnum = roomnum;
        this.instruction = instruction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatname() {
        return patname;
    }

    public void setPatname(String patname) {
        this.patname = patname;
    }

    public String getStaffname() {
        return staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

    public String getRoomnum() {
        return roomnum;
    }

    public void setRoomnum(String roomnum) {
        this.roomnum = roomnum;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
