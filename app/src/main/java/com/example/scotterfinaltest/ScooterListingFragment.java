package com.example.scotterfinaltest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ScooterListingFragment extends Fragment {
    private Button btnQuitUsing;
    FirebaseFirestore scootersdb;
    DocumentReference docRef;
    TextView tvScooterName;
    Scooter scooter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_scooter_listing, container, false);
        btnQuitUsing = view.findViewById(R.id.btnQuitUsing);
        tvScooterName = view.findViewById(R.id.tvScooterName);
        scootersdb = FirebaseFirestore.getInstance();

        // Retrieve the scooter object from the arguments
        Bundle args = getArguments();
        if (args != null && args.containsKey("scooter")) {
            scooter = args.getParcelable("scooter");
        }
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        Map<String, Object> updates = new HashMap<>();
        tvScooterName.setText(scooter.getName());

        docRef = scootersdb.collection("Scootersdb").document(scooter.getQrCode());

        btnQuitUsing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updates.put("IsActivated", false);
                docRef.update(updates);
                navigateToScanFragment();
            }
        });
    }

    private void navigateToScanFragment() {
        // Create an instance of the DestinationFragment

        ScanFragment scanFragment = new ScanFragment();

        // Get the parent activity's fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // Perform the fragment transaction to replace the current fragment with the DestinationFragment
        fragmentManager.beginTransaction()
                .replace(getId(), scanFragment) // Use the ID of the current fragment container
                .commit();
    }
}