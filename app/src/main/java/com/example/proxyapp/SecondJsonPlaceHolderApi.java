package com.example.proxyapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SecondJsonPlaceHolderApi {


    @GET("content")
    Call<List<PostModel>> getJsons();


    @POST("content")
    Call<PostModel> sendJsonToServer(@Body PostModel postModel);

}
