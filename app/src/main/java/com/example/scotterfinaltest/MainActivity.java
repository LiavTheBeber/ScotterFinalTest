package com.example.scotterfinaltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.scotterfinaltest.SettingsFragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavView;

    //private MenuItem itemId;
    private MenuItem itemId;

    private String currentFragmentTag;
    private static final String TAG_MAP_FRAGMENT = "MAP_FRAGMENT";
    private static final String TAG_SCAN_FRAGMENT = "SCAN_FRAGMENT";
    private static final String TAG_SETTINGS_FRAGMENT = "SETTINGS_FRAGMENT";

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
                    replaceFragment(new ScanFragment());
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
