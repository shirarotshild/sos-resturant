package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Tables extends AppCompatActivity {
    Button button_1,button_2,button_3,button_4,button_5,button_6,button_7,button_8,button_9;
    DatabaseReference tables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        button_1= findViewById(R.id.button_1);
        button_2= findViewById(R.id.button_2);
        button_3= findViewById(R.id.button_3);
        button_4= findViewById(R.id.button_4);
        button_5= findViewById(R.id.button_5);
        button_6= findViewById(R.id.button_6);
        button_7= findViewById(R.id.button_7);
        button_8= findViewById(R.id.button_8);
        button_9= findViewById(R.id.button_9);

        tables= FirebaseDatabase.getInstance().getReference("order").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Table");

        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               tables.setValue(1);
                startActivity(new Intent(Tables.this, mainScreenMembers.class));
            }
        });
        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tables.setValue(2);
                startActivity(new Intent(Tables.this, mainScreenMembers.class));
            }
        });
        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tables.setValue(3);
                startActivity(new Intent(Tables.this, mainScreenMembers.class));
            }
        });
        button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tables.setValue(4);
                startActivity(new Intent(Tables.this, mainScreenMembers.class));
            }
        });
        button_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tables.setValue(5);
                startActivity(new Intent(Tables.this, mainScreenMembers.class));
            }
        });
        button_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tables.setValue(6);
                startActivity(new Intent(Tables.this, mainScreenMembers.class));
            }
        });
        button_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tables.setValue(7);
                startActivity(new Intent(Tables.this, mainScreenMembers.class));
            }
        });
        button_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tables.setValue(8);
                startActivity(new Intent(Tables.this, mainScreenMembers.class));
            }
        });
        button_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tables.setValue(9);
                startActivity(new Intent(Tables.this, mainScreenMembers.class));
            }
        });

    }
}
