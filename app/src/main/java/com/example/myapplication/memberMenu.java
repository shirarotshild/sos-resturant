package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class memberMenu extends AppCompatActivity {
    DatabaseReference reff;
    DatabaseReference order;
    ArrayList<String> arrayList= new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView list_dishes;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabase;
    Permit m;
    Button myOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_menu);
        firebaseDatabase=FirebaseDatabase.getInstance();
        reff= FirebaseDatabase.getInstance().getReference("dishInformation");
        list_dishes= findViewById(R.id.list_dishes);
        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        list_dishes.setAdapter(adapter);
        mFirebaseAuth = FirebaseAuth.getInstance();
        myOrder= findViewById(R.id.button_my_order);
        mDatabase = FirebaseDatabase.getInstance().getReference("Orders");


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


        list_dishes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                final String sauv=(String) parent.getItemAtPosition(position);
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(memberMenu.this);
                mBuilder.setTitle("Valide Your Command ");
                mBuilder.setMessage(sauv);

                mBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Accept", Toast.LENGTH_SHORT).show();
                        // m= new Member(mFirebaseAuth,mDatabase);

                        Order orders =new Order(sauv,mFirebaseAuth.getCurrentUser().getUid());
                        FirebaseDatabase.getInstance().getReference("Orders")
                                .child(mFirebaseAuth.getCurrentUser().getUid()).push()
                                .setValue(orders).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                }
                            }

                        });

                    }});
                mBuilder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Deny", Toast.LENGTH_SHORT).show();

                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();



            }

        });

        myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(memberMenu.this, MyOrder.class);
                startActivity(i);
            }
        });
    }
}