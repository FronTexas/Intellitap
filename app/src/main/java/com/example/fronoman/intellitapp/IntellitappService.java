package com.example.fronoman.intellitapp;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Fahran on 2/13/2015.
 */
public interface IntellitappService {

    @GET("/app/resources/queryTutors")
    void listTutors(@Query("skill") @Nullable String skill,
                    @Query("identifier") @Nullable String identifier,
                    @Query("city") @Nullable String city,
                    @Query("state") @Nullable String state,
                    Callback<ArrayList<Tutor>> callback);

    @POST("/app/resources/newUser/{firstName}-{lastName}/{email}")
    void newUser(@Path("firstName") String firstName,
                 @Path("lastName") String lastName,
                 @Path("email") String email,
                 @Body HashMap<String, String> user,
                 Callback<Object> callback);

    @POST("/app/resources/tutorDetails/{email}")
    void tutorDetails(@Path("email") String email,
                      Callback<Objects> callback);

    // Hashmap contains tagline and about me
    @PUT("/app/resources/updateUser/{email}")
    void updateUser(@Path("email") String email,
                    @Body HashMap<String, String> updates,
                    Callback<Objects> callback);

    @POST("/app/resources/addEmployments")
    void addEmployments(@Query("email") String email,
                        @Body HashMap[] employments,
                        Callback<Object> callback);

    @POST("/app/resources/addEducation")
    void addEducation(@Query("email") String email,
                      @Body HashMap[] education,
                      Callback<Object> callback);


}
