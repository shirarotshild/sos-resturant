package com.example.myapplication;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Member implements Permit{

    private String name;
    private String mail;
    private String uid;
    int kind= 1;

    Member(String name, String mail,String uid){
        this.name= name;
        this.mail= mail;
        this.uid=uid;

    }

    Member(FirebaseAuth a, DatabaseReference r){
        FirebaseUser u= a.getCurrentUser();
        String uid= u.getUid();
        name= r.child(uid).child("name").getKey();
        mail =r.child(uid).child("mail").getKey();

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

    @Override
    public String getUid() {
        return uid;
    }


}
