package com.example.shnyagashnyajnaya.OTMAPI;

import com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf.ResponseOTMInf;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceToGetInfoAboutPlaces {
    @GET("0.1/{lang}/places/xid/{xid}")
    Call<ResponseOTMInf> getInfo(
            @Path(value = "xid", encoded = true) String xid,
            @Path(value = "lang", encoded = true) String lang,
            @Query("apikey") String key
    );
}
