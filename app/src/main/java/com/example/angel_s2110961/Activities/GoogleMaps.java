package com.example.angel_s2110961.Activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.angel_s2110961.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GoogleMaps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*searchInput = findViewById(R.id.searchmap);
        searchInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = searchInput.getText().toString();
                if (!query.isEmpty()) {
                    searchLocation(query);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a location", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });*/

        bottomMenu();
    }

    private void bottomMenu() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    // Handle the Home menu item click
                    Intent mapIntent = new Intent(GoogleMaps.this, MainActivity.class);
                    startActivity(mapIntent);
                    return true;
                } else if (item.getItemId() == R.id.map) {
                    // Handle the Map menu item click

                    return true;
                }
                return false;
            }
        });
    }

    private void searchLocation(String location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(location, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                myMap.addMarker(new MarkerOptions().position(latLng).title(location));
                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
            } else {
                Toast.makeText(getApplicationContext(), "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        // Initialize markers for predefined cities
        LatLng glasgow = new LatLng(55.8642, -4.2518);
        LatLng london = new LatLng(51.5074, -0.1278);
        LatLng oman = new LatLng(21.4735, 55.9754);
        LatLng newYork = new LatLng(40.7128, -74.0060);
        LatLng portLouis = new LatLng(-20.1653, 57.4896);
        LatLng bangladesh = new LatLng(23.6850, 90.3563);

        // Add markers for predefined cities
        myMap.addMarker(new MarkerOptions().position(glasgow).title("Glasgow"));
        myMap.addMarker(new MarkerOptions().position(london).title("London"));
        myMap.addMarker(new MarkerOptions().position(oman).title("Oman"));
        myMap.addMarker(new MarkerOptions().position(newYork).title("New York"));
        myMap.addMarker(new MarkerOptions().position(portLouis).title("Port Louis"));
        myMap.addMarker(new MarkerOptions().position(bangladesh).title("Bangladesh"));

        // Move camera to a suitable position to view all markers
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(glasgow);
        builder.include(london);
        builder.include(oman);
        builder.include(newYork);
        builder.include(portLouis);
        builder.include(bangladesh);
        LatLngBounds bounds = builder.build();
        int padding = 100;
        myMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
    }
}
