package com.example.movietrailer.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movietrailer.R;
import com.example.movietrailer.adapter.FavoriteAdapter;
import com.example.movietrailer.database.MovieDatabase;
import com.example.movietrailer.model.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FavoriteFragment extends Fragment {
    private FavoriteAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_favorite, container, false);
        loadData();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void loadData() {
        adapter = new FavoriteAdapter();
        List<Movie> mMovieList =  MovieDatabase.getInstance(getContext()).movieDAO().getListMovie();
        Collections.reverse(mMovieList);
        adapter.setData(mMovieList);
    }

}