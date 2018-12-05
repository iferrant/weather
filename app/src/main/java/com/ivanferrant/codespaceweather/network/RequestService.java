package com.ivanferrant.codespaceweather.network;

import com.ivanferrant.codespaceweather.model.LocationWeather;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RequestService {

    /**
     * Request current weather of a location
     * @param location Name of the location
     * @return {@link Observable} with a {@link LocationWeather} object of the {@param location}
     */
    public Observable<LocationWeather> currentWeather(String location) {
        IEndpoints service = ServiceGenerator.retrofit().create(IEndpoints.class);
        return service.getCountryWeather(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
