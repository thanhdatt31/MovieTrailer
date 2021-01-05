package com.example.movietrailer.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movietrailer.DetailsActivity;
import com.example.movietrailer.R;
import com.example.movietrailer.adapter.FavoriteAdapter;
import com.example.movietrailer.database.MovieDatabase;
import com.example.movietrailer.model.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FavoriteFragment extends Fragment {
    private FavoriteAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_favorite, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new FavoriteAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(adapter);

        loadData();
        adapter.setClickEvent(new FavoriteAdapter.OnClickEvent() {
            @Override
            public void onClickItem(Movie movie) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("movie_id",String.valueOf(movie.getId()));
                startActivityForResult(intent,11);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadData();
            swipeRefreshLayout.setRefreshing(false);
        });
        return view;
    }

    // chay duoc roi ma
    // delete xong k update a oi
    // xu ly delêt o dau
    private void loadData() {
        List<Movie> mMovieList =  MovieDatabase.getInstance(getContext()).movieDAO().getListMovie();
        Log.d("thinhvh", "loadData: " + mMovieList.size());
        Collections.reverse(mMovieList);
        adapter.setData(mMovieList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==11 && resultCode == Activity.RESULT_OK){
            Log.d("thinhvh", "onActivityResult: ");
            loadData();
        }
    }
}