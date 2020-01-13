package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class mainScreenEmployee extends AppCompatActivity {

    ListView kitchenOrder;
    ArrayAdapter adapter;
    ArrayList<Order> dishes= new ArrayList<>();
    DatabaseReference pullOrder;
    DatabaseReference pullDish;
    DatabaseReference pullUserName;
    DatabaseReference check;
    Button button_check;
    DatabaseAction DA= new DatabaseAction();

    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_employee);

        button_check= findViewById(R.id.button_check);
        check= FirebaseDatabase.getInstance().getReference("Checks");
        kitchenOrder = findViewById(R.id.kitchen_orders);
        pullUserName = FirebaseDatabase.getInstance().getReference("Users");
        pullOrder = FirebaseDatabase.getInstance().getReference("try");
        pullDish = FirebaseDatabase.getInstance().getReference("dishInformation");
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dishes);
        kitchenOrder.setAdapter(adapter);

        pullDish.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot d) {


                pullUserName.addListenerForSingleValueEvent(new ValueEventListener() {
                    Order order;

                    @Override
                    public void onDataChange(@NonNull final DataSnapshot data) {


                        pullOrder.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                order = dataSnapshot.getValue(Order.class);
                                order.userName = DA.getUserName(order.getMemberUID(), data);
                                for (String key : order.getDishKeys())
                                    order.dishesName.add(DA.getDishName(key, d));

                                dishes.add(order);
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


        kitchenOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                final Order order = (Order) parent.getItemAtPosition(position);
                //view.setSelected(true);
                //   view.setBackgroundColor(Color.GREEN_FIELD_NUMBER);
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(mainScreenEmployee.this);
                mBuilder.setTitle("change order status");
                if (order.status == 0) {
                    mBuilder.setMessage("send to preparation");
                    mBuilder.setPositiveButton("send", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            view.setSelected(true);
                            order.status++;
                            pullOrder.child(order.getOrderId()).child("status").setValue(order.status++);
                            startActivity(new Intent(mainScreenEmployee.this,mainScreenEmployee.class));
                        }
                    });
                } else if (order.status == 1) {
                    mBuilder.setMessage("send to table");

                    mBuilder.setPositiveButton("send", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //change color
                            order.status++;
                            pullOrder.child(order.getOrderId()).child("status").setValue(order.status++);
                            startActivity(new Intent(mainScreenEmployee.this,mainScreenEmployee.class));

                        }
                    });
                } else if (order.status == 2) {
                    mBuilder.setMessage("send to payment");
                    mBuilder.setPositiveButton("send", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pullOrder.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        if (data.getKey().equals(order.getOrderId())) {
                                            pullOrder.child(data.getKey()).removeValue();
                                            startActivity(new Intent(mainScreenEmployee.this, mainScreenEmployee.class));
                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            check.child(order.getOrderId()).setValue(order);
                        }
                    });
                }
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainScreenEmployee.this,Check.class));
            }
        });
    }

}