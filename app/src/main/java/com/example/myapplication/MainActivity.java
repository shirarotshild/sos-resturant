package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  {

    Button button_manager;
    Button button_workers;
    Button button_members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "fireBase co ", Toast.LENGTH_LONG).show();
        button_manager = (Button) findViewById(R.id.button_manger);
        button_workers = (Button) findViewById(R.id.button_workers);
        button_members = (Button) findViewById(R.id.button_members);


        button_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, loginManager.class);
                startActivity(i);
            }
        });

        button_workers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginEmployee.class);
                startActivity(i);
            }
        });

        button_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginMember.class);
                startActivity(i);
            }
        });


    }


}


