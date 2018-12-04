package com.ivanferrant.codespaceweather;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FutureWeatherFragment extends Fragment {

    public FutureWeatherFragment() {}

    public static FutureWeatherFragment newInstance() {
        FutureWeatherFragment fragment = new FutureWeatherFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_future_weather, container, false);
    }

}
