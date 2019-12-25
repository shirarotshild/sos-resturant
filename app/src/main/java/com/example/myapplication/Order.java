package com.example.myapplication;

public class Order {

    private String dish;
    private String uid;

    Order (){}

    Order(String name,String uid){

        this.dish=name;
        this.uid=uid;
    }
    public void setName(String name) {
        this.dish = name;
    }

    public String getName() {
        return dish;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String toString(){return getName();}
}

