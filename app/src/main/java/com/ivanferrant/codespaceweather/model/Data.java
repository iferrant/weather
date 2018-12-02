package com.ivanferrant.codespaceweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("request")
    @Expose
    private List<Request> requests;

    @SerializedName("current_condition")
    @Expose
    private List<CurrentCondition> currentCondition;

    @SerializedName("weather")
    @Expose
    private List<Weather> weather;

    @SerializedName("ClimateAverage")
    @Expose
    private List<ClimateAverage> climateAverage;

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public List<CurrentCondition> getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(List<CurrentCondition> currentCondition) {
        this.currentCondition = currentCondition;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public List<ClimateAverage> getClimateAverage() {
        return climateAverage;
    }

    public void setClimateAverage(List<ClimateAverage> climateAverage) {
        this.climateAverage = climateAverage;
    }
}
