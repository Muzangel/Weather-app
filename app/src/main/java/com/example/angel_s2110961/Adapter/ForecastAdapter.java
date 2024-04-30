package com.example.angel_s2110961.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.angel_s2110961.Domains.Forecast;
import com.example.angel_s2110961.R;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    private List<Forecast> forecastList;

    public ForecastAdapter(List<Forecast> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tomorrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Forecast forecast = forecastList.get(position);
        holder.dayTextView.setText(forecast.getDate());
        holder.lowTempTextView.setText(forecast.getMinTemperature());
        holder.highTempTextView.setText(forecast.getMaxTemperature());

        // Set the weather icon
        String status = forecast.getStatus().toLowerCase();
        if (status.contains("sunny")) {
            holder.weatherIconImageView.setImageResource(R.drawable.sun);
        } else if (status.contains("cloudy")) {
            holder.weatherIconImageView.setImageResource(R.drawable.cloudy);
        } else if (status.contains("rainy") || status.contains("rain")) {
            holder.weatherIconImageView.setImageResource(R.drawable.rainy);
        } else if (status.contains("clear")) {
            holder.weatherIconImageView.setImageResource(R.drawable.cloud);
        } else {
            holder.weatherIconImageView.setImageResource(R.drawable.cloudy);
        }

        // Set the temperature bar progress
        int temperature = 0;
        int minTemperature = 0;
        int maxTemperature = 0;

        if (forecast.getTemperature() != null && !forecast.getTemperature().isEmpty()) {
            temperature = Integer.parseInt(forecast.getTemperature().split("°")[0]);
        }

        if (forecast.getMinTemperature() != null && !forecast.getMinTemperature().isEmpty()) {
            minTemperature = Integer.parseInt(forecast.getMinTemperature().split("°")[0]);
        }

        if (forecast.getMaxTemperature() != null && !forecast.getMaxTemperature().isEmpty()) {
            maxTemperature = Integer.parseInt(forecast.getMaxTemperature().split("°")[0]);
        }

        int progress = (temperature - minTemperature) * 100 / (maxTemperature - minTemperature);
        holder.temperatureBar.setProgress(progress);
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayTextView;
        ImageView weatherIconImageView;
        TextView lowTempTextView;
        TextView highTempTextView;
        ProgressBar temperatureBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            weatherIconImageView = itemView.findViewById(R.id.weatherIconImageView);
            lowTempTextView = itemView.findViewById(R.id.lowTempTextView);
            highTempTextView = itemView.findViewById(R.id.highTempTextView);
            temperatureBar = itemView.findViewById(R.id.temperatureBar);
        }
    }
}