package com.example.myapplication;

import java.io.Serializable;

public class employeesInformation implements Serializable {


    private String name;
    String mail;
    String phoneNum;
    private String ID;
    String uid;


    public employeesInformation(){}

    public employeesInformation(String name,String mail, String number,String id,String uid) {
        this.name = name;
        this.mail=mail;
        this.phoneNum=number;
        this.ID=id;
        this.uid= uid;
    }

    public String getUid() {
        return uid;
    }

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNum(){return phoneNum;}
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNum(String num) {
        this.phoneNum = num;
    }


    public String toString(){
        return "Name: "+name+"\ne-Mail: "+mail+"\nPhone number: "+phoneNum+"\n ID: "+ID;
    }
}
