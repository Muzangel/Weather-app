package com.example.angel_s2110961.Activities;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.EditText;

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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GoogleMaps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private EditText searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        searchView = findViewById(R.id.searchmap);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        // Create LatLng objects for each city
        LatLng glasgow = new LatLng(55.8642, -4.2518);
        LatLng london = new LatLng(51.5074, -0.1278);
        LatLng oman = new LatLng(21.4735, 55.9754);
        LatLng newYork = new LatLng(40.7128, -74.0060);
        LatLng mumbai = new LatLng(19.0760, 72.8777);
        LatLng bangladesh = new LatLng(23.6850, 90.3563);

        // Add markers for each city
        myMap.addMarker(new MarkerOptions().position(glasgow).title("Glasgow"));
        myMap.addMarker(new MarkerOptions().position(london).title("London"));
        myMap.addMarker(new MarkerOptions().position(oman).title("Oman"));
        myMap.addMarker(new MarkerOptions().position(newYork).title("New York"));
        myMap.addMarker(new MarkerOptions().position(mumbai).title("Mumbai"));
        myMap.addMarker(new MarkerOptions().position(bangladesh).title("Bangladesh"));

        // Move camera to a suitable position to view all markers
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(glasgow);
        builder.include(london);
        builder.include(oman);
        builder.include(newYork);
        builder.include(mumbai);
        builder.include(bangladesh);
        LatLngBounds bounds = builder.build();
        int padding = 100;
        myMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));

        myMap.setOnCameraIdleListener(() -> {
            LatLng center = myMap.getCameraPosition().target;
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocation(center.latitude, center.longitude, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    String addressString = address.getAddressLine(0);
                    searchView.setText(addressString);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}