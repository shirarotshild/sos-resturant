package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class mainScreenMembers extends AppCompatActivity {
    Button button_menu,button_orders,button_call_for_waiter;
    Button btnLogout;
    Button button_favorite_dishes;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference ref, pullOrder,check,pullUserName,pullDish,waiterCalls;
    DatabaseAction DA= new DatabaseAction();
    Order finalOrder= new Order();

    String u;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_members);

        waiterCalls = FirebaseDatabase.getInstance().getReference("waiter call").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        button_call_for_waiter = findViewById(R.id.button_call_for_waiter);
        button_orders = findViewById(R.id.button_orders);
        check = FirebaseDatabase.getInstance().getReference("payed bills");
        pullUserName = FirebaseDatabase.getInstance().getReference("Users");
        pullOrder = FirebaseDatabase.getInstance().getReference("kitchenOrder");
        pullDish = FirebaseDatabase.getInstance().getReference("dishInformation");
        ref=FirebaseDatabase.getInstance().getReference("order").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        button_favorite_dishes = findViewById(R.id.button_favorite_dishes);
        btnLogout = findViewById(R.id.button_log_out);
        mFirebaseAuth = FirebaseAuth.getInstance();


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(mainScreenMembers.this, MainActivity.class);
                startActivity(intToMain);
            }
        });

        button_menu = findViewById(R.id.button_menu);
        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   FirebaseAuth.getInstance().signOut();

                Intent intToMain = new Intent(mainScreenMembers.this, memberMenu.class);
                // intToMain.putExtra("uid",mFirebaseAuth.getCurrentUser().getUid());

                startActivity(intToMain);
            }
        });
        button_favorite_dishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mainScreenMembers.this, favoriteDishes.class);
                startActivity(i);
            }
        });

        button_call_for_waiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waiterCalls.setValue(new Call(FirebaseAuth.getInstance().getCurrentUser().getUid(), 0));
            }
        });

        button_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(mainScreenMembers.this);
                pullDish.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot d) {
                        pullUserName.addListenerForSingleValueEvent(new ValueEventListener() {
                            Order order;

                            @Override
                            public void onDataChange(@NonNull final DataSnapshot data) {


                                pullOrder.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        String message = "";
                                        mBuilder.setTitle("orders of " + DA.getUserName(uid, data));
                                        for (DataSnapshot i : dataSnapshot.getChildren()) {
                                            order = i.getValue(Order.class);
                                            //if (!order.getOrderId().equals(finalOrder.getOrderId())) {
                                            if (order.getMemberUID().equals(uid)) {
                                                for (String key : order.getDishKeys())
                                                    order.dishesName.add(DA.getDishName(key, d));
                                                order.setStatus(4);
                                                finalOrder.addSameUser(order);
                                                message = message + order.toString();
                                            }
                                            //  }
                                        }
                                        mBuilder.setMessage(message);
                                        mBuilder.setPositiveButton("Bill", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                final AlertDialog.Builder bill = new AlertDialog.Builder(mainScreenMembers.this);
                                                bill.setMessage("price:"+finalOrder.prices);
                                                final AlertDialog.Builder bye = new AlertDialog.Builder(mainScreenMembers.this);
                                                bill.setPositiveButton("credit", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        bye.setTitle("credit payment");
                                                        bye.setMessage("(after credit approval)\nThanks for eating at SOS Restaurant, have a nice day");
                                                        String date = "";  // Start date
                                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                                        Calendar c = Calendar.getInstance();
                                                        finalOrder.setDate(sdf.format(c.getTime()));
                                                        check.child(finalOrder.dateOnly()).child(finalOrder.getOrderId()).setValue(finalOrder);
                                                        for (String id : finalOrder.oldOrders) {
                                                            pullOrder.child(id).removeValue();
                                                        }
                                                        waiterCalls.setValue(new Call(uid, 2));
                                                        bye.setPositiveButton("bye", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                ref.removeValue();
                                                                FirebaseAuth.getInstance().signOut();
                                                                Intent intToMain = new Intent(mainScreenMembers.this, MainActivity.class);
                                                                startActivity(intToMain);
                                                            }
                                                        });
                                                        AlertDialog mDialog = bye.create();
                                                        bye.show();
                                                    }
                                                });
                                                bill.setNeutralButton("cash", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        for (String id : finalOrder.oldOrders) {
                                                            pullOrder.child(id).removeValue();
                                                        }
                                                        pullOrder.child(finalOrder.getOrderId()).setValue(finalOrder);
                                                        waiterCalls.setValue(new Call(uid, 1));
                                                        AlertDialog.Builder wait = new AlertDialog.Builder(mainScreenMembers.this);
                                                        wait.setMessage("please wait..");

                                                    }
                                                });
                                                bill.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        startActivity(new Intent(mainScreenMembers.this, mainScreenMembers.class));
                                                    }
                                                });
                                                AlertDialog mDialog = bill.create();
                                                bill.show();

                                            }
                                        });
                                        AlertDialog mDialog = mBuilder.create();
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
}
