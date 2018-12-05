package com.ivanferrant.codespaceweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivanferrant.codespaceweather.adapter.PredictionListAdapter;
import com.ivanferrant.codespaceweather.model.LocationWeather;
import com.ivanferrant.codespaceweather.model.Weather;

import java.util.ArrayList;
import java.util.List;


public class FutureWeatherFragment extends Fragment implements OnLocationWeather{

    private PredictionListAdapter mPredictionListAdapter;
    private RecyclerView mRecyclerView;

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
        setAdapterData(locationWeather);
    }

    private void setAdapterData(LocationWeather locationWeather) {
        if (locationWeather != null && locationWeather.getData() != null
                && locationWeather.getData().getWeather() != null) {
            List<Weather> weather = locationWeather.getData().getWeather();
            // We just want a maximum of 10 days of prediction
            if (weather.size() >= 10) {
                weather = weather.subList(0, 10);
            }
            mPredictionListAdapter = new PredictionListAdapter(weather);
            mRecyclerView.setAdapter(mPredictionListAdapter);
        }
    }

}
