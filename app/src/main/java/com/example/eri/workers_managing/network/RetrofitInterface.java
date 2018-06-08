package com.example.eri.workers_managing.network;


import com.example.eri.workers_managing.model.User;
import com.example.eri.workers_managing.model.Response;

import java.util.ArrayList;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface RetrofitInterface {

    @POST("users")
    Observable<Response> register(@Body User user);

    @POST("authenticate")
    Observable<Response> login();

    @GET("/api/v1/{available}")
    Observable<ArrayList<User>> getProfile(@Path ("available") Boolean available);

    @GET("/api/v1/list/{available}/{sort}")
    Observable<ArrayList<User>> getAllWorkers(@Path("available") Boolean available,@Path ("sort") String sort);   ///radi

    @POST("api/v1/users/all")
    Observable<ArrayList<User>> getListdata(@Body User user);

    @GET("/api/v1/list/{sort_by}")
    Observable<ArrayList<User>> getListSort(@Path ("sort_by") String sort_by);

    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body User user);

    @PUT("users/{email}/job")
    Observable<Response> putJob(@Path("email") String email,@Body User user);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);
}
