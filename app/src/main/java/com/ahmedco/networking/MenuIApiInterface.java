package com.ahmedco.networking;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MenuIApiInterface {
    @GET("mobiletest1.json")
    Call<String> getString();
}
























