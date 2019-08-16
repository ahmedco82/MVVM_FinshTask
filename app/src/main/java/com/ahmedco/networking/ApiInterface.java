package com.ahmedco.networking;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    //String JSONURL = "http://legacy.vibuy.com/dump/";
    @GET("mobiletest1.json")
    Call<String> getString();
}
























