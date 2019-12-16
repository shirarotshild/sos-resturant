package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginManager extends AppCompatActivity {
    EditText manager_mail, manager_password;
    Button login_manager;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_manager);
        mFirebaseAuth = FirebaseAuth.getInstance();
        manager_mail = findViewById(R.id.manager_mail);
        manager_password = findViewById(R.id.manager_password);
        login_manager = findViewById(R.id.login_manager);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(loginManager.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(loginManager.this, mainScreenManager.class);
                    startActivity(i);
                } else {
                    Toast.makeText(loginManager.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };


        login_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = manager_mail.getText().toString();
                String pwd = manager_password.getText().toString();

                if (email.isEmpty()) {
                    manager_mail.setError("Please inter email id");
                    manager_mail.requestFocus();
                } else if (pwd.isEmpty()) {
                    manager_password.setError("Please enter your password");
                    manager_password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(loginManager.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {

                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(loginManager.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(loginManager.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intToHome = new Intent(loginManager.this, mainScreenManager.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                } else {
                    Toast.makeText(loginManager.this, "Error Occurred!", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);


    }
}
