package com.example.scotterfinaltest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingsFragment extends Fragment
{

    FirebaseAuth mAuth;
    TextView tvName;
    TextView tvEmail;
    TextView tvMobile;
    TextView tvDate;

    FirebaseFirestore usersdb = FirebaseFirestore.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        tvName.findViewById(R.id.tvName);
        tvEmail.findViewById(R.id.tvEmail);
        tvMobile.findViewById(R.id.tvMobile);
        tvDate.findViewById(R.id.tvDate);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


    }

    private void getCredentials(String tvName,String tvEmail,String tvMobile,String tvDate){
        usersdb.collection("usersdb").document(mAuth.getCurrentUser().getUid());
    }


}