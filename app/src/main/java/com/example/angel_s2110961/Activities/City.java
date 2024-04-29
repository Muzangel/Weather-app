/*
package com.example.angel_s2110961.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.angel_s2110961.Domains.CityDomain;
import com.example.angel_s2110961.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class City extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView cityNameTextView, temperatureTextView, sceneTextView, highLowTextView;
    private ImageView weatherIconImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city);

        initViews();
        setBottomNavigation();

        // Get the city data from the intent
        City city = (City) getIntent().getSerializableExtra("city");
        if (city != null) {
            displayCityDetails(city);
        }
    }

    private void initViews() {
        cityNameTextView = findViewById(R.id.cityNameTextView);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        sceneTextView = findViewById(R.id.sceneTextView);
        highLowTextView = findViewById(R.id.highLowTextView);
        weatherIconImageView = findViewById(R.id.weatherIconImageView);
    }

    private void displayCityDetails(City city) {
        cityNameTextView.setText(city.getTheme());
        temperatureTextView.setText(String.valueOf(city.getTemperature()));
        sceneTextView.setText(city.getWeatherScene());
        highLowTextView.setText(getString(R.string.high_low_temperature, city.getHighTemperature(), city.getLowTemperature()));
        weatherIconImageView.setImageResource(getWeatherIconResource(city.getWeatherIcon()));
    }

private int getWeatherIconResource(String weatherIcon) {
        // Map the weather icon string to the corresponding resource ID
        // You can create a separate utility class or method for this mapping
        return R.drawable.your_weather_icon_drawable;
    }


private void setBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.nav_map:
                startActivity(new Intent(this, MapsActivity.class));
                return true;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }
        return false;
    }

}
*/
