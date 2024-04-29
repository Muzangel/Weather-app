package com.example.angel_s2110961.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.angel_s2110961.Adapter.HourlyAdapter;
import com.example.angel_s2110961.Adapter.PullParser;
import com.example.angel_s2110961.Domains.Weather;
import com.example.angel_s2110961.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HourlyAdapter adapter;
    private List<Weather> weatherList;

    private static final String[] CITIES = {"London", "Glasgow", "Oman", "Mauritius", "Bangladesh", "NewYork"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city);

        // Set activity to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Initialize RecyclerView
        initRecyclerView();

        // Execute weather tasks for all cities
        for (String city : CITIES) {
            new WeatherTask().execute(city);
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.cities);
        weatherList = new ArrayList<>();
        adapter = new HourlyAdapter(weatherList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private class WeatherTask extends AsyncTask<String, Void, List<Weather>> {

        @Override
        protected List<Weather> doInBackground(String... params) {
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
                    PullParser parser = new PullParser();
                    List<Weather> weatherData = parser.parse(inputStream);
                    Log.d("WeatherTask", "Fetched weather data size: " + weatherData.size());
                    return weatherData;
                } else {
                    Log.e("WeatherTask", "Failed to fetch weather data. Response Code: " + connection.getResponseCode());
                }
            } catch (IOException | XmlPullParserException e) {
                Log.e("WeatherTask", "Error fetching weather data", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Weather> weatherData) {
            if (weatherData != null && !weatherData.isEmpty()) {
                weatherList.clear();
                weatherList.addAll(weatherData);
                adapter.notifyDataSetChanged();
                Log.d("WeatherTask", "Weather data updated in the adapter");
            } else {
                Log.d("WeatherTask", "No weather data available");
            }
        }
        private String getRSSFeedUrl(String city) {
            String rssFeedUrl;
            switch (city) {
                case "London":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743";
                    break;
                case "Glasgow":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579";
                    break;
                case "Oman":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286";
                    break;
                case "Mauritius":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154";
                    break;
                case "Bangladesh":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241";
                    break;
                case "NewYork":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581";
                    break;
                default:
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643123";
                    break;
            }
            return rssFeedUrl;
        }
    }
}