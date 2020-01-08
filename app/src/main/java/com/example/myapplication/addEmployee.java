package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addEmployee extends AppCompatActivity {

    EditText emailId, password,editText_employee_name,editText_employee_phone_num,employee_id;
    Button button_save_employee;
    AuthAction log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        employee_id= findViewById(R.id.employee_id);
        editText_employee_name = findViewById(R.id.editText_employee_name);
        emailId = findViewById(R.id.editText_mail);
        password = findViewById(R.id.editText_pwd);
        editText_employee_phone_num= findViewById(R.id.editText_employee_phone_num);
        button_save_employee = findViewById(R.id.button_save_employee);
        log= new AuthAction();

        button_save_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.signIn(emailId, password, editText_employee_name,editText_employee_phone_num,employee_id,
                        addEmployee.this,employees.class);
            }
        });
    }
}
