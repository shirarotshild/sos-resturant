package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editDish extends AppCompatActivity {
    EditText txtdishName;
    EditText txtdishPrice;
    EditText txtdishType;
    dishInformation dish;
    Button button_save;
    public DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dish);
        txtdishName= (EditText)findViewById(R.id.editText_dis_name);
        txtdishPrice = (EditText)findViewById(R.id.editText_price);
        txtdishType=(EditText)findViewById(R.id.editText_type);
        button_save= (Button)findViewById(R.id.button_save);
        dish=(dishInformation) getIntent().getSerializableExtra("dish");
        txtdishName.setText(dish.getDish_name());
        txtdishPrice.setText(dish.getDish_price());
        txtdishType.setText(dish.getDish_type());
        ref= FirebaseDatabase.getInstance().getReference().child("dishInformation");
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dishName= txtdishName.getText().toString();
                String dishPrice=txtdishPrice.getText().toString();
                String dishType=txtdishType.getText().toString();
                dish.setDish_name(dishName);
                dish.setDish_price(dishPrice);
                dish.setDish_type(dishType);
                // String dishId1=Integer.toString(DI.getDish_id());
                ref.child(dish.getDish_type()).child(dish.getDish_key_()).setValue(dish);
                Toast.makeText(editDish.this, "data insert sucessfully",Toast.LENGTH_LONG).show();
                Intent i= new Intent(editDish.this, editMenuDrink.class);
                startActivity(i);
            }
        });

    }
}
