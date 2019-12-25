package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyOrder extends AppCompatActivity {

    ListView order_list;
    Button order;
    DatabaseReference reff;
    ArrayList<String> arrayList= new ArrayList<>();
    ArrayAdapter<String> adapter;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference from;
    Order tempOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        tempOrder= (Order) getIntent().getSerializableExtra("order");

        mFirebaseAuth = FirebaseAuth.getInstance();
        order_list = findViewById(R.id.my_order_list);
        order = findViewById(R.id.button_order);
        reff= FirebaseDatabase.getInstance().getReference("Orders").child(mFirebaseAuth.getCurrentUser().getUid());
        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tempOrder.getDishes());
        order_list.setAdapter(adapter);
        from= FirebaseDatabase.getInstance().getReference("Orders");





//to add delete option
        order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.notifyDataSetChanged();

            }

        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff.push().setValue(tempOrder);

            }
        });

    }
}