package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class orderDrink extends AppCompatActivity {
    ListView list_dishes;
    DatabaseReference reff;
    DatabaseReference reff1;
    DatabaseReference ref;
    ArrayList<String> arrayList= new ArrayList<>();
    ArrayList<Long> arrayList1 = new ArrayList<Long>();
    ArrayAdapter adapter;
    ArrayList<dishInformation> dishes= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_drink);
        reff1=FirebaseDatabase.getInstance().getReference().child("order").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("price");
        reff= FirebaseDatabase.getInstance().getReference("order").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("drink");
        ref= FirebaseDatabase.getInstance().getReference("dishInformation").child("drink");
        list_dishes= findViewById(R.id.list_dishes);
        adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, dishes);
        list_dishes.setAdapter(adapter);

        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key=dataSnapshot.getKey();
                long amount=dataSnapshot.getValue(Long.class);
                arrayList.add(key);
                arrayList1.add(amount);


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
                for(int i=0; i<arrayList.size();i++) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if(arrayList.get(i).equals(data.getKey())){
                            dishInformation dish=data.getValue(dishInformation.class);
                            dish.setAmount(arrayList1.get(i));
                            dish.setDish_key(arrayList.get(i));
                            dishes.add(dish);
                            adapter.notifyDataSetChanged();

                        }
                    }
                }
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
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(orderDrink.this);
                mBuilder.setMessage(dish.getDish_name());

                mBuilder.setPositiveButton("Add another one", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reff.addListenerForSingleValueEvent(new ValueEventListener() {
                            long count=1;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot data: dataSnapshot.getChildren()){
                                    if( data.getKey().equals(dish.getDish_key_())){
                                        count= (long)data.getValue()+1;
                                        break;
                                    }
                                }
                                reff.child(dish.getDish_key_()).setValue(count);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        reff1.addListenerForSingleValueEvent(new ValueEventListener() {
                            long price;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                price=(long)dataSnapshot.getValue();
                                reff1.setValue(price+Integer.parseInt(dish.getDish_price()));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Toast.makeText(getApplicationContext(), "order", Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(orderDrink.this, orderDesserts.class);

                        startActivity(i);






                    }});
                mBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { ;
                        reff.addListenerForSingleValueEvent(new ValueEventListener() {
                            long count=1;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot data: dataSnapshot.getChildren()){
                                    if( data.getKey().equals(dish.getDish_key_())){
                                        count= (long)data.getValue()-1;
                                        break;
                                    }
                                }
                                if(count==0){
                                    reff.child(dish.getDish_key_()).removeValue();
                                }
                                else {
                                    reff.child(dish.getDish_key_()).setValue(count);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        reff1.addListenerForSingleValueEvent(new ValueEventListener() {
                            long price;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                price=(long)dataSnapshot.getValue();
                                reff1.setValue(price-Integer.parseInt(dish.getDish_price()));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(orderDrink.this, orderDrink.class));
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();



            }

        });
    }



}
