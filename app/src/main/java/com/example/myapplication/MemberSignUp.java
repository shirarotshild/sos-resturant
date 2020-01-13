package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MemberSignUp extends AppCompatActivity {


    EditText emailId, password,editText_member_name,editText_member_phone_num;
    Button btnSignUp;
    TextView tvSignIn;
    //  FirebaseAuth mFirebaseAuth;
    AuthAction log;
    //private DatabaseReference mDatabase;
// ...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_sign_up);

        // mFirebaseAuth = FirebaseAuth.getInstance();
        editText_member_name = findViewById(R.id.editText_member_name);
        emailId = findViewById(R.id.editText_mail);
        password = findViewById(R.id.editText_pwd);
        editText_member_phone_num= findViewById(R.id.editText_member_phone_num);
        btnSignUp = findViewById(R.id.button_sign);
        tvSignIn = findViewById(R.id.textView_login);
        log= new AuthAction();


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.signIn(emailId, password, editText_member_name,editText_member_phone_num,editText_member_phone_num,
                        MemberSignUp.this, Tables.class);
            }
        });
//                final String email = emailId.getText().toString();
//                String pwd = password.getText().toString();
//                final String name = editText_member_name.getText().toString();
//                if(email.isEmpty()){
//                    emailId.setError("Please enter email id");
//                    emailId.requestFocus();
//                }
//                else  if(pwd.isEmpty()){
//                    password.setError("Please enter your password");
//                    password.requestFocus();
//                }
//                else  if(email.isEmpty() && pwd.isEmpty()){
//                    Toast.makeText(MemberSignUp.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
//                }
//                else  if(!(email.isEmpty() && pwd.isEmpty())){
//                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(MemberSignUp.this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(!task.isSuccessful()){
//                                Toast.makeText(MemberSignUp.this,"SignUp Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                String ID= member_ID.getText().toString();
//                                writeNewUser(ID, name,email);
//                                startActivity(new Intent(MemberSignUp.this, mainScreenMembers.class));
//                            }
//                        }
//                    });
//                }
//                else{
//                    Toast.makeText(MemberSignUp.this,"Error Occurred!",Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MemberSignUp.this, LoginMember.class);
                startActivity(i);
            }
        });
    }
//    private void writeNewUser(String userId, String name, String email) {
//        Permit newMember = new Member(name, email,mFirebaseAuth.getCurrentUser().getUid());
//
//        mDatabase.child("Members").child( mFirebaseAuth.getCurrentUser().getUid() ).setValue(newMember);
//    }

}