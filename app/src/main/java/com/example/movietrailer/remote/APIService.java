package com.example.movietrailer.remote;

import com.example.movietrailer.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("trending/movie/day?api_key=7b34cb5e4b8dbb12bf16e4bd9d9beb8f")
    Call<MovieResponse> getMovieResponse();

    @GET("discover/movie?api_key=7b34cb5e4b8dbb12bf16e4bd9d9beb8f")
    Call<MovieResponse> getMovieDiscover();

    @GET("movie/top_rated?api_key=7b34cb5e4b8dbb12bf16e4bd9d9beb8f")
    Call<MovieResponse> getMovieTopRated();
}