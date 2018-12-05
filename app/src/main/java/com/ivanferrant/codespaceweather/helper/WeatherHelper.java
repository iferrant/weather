package com.ivanferrant.codespaceweather.helper;

import com.ivanferrant.codespaceweather.model.CurrentCondition;
import com.ivanferrant.codespaceweather.model.LocationWeather;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WeatherHelper {

    private LocationWeather locationWeather;

    public WeatherHelper(LocationWeather locationWeather) {
        this.locationWeather = locationWeather;
    }

    /**
     * Observation time with format "MMMM dd, HH:MM"
     * @return String with the observation time
     */
    public String getObservationTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd", Locale.getDefault());
        String time = format.format(calendar.getTime());
        CurrentCondition currentCondition = this.getCurrentCondition();
        if (currentCondition != null) {
            return time + ", " + currentCondition.getObservationTime();
        }
        return time;
    }

    /**
     * Gets the current temperature in Celsius
     * @return String with the temperature in Celsius
     */
    public String getCurrentTempC() {
        if (this.getCurrentCondition() != null) {
            return this.getCurrentCondition().getTempC();
        }
        return "";
    }

    /**
     * Gets the current temperature feeling
     * @return String with the temperature feeling
     */
    public String getCurrentTempFeelsC() {
        if (this.getCurrentCondition() != null) {
            return this.getCurrentCondition().getFeelsLikeC();
        }
        return "";
    }

    /**
     * Gets the current weather description
     * @return String with the weather description
     */
    public String getCurrentWeatherDesc() {
        if (this.getCurrentCondition() != null
                && this.getCurrentCondition().getWeatherDesc() != null
                && this.getCurrentCondition().getWeatherDesc().size() > 0) {
            return this.getCurrentCondition().getWeatherDesc().get(0).getValue();
        }
        return "";
    }

    /**
     * Gets the maximum temperature in Celsius for today
     * @return String with the maximum temperature in Celsius
     */
    public String getTodayMaxTempC() {
        if (hasData() && locationWeather.getData().getWeather() != null
                && locationWeather.getData().getWeather().size() > 0) {
            return locationWeather.getData().getWeather().get(0).getMaxtempC();
        }
        return "";
    }

    /**
     * Gets the minimum temperature in Celsius for today
     * @return String with the minimum temperature in Celsius
     */
    public String getTodayMinTempC() {
        if (hasData() && locationWeather.getData().getWeather() != null
                && locationWeather.getData().getWeather().size() > 0) {
            return locationWeather.getData().getWeather().get(0).getMintempC();
        }
        return "";
    }

    private CurrentCondition getCurrentCondition() {
        if ( hasData()
                && locationWeather.getData().getCurrentCondition() != null
                && locationWeather.getData().getCurrentCondition().size() > 0)
            return locationWeather.getData().getCurrentCondition().get(0);
        else return null;
    }

    private boolean hasData() {
        return locationWeather != null && locationWeather.getData() != null;
    }
}
