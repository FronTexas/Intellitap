package com.example.fronoman.intellitap;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
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


}
