package com.example.angel_s2110961.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.angel_s2110961.Domains.DayDomain;
import com.example.angel_s2110961.R;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {
    private List<DayDomain> dayList;

    public DayAdapter(List<DayDomain> dayList) {
        this.dayList = dayList;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_cards, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        DayDomain day = dayList.get(position);
        holder.bind(day);
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        private TextView dateTextView;
        private TextView temperatureTextView;
        private TextView descriptionTextView;
        private TextView windSpeedTextView;
        private TextView pressureTextView;
        private TextView visibilityTextView;
        private TextView humidityTextView;
        private TextView uvRiskTextView;
        private TextView sunsetTextView;
        private TextView windDirectionTextView;
        private TextView pollutionTextView;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            temperatureTextView = itemView.findViewById(R.id.temperatureTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            windSpeedTextView = itemView.findViewById(R.id.windSpeedTextView);
            pressureTextView = itemView.findViewById(R.id.pressureTextView);
            visibilityTextView = itemView.findViewById(R.id.visibilityTextView);
            humidityTextView = itemView.findViewById(R.id.humidityTextView);

            windDirectionTextView = itemView.findViewById(R.id.windDirectionTextView);
        }

        public void bind(DayDomain day) {
            if (dateTextView != null) {
                dateTextView.setText(day.getDate());
            }
            if (temperatureTextView != null) {
                temperatureTextView.setText(day.getTemperature());
            }
            if (descriptionTextView != null) {
                descriptionTextView.setText(day.getDescription());
            }
            if (windSpeedTextView != null) {
                windSpeedTextView.setText(day.getWindSpeed());
            }
            if (pressureTextView != null) {
                pressureTextView.setText(day.getPressure());
            }
            if (visibilityTextView != null) {
                visibilityTextView.setText(day.getVisibility());
            }
            if (humidityTextView != null) {
                humidityTextView.setText(day.getHumidity());
            }
            if (uvRiskTextView != null) {
                uvRiskTextView.setText(day.getUvRisk());
            }
            if (sunsetTextView != null) {
                sunsetTextView.setText(day.getSunset());
            }
            if (windDirectionTextView != null) {
                windDirectionTextView.setText(day.getWindDirection());
            }
        }
    }
}