package com.ivanferrant.codespaceweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivanferrant.codespaceweather.helper.WeatherHelper;
import com.ivanferrant.codespaceweather.model.LocationWeather;


public class CurrentWeatherFragment extends Fragment implements OnLocationWeather {

    // Views
    private TextView time;
    private TextView maxMinTemp;
    private TextView temperature;
    private TextView feelsLike;
    private TextView weatherDesc;

    public CurrentWeatherFragment() {}

    public static CurrentWeatherFragment newInstance() {
        return new CurrentWeatherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_current_weather, container, false);
        this.time = root.findViewById(R.id.tv_time);
        this.maxMinTemp = root.findViewById(R.id.tv_max_min_temp);
        this.temperature = root.findViewById(R.id.tv_temperature);
        this.feelsLike = root.findViewById(R.id.tv_temp_feel);
        this.weatherDesc = root.findViewById(R.id.tv_weather_desc);
        return root;
    }


    @Override
    public void onData(LocationWeather locationWeather) {
        setWeatherValues(locationWeather);
    }


    /**
     * Set the view with the values of {@link LocationWeather}
     * @param locationWeather {@link LocationWeather} object with the weather condition
     */
    private void setWeatherValues(LocationWeather locationWeather) {
        WeatherHelper weatherHelper = new WeatherHelper(locationWeather);
        // Format temperatures
        String maxMin = String.format(
                this.getResources().getString(R.string.min_max_temperature_c),
                weatherHelper.getTodayMaxTempC(),
                weatherHelper.getTodayMinTempC());
        String currentTemp = String.format(
                this.getResources().getString(R.string.current_temp_c),
                weatherHelper.getCurrentTempC());
        String feels = String.format(
                this.getResources().getString(R.string.temp_feels_c),
                weatherHelper.getCurrentTempFeelsC());

        // Set views
        this.time.setText(weatherHelper.getObservationTime());
        this.maxMinTemp.setText(maxMin);
        this.temperature.setText(currentTemp);
        this.feelsLike.setText(feels);
        this.weatherDesc.setText(weatherHelper.getCurrentWeatherDesc());
    }

}
