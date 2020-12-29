package com.example.movietrailer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.movietrailer.DetailsActivity;
import com.example.movietrailer.OnClickEvent;
import com.example.movietrailer.R;
import com.example.movietrailer.adapter.GenreAdapter;
import com.example.movietrailer.model.Genre;
import com.example.movietrailer.model.Movie;
import com.example.movietrailer.model.MovieResponse;
import com.example.movietrailer.remote.APIService;
import com.example.movietrailer.remote.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private final APIService apiService = RetrofitClient
            .getClient()
            .create(APIService.class);
    private GenreAdapter adapter;
    private ImageSlider imageSlider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMovie();
    }

    private void loadMovie() {
        apiService.getMovieResponse().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                String genreTitle = "Trending " + getEmojiByUnicode(0x1F525);
                addDataToAdapter(movies, genreTitle);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        });

        apiService.getMovieDiscover().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                imageSlider(response);
                List<Movie> movies = response.body().getResults();
                String genreTitle = "Discover " + getEmojiByUnicode(0x1F31F);
                addDataToAdapter(movies, genreTitle);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        apiService.getMovieTopRated().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                String genreTitle = "Top Rated " + getEmojiByUnicode(0x1F4AF);
                addDataToAdapter(movies, genreTitle);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    private void imageSlider(Response<MovieResponse> response) {
        if (response != null) {
            assert response.body() != null;
            List<Movie> mMovieList = response.body().getResults();
            List<SlideModel> slideModels = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                slideModels.add(new SlideModel("https://image.tmdb.org/t/p/w500/" + mMovieList.get(i).getBackdropPath()
                        , String.format("%s (%s)", mMovieList.get(i).getTitle(), mMovieList.get(i).getReleaseDate()), ScaleTypes.FIT));
                imageSlider.setImageList(slideModels);
            }

        }
    }

    private void addDataToAdapter(List<Movie> movies, String genreTitle) {
        if (movies != null && !movies.isEmpty()) {
            List<Genre> genreList = new ArrayList<>();
            genreList.add(new Genre(genreTitle, movies));
            adapter.addData(genreList);
        }
    }

    public String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = view.findViewById(R.id.imageslider);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.category_recycler);
        adapter = new GenreAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setClickEvent(new OnClickEvent() {
            @Override
            public void onClickMovie(Movie movie) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("movie_id", String.valueOf(movie.getId()));
                getActivity().startActivity(intent);
            }
        });
    }
}