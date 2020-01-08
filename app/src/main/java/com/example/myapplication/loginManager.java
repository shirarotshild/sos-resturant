package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginManager extends AppCompatActivity {
    EditText manager_mail, manager_password;
    Button login_manager;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    AuthAction log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_manager);

        manager_mail = findViewById(R.id.manager_mail);
        manager_password = findViewById(R.id.manager_password);
        login_manager = findViewById(R.id.login_manager);
        log = new AuthAction();


      //  mAuthStateListener = log.isConnect(loginManager.this, mainScreenManager.class);


        login_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.connect(manager_mail, manager_password, loginManager.this, mainScreenManager.class);

            }
        });

    }

        @Override
        protected void onStart () {
            super.onStart();
           // log.getFirebaseAuth().addAuthStateListener(mAuthStateListener);



    }
}
