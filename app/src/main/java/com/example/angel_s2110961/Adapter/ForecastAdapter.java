package com.example.angel_s2110961.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.angel_s2110961.Activities.Day;
import com.example.angel_s2110961.Domains.Forecast;
import com.example.angel_s2110961.R;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    private List<Forecast> forecastList;
    private OnItemClickListener listener;
    private Context context;
    private String selectedCity;

    public ForecastAdapter(List<Forecast> forecastList, Context context) {
        this.forecastList = forecastList;
        this.context = context;
        this.selectedCity = "Glasgow";
    }

    public interface OnItemClickListener {
        void onItemClick(Forecast forecast);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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
        String currentTemp = forecast.getTemperature();
        if (currentTemp != null && !currentTemp.isEmpty()) {
            // Extract the numeric part of the temperature string
            String[] tempParts = currentTemp.split("\\D+");
            if (tempParts.length > 0) {
                String tempValue = tempParts[0];
                try {
                    // Convert the temperature string to an integer
                    int temperature = Integer.parseInt(tempValue);
                    // Set the progress of the temperature bar
                    holder.temperatureBar.setProgress(temperature);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        holder.itemView.setOnClickListener(item -> {
            System.out.println("Item City clicked Forecast adapter");
            Intent intent = new Intent(context, Day.class);
            intent.putExtra("city", selectedCity);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Start the activity
            context.startActivity(intent);
        });
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