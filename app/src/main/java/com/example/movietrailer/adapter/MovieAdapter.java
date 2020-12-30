package com.example.movietrailer.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.example.movietrailer.DetailsActivity;
import com.example.movietrailer.OnClickEvent;
import com.example.movietrailer.R;
import com.example.movietrailer.model.Movie;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {


    private List<Movie> mMovieList = new ArrayList<>();
    private OnClickEvent mMovieClickEvent;

    public void setmMovieClickEvent(OnClickEvent mMovieClickEvent) {
        this.mMovieClickEvent = mMovieClickEvent;
    }

    public void setmMovieList(List<Movie> mMovieList) {
        this.mMovieList = mMovieList;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .into(holder.img_movie_thumbnail);
        holder.img_movie_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                DetailFragment detailFragment = new DetailFragment();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, detailFragment)
//                        .addToBackStack(null).commit();
//                detailFragment.setArguments(sendDataToDetails(movie));

                if (mMovieClickEvent!=null){
                    mMovieClickEvent.onClickMovie(movie);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView img_movie_thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_movie_thumbnail = itemView.findViewById(R.id.img_movie_thumbnail);

        }
    }

    public void setData(List<Movie> listData) {
        this.mMovieList = listData;
        notifyDataSetChanged();
    }



}

