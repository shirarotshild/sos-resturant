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


    public dishInformation getDishName(String key, DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            for (DataSnapshot item : data.getChildren()) {
                if (key.equals(item.getKey())) {
                    return (item.getValue(dishInformation.class));
                }
            }
        }
        return new dishInformation();
    }

    public String getUserName(String uids,DataSnapshot dataSnapshot) {

        String [] usersUID= uids.split(",");
        String users="";
        for(String uid: usersUID) {
            for (DataSnapshot data : dataSnapshot.getChildren()) {
                for (DataSnapshot item : data.getChildren()) {
                    if (uid.equals(item.getKey())) {
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
