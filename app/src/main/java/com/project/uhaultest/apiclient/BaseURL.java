package com.project.uhaultest.apiclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseURL {

    private static final String BASE_URL = "http://jsonplaceholder.typicode.com";

    private static Retrofit getBaseUrl() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static UHaulAPI getAPI() {
        return BaseURL.getBaseUrl().create(UHaulAPI.class);
    }
}
