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
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText user_email;
    EditText user_pwd;
    Button btnLogin;
    Button reg;


    private FirebaseDatabase database;
    FirebaseAuth mAuth;
    private Boolean loggedIn;

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Change Action bar Title
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Log In");

        // connect variable to view components
        user_email=findViewById(R.id.user_email);
        user_pwd=findViewById(R.id.user_pwd);
        btnLogin=findViewById(R.id.btnLogin);
        reg=findViewById(R.id.btnReg);

        //get the database
        mAuth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();

        //set action button reg when clicked
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUp();
            }
        });


        //set actions for when log in button is created
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get user inputs
                String email= user_email.getText().toString();
                String password= user_pwd.getText().toString();

                    //validate data
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    user_email.setError("All Fields are Required");
                    user_pwd.setText("");
                    return;

                }

                // authenticate user
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //if user is logged send them to the home page
                            gotoHome();

                        }
                        else{
                            Toast.makeText(LoginActivity.this, "error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            user_pwd.setText("");

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

    private void goToSignUp() {
        //create intent to send user to sign in page
        Intent intent= new Intent(this,SignUp.class);
        startActivity(intent);

    }
}