package com.example.myapplication;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Order implements Serializable {

    String orderId;
    String memberUID;
    ArrayList<String> dishKeys= new ArrayList<>();
    ArrayList<String> dishesName= new ArrayList<>();
    String userName;
    long table;
    int status;

    String date = "";  // Start date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Calendar c = Calendar.getInstance();


    Order (){
           date= sdf.format(c.getTime());
    }


    public boolean addByTable(Order second){
        if(getTable() == second.getTable()){
            dishKeys.addAll(second.dishKeys);
            if(!getMemberUID().equals(second.getMemberUID())) {
                memberUID = memberUID + "," + second.getMemberUID();
            }
            return true;
        }
        return false;
    }
    public boolean addByUser(Order second){
        if(getMemberUID().equals(second.getMemberUID())){
            getDishKeys().addAll(second.dishKeys);
            return true;
        }
        return false;
    }

    public String dateOnly(){
        String []a= date.split(" ");
        return a[0];
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
        String s ="-"+table+"-\n"+ userName + ":\n";
        for (String dish : dishesName) {
            s = s + dish + "\n";
        }
        if(status==0){
            s=s +"--waiting--";
        }
        else if(status==1){
            s=s+"--In preparation--";
        }
        else if(status==2){
            s=s+"--sent to table--";
        }


        return s;
    }


}