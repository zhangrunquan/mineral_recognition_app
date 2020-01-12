package com.example.mine.network;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadFileService {
        @Multipart
//    @FormUrlEncoded

    @POST("api/upload/image")
    Call<MineTypeResponse> uploadFile(
            @Part MultipartBody.Part file
    );

//    @FormUrlEncoded
//    @POST("api/upload/image")
//    Call<MineTypeResponse> uploadFile(@Field("content") String content);
}
