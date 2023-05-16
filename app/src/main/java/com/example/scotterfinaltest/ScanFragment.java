package com.example.scotterfinaltest;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ScanFragment extends Fragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    private void navigateToScooterListingFragment() {
        // Create an instance of the DestinationFragment
        ScooterListingFragment scooterListingFragment = new ScooterListingFragment();

        // Get the parent activity's fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // Perform the fragment transaction to replace the current fragment with the DestinationFragment
        fragmentManager.beginTransaction()
                .replace(getId(), scooterListingFragment) // Use the ID of the current fragment container
                .commit();
    }

}