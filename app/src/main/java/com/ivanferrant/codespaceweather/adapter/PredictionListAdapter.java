package com.ivanferrant.codespaceweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivanferrant.codespaceweather.R;
import com.ivanferrant.codespaceweather.model.Hourly;
import com.ivanferrant.codespaceweather.model.Weather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PredictionListAdapter extends
        RecyclerView.Adapter<PredictionListAdapter.ItemViewHolder> {

    /**
     * Adapter viewholder
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public TextView max;
        public TextView desc;
        public TextView min;

        public ItemViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tv_prediction_date);
            max = itemView.findViewById(R.id.tv_prediction_max);
            desc = itemView.findViewById(R.id.tv_prediction_desc);
            min = itemView.findViewById(R.id.tv_prediction_min);
        }
    }

    private List<Weather> weatherDataset;

    public PredictionListAdapter(List<Weather> weatherDataset) {
        this.weatherDataset = weatherDataset;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_prediction_list, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        this.setItemData(weatherDataset.get(position), holder);
    }

    @Override
    public int getItemCount() {
        return weatherDataset.size();
    }

    private void setItemData(Weather weather, ItemViewHolder holder) {
        // Format date to show weekday
        String date = weather.getDate();
        String time = date;
        SimpleDateFormat input = new SimpleDateFormat("yy-MM-dd", Locale.getDefault());
        SimpleDateFormat output = new SimpleDateFormat("EEEE, dd", Locale.getDefault());
        try {
            Date oneWayTripDate = input.parse(date);
            time = output.format(oneWayTripDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.date.setText(time);

        // Set temperatures
        String max = weather.getMaxtempC();
        holder.max.setText(max);
        String min = weather.getMintempC();
        holder.min.setText(min);

        // Get weather description from the first prediction of the day
        if (weather.getHourly() != null && weather.getHourly().size() > 0) {
            List<Hourly> hourly = weather.getHourly();
            if (hourly.size() > 0
                    && hourly.get(0) != null
                    && hourly.get(0).getWeatherDesc() != null
                    && hourly.get(0).getWeatherDesc().size() > 0)
            holder.desc.setText(weather.getHourly().get(0).getWeatherDesc().get(0).getValue());
        }
    }
}
