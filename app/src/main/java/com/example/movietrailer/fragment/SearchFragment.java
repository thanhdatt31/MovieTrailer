package com.example.movietrailer.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.movietrailer.R;
import com.example.movietrailer.adapter.SearchAdapter;
import com.example.movietrailer.model.Movie;
import com.example.movietrailer.model.MovieResponse;
import com.example.movietrailer.remote.APIService;
import com.example.movietrailer.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private final APIService apiService = RetrofitClient
            .getClient()
            .create(APIService.class);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_search, container, false);
       EditText edt_search = view.findViewById(R.id.edt_search);
        Button btn_search = view.findViewById(R.id.btn_search);
       recyclerView = view.findViewById(R.id.recyclerview);
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       adapter = new SearchAdapter();
       btn_search.setOnClickListener(v -> searchMovieByKeyword(edt_search.getText().toString().trim()));
       return view;
    }

    private void searchMovieByKeyword(String keyword) {
        apiService.getResultMovie(keyword).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.body() != null){
                    List<Movie> movieList = response.body().getResults();
                    adapter.setData(movieList);
                    recyclerView.setAdapter(adapter);
                }


            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }
}