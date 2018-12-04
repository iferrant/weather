package com.ivanferrant.codespaceweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivanferrant.codespaceweather.model.CurrentCondition;
import com.ivanferrant.codespaceweather.network.RequestService;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class CurrentWeatherFragment extends Fragment {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    // Views
    private TextView temperature;

    public CurrentWeatherFragment() {}

    public static CurrentWeatherFragment newInstance() {
        return new CurrentWeatherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestCurrentWeather();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_current_weather, container, false);
        this.temperature = root.findViewById(R.id.tv_temp);
        return root;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    /**
     * Request current weather of an specific location
     */
    private void requestCurrentWeather() {
        RequestService requestService = new RequestService();
        Disposable d = requestService.currentWeather("").subscribe(
                this::setWeatherValues,
                this::handleError
        );
        compositeDisposable.add(d);
    }

    /**
     * Set the view with the values of {@link CurrentCondition}
     * @param currentCondition {@link CurrentCondition} object with the weather condition
     */
    private void setWeatherValues(CurrentCondition currentCondition) {
        this.temperature.setText(currentCondition.getTempC());
    }

    /**
     * Handle request error
     * @param t {@link Throwable} object
     */
    private void handleError(Throwable t) {
        if (getActivity() != null) {
            Snackbar.make(getActivity().findViewById(android.R.id.content),
                    "Something went wrong", Snackbar.LENGTH_LONG).show();
        }
    }

}
