package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class editMenu extends AppCompatActivity {

    Button button_add_dish;
    ListView list_dishes;
    DatabaseReference reff;
    ArrayList<String> arrayList= new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        button_add_dish= findViewById(R.id.button_add_dish);
        button_add_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(editMenu.this, addDish1.class);
                startActivity(i);
            }
        });

        reff= FirebaseDatabase.getInstance().getReference("dishInformation");

        list_dishes= findViewById(R.id.list_dishes);
        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        list_dishes.setAdapter(adapter);


        reff.addChildEventListener(new ChildEventListener() {
            @Override
            //1111
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String string=  dataSnapshot.getValue(dishInformation.class).toString();
                arrayList.add(string);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void onClick(View v) {
        if (v == button_add_dish) {
        }
    }
}
