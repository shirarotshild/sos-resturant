package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Check extends AppCompatActivity {

    ListView cheks_list;
    DatabaseReference check;
    DatabaseReference save;
    DatabaseReference pullDish;
    DatabaseReference pullUserName;
    ArrayList<Order> array= new ArrayList<>();
    ArrayAdapter adapter;
    DatabaseAction DA= new DatabaseAction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        cheks_list= findViewById(R.id.cheks_list);
        check = FirebaseDatabase.getInstance().getReference("Checks");
        save= FirebaseDatabase.getInstance().getReference("Paid checks");
        pullUserName = FirebaseDatabase.getInstance().getReference("Users");
        pullDish = FirebaseDatabase.getInstance().getReference("dishInformation");
        adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1, array);
        cheks_list.setAdapter(adapter);

        pullDish.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot d) {
                pullUserName.addListenerForSingleValueEvent(new ValueEventListener() {
                    Order order;

                    @Override
                    public void onDataChange(@NonNull final DataSnapshot data) {


                        check.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                order = dataSnapshot.getValue(Order.class);
                                boolean oldUser= false;
                                for(Order or: array){
                                    // oldUser= or.addByUser(order);
                                    oldUser= or.addByTable(order);
                                    if(oldUser){
                                        or.userName = DA.getUserName(or.getMemberUID(), data);
                                        for (String key : or.getDishKeys())
                                            or.dishesName.add(DA.getDishName(key, d));
                                        break;
                                    }
                                }
                                if(!oldUser) {
                                    array.add(order);

                                    order.userName = DA.getUserName(order.getMemberUID(), data);
                                    for (String key : order.getDishKeys())
                                        order.dishesName.add(DA.getDishName(key, d));
                                }
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

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        check.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Order order= dataSnapshot.getValue(Order.class);
//                boolean oldUser= false;
//                for(Order or: array){
//                   // oldUser= or.addByUser(order);
//                    oldUser= or.addByTable(order);
//                }
//                if(!oldUser){
//                    array.add(order);
//                }
//                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }
}
