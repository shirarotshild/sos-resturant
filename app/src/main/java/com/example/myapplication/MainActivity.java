package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.mainScreenManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button_manager;
    Button button_workers;
    Button button_members;
    Button button_guest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "fireBase co ", Toast.LENGTH_LONG).show();
        button_manager=(Button)findViewById(R.id.button_manger);
        button_workers=(Button) findViewById(R.id.button_workers);
        button_members=(Button) findViewById(R.id.button_members);
        button_guest=(Button) findViewById(R.id.button_guest);

        button_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, mainScreenManager.class);
                startActivity(i);
            }
        });

        button_workers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, mainScreenWorkers.class);
                startActivity(i);
            }
        });

        button_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, mainScreenMembers.class);
                startActivity(i);
            }
        });

        button_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, mainScreenGuest.class);
                startActivity(i);
            }
        });
    }

   @Override
    public void onClick(View v) {
        if (v==button_manager){
        }
        if (v==button_workers){
        }
        if (v==button_members){
        }
       if (v==button_guest){
       }
       }


}


