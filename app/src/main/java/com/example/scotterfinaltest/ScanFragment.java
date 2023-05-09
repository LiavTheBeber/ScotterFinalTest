package com.example.scotterfinaltest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class ScanFragment extends Fragment implements SurfaceHolder.Callback {

    private CameraManager cameraManager;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private boolean isFlashlightOn = false;
    private boolean hasFlash;
    private Context context;
    private String cameraId;
    private Boolean CameraPermissionGranted = false;


    private static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;

    private static final int REQUEST_CAMERA_PERMISSION = 200;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);

        context = getContext();
        surfaceView = view.findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();

        // Check for camera permission
        getCameraPermission();

        FloatingActionButton flashButton = view.findViewById(R.id.flashButton);
        hasFlash = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {
            flashButton.setVisibility(View.GONE);
        }

        // Toggle flashlight on/off
        flashButton.setOnClickListener(v -> {
            if (hasFlash) {
                if (isFlashlightOn) {
                    turnOffFlashlight();
                } else {
                    turnOnFlashlight();
                }
            } else {
                Toast.makeText(context, "Flashlight not available", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        try {
            cameraManager = (CameraManager) requireActivity().getSystemService(Context.CAMERA_SERVICE);
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            Toast.makeText(context, "Camera not found", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            cameraManager.setTorchMode(cameraId, true);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        try {
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void turnOnFlashlight() {
        if (hasFlash) {
            try {
                cameraManager.setTorchMode(cameraId, true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            isFlashlightOn = true;
        } else {
            Toast.makeText(context, "Flashlight not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void turnOffFlashlight()
    {
        try {
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        isFlashlightOn = false;
    }

    private void getCameraPermission()
    {
        String[] permissions = {Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(requireContext(),CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED)
        {
            // Open camera
            surfaceHolder.addCallback(this);
            surfaceHolder.setFixedSize(300, 300);
        }

        else
        {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }


}

