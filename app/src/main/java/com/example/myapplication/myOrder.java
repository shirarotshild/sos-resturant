package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class myOrder extends AppCompatActivity {
    ListView list_type;
    DatabaseReference reff;
    DatabaseReference ref;
    ArrayList<String> arrayList= new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order);
        reff = FirebaseDatabase.getInstance().getReference("order").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref=FirebaseDatabase.getInstance().getReference("order").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        list_type = findViewById(R.id.list_dishes);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        list_type.setAdapter(adapter);

        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String type = dataSnapshot.getKey();
                arrayList.add(type);
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


        list_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String type=(String) parent.getItemAtPosition(position).toString();
                if(type.equals("drink")){
                    Intent i= new Intent(myOrder.this, orderDrink.class);
                    startActivity(i);
                }
                if(type.equals("salads")){
                    Intent i= new Intent(myOrder.this, orderSalads.class);
                    startActivity(i);
                }
                if(type.equals("pasta")){
                    Intent i= new Intent(myOrder.this, orderPasta.class);
                    startActivity(i);
                }
                if(type.equals("pizzas")){
                    Intent i= new Intent(myOrder.this, orderPizzas.class);
                    startActivity(i);

                }
                if(type.equals("desserts")){
                    Intent i= new Intent(myOrder.this, orderDesserts.class);
                    startActivity(i);
                }




            }
        });



}}