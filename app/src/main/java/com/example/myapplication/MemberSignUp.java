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


import android.os.Bundle;

public class MemberSignUp extends AppCompatActivity {


    EditText emailId, password,editText_member_name, member_ID;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;

    private DatabaseReference mDatabase;
// ...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_sign_up);

        mFirebaseAuth = FirebaseAuth.getInstance();
        editText_member_name = findViewById(R.id.editText_member_name);
        emailId = findViewById(R.id.editText_mail);
        password = findViewById(R.id.editText_pwd);
        btnSignUp = findViewById(R.id.button_sign);
        tvSignIn = findViewById(R.id.textView_login);
        member_ID= findViewById(R.id.editText_member_ID);
        mDatabase = FirebaseDatabase.getInstance().getReference();



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                final String name = editText_member_name.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else  if(pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else  if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MemberSignUp.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(MemberSignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MemberSignUp.this,"SignUp Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String ID= member_ID.getText().toString();
                                writeNewUser(ID, name,email);
                                startActivity(new Intent(MemberSignUp.this,mainScreenMembers.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MemberSignUp.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MemberSignUp.this,LoginMember.class);
                startActivity(i);
            }
        });
    }
    private void writeNewUser(String userId, String name, String email) {
        Permit newMember = new Member(name, email);

        mDatabase.child("members").child(userId).setValue(newMember);
    }

}
