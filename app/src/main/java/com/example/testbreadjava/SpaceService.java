package com.example.testbreadjava;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface SpaceService {
    @GET("/planetary/apod")
    Call<SpaceInfo> getImage(@Query("api_key") String key);
}
