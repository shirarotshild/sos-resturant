package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class editMenu extends AppCompatActivity {

    Button button_edit_drink;
    Button button_edit_pizza;
    Button button_edit_desserts;
    Button button_edit_pasta;
    Button button_edit_salads;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);
        button_edit_drink = findViewById(R.id.button_menu_drink);
        button_edit_pizza=findViewById(R.id.button_edit_pizza);
        button_edit_desserts=findViewById(R.id.button_edit_desserts);
        button_edit_pasta=findViewById(R.id.button_edit_pasta);
        button_edit_salads=findViewById(R.id.button_edit_salads);

        button_edit_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(editMenu.this, editMenuDrink.class);
                startActivity(i);
            }
        });

        button_edit_desserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(editMenu.this, editMenuDesserts.class);
                startActivity(i);
            }
        });
        button_edit_pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(editMenu.this, editMenuPizza.class);
                startActivity(i);
            }
        });

        button_edit_pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(editMenu.this, editMenuPasta.class);
                startActivity(i);
            }
        });

        button_edit_salads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(editMenu.this, editMenuSalads.class);
                startActivity(i);
            }
        });




    }
}
