package com.example.movietrailer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietrailer.DetailsActivity;
import com.example.movietrailer.OnClickEvent;
import com.example.movietrailer.R;
import com.example.movietrailer.model.Genre;
import com.example.movietrailer.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
    private List<Genre> genreList = new ArrayList<>();
    private Context mContext;
    private OnClickEvent clickEvent;

    public void setClickEvent(OnClickEvent clickEvent) {
        this.clickEvent = clickEvent;
    }

    public GenreAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public GenreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapter.ViewHolder holder, int position) {
        Genre genre = genreList.get(position);
        holder.tv_genre_title.setText(genre.getGenreTitle());

        MovieAdapter movieAdapter = new MovieAdapter();
        movieAdapter.setData(genre.getMovieList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        holder.rcvMovie.setLayoutManager(linearLayoutManager);
        holder.rcvMovie.setAdapter(movieAdapter);
        movieAdapter.setmMovieClickEvent(clickEvent);

    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public void addData(List<Genre> x) {
        if (x==null)
            return;

        genreList.addAll(x);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_genre_title;
        RecyclerView rcvMovie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_genre_title = itemView.findViewById(R.id.tv_genre_title);
            rcvMovie = itemView.findViewById(R.id.movie_recycler);
        }
    }

    public void setData(List<Genre> listData) {
        this.genreList = listData;
        notifyDataSetChanged();
    }
}
