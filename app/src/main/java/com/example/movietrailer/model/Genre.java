package com.example.movietrailer.model;

import java.util.List;

public class Genre {
    private String genreTitle;
    private List<Movie> movieList;

    public String getGenreTitle() {
        return genreTitle;
    }

    public void setGenreTitle(String genreTitle) {
        this.genreTitle = genreTitle;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public Genre(String genreTitle, List<Movie> movieList) {
        this.genreTitle = genreTitle;
        this.movieList = movieList;
    }
}
