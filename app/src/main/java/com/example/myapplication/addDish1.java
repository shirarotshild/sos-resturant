package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addDish1 extends AppCompatActivity {
    EditText txtdisName;
    EditText txtdisId;
    EditText txtprice;
    Button button_save;
    public DatabaseReference ref;
    dishInformation DI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish1);

        txtdisName= (EditText)findViewById(R.id.editText_dis_name);
        txtdisId = (EditText)findViewById(R.id.editText_dis_id);
        txtprice = (EditText)findViewById(R.id.editText_price);
        ref= FirebaseDatabase.getInstance().getReference().child("dishInformation");
        DI= new dishInformation();
        button_save= (Button)findViewById(R.id.button_save);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dishName= txtdisName.getText().toString();
                String dishId= txtdisId.getText().toString();
                String price= txtprice.getText().toString();
                DI.setDish_name(dishName);
                DI.setDish_id(dishId);
                DI.setPrice(price);
                ref.child(dishName).setValue(DI);
                //ref.push().setValue(EI);
                Toast.makeText(addDish1.this, "data insert sucessfully",Toast.LENGTH_LONG).show();
            }
        });



            }
}
