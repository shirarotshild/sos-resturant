package com.example.myapplication;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class DatabaseAction {

    public void getKeyByValue(String name, String value, DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            if (value.equals(data.getValue())) {
                name = data.getKey();
                break;
            }
        }
        //exception?
    }

    public void getValueByKey(Object value, String key, DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            if (key.equals(data.getKey())) {
                value = data.getValue();
                break;
            }
        }
        //exception?
    }


    public String getDishName(String key, DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            for (DataSnapshot item : data.getChildren()) {
                if (key.equals(item.getKey())) {
                    return (item.getValue(dishInformation.class).getDish_name());
                }
            }
        }
        return key;
    }

    public String getUserName(String uids,DataSnapshot dataSnapshot) {

        String [] usersUID= uids.split(",");
        String users="";
        for(String uid: usersUID) {
            for (DataSnapshot data : dataSnapshot.getChildren()) {
                for (DataSnapshot item : data.getChildren()) {
                    if (uid.equals(item.getKey())) {
                        Log.d("uid", item.getKey());
                        Log.d("emplo", data.getKey());
                        if (data.getKey().equals("Employees")) {
                            users=users+" "+ item.getValue(employeesInformation.class).getName();
                        } else if (data.getKey().equals("Members")) {
                            users=users+" "+(item.getValue(Member.class).getName());
                        } else {
                            users=users+" the Manager";
                        }
                    }
                }
            }
        }
        return users;
    }
}
