package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class mainScreenManager extends AppCompatActivity implements View.OnClickListener{
    Button button_out_manager;
    Button button_employees;
    Button button_edit_menu;
    Button button_vendors;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_manager);
        button_out_manager = findViewById(R.id.button_out_manager);
        button_employees = findViewById(R.id.button_emloyees);
        button_edit_menu=findViewById(R.id.button_edit_menu);
        button_vendors=findViewById(R.id.button_vendors);
        button_out_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(mainScreenManager.this, loginManager.class);
                startActivity(intToMain);
            }
        });

        button_employees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mainScreenManager.this, employees.class);
                startActivity(i);
            }
        });

        button_edit_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mainScreenManager.this, editMenu.class);
                startActivity(i);
            }
        });


    }
    public void onClick(View v) {
        if (v == button_out_manager) {
        }
        if (v == button_employees) {
        }
        if (v == button_edit_menu) {
        }
    }
        }
