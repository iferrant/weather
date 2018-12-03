package com.ivanferrant.codespaceweather.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.worldweatheronline.com/premium/v1/";

    /**
     * Creates an instance of Retrofit object
     * */
    public static Retrofit retrofit() {
        if (retrofit == null) {
            // Create interceptor to add headers (API key, response format...)
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new WorldWeatherInterceptor());
            OkHttpClient headersInterceptor = httpClient.build();

            // Build and configure retrofit service
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(headersInterceptor)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
