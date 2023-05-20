package com.example.scotterfinaltest;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ScanFragment extends Fragment {

    Boolean isListing = false;
    Boolean isActivatedCheck = false;
    FirebaseFirestore scootersdb;
    DocumentReference docRef;
    private String qrStringResult;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private boolean mCameraPermissionGranted = false;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private AlertDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        scannerView = view.findViewById(R.id.cameraView);
        mCodeScanner = new CodeScanner(requireActivity(),scannerView);
        scootersdb = FirebaseFirestore.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getCameraPermission();

        scanQr();

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // refresh scanner
                mCodeScanner.startPreview();
            }
        });
    }



    private void scanQr()
    {
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        // code scanned successfully
                        qrStringResult = result.getText().toString();
                        docRef = scootersdb.collection("Scootersdb").document(qrStringResult);
                        Map<String, Object> updates = new HashMap<>();

                        docRef.get().addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                isActivatedCheck = documentSnapshot.getBoolean("IsActivated");
                                isListing = isActivatedCheck;
                                if (isActivatedCheck) {
                                    // Scooter Is Activated, handle the case accordingly
                                    showScooterDeniedDialog("Scooter Is Being Used By Someone Else");
                                }
                                else {
                                    // Scooter Isn't Activated
                                    updates.put("IsActivated", true);
                                    docRef.update(updates);
                                    Scooter scooter = new Scooter(documentSnapshot.getString("Name"),documentSnapshot.getString("qrCode"),
                                            true);
                                    navigateToScooterListingFragment();
                                }
                            }
                            else {
                                // Document doesn't exist, handle the case accordingly
                                mCodeScanner.startPreview();
                            }
                        });
                    }
                });
            }
        });
    }

    private void getCameraPermission() {
        String CAMERA_PERMISSION = Manifest.permission.CAMERA;
        if (ContextCompat.checkSelfPermission(requireContext(), CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            mCameraPermissionGranted = true;
            mCodeScanner.startPreview();
        } else {
            requestPermissionLauncher.launch(CAMERA_PERMISSION);
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    isGranted -> {
                        if (isGranted) {
                            mCameraPermissionGranted = true;
                            mCodeScanner.startPreview();
                        } else {
                            // Camera permission denied, handle accordingly
                            showPermissionDeniedDialog("Camera Access Denied");
                        }
                    });


    private void showPermissionDeniedDialog(String dialogMessage) {
        mDialog = new AlertDialog.Builder(requireContext())
                .setMessage(dialogMessage)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialog.dismiss();
                        requireActivity().finish();
                    }
                })
                .create();
        mDialog.show();
    }


    private void showScooterDeniedDialog(String dialogMessage) {
        mDialog = new AlertDialog.Builder(requireContext())
                .setMessage(dialogMessage)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialog.dismiss();
                    }
                })
                .create();
        mDialog.show();
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

    public boolean isListing(){
        return isListing;
    }

    @Override
    public void onResume(){
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause(){
        mCodeScanner.releaseResources();
        super.onPause();
    }

}
