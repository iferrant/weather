package com.ivanferrant.codespaceweather.network;

import com.ivanferrant.codespaceweather.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class WorldWeatherInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("key", BuildConfig.WORLDWEATHER_KEY)
                .addQueryParameter("format", "json")
                .build();

        // Add new headers and perform request
        return chain.proceed(original.newBuilder().url(url).build());
    }
}
