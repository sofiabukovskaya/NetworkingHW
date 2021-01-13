package com.example.networkinghw.model;

import com.example.networkinghw.data.LoginRequest;
import com.example.networkinghw.data.LoginResponse;
import com.example.networkinghw.data.RegisterRequest;
import com.example.networkinghw.data.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("api-mobile/user/login")
    Call<LoginResponse> emailUser(@Body LoginRequest loginRequest);

    @POST("api-mobile/user/create")
    Call<RegisterResponse> registerUsers(@Body RegisterRequest registerRequest);
}
