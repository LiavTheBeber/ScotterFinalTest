package com.example.scotterfinaltest;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.camera.CameraManager;


public class ScanFragment extends Fragment {

    private FloatingActionButton btnFlash;
    private static final String TAG = ScanFragment.class.getSimpleName();
    private boolean mCameraPermissionGranted = false;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private AlertDialog mDialog;
    private CameraManager mCameraManager;
    private DecoratedBarcodeView mBarcodeView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        mBarcodeView = view.findViewById(R.id.cameraView);
        btnFlash = view.findViewById(R.id.flashButton);
        mCameraManager = new CameraManager(requireContext());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getCameraPermission();

        btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFlashlightOn())
                    turnOnFlashlight();
                else
                    turnOffFlashlight();
            }
        });




    }

    private void getCameraPermission() {
        String CAMERA_PERMISSION = Manifest.permission.CAMERA;
        if (ContextCompat.checkSelfPermission(requireContext(), CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            mCameraPermissionGranted = true;
            // Initialize Camera
        } else {
            requestPermissionLauncher.launch(CAMERA_PERMISSION);
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    isGranted -> {
                        if (isGranted) {
                            mCameraPermissionGranted = true;
                            // Initialize Camera
                        } else {
                            // Camera permission denied, handle accordingly
                        }
                    });


    private void showPermissionDeniedDialog() {
        mDialog = new AlertDialog.Builder(requireContext())
                .setMessage("Camera permission denied")
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

    private boolean isFlashlightOn()
    {
        return mCameraManager.isTorchOn();
    }

    private void turnOnFlashlight()
    {
        try {
            mCameraManager.setTorch(true);
        }
        catch (Exception e){
            Log.e(TAG,"torch set enable failed");
        }
    }

    private void turnOffFlashlight()
    {
        try {
            mCameraManager.setTorch(false);
        }
        catch (Exception e){
            Log.e(TAG,"torch set disable failed");
        }
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
