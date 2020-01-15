package com.example.myapplication;

public class Call {

    String uid;
    long status;
    boolean start;
    String userName;
    long table;

    Call(){ }
    Call(String uid, long status){
        this.uid=uid;
        this.status= status;
    }


    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }


    public String message(){
        String string="";
        if(getStatus()==0){
            string= userName+" asked for help at table number "+table;
        }
        else if(getStatus()==1){
            string= userName+" asked for check at table number "+table;
        }
        else{
            string= userName+" finished eating. please clean table number "+table;
        }
        return string;
    }


    public String toString(){
        String string;
        if(start){
            string= "--in process--\n";
        }
        else{
            string= "--waiting--\n";
        }


        string= string+userName+"\n";
        if(status==0){
            string= string+"call";
        }
        else if(getStatus()==1){
            string=string+"check";
        }
        else {
            string=string+"clean";
        }

        return string;
    }
}
