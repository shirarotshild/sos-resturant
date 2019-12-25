package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    private ArrayList<String> dishes;
    String orderId;

    Order (){
        dishes= new ArrayList<>();

    }


    public void addDish(String dish){
        dishes.add(dish);
    }

    public int getSize(){
        return dishes.size();
    }
    public ArrayList<String> getDishes(){return dishes;}


    public String toString(){return orderId;}
}