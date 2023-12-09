package com.example.orderfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText email,pass;
    Button register;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        register=findViewById(R.id.register);
        progressBar=findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String emaill,password;
                emaill=email.getText().toString();
                password=pass.getText().toString();
                if (emaill.isEmpty() || password.isEmpty() ) {
                    Toast.makeText(Register.this, "Fill in all the fields", Toast.LENGTH_SHORT).show();

                    return;
                }
                mAuth.createUserWithEmailAndPassword(emaill, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                     // Sign in success, update UI with the signed-in user's information
                                    //  Log.d(TAG, "createUserWithEmail:success");
                                    // FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                    Toast.makeText(Register.this, "creation avec succes .",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //   updateUI(null);
                                }
                            }
                        });
            }
        });
    }
}