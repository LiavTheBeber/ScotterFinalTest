package com.example.scotterfinaltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavView = findViewById(R.id.bottomNavigationView);
        replaceFragment(new MapFragment());

        bottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_map) {
                    // Handle click on "Map" item
                    replaceFragment(new MapFragment());
                    return true;
                }
                else if (item.getItemId() == R.id.nav_scooter) {
                    // Handle click on "Scooter" item
                    replaceFragment(new ScooterFragment());
                    return true;
                }
                else if (item.getItemId() == R.id.nav_scan) {
                    // Handle click on "Scan" item
                    replaceFragment(new ScanFragment());
                    return true;
                }
                else if (item.getItemId() == R.id.nav_profile) {
                    // Handle click on "Profile" item
                    replaceFragment(new ProfileFragment());
                    return true;
                }
                else if (item.getItemId() == R.id.nav_settings) {
                    // Handle click on "Settings" item
                    replaceFragment(new SettingsFragment());
                    return true;
                }
                return false;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_frame_layout, fragment)
                .commit();
    }





}
