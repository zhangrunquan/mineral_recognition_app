package com.example.mine.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL_API = "https:192.168.0.107:5000/";

    public static Retrofit getRetrofitInstance() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HTTPSCerUtils.setTrustAllCertificate(builder);

        OkHttpClient client = builder
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).build();

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL_API).client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
