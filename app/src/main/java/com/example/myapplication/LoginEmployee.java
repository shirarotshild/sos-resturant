package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginEmployee extends AppCompatActivity {
    EditText manager_mail, manager_password;
    Button login_manager;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    AuthAction log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_employee);


        manager_mail = findViewById(R.id.editText_employee_mail);
        manager_password = findViewById(R.id.editText_employee_password);
        login_manager = findViewById(R.id.button_employee_sign_in);
        log = new AuthAction();


        login_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.connect(manager_mail, manager_password, LoginEmployee.this, mainScreenEmployee.class);

            }
        });

    }

}
