package com.example.angel_s2110961.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.angel_s2110961.Adapter.CityParser;
import com.example.angel_s2110961.Adapter.ForecastAdapter;
import com.example.angel_s2110961.Domains.Forecast;
import com.example.angel_s2110961.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class City extends AppCompatActivity {
    private TextView cityNameTextView;
    private RecyclerView recyclerView;
    private ForecastAdapter adapter;
    private List<Forecast> forecastList;
    public String selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityNameTextView = findViewById(R.id.cityNameTextView);
        initRecyclerView();

        // Get the selected city from the intent
        selectedCity = getIntent().getStringExtra("city");

        // Execute weather task for the selected city
        new WeatherTask().execute(selectedCity);

        // Find the back button view
        ImageView backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start MainActivity
                Intent intent = new Intent(City.this, MainActivity.class);
                // Start the activity
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.forecastRecyclerView);
        forecastList = new ArrayList<>();
        adapter = new ForecastAdapter(forecastList, this.getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private class WeatherTask extends AsyncTask<String, Void, List<Forecast>> {

        @Override
        protected List<Forecast> doInBackground(String... params) {
            String city = params[0];
            String rssFeedUrl = getRSSFeedUrl(city);
            Log.d("WeatherTask", "Fetching weather data for city: " + city);
            Log.d("WeatherTask", "RSS Feed URL: " + rssFeedUrl);
            try {
                URL url = new URL(rssFeedUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    CityParser parser = new CityParser();
                    List<Forecast> forecastData = parser.parse(inputStream);
                    Log.d("WeatherTask", "Fetched forecast data size: " + forecastData.size());
                    return forecastData;
                } else {
                    Log.e("WeatherTask", "Failed to fetch weather data. Response Code: " + connection.getResponseCode());
                }
            } catch (IOException | XmlPullParserException e) {
                Log.e("WeatherTask", "Error fetching weather data", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Forecast> forecastData) {
            if (forecastData != null && !forecastData.isEmpty()) {
                Forecast currentForecast = forecastData.get(0);
                updateUI(currentForecast);

                forecastList.clear();
                forecastList.addAll(forecastData.subList(0, forecastData.size()));
                adapter.notifyDataSetChanged();
                Log.d("WeatherTask", "Forecast data updated in the adapter");
            } else {
                Log.d("WeatherTask", "No forecast data available");
            }
        }

        private String getRSSFeedUrl(String city) {
            String rssFeedUrl;
            switch (city.toLowerCase()) { // Ensure the city name is converted to lowercase for matching
                case "london":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743";
                    break;
                case "glasgow":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579";
                    break;
                case "oman":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286";
                    break;
                case "port louis":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154";
                    break;
                case "bangladesh":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241";
                    break;
                case "new york":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581";
                    break;
                default:
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743"; // Default URL
                    break;
            }
            // Validate the URL before returning
            if (rssFeedUrl != null && rssFeedUrl.startsWith("http")) {
                return rssFeedUrl;
            } else {
                Log.e("WeatherTask", "Invalid RSS feed URL for city: " + city);
                return null; // Return null if the URL is invalid
            }
        }


        private void updateUI(Forecast forecast) {
            String selectedCity = getIntent().getStringExtra("city");
            switch (selectedCity) {
                case "London":
                    cityNameTextView.setText("London");
                    break;
                case "Glasgow":
                    cityNameTextView.setText("Glasgow");
                    break;
                case "Oman":
                    cityNameTextView.setText("Oman");
                    break;
                case "Port Louis":
                    cityNameTextView.setText("Port Louis");
                    break;
                case "Bangladesh":
                    cityNameTextView.setText("Bangladesh");
                    break;
                case "NewYork":
                    cityNameTextView.setText("New York");
                    break;
            }
            adapter.notifyDataSetChanged();
        }

    }
}
