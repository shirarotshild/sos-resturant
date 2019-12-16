package com.example.myapplication;

public class employeesInformation {


    String last_name;
    String first_name;
    String number;


    public employeesInformation(){}
    public employeesInformation(String last_name,String first_name, String number) {
        this.last_name = last_name;
        this.first_name=first_name;
        this.number=number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }


    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }



    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String toString(){
        return this.first_name+" "+last_name+" phone number:"+number;
    }

}
