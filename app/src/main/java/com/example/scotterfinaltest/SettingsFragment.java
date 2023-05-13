package com.example.scotterfinaltest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class SettingsFragment extends Fragment {
    FirebaseAuth mAuth;
    TextView tvName;
    TextView tvEmail;
    TextView tvMobile;
    TextView tvDate;
    FirebaseFirestore usersdb;
    String userId;
    DocumentReference docRef;
    Button btnLogOut;
    CheckBox checkBox;
    boolean isDarkMode;
    SharedPreferences prefs;
    private static final String DARK_MODE_PREF = "dark_mode_pref";
    //public String currentFragmentTag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mAuth = FirebaseAuth.getInstance();
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvMobile = view.findViewById(R.id.tvMobile);
        tvDate = view.findViewById(R.id.tvDate);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        checkBox = view.findViewById(R.id.checkBox);
        usersdb = FirebaseFirestore.getInstance();
        prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        //currentFragmentTag = getArguments().getString("CURRENT_FRAGMENT_TAG", "");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isDarkMode = prefs.getBoolean(DARK_MODE_PREF, false);
        checkBox.setChecked(isDarkMode);


        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        docRef = usersdb.collection("usersdb").document(userId);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                tvName.setText("Name: " + (documentSnapshot.getString("fullName")));
                tvEmail.setText("Email: " + (documentSnapshot.getString("email")));
                tvMobile.setText("Mobile: " + (documentSnapshot.getString("mobile")));
                tvDate.setText("Birthdate: " + (documentSnapshot.getDate("birthdate")));
            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(requireContext(), AuthenticationActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save the checkbox state to the shared preferences
                prefs.edit().putBoolean(DARK_MODE_PREF, isChecked).apply();

                // Set the current fragment tag to "settings"
                //currentFragmentTag = "settings";

                // Call the setTheme() method to enable or disable dark mode
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

                // Recreate the activity to apply the new theme
                requireActivity().recreate();
            }
        });

    }
}