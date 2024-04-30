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
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String city);
    }

    public HourlyAdapter(List<Weather> weatherList, OnItemClickListener listener) {
        this.weatherList = weatherList;
        this.listener = listener;
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
        holder.descriptionTextView.setText(currentWeather.getDescription());

        holder.temperature.setText(currentWeather.getTemperature());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(currentWeather.getCityName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;
        TextView date;
        TextView temperature;
        TextView descriptionTextView;
        TextView minTemp;
        TextView maxTemp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityNameTextView);
            temperature = itemView.findViewById(R.id.temperatureTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}