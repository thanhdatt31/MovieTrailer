package com.example.movietrailer.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movietrailer.model.Movie;

import java.util.List;

@Dao
public interface MovieDAO {
    @Insert
    void insertMovie(Movie movie);

    @Query("SELECT * FROM movie")
    List<Movie> getListMovie();

    @Query("SELECT * FROM movie where id = :id")
    List<Movie> checkMovieById(String id);
}
