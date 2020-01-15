package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class favoriteDishes extends AppCompatActivity {
  Button button_favorite_drink;
  Button button_favorite_pizzas;
  Button button_favorite_desserts;
  Button button_favorite_pasta;
  Button button_favorite_salads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_dishes);
        button_favorite_drink=findViewById(R.id.button_favorite_drink);
        button_favorite_pizzas=findViewById(R.id.button_favorite_pizzas);
        button_favorite_desserts=findViewById(R.id.button_favorite_desserts);
        button_favorite_pasta=findViewById(R.id.button_favorite_pasta);
        button_favorite_salads=findViewById(R.id.button_favorite_salds);
        button_favorite_salads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(favoriteDishes.this, favoriteSalads.class);
                startActivity(i);
            }
        });
        button_favorite_pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(favoriteDishes.this,favoritePasta.class);
                startActivity(i);
            }
        });

        button_favorite_desserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(favoriteDishes.this,favoriteDesserts.class);
                startActivity(i);
            }
        });
        button_favorite_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(favoriteDishes.this, favoriteDrink.class);
                startActivity(i);
            }
        });

        button_favorite_pizzas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(favoriteDishes.this, favoritePizzas.class);
                startActivity(i);
            }
        });


    }
}
