package com.arevalo.petsapp.services;

import com.arevalo.petsapp.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    String API_BASE_URL = "http://10.0.2.2:8080";

    @POST("/users/")
    Call<User> createUser(@Body User user);

}
