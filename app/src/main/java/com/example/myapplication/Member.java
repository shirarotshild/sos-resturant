package com.example.myapplication;

import com.google.firebase.database.DatabaseReference;

public class Member implements Permit{

    private String name;
    private String mail;
    int kind= 1;

    Member(String name, String mail){
        this.name= name;
        this.mail= mail;
    }

    @Override
    public int getKind() {
        return kind;
    }

    @Override
    public String getMail() {
        return mail;
    }

    @Override
    public String getName() {
        return name;
    }


}
