package com.example.movietrailer.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movietrailer.DetailsActivity;
import com.example.movietrailer.R;
import com.example.movietrailer.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    List<Movie> mMovieList = new ArrayList<>();

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        holder.tv_title.setText(movie.getTitle());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .into(holder.img_poster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (clickEvent!=null){
                 clickEvent.onClickItem(movie);
             }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        ImageView img_poster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title_favor);
            img_poster = itemView.findViewById(R.id.img_poster_favor);

        }
    }

    public void setData(List<Movie> listData){
        this.mMovieList.clear();
        this.mMovieList.addAll(listData);
        notifyDataSetChanged();
    }


    private OnClickEvent clickEvent;

    public void setClickEvent(OnClickEvent clickEvent) {
        this.clickEvent = clickEvent;
    }

    public interface OnClickEvent{
        void onClickItem(Movie movie);
    }
}
