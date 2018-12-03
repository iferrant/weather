package com.ivanferrant.codespaceweather.network;

import com.ivanferrant.codespaceweather.model.LocationWeather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IEndpoints {

    @GET("weather.ashx")
    Observable<LocationWeather> getCountryWeather(@Query("q") String country);

}
