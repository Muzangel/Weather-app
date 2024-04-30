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

import com.example.angel_s2110961.Adapter.DayParser;
import com.example.angel_s2110961.Adapter.DayAdapter;
import com.example.angel_s2110961.Domains.DayDomain;
import com.example.angel_s2110961.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Day extends AppCompatActivity {
    private TextView cityNameTextView;
    private TextView dayTextView;
    private RecyclerView recyclerView;
    private DayAdapter adapter;
    private List<DayDomain> dayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String selectedCity = getIntent().getStringExtra("city");

        setContentView(R.layout.days);

        cityNameTextView = findViewById(R.id.cityNameTextView);
        dayTextView = findViewById(R.id.dayTextView);
        initRecyclerView();

        // Get the selected city from the intent
        int dayIndex = getIntent().getIntExtra("dayIndex", 0);

        // Execute weather task for the selected city
        new WeatherTask().execute(selectedCity, dayIndex);

        // Find the back button view
        ImageView backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start MainActivity
                Intent intent = new Intent(Day.this, City.class);
                // Start the activity
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.dayRecyclerView);
        dayList = new ArrayList<>();
        adapter = new DayAdapter(dayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    private class WeatherTask extends AsyncTask<Object, Void, DayDomain> {

        @Override
        protected DayDomain doInBackground(Object... params) {
            String city = (String) params[0];
            int dayIndex = (int) params[1];
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
                    DayParser parser = new DayParser();
                    List<DayDomain> dayData = parser.parse(inputStream);
                    Log.d("WeatherTask", "Fetched Day data size: " + dayData.size());
                    if (!dayData.isEmpty() && dayIndex < dayData.size()) {
                        return dayData.get(dayIndex);
                    }
                } else {
                    Log.e("WeatherTask", "Failed to fetch weather data. Response Code: " + connection.getResponseCode());
                }
            } catch (IOException | XmlPullParserException e) {
                Log.e("WeatherTask", "Error fetching weather data", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(DayDomain selectedDay) {
            if (selectedDay != null) {
                updateUI(selectedDay);
                dayList.clear();
                dayList.add(selectedDay);
                adapter.notifyDataSetChanged();
                Log.d("WeatherTask", "Day data updated in the adapter");
            } else {
                Log.d("WeatherTask", "No Day data available");
            }
        }

    }



    private String getRSSFeedUrl(String city) {
            String rssFeedUrl;
            switch (city) {
                case "London":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/2643743";
                    break;
                case "Glasgow":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/2648579";
                    break;
                case "Oman":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/287286";
                    break;
                case "Port Louis":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/934154";
                    break;
                case "Bangladesh":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/1185241";
                    break;
                case "NewYork":
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/5128581";
                    break;
                default:
                    rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/2643743";
                    break;
            }
            return rssFeedUrl;

    }

    private void updateUI(DayDomain day) {
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
        dayTextView.setText(day.getDate());
        adapter.notifyDataSetChanged();
    }
}