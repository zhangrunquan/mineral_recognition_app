package com.example.mine.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UploadStringService {
    @FormUrlEncoded
    @POST("api/upload/json")
    Call<MineTypeResponse> uploadString(@Field("content") String content);
}
