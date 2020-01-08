package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeDetails extends AppCompatActivity {

    EditText editText_employee_name;
    EditText editText_employee_phone_num;
    EditText editText_employee_id;
    employeesInformation emp;
    Button button_save_employee;
    public DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_details);

        emp = (employeesInformation) getIntent().getSerializableExtra("empl");
        editText_employee_phone_num = (EditText) findViewById(R.id.editText_employee_phone_num);

        editText_employee_name = (EditText) findViewById(R.id.editText_employee_name);
        editText_employee_id = (EditText) findViewById(R.id.editText_employee_id);
        button_save_employee = (Button) findViewById(R.id.button_save_employee);
        editText_employee_phone_num.setText(emp.getPhoneNum());

        editText_employee_name.setText(emp.getName());
        editText_employee_id.setText(emp.getID());
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees");
        button_save_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText_employee_name.getText().toString();
                String phone = editText_employee_phone_num.getText().toString();
                String ID = editText_employee_id.getText().toString();
                emp.setName(name);
                emp.setPhoneNum(phone);
                emp.setID(ID);

                ref.child(emp.getUid()).setValue(emp);
                Toast.makeText(ChangeDetails.this, "data insert sucessfully", Toast.LENGTH_LONG).show();
                Intent i = new Intent(ChangeDetails.this, employees.class);
                startActivity(i);
            }
        });

    }
}
