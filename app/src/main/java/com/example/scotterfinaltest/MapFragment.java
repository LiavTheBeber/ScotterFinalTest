package com.example.scotterfinaltest;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Map;

public class MapFragment extends Fragment
{

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15f;
    UiModeManager uiModeManager;
    boolean isNightMode;

    private GoogleMap mMap;


    private OnMapReadyCallback callback = new OnMapReadyCallback()
    {
        @Override
        public void onMapReady(GoogleMap googleMap) {

            mMap = googleMap;

            if (mLocationPermissionGranted) {
                getDeviceLocation();

                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);


                addCustomMarker(new LatLng(31.687312, 34.582416) , "Scooter No.1", "Cost: 5₪ For Start, 1₪ Per Minute \nEnjoy Your Ride!!");
                addCustomMarker(new LatLng(31.688528, 34.582913) , "Scooter No.2", "Cost: 5₪ For Start, 1₪ Per Minute \nEnjoy Your Ride!!");
            }
        }
    };



    private void addCustomMarker(LatLng position, String title, String snippet) {
        // Create a marker options object
        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .title(title)
                .snippet(snippet);

        // Load marker icon as a Bitmap
        Bitmap markerIcon = BitmapFactory.decodeResource(getResources(), R.drawable.scootermarkerresized);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(markerIcon));

        // Add the marker to the map
        mMap.addMarker(markerOptions);




        // Set a custom info window adapter
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Nullable
            @Override
            public View getInfoContents(@NonNull Marker marker) {
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);

                // Customize the views in the info window layout
                TextView titleTextView = infoWindow.findViewById(R.id.textViewTitle);
                TextView descriptionTextView = infoWindow.findViewById(R.id.textViewDescription);

                // Set the title, and description
                titleTextView.setText(marker.getTitle());
                descriptionTextView.setText(marker.getSnippet());

                return infoWindow;
            }

            @Override
            public View getInfoWindow(Marker marker) {
                return null; // Return null to use default info window
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(@NonNull Marker marker) {

                moveCamera(new LatLng(position.latitude , position.longitude), DEFAULT_ZOOM);

                // Handle info window click event
                showDialogWithInfoWindowData(marker);
            }
        });
    }

    private void showDialogWithInfoWindowData(Marker marker)
    {
        // Create a Dialog object
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        // Inflate the custom_info_window.xml layout
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_info_window, null);

        // Find and set the data from the marker to the dialog view
        TextView titleTextView = dialogView.findViewById(R.id.textViewTitle);
        TextView snippetTextView = dialogView.findViewById(R.id.textViewDescription);
        // Assuming you have TextViews with id 'titleTextView' and 'snippetTextView' in your custom_info_window.xml
        titleTextView.setText(marker.getTitle());
        snippetTextView.setText(marker.getSnippet());

        // Set the custom view to the dialog
        builder.setView(dialogView);

        // Add any additional configuration to the dialog (e.g., buttons, listeners, etc.)
        // For example, you can add buttons to perform actions or dismiss the dialog.
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle button click event
                dialog.dismiss();
            }
        });

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getLocationPermission();
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(requireContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(requireContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            }
        } else {
            requestPermissionLauncher.launch(permissions);
        }
    }

    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationTask = mFusedLocationProviderClient.getLastLocation();
                locationTask.addOnCompleteListener(requireActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = task.getResult();
                            if (currentLocation != null) {
                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                            } else {
                                // Handle case when location is null
                            }
                        } else {
                            // Handle unsuccessful task
                            Log.e(TAG, "getLastLocation: Exception: " + task.getException());
                        }
                    }
                });
            }

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCameraAnimated(LatLng latLng, float zoom) {
        if (mMap != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(zoom)
                    .build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        if (mMap != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                    new ActivityResultCallback<Map<String, Boolean>>() {
                        @Override
                        public void onActivityResult(Map<String, Boolean> isGranted) {
                            boolean allGranted = true;
                            for (Boolean granted : isGranted.values()) {
                                if (!granted) {
                                    mLocationPermissionGranted = false;
                                    allGranted = false;
                                    break;
                                }
                            }
                            if (allGranted) {
                                // Permissions are granted, do something here
                                mLocationPermissionGranted = true;
                                initMap();
                            }
                        }
                    });
}
