package com.example.myapplication;

import java.io.Serializable;

public class dishInformation implements Serializable {
    String dish_name;
    String dish_price;
    String dish_type;
    String dish_key;

    public dishInformation(String dish_name,String dish_id, String price,String dish_type) {
        this.dish_name = dish_name;
        this.dish_price = price;
        this.dish_type=dish_type;

    }
    public dishInformation(){};

    public String getDish_price() {
        return dish_price;
    }

    public void setDish_price(String price) {
        this.dish_price = price;
    }

    public String getDish_type(){return this.dish_type;}

    public void setDish_type(String dish_type){this.dish_type=dish_type;}

    public String getDish_key_() {
        return dish_key;
    }

    public void setDish_key(String dish_key) {
        this.dish_key = dish_key;
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
