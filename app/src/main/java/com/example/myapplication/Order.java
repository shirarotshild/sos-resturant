package com.example.myapplication;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Order implements Serializable {

    String orderId;
    String memberUID;
    long prices;
    ArrayList<String> dishKeys= new ArrayList<>();
    ArrayList<dishInformation> dishesName= new ArrayList<>();
    String userName;
    long table;
    int status;
    ArrayList<String> oldOrders= new ArrayList<>();

    String date = "";  // Start date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Calendar c = Calendar.getInstance();


    Order (){
           date= sdf.format(c.getTime());
    }




    public void addSameUser(Order second){
        getDishKeys().addAll(second.getDishKeys());
        oldOrders.add(second.getOrderId());
        prices=prices+second.prices;
        if(getMemberUID()==null) {
            setOrderId(second.getOrderId());
            setStatus(second.getStatus());
            setMemberUID(second.getMemberUID());
            setTable(second.getTable());
        }

    }

    public String dateOnly(){
        String []a= date.split(" ");
        return a[0];
    }
    public long getPrices() {
        return prices;
    }

    public void setPrices(long prices) {
        this.prices = prices;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<String> getDishKeys() {
        return dishKeys;
    }

    public void setDishKeys(ArrayList<String> dishKeys) {
        this.dishKeys = dishKeys;
    }

    public String getMemberUID() {
        return memberUID;
    }

    public void setMemberUID(String memberUID) {
        this.memberUID = memberUID;
    }

    public long getTable() {
        return table;
    }

    public void setTable(long table) {
        this.table = table;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String toString() {
        String s="";
        if(status==3){
            s="-"+table+"-\n";
        }
        else if(status==4){
            for (dishInformation dish : dishesName) {
                s = s + dish + "\n";
            }
            //s=s+"price: "+prices;
        }
        else {
            if (status == 0) {
                s = s + "--waiting--";
            } else if (status == 1) {
                s = s + "--In preparation--";
            } else if (status == 2) {
                s = s + "--sent to table--";
            }
            s=s+"\n"+"-"+table+"- "+ userName + ":\n";
            for (dishInformation dish : dishesName) {
                s = s + dish.getDish_name() + "\n";
            }
        }


        return s;
    }





}