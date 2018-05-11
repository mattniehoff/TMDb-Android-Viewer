package com.mattniehoff.tmdbandroidviewer.network;

import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesDatabaseClient {

    @GET("movie/popular")
    Call<TheMovieDatabaseResponse> moviesByPopularity(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<TheMovieDatabaseResponse> moviesByRAting(@Query("api_key") String apiKey);
}
