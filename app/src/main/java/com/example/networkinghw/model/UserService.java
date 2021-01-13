package com.example.networkinghw.model;

import com.example.networkinghw.data.LoginRequest;
import com.example.networkinghw.data.LoginResponse;
import com.example.networkinghw.data.RegisterRequest;
import com.example.networkinghw.data.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @POST("api-mobile/user/login")
    Call<LoginResponse> emailUser(@Body LoginRequest loginRequest);

    @POST("api-mobile/user/create")
    Call<RegisterResponse> registerUsers(@Body RegisterRequest registerRequest);

    @GET("api-mobile/user/login")
    Call<LoginResponse> getToken(@Header("device_token") String authToken);

    @POST("/api-mobile/user/logout")
    Call<LoginResponse> logOutUser(@Header("device_token") String token);
}
