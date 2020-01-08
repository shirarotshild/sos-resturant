 package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

 public class memberMenu extends AppCompatActivity {
    Button button_edit_drink;
    Button button_edit_pizza;
    Button button_edit_desserts;
    Button button_edit_pasta;
    Button button_edit_salads;
    Button button_my_order;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_menu);
        button_edit_drink = findViewById(R.id.button_menu_drink);
        button_edit_pizza=findViewById(R.id.button_edit_pizza);
        button_edit_desserts=findViewById(R.id.button_edit_desserts);
        button_edit_pasta=findViewById(R.id.button_edit_pasta);
        button_edit_salads=findViewById(R.id.button_edit_salads);
        button_my_order=findViewById(R.id.button_my_order);

        button_my_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(memberMenu.this,myOrder.class);
                startActivity(i);
            }
        });

        button_edit_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(memberMenu.this, menuDrink.class);
                startActivity(i);
            }
        });

        button_edit_desserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(memberMenu.this, menuDessert.class);
                startActivity(i);
            }
        });
        button_edit_pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(memberMenu.this, menuPizzas.class);
                startActivity(i);
            }
        });

        button_edit_pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(memberMenu.this, menuPasta.class);
                startActivity(i);
            }
        });

        button_edit_salads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(memberMenu.this, menuSalads.class);
                startActivity(i);
            }
        });




    }
}



