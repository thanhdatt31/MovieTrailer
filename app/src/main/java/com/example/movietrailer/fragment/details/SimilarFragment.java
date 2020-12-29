package com.example.movietrailer.fragment.details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movietrailer.R;
import com.example.movietrailer.adapter.SimilarAdapter;
import com.example.movietrailer.model.Movie;
import com.example.movietrailer.model.MovieResponse;
import com.example.movietrailer.remote.APIService;
import com.example.movietrailer.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SimilarFragment extends Fragment {
    private final APIService apiService = RetrofitClient
            .getClient()
            .create(APIService.class);
    private RecyclerView recyclerView;
    private SimilarAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_similar, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        String movie_id = getArguments().getString("movie_id");
        apiService.getSimilarMovie(movie_id).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movieList = response.body().getResults();
                initView(movieList);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
        return view;
    }

    private void initView(List<Movie> movieList) {
        adapter = new SimilarAdapter();
        adapter.setData(movieList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(adapter);
    }


}