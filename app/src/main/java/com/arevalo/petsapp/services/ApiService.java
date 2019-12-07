package com.arevalo.petsapp.services;

import com.arevalo.petsapp.models.Pet;
import com.arevalo.petsapp.models.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    String API_BASE_URL = "http://10.0.2.2:8080";

    @POST("/users/")
    Call<User> createUser(@Body User user);

    @GET("/users/{id}")
    Call<User> getUser(@Path("id") Long id);

    @GET("/login/{useremail}/{userpassword}")
    Call<User> login(@Path("useremail") String useremail,@Path("userpassword") String userpassword);

    @FormUrlEncoded
    @POST("/pets")
    Call<Pet> createProducto(@Field("nombre") String nombre,
                             @Field("precio") String precio,
                             @Field("detalles") String detalles
    );

    @Multipart
    @POST("/pets")
    Call<Pet> createProducto(@Part("nombre") RequestBody nombre,
                             @Part("precio") RequestBody precio,
                             @Part("detalles") RequestBody detalles,
                             @Part MultipartBody.Part imagen
    );
}
