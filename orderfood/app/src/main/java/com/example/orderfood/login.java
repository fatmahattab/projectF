package com.example.orderfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
TextView regist;
EditText email,password;
Button log ;

ProgressBar progress ;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        regist=findViewById(R.id.text);
        mAuth=FirebaseAuth.getInstance();
        password = findViewById(R.id.pass);

        email=findViewById(R.id.email);
        log = findViewById(R.id.login);
        progress=findViewById(R.id.progressBar);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(login.this,Register.class);
                startActivity(i);
                finish();
            }
        });

        log.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                String pass = password.getText().toString();
                String mail = email.getText().toString();
                if(TextUtils.isEmpty(mail)){
                    Toast.makeText(login.this, "Enter your  email", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(login.this, "Enter your  password", Toast.LENGTH_SHORT).show();
                }

                mAuth.signInWithEmailAndPassword(mail, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progress.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(login.this, "Authentication succes.",Toast.LENGTH_SHORT).show();
                                     Intent i = new Intent(getApplicationContext(), home.class);
                                   startActivity(i);
                                   finish();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(login.this, "Authentication failed.",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}