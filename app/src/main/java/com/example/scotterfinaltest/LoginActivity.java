package com.example.scotterfinaltest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etLogInEmail;
    TextInputEditText etLogInPassword;
    TextView tvRegisterHere;
    Button btnLogIn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLogInPassword = findViewById(R.id.etLoginPass);
        etLogInEmail = findViewById(R.id.etLoginEmail);
        tvRegisterHere = findViewById(R.id.tvRegisterHere);
        btnLogIn = findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();


        tvRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signInWithEmailAndPassword(etLogInEmail.getText().toString(), etLogInPassword.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if(task.isSuccessful())
                                {
                                    // Sign in success, update UI with the signed-in user's information
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    if(user != null)
//                                        Toast.makeText(LoginActivity.this,"User Is Logged In",Toast.LENGTH_SHORT).show();

                                    //User user = new User(etLogInEmail.getText().toString(), etLogInPassword.getText().toString());
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    //intent.putExtra("user", user);
                                    startActivity(intent);
                                    finish();
                                }

                                else
                                {
                                    Toast.makeText(LoginActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


            }
        });



    }

}