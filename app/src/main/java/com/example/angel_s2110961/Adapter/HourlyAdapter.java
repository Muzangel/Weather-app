package com.example.angel_s2110961.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.angel_s2110961.Domains.Weather;
import com.example.angel_s2110961.R;

import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {
    private List<Weather> weatherList;

    public HourlyAdapter(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eachcity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Weather currentWeather = weatherList.get(position);
        holder.cityName.setText(currentWeather.getCityName());
        holder.date.setText(currentWeather.getDate());
        holder.temperature.setText(currentWeather.getTemperature());
        holder.minTemp.setText("L: " + currentWeather.getMinTemp());
        holder.maxTemp.setText("H: " + currentWeather.getMaxTemp());
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;
        TextView date;
        TextView temperature;
        TextView minTemp;
        TextView maxTemp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityNameTextView);
            date = itemView.findViewById(R.id.dateTextView);
            temperature = itemView.findViewById(R.id.temperatureTextView);
            minTemp = itemView.findViewById(R.id.minTempTextView);
            maxTemp = itemView.findViewById(R.id.maxTempTextView);
        }
    }
}