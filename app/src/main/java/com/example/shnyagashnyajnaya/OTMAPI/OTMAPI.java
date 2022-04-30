package com.example.shnyagashnyajnaya.OTMAPI;

import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.HOST_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OTMAPI {
    public static <T> T CreateService(Class<T> serviceClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

}
