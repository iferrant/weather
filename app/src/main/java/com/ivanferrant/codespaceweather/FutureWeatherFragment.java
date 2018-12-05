package com.ivanferrant.codespaceweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivanferrant.codespaceweather.adapter.PredictionListAdapter;
import com.ivanferrant.codespaceweather.helper.WeatherHelper;
import com.ivanferrant.codespaceweather.model.LocationWeather;
import com.ivanferrant.codespaceweather.model.Weather;

import java.util.ArrayList;
import java.util.List;


public class FutureWeatherFragment extends Fragment implements OnLocationWeather{

    private PredictionListAdapter mPredictionListAdapter;
    private RecyclerView mRecyclerView;
    private TextView emptyState;

    public FutureWeatherFragment() {}

    public static FutureWeatherFragment newInstance() {
        return new FutureWeatherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_future_weather, container, false);

        emptyState = root.findViewById(R.id.tv_empty_state);
        mRecyclerView = root.findViewById(R.id.rv_prediction);
        mRecyclerView.setHasFixedSize(true);
        // Set layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // Set empty adapter until the request is completed
        mPredictionListAdapter = new PredictionListAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mPredictionListAdapter);

        return root;
    }

    @Override
    public void onData(LocationWeather locationWeather) {
        WeatherHelper weatherHelper = new WeatherHelper(locationWeather);
        setEmptyState(weatherHelper.hasWeather());
        if (weatherHelper.hasWeather()) {
            setAdapterData(weatherHelper.getWeather());
        }
    }

    private void setAdapterData(List<Weather> weather) {
        // We just want a maximum of 10 days of prediction
        if (weather.size() >= 10) {
            weather = weather.subList(0, 10);
        }
        mPredictionListAdapter = new PredictionListAdapter(weather);
        mRecyclerView.setAdapter(mPredictionListAdapter);
    }

    /**
     * Checks if the {@link LocationWeather} object has weather data
     * @param hasWeather True if there is weather data
     */
    private void setEmptyState(boolean hasWeather) {
        this.mRecyclerView.setVisibility(hasWeather? View.VISIBLE : View.GONE);
        this.emptyState.setVisibility(hasWeather? View.GONE : View.VISIBLE);
    }

}
