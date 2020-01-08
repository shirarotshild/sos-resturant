package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ModuleInfo;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.Authenticator;
import java.util.ArrayList;

public class employees extends AppCompatActivity  {

  Button butten_add_employee;
  ListView list_employees;
  DatabaseReference reff;
  ArrayList<employeesInformation> arrayList= new ArrayList<>();
  ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        butten_add_employee = findViewById(R.id.button_add_employee);
        butten_add_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(employees.this, addEmployee.class);
                startActivity(i);
            }
        });

        reff = FirebaseDatabase.getInstance().getReference("Users").child("Employees");

        list_employees = findViewById(R.id.list_employees);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        list_employees.setAdapter(adapter);

        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                employeesInformation string = dataSnapshot.getValue(employeesInformation.class);
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

        list_employees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final employeesInformation employee = (employeesInformation) parent.getItemAtPosition(position);
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(employees.this);
                mBuilder.setTitle("change details");
                mBuilder.setMessage("would you like to change " + employee.getName() + " details?");

                mBuilder.setPositiveButton("change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(employees.this, ChangeDetails.class);
                        i.putExtra("empl", employee);
                        employeesInformation e = (employeesInformation) i.getSerializableExtra("empl");

                        startActivity(i);

                    }
                });

              mBuilder.setNegativeButton("delete", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      reff.child(employee.uid).addListenerForSingleValueEvent(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                              reff.child(employee.uid).removeValue();

                          }

                          @Override
                          public void onCancelled(@NonNull DatabaseError databaseError) {

                          }
                      });
                      Toast.makeText(getApplicationContext(), employee.getName()+"was deleted", Toast.LENGTH_SHORT).show();
                  }
              });
                mBuilder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });


                AlertDialog mDialog = mBuilder.create();
                mDialog.show();


            }

        });

    }
}
