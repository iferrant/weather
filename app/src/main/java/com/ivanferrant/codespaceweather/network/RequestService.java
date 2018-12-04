package com.ivanferrant.codespaceweather.network;

import com.ivanferrant.codespaceweather.model.CurrentCondition;
import com.ivanferrant.codespaceweather.model.LocationWeather;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RequestService {

    /**
     * Request current weather of a location
     * @param location Name of the location
     * @return {@link Observable} with a {@link CurrentCondition} object of the {@param location}
     */
    public Observable<CurrentCondition> currentWeather(String location) {
        IEndpoints service = ServiceGenerator.retrofit().create(IEndpoints.class);
        return service.getCountryWeather(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(locationWeather ->{
                    if (locationWeather != null
                            && locationWeather.getData() != null
                            && locationWeather.getData().getCurrentCondition() != null
                            && locationWeather.getData().getCurrentCondition().size() > 0)
                        return locationWeather.getData().getCurrentCondition().get(0);
                    else return new CurrentCondition();
                });
    }
}
