package com.machinetest.ad_movie.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public class RetrofitClientInstance {
    private static Retrofit retrofit, retrofitOut;
    private static final String BASE_URL = "https://ambassadorkw.com/adminpanel/index.php/";

    public static Retrofit getRetrofitInstance() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor).build();
        Gson gson = new GsonBuilder()
            .setLenient()
            .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)

                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        }
        return retrofit;
    }



    public interface GetDataService {


        @GET
        Call<JsonObject> get_trending(@Url String url);


    }

}
