package com.example.myapp_broke_petr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Document;

public class SignUp extends AppCompatActivity {
    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etpwd;
    Button btnSignup;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    FirebaseAuth mAuth;
    FirebaseUser curr_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Editing the Action Bar
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Create an Account");


        //connect variables to components

        etFirstName=findViewById(R.id.etFirstName);
        etLastName=findViewById(R.id.etLastName);
        etEmail=findViewById(R.id.etEmail);
        etpwd=findViewById(R.id.etPwd);
        btnSignup=findViewById(R.id.btnSignUp);


        //getting current instance of database
        mAuth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();




        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get user inputs
                final String email= etEmail.getText().toString();
                final String password= etpwd.getText().toString();
                final String first_name=etFirstName.getText().toString();
                final String last_name=etLastName.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    etEmail.setError("All Fields are Required");
                    return;
                }

                //Register the user to the firebase database
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //user created successful
                            Toast.makeText(SignUp.this, "Account Created", Toast.LENGTH_SHORT).show();
                            //add user info
                            curr_user=mAuth.getCurrentUser();
                            myRef=database.getReference(curr_user.getUid());
                            myRef.child("first_name").setValue(first_name);
                            myRef.child("last_name").setValue(last_name);
                            myRef.child("password").setValue(password);

                            //send user to home screen
                            gotoHome();


                        }
                        else{
                            Toast.makeText(SignUp.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                            etpwd.setText("");

                        }
                    }
                });

            }
        });





    }

    private void gotoHome() {
        //create intent to send user to home page
        Intent intent= new Intent(this,Main2Activity.class);
        startActivity(intent);
        finish();
    }
}


