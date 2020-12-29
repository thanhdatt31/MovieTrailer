package com.example.movietrailer.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movietrailer.DetailsActivity;
import com.example.movietrailer.R;
import com.example.movietrailer.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.ViewHolder> {
    private List<Movie> mMovieList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.similar_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .centerCrop()
                .into(holder.img_movie_thumbnail);
        holder.img_movie_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),DetailsActivity.class);
                intent.putExtra("movie_id",String.valueOf(movie.getId()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_movie_thumbnail;

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
