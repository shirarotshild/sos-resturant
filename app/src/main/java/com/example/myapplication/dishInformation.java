package com.example.myapplication;

public class dishInformation {

    String dish_name;
    String dish_id;
    String dish_price;

    public dishInformation(String dish_name,String dish_id, String price) {
        this.dish_name = dish_name;
        this.dish_id=dish_id;
        this.dish_price=price;
    }
    public dishInformation(){};

    public String getDish_id() {
        return dish_id;
    }

    public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
    }

    public String getPrice() {
        return dish_price;
    }

    public void setPrice(String price) {
        this.dish_price = price;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String toString(){
        return this.dish_name+" "+dish_price;
    }


}
