package com.example.myapplication;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AuthAction {

    private FirebaseAuth mFirebaseAuth;
    private String uid;
    private DatabaseReference mDatabase;




    public AuthAction(){

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference("Users");


    }
    public FirebaseAuth getFirebaseAuth(){
        return mFirebaseAuth;
    }


     public void connect(EditText editMail, EditText editPwd, final AppCompatActivity at,final Class to){

        String email = editMail.getText().toString();
        String pwd = editPwd.getText().toString();
        if(email.isEmpty()){
            editMail.setError("Please enter email id");
            editMail.requestFocus();
        }
        else  if(pwd.isEmpty()){
            editPwd.setError("Please enter your password");
            editPwd.requestFocus();
        }
        else  if(email.isEmpty() && pwd.isEmpty()){
            Toast.makeText(at,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
        }
        else  if(!(email.isEmpty() && pwd.isEmpty())){
            mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(at, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(at,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        permission(at,to);
                    }
                }
            });
        }
        else{
            Toast.makeText(at,"Error Occurred!",Toast.LENGTH_SHORT).show();
        }

    }

    public void logout(){}
    public void signIn(EditText emailId, EditText password, EditText editText_member_name,EditText phone_num,EditText employee_id,
                          final AppCompatActivity at, final Class to){
        final String email = emailId.getText().toString();
        String pwd = password.getText().toString();
        final String name = editText_member_name.getText().toString();
        final String phoneNum= phone_num.getText().toString();
        final String ID= employee_id.getText().toString();
        if(email.isEmpty()){
            emailId.setError("Please enter email id");
            emailId.requestFocus();
        }
        else  if(pwd.isEmpty()){
            password.setError("Please enter a password");
            password.requestFocus();
        }
        else  if(name.isEmpty()){
            editText_member_name.setError("Please enter full name");
            editText_member_name.requestFocus();
        }
        else  if(phoneNum.isEmpty()){
            phone_num.setError("Please enter phone number");
            phone_num.requestFocus();
        }
        else  if(email.isEmpty() && pwd.isEmpty()&& name.isEmpty()&& phoneNum.isEmpty()){
            Toast.makeText(at,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
        }
        else  if(!(email.isEmpty() && pwd.isEmpty() && name.isEmpty() && phoneNum.isEmpty())){
            mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(at, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(at,"SignUp Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        writeNewUser( name,email,phoneNum,ID,at);
                        at.startActivity(new Intent(at, to));


                    }
                }
            });

        }
        else{
            Toast.makeText(at,"Error Occurred!",Toast.LENGTH_SHORT).show();

        }

    }


    private void permission(final AppCompatActivity at,final Class to){
      //  final boolean ans[]= new boolean[1];
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int k=whichKind(dataSnapshot);

                if(at.getClass().equals(LoginMember.class) ) {
                    if(k<=2) {
                        Intent intToHome = new Intent(at, to);
                        at.startActivity(intToHome);
                    }
                    else{
                        at.startActivity(new Intent(at, MainActivity.class));
                        Toast.makeText(at, "You do not have permission to access this page.", Toast.LENGTH_SHORT).show();
                    }
                }

                else if(at.getClass().equals(LoginEmployee.class)){
                    if(k<= 1){
                        at.startActivity(new Intent(at,to));
                    }
                    else {
                        at.startActivity(new Intent(at, MainActivity.class));
                        Toast.makeText(at, "You do not have permission to access this page.", Toast.LENGTH_SHORT).show();
                    }
                }

                else if(at.getClass().equals(loginManager.class)){
                    if( k == 0) {
                        Intent intToHome = new Intent(at, to);
                        at.startActivity(intToHome);
                    }
                    else{
                        Intent intToHome = new Intent(at,MainActivity.class);
                        at.startActivity(intToHome);
                        Toast.makeText(at,"You do not have permission to access this page.",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private int whichKind(DataSnapshot dataSnapshot){
        if(uid.equals("HTE7l3axTadoaY0eTZ3A76fnnxC2"))
            return 0;
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            for (DataSnapshot item : data.getChildren()){

                if (item.getKey().equals(uid)) {
                    if (data.getKey().equals("Members")) {

                        return 2;
                    } else if (data.getKey().equals("Employees")) {
                        return 1;
                    }
                }



            }
        }
        return 3;
    }
    private void writeNewUser(String name, String email,String phoneNum,String id,final AppCompatActivity at) {
        String userID=mFirebaseAuth.getCurrentUser().getUid();
        if (at.getClass().equals(MemberSignUp.class)) {
            Member newMember = new Member(name, email,phoneNum,userID);
            mDatabase.child("Members").child(userID).setValue(newMember);
         }
        else{
            employeesInformation newEmployee= new employeesInformation(name,email,phoneNum,id,userID);
            mDatabase.child("Employees").child(userID).setValue(newEmployee);

        }
    }


}
