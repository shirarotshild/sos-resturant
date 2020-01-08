package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginMember extends AppCompatActivity {


    Button button_login_member;
    EditText editText_member_mail;
    EditText editText_member_password;
    TextView textView_member_sign;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    AuthAction log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_member);

        editText_member_mail = findViewById(R.id.editText_member_mail);
        editText_member_password = findViewById(R.id.editText_member_password);
        button_login_member = findViewById(R.id.button_member_sign_up);
        textView_member_sign = findViewById(R.id.textView_member_login);
        log = new AuthAction();
        // mAuthStateListener =log.isConnect(LoginMember.this,mainScreenMembers.class);

        button_login_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.connect(editText_member_mail, editText_member_password, LoginMember.this, mainScreenMembers.class);
            }
        });


        textView_member_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(LoginMember.this, MemberSignUp.class);
                startActivity(intSignUp);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // log.getFirebaseAuth().addAuthStateListener(mAuthStateListener);
    }

}