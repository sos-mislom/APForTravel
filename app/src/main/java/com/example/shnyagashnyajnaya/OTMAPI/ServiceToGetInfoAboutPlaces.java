package com.example.shnyagashnyajnaya.OTMAPI;

import com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf.ResponseOTMInf;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceToGetInfoAboutPlaces {
    @GET("0.1/ru/places/xid/{xid}")
    Call<ResponseOTMInf> getInfo(
            @Path(value = "xid", encoded = true) String xid,
            @Query("apikey") String key
    );
}
