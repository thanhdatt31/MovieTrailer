package com.example.movietrailer.remote;

import com.example.movietrailer.model.CastResponse;
import com.example.movietrailer.model.Movie;
import com.example.movietrailer.model.MovieResponse;
import com.example.movietrailer.model.VideoTrailer;
import com.example.movietrailer.model.VideoTrailerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @GET("trending/movie/day?api_key=7b34cb5e4b8dbb12bf16e4bd9d9beb8f")
    Call<MovieResponse> getMovieResponse();

    @GET("discover/movie?api_key=7b34cb5e4b8dbb12bf16e4bd9d9beb8f")
    Call<MovieResponse> getMovieDiscover();

    @GET("movie/top_rated?api_key=7b34cb5e4b8dbb12bf16e4bd9d9beb8f")
    Call<MovieResponse> getMovieTopRated();

    @GET("movie/{movie_id}?api_key=7b34cb5e4b8dbb12bf16e4bd9d9beb8f")
    Call<Movie> getDetailMovie(@Path("movie_id") String id);

    @GET("movie/{movie_id}/videos?api_key=7b34cb5e4b8dbb12bf16e4bd9d9beb8f")
    Call<VideoTrailerResponse> getVideoTrailer(@Path("movie_id") String id);

    @GET("movie/{movie_id}/similar?api_key=7b34cb5e4b8dbb12bf16e4bd9d9beb8f")
    Call<MovieResponse> getSimilarMovie(@Path("movie_id") String id);

    @GET("movie/{movie_id}/credits?api_key=7b34cb5e4b8dbb12bf16e4bd9d9beb8f")
    Call<CastResponse> getCast(@Path("movie_id") String id);

    @GET("search/movie?api_key=7b34cb5e4b8dbb12bf16e4bd9d9beb8f&language=en-US")
    Call<MovieResponse> getResultMovie(@Query("query") String keyword);

}