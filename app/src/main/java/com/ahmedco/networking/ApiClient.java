package com.ahmedco.networking;


import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "http://legacy.vibuy.com/dump/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build();
        }
        return retrofit;
    }
}