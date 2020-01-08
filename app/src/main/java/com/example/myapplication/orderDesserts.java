package com.example.myapplication;

import android.os.Bundle;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class orderDesserts extends AppCompatActivity {
    ListView list_dishes;
    DatabaseReference reff;
    DatabaseReference ref;
    ArrayList<String> arrayList= new ArrayList<>();
    ArrayAdapter adapter;
    ArrayList<dishInformation> dishes= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_desserts);
        reff= FirebaseDatabase.getInstance().getReference("order").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("desserts");
        ref= FirebaseDatabase.getInstance().getReference("dishInformation").child("desserts");
        list_dishes= findViewById(R.id.list_dishes);
        adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, dishes);
        list_dishes.setAdapter(adapter);
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key=dataSnapshot.getKey();
                arrayList.add(key);


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




                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(String key: arrayList) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                if(key.equals(data.getKey())){
                                    dishes.add(data.getValue(dishInformation.class));
                                    adapter.notifyDataSetChanged();

                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });







    }
}
