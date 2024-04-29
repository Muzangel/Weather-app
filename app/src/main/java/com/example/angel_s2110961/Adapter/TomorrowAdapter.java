package com.example.angel_s2110961.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.angel_s2110961.Domains.TomorrowDomain;
import com.example.angel_s2110961.R;

import java.util.ArrayList;

public class TomorrowAdapter extends RecyclerView.Adapter<TomorrowAdapter.ViewHolder> {

    private ArrayList<TomorrowDomain> items;
    private Context context;

    public TomorrowAdapter(ArrayList<TomorrowDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tomorrow, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TomorrowDomain tomorrowItem = items.get(position);

        holder.day.setText(tomorrowItem.getDay());
        holder.status.setText(tomorrowItem.getStatus());
        holder.highTemp.setText(String.valueOf(tomorrowItem.getHighTemp()));
        holder.lowTemp.setText(String.valueOf(tomorrowItem.getLowTemp()));

        int drawableResourceId = holder.itemView.getResources().getIdentifier(tomorrowItem.getPic(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day, status, highTemp, lowTemp;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            status = itemView.findViewById(R.id.status);
            highTemp = itemView.findViewById(R.id.highTemp);
            lowTemp = itemView.findViewById(R.id.lowTemp);
            pic = itemView.findViewById(R.id.imageView14);
        }
    }
}
