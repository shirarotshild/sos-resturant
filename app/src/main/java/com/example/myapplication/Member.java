package com.example.myapplication;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Member{

    private String name;
    private String mail;
    private String phoneNum;
    private String uid;


    Member(){}

    Member(String name, String mail,String phone,String uid){
        this.name= name;
        this.mail= mail;
        this.phoneNum= phone;
        this.uid=uid;

    }

    Member(FirebaseAuth a, DatabaseReference r){
        FirebaseUser u= a.getCurrentUser();
        String uid= u.getUid();
        name= r.child(uid).child("name").getKey();
        mail =r.child(uid).child("mail").getKey();
        phoneNum= r.child(uid).child("phoneNum").getKey();

    }




    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNum(){return phoneNum;}
}