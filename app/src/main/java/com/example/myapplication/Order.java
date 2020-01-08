package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    private ArrayList<String> dishes;
    String orderId;

    Order (){ }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}