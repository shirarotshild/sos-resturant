package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginMember extends AppCompatActivity {


    Button button_login_member;
    EditText editText_member_mail;
    EditText editText_member_password;
    TextView textView_member_sign;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mDatabase;
    Permit m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_member);

        mFirebaseAuth = FirebaseAuth.getInstance();
        editText_member_mail = findViewById(R.id.editText_member_mail);
        editText_member_password = findViewById(R.id.editText_member_password);
        button_login_member = findViewById(R.id.button_member_sign_up);
        textView_member_sign = findViewById(R.id.textView_member_login);
        mDatabase = FirebaseDatabase.getInstance().getReference("Orders");

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull  FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null ){
                    Toast.makeText(LoginMember.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginMember.this, mainScreenMembers.class);
                    startActivity(i);

                }
                else{

                    Toast.makeText(LoginMember.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };


        button_login_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText_member_mail.getText().toString();
                String pwd = editText_member_password.getText().toString();
                if(email.isEmpty()){
                    editText_member_mail.setError("Please enter email id");
                    editText_member_mail.requestFocus();
                }
                else  if(pwd.isEmpty()){
                    editText_member_password.setError("Please enter your password");
                    editText_member_password.requestFocus();
                }
                else  if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LoginMember.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(email.isEmpty() && pwd.isEmpty())){


                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginMember.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginMember.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                            }
                            else{

                                //  m= new Member(mFirebaseAuth,mDatabase);
                                //  if(m.getKind()==1) {
                                FirebaseDatabase.getInstance().getReference("Orders")
                                        .child(mFirebaseAuth.getCurrentUser().getUid()).setValue(0);
                                Intent intToHome = new Intent(LoginMember.this, mainScreenMembers.class);
                                startActivity(intToHome);
                                // }*/
                                //  else{
                                //  Toast.makeText(LoginMember.this, " You do not have permission for this page",Toast.LENGTH_SHORT).show();
                                // }
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginMember.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }

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
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }



}
