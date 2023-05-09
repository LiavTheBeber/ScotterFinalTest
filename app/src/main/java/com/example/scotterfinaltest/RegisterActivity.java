package com.example.scotterfinaltest;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etRegFullName;

    TextInputEditText etRegMobile;

    TextInputEditText etRegEmail;
    TextInputEditText etRegPassword;
    TextView tvLoginHere;
    Button btnRegister;
    FirebaseAuth mAuth;

    private static final String KEY_FULLNAME = "fullName";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_EMAIL = "emial";

    FirebaseFirestore usersdb = FirebaseFirestore.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegFullName = findViewById(R.id.etRegFullName);
        etRegMobile = findViewById(R.id.etRegMobile);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPass);
        tvLoginHere = findViewById(R.id.tvLoginHere);
        btnRegister = findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();



        tvLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });




        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.createUserWithEmailAndPassword(etRegEmail.getText().toString(), etRegPassword.getText().toString())
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {

                                if (etRegFullName.getText().toString() != null
                                        && etRegMobile.getText().toString() != null){
                                    if(task.isSuccessful())
                                    {
                                        // Sign in success, update UI with the signed-in user's information
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    if(user != null)
//                                        Toast.makeText(RegisterActivity.this,"Authentication Succeeded",Toast.LENGTH_SHORT).show();

                                        Map<String , Object> userMap= new HashMap<>();
                                        User user = new User(etRegFullName.getText().toString(), etRegMobile.getText().toString() , etRegEmail.getText().toString());

                                        userMap.put(KEY_FULLNAME,etRegFullName.getText().toString());
                                        userMap.put(KEY_MOBILE,etRegMobile.getText().toString());
                                        userMap.put(KEY_EMAIL,etRegEmail.getText().toString());

                                        usersdb.collection("usersdb").document(mAuth.getCurrentUser().getUid()).set(user);

                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.putExtra("user", user);
                                        startActivity(intent);
                                        finish();

                                    }

                                    else
                                    {
                                        Toast.makeText(RegisterActivity.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
    }
}