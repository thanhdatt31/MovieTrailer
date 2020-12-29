package com.example.movietrailer.fragment.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.movietrailer.R;
import com.example.movietrailer.database.MovieDatabase;
import com.example.movietrailer.model.Movie;
import com.example.movietrailer.remote.APIService;
import com.example.movietrailer.remote.RetrofitClient;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InfoFragment extends Fragment {
    private final APIService apiService = RetrofitClient
            .getClient()
            .create(APIService.class);
    private CircleImageView mCircleImageView;
    private TextView tv_title, tv_date, tv_rating, tv_runtime, tv_overview;
    private RatingBar ratingBar;
    private MaterialFavoriteButton btn_favorite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        mCircleImageView = view.findViewById(R.id.img_poster);
        tv_title = view.findViewById(R.id.tv_title);
        tv_date = view.findViewById(R.id.tv_date);
        ratingBar = view.findViewById(R.id.rating_bar);
        tv_rating = view.findViewById(R.id.tv_rating);
        tv_runtime = view.findViewById(R.id.tv_runtime);
        tv_overview = view.findViewById(R.id.tv_desc);
        btn_favorite = view.findViewById(R.id.btn_favorite);
        String movie_id = getArguments().getString("movie_id");
        loadDetails(movie_id);

        return view;
    }


    private void loadDetails(String movie_id) {
        apiService.getDetailMovie(movie_id).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                Glide.with(getContext()).load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                        .dontAnimate()
                        .into(mCircleImageView);
                tv_title.setText(movie.getTitle());
                Double ratingMovie = movie.getVoteAverage();
                String runTime = movie.getRuntime() + " mins";
                ratingBar.setRating((float) (ratingMovie / 2));
                tv_rating.setText(String.valueOf(ratingMovie / 2));
                tv_date.setText(movie.getReleaseDate());
                tv_runtime.setText(runTime);
                tv_overview.setText(movie.getOverview());
                if (checkMovieExists(movieToAdd(movie))) {
                    btn_favorite.setEnabled(false);
                    btn_favorite.setFavorite(true);
                } else {
                    btn_favorite.setOnClickListener(v -> addMovieToDB(movieToAdd(movie)));
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }

    private Movie movieToAdd(Movie movie) {
        String id = String.valueOf(movie.getId());
        String title = movie.getTitle();
        String posterPath = movie.getPosterPath();
        return new Movie(id, posterPath, title);
    }

    private void addMovieToDB(Movie movieToAdd) {
        MovieDatabase.getInstance(getContext()).movieDAO().insertMovie(movieToAdd);
        btn_favorite.setEnabled(false);
        btn_favorite.setFavorite(true);
        Toast.makeText(getContext(), "Added To Favorite", Toast.LENGTH_SHORT).show();
    }

    private boolean checkMovieExists(Movie movie) {
        List<Movie> list = MovieDatabase.getInstance(getContext()).movieDAO().checkMovieById(movie.getId());
        return list != null && !list.isEmpty();
    }
}