package com.example.scotterfinaltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.scotterfinaltest.SettingsFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ScanFragment scanFragment = (ScanFragment) getSupportFragmentManager().findFragmentById(R.id.nav_frame_layout);
    BottomNavigationView bottomNavView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor myEdit;
    String qrCode = "";

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
                else if (item.getItemId() == R.id.nav_scan) {
                    // Handle click on "Scan" item
                    sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    myEdit = sharedPreferences.edit();
                    qrCode = sharedPreferences.getString("qrCode", "");
                    if (!qrCode.isEmpty())
                    {
                        replaceFragment(new ScooterListingFragment());
                    }
                    else {
                        replaceFragment(new ScanFragment());
                    }

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