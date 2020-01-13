package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

public class myOrder extends AppCompatActivity {
    ListView list_type;
    DatabaseReference reff;
    DatabaseReference reff1;
    DatabaseReference ref;
    Order order;
    ArrayList<String> arrayList= new ArrayList<>();
    ArrayAdapter<String> adapter;
    Button button_order;
    TextView txtPrice;
    long table;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order);
        order=new Order();
        reff = FirebaseDatabase.getInstance().getReference("order").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref=FirebaseDatabase.getInstance().getReference("kitchenOrder");
        reff1=FirebaseDatabase.getInstance().getReference().child("order").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("price");
        list_type = findViewById(R.id.list_dishes);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        list_type.setAdapter(adapter);
        button_order=findViewById(R.id.button_order);
        txtPrice = (TextView)findViewById(R.id.textViewprice);
        button_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        order.setOrderId(ref.child(dataSnapshot.getKey()).push().getKey());
                        order.setMemberUID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        order.setDishKeys(new ArrayList<String>());
                        table=dataSnapshot.child("Table").getValue(Long.class);
                        order.setTable(table);
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            for(DataSnapshot item: data.getChildren()){
                                long times= item.getValue(Long.class);

                                while (times>0) {
                                    times--;
                                    order.getDishKeys().add(item.getKey());
                                }
                            }
                        }
                        ref.child(order.getOrderId()).setValue(order);
                        reff.removeValue();
                        reff.child("Table").setValue(table);
                        reff1.setValue(0);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                Intent intToMain = new Intent(myOrder.this, memberMenu.class);
                startActivity(intToMain);
            }
        });

        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String type = dataSnapshot.getKey();
                if(!type.equals("price")&& !type.equals("Table")) {
                    arrayList.add(type);
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

        reff1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                    long price = (long) dataSnapshot.getValue();
                    String price1 = "" + price;
                    txtPrice.setText((price1));
                }
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