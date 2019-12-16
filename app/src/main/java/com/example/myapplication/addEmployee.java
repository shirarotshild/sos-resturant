package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addEmployee extends AppCompatActivity {

    EditText txtFirstName;
    EditText txtLastName;
    EditText txtPhoneNumber;
    Button button_save;
   public DatabaseReference ref;
    employeesInformation EI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);


        txtFirstName= (EditText)findViewById(R.id.editText_firstName);
        txtLastName= (EditText)findViewById(R.id.editText_lastName);
        txtPhoneNumber= (EditText)findViewById(R.id.editText_phoneNumber);
        ref= FirebaseDatabase.getInstance().getReference().child("employeesInformation");
        EI= new employeesInformation();
        button_save= (Button)findViewById(R.id.button_save);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName= txtFirstName.getText().toString();
                String lastName= txtLastName.getText().toString();
                String phoneNum= txtPhoneNumber.getText().toString();
                EI.setFirst_name(firstName);
                EI.setLast_name(lastName);
                EI.setNumber(phoneNum);
                ref.child(firstName).setValue(EI);
                //ref.push().setValue(EI);
                Toast.makeText(addEmployee.this, "data insert sucessfully",Toast.LENGTH_LONG).show();

            }
        });
    }
}
