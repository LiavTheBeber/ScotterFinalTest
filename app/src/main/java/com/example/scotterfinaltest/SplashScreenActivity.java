package com.example.scotterfinaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;


public class SplashScreenActivity extends AppCompatActivity {


    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mAuth.getCurrentUser() != null){
                    // user is logged in
                    startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                    finish();
                }

                else if (mAuth.getCurrentUser() == null){
                    startActivity(new Intent(SplashScreenActivity.this,AuthenticationActivity.class));
                    finish();
                }
            }
        }, 3000);

    }
}