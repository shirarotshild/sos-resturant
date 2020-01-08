package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class editMenuDesserts extends AppCompatActivity {
    Button button_add_dish;
    ListView list_dishes;
    DatabaseReference reff;
    ArrayList<dishInformation> arrayList= new ArrayList<>();
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu_desserts);
        button_add_dish= (Button)findViewById(R.id.button_add_dish);
        button_add_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(editMenuDesserts.this, addDish1.class);
                startActivity(i);
            }
        });
        reff= FirebaseDatabase.getInstance().getReference("dishInformation").child("desserts");
        list_dishes= findViewById(R.id.list_dishes);
        adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        list_dishes.setAdapter(adapter);

        reff.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                dishInformation dish=  dataSnapshot.getValue(dishInformation.class);
                String key=dataSnapshot.getKey();
                dish.setDish_key(key);
                arrayList.add(dish);
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


        list_dishes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //   Toast.makeText(getApplicationContext(), (dishInformation) parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                final dishInformation dish=(dishInformation) parent.getItemAtPosition(position);
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(editMenuDesserts.this);
                mBuilder.setTitle("Valide Your Command ");
                mBuilder.setMessage(dish.getDish_name());

                mBuilder.setPositiveButton("edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "edit", Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(editMenuDesserts.this, editDish.class);
                        i.putExtra("dish",dish);
                        startActivity(i);
                        // m= new Member(mFirebaseAuth,mDatabase);





                    }});
                mBuilder.setNegativeButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { ;
                        reff.child(dish.getDish_key_()).removeValue();
                        Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(editMenuDesserts.this, editMenuDesserts.class));
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();



            }

        });
    }



    public void onClick(View v) {
        if (v == button_add_dish) {
        }
    }
}
