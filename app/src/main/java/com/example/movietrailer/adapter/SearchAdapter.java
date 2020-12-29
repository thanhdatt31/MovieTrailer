package com.example.movietrailer.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movietrailer.DetailsActivity;
import com.example.movietrailer.R;
import com.example.movietrailer.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Movie> mMovieList = new ArrayList<>();

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        holder.tv_title.setText(movie.getTitle());
        String date;
        if (!movie.getReleaseDate().isEmpty()) {
            date = " (" + movie.getReleaseDate() + ")";
        } else {
            date = " ( No information ) ";
        }
        holder.tv_date.setText(date);
        Double ratingMovie = movie.getVoteAverage();
        holder.ratingBar.setRating((float) (ratingMovie / 2));
        holder.tv_rating.setText(String.valueOf(ratingMovie / 2));
       if (movie.getPosterPath() != null){
           Glide.with(holder.itemView.getContext())
                   .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                   .centerCrop()
                   .into(holder.img_posters);
       } else{
           Glide.with(holder.itemView.getContext())
                   .load("https://www.jakartaplayers.org/uploads/1/2/5/5/12551960/2297419_orig.jpg")
                   .centerCrop()
                   .into(holder.img_posters);
       }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailsActivity.class);
            intent.putExtra("movie_id", String.valueOf(movie.getId()));
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_posters;
        TextView tv_title, tv_rating, tv_date;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_posters = itemView.findViewById(R.id.img_poster);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_rating = itemView.findViewById(R.id.tv_rating);
            ratingBar = itemView.findViewById(R.id.ratingbar);
        }
    }

    public void setData(List<Movie> listData) {
        this.mMovieList = listData;
        notifyDataSetChanged();
    }
}
