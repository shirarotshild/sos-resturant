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
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class mainScreenEmployee extends AppCompatActivity {

    ListView kitchenOrder, callList;
    ArrayAdapter adapter;
    ArrayList<Order> dishes= new ArrayList<>();
    DatabaseReference pullOrder;
    DatabaseReference pullDish;
    DatabaseReference pullUserName;
    DatabaseReference payed, waiterCalls;
    Button button_check;
    DatabaseAction DA= new DatabaseAction();
    ArrayList<Call> calls= new ArrayList<>();
    ArrayAdapter adapterCall;


    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_employee);

     //   button_check= findViewById(R.id.button_check);
        callList= findViewById(R.id.call);
        payed= FirebaseDatabase.getInstance().getReference("payed bills");
        kitchenOrder = findViewById(R.id.kitchen_orders);
        pullUserName = FirebaseDatabase.getInstance().getReference("Users");
        pullOrder = FirebaseDatabase.getInstance().getReference("kitchenOrder");
        pullDish = FirebaseDatabase.getInstance().getReference("dishInformation");
        waiterCalls= FirebaseDatabase.getInstance().getReference("waiter call");
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dishes);
        kitchenOrder.setAdapter(adapter);
        adapterCall = new ArrayAdapter(this, android.R.layout.simple_list_item_1, calls);
        callList.setAdapter(adapterCall);


        pullUserName.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull final DataSnapshot data) {

                waiterCalls.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                        FirebaseDatabase.getInstance().getReference("order").addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot num) {
                                Call call= dataSnapshot.getValue(Call.class);
                                call.table=0;
                                call.userName=DA.getUserName(call.getUid(),data);
                                for(DataSnapshot i: num.getChildren()){
                                    if(i.getKey().equals(call.getUid())){
                                        for(DataSnapshot j: i.getChildren()){
                                            if(j.getKey().equals("Table")){
                                                call.table= j.getValue(Long.class);
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                                calls.add(call);
                                adapterCall.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


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
                                if(order.getStatus()<4) {
                                    order.userName = DA.getUserName(order.getMemberUID(), data);
                                    for (String key : order.getDishKeys())
                                        order.dishesName.add(DA.getDishName(key, d));

                                    dishes.add(order);
                                    adapter.notifyDataSetChanged();
                                }
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


        callList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Call call = (Call) parent.getItemAtPosition(position);
                setCall(call);
                final AlertDialog.Builder mBulder = new AlertDialog.Builder(mainScreenEmployee.this);
                mBulder.setMessage(call.message());
                if (call.getStatus() == 0) {
                    if (!call.isStart()) {
                        mBulder.setPositiveButton("send", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                waiterCalls.child(call.getUid()).child("start").setValue(true);
                                startActivity(new Intent(mainScreenEmployee.this, mainScreenEmployee.class));
                            }
                        });

                    } else {
                        mBulder.setPositiveButton("done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                waiterCalls.child(call.getUid()).removeValue();
                                startActivity(new Intent(mainScreenEmployee.this, mainScreenEmployee.class));
                            }
                        });
                    }
                } else if (call.getStatus() == 1) {

                    mBulder.setPositiveButton("bill", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pullDish.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull final DataSnapshot d) {


                                    pullUserName.addListenerForSingleValueEvent(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(@NonNull final DataSnapshot data) {

                                            pullOrder.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    final AlertDialog.Builder check = new AlertDialog.Builder(mainScreenEmployee.this);
                                                    for (DataSnapshot i : dataSnapshot.getChildren()) {
                                                        if (i.getValue(Order.class).getMemberUID().equals(call.getUid())) {

                                                            final Order order = i.getValue(Order.class);
                                                            order.setStatus(4);
                                                            order.userName = DA.getUserName(order.getMemberUID(), data);
                                                            for (String key : order.getDishKeys())
                                                                order.dishesName.add(DA.getDishName(key, d));

                                                            check.setTitle("bill of "+ order.userName);
                                                            check.setMessage(order.toString());
                                                            check.setPositiveButton("pay", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    String date = "";  // Start date
                                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                                                    Calendar c = Calendar.getInstance();
                                                                    order.setDate(sdf.format(c.getTime()));
                                                                    payed.child(order.dateOnly()).child(order.getOrderId()).setValue(order);
                                                                    pullOrder.child(order.getOrderId()).removeValue();
                                                                    waiterCalls.child(call.getUid()).removeValue();
                                                                    startActivity(new Intent(mainScreenEmployee.this, mainScreenEmployee.class));
                                                                    Toast.makeText(mainScreenEmployee.this, "done", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                            check.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                    startActivity(new Intent(mainScreenEmployee.this, mainScreenEmployee.class));
                                                                }
                                                            });

                                                            break;
                                                        }

                                                    }
                                                    AlertDialog mDialog = check.create();
                                                    mDialog.show();

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
                        }
                    });
                }
                else{
                    if (!call.isStart()) {
                        mBulder.setPositiveButton("send", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                waiterCalls.child(call.getUid()).child("start").setValue(true);
                                startActivity(new Intent(mainScreenEmployee.this, mainScreenEmployee.class));
                            }
                        });

                    } else {
                        mBulder.setPositiveButton("done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                waiterCalls.child(call.getUid()).removeValue();
                                FirebaseDatabase.getInstance().getReference("order").child(call.getUid()).removeValue();
                                startActivity(new Intent(mainScreenEmployee.this, mainScreenEmployee.class));
                            }
                        });
                    }

                }
                mBulder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(mainScreenEmployee.this, mainScreenEmployee.class));

                    }
                });
                AlertDialog mDialog = mBulder.create();
                mDialog.show();
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
                            pullOrder.child(order.getOrderId()).child("status").setValue(order.status);
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
                            pullOrder.child(order.getOrderId()).child("status").setValue(order.status);
                            startActivity(new Intent(mainScreenEmployee.this, mainScreenEmployee.class));

                        }
                    });
                }
//                 else if (order.status == 2) {
//
//                    mBuilder.setMessage("send to payment");
//                    mBuilder.setPositiveButton("send", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            order.status++;
//                            pullOrder.child(order.getOrderId()).child("status").setValue(order.status);
//                            pullOrder.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
//                                        if (data.getKey().equals(order.getOrderId())) {
//                                            pullOrder.child(data.getKey()).removeValue();
//                                            startActivity(new Intent(mainScreenEmployee.this, mainScreenEmployee.class));
//                                            break;
//                                        }
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
//                        }
//                    });
//                }
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

//        button_check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(mainScreenEmployee.this,Check.class));
//            }
//        });
    }

    private void setCall(final Call call){
        pullUserName.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull final DataSnapshot data) {

                        FirebaseDatabase.getInstance().getReference("order").addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot num) {
                                call.table=0;
                                call.userName=DA.getUserName(call.getUid(),data);
                                for(DataSnapshot i: num.getChildren()){
                                    if(i.getKey().equals(call.getUid())){
                                        for(DataSnapshot j: i.getChildren()){
                                            if(j.getKey().equals("Table")){
                                                call.table= j.getValue(Long.class);
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
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

}