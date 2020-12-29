package com.example.movietrailer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoTrailerResponse {@SerializedName("id")
@Expose
private Integer id;
    @SerializedName("results")
    @Expose
    private List<VideoTrailer> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<VideoTrailer> getResults() {
        return results;
    }

    public void setResults(List<VideoTrailer> results) {
        this.results = results;
    }

}
