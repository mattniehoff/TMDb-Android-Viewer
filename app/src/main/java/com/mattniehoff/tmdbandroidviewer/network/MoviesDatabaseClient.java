package com.mattniehoff.tmdbandroidviewer.network;

import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseResponse;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseReviewsResponse;
import com.mattniehoff.tmdbandroidviewer.model.TheMovieDatabaseVideosResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// Using https://guides.codepath.com/android/Consuming-APIs-with-Retrofit for syntax help.
public interface MoviesDatabaseClient {

    @GET("movie/popular")
    Call<TheMovieDatabaseResponse> moviesByPopularity(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<TheMovieDatabaseResponse> moviesByRating(@Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<TheMovieDatabaseReviewsResponse> reviewsByMovieId(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<TheMovieDatabaseVideosResponse> videosByMovieId(@Path("id") int movieId, @Query("api_key") String apiKey);
}
