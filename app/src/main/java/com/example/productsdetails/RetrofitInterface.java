package com.example.productsdetails;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("login")
    Call<String> Login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("signup")
    Call<String> Signup(@Field("full_name") String fullname, @Field("age") String age  ,@Field("email") String email, @Field("password") String password);
}
