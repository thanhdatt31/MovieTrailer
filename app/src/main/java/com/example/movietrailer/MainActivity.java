package com.example.movietrailer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.movietrailer.adapter.GenreAdapter;
import com.example.movietrailer.model.Genre;
import com.example.movietrailer.model.Movie;
import com.example.movietrailer.model.MovieResponse;
import com.example.movietrailer.remote.APIService;
import com.example.movietrailer.remote.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private GenreAdapter adapter;
    APIService apiService = RetrofitClient
            .getClient()
            .create(APIService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMovie();
        initView();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_search:
                        Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_favorite:
                        Toast.makeText(MainActivity.this, "favor", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_user:
                        Toast.makeText(MainActivity.this, "user", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });
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

    private void addDataToAdapter(List<Movie> movies, String genreTitle) {
        if (movies != null && !movies.isEmpty()) {
            List<Genre> genreList = new ArrayList<>();
            genreList.add(new Genre(genreTitle, movies));
            adapter.addData(genreList);
        }
    }


    private void imageSlider(Response<MovieResponse> response) {
        if (response != null) {
            assert response.body() != null;
            List<Movie> mMovieList = response.body().getResults();
            ImageSlider imageSlider = findViewById(R.id.imageslider);
            List<SlideModel> slideModels = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                slideModels.add(new SlideModel("https://image.tmdb.org/t/p/w500/" + mMovieList.get(i).getBackdropPath()
                        , mMovieList.get(i).getTitle(), ScaleTypes.FIT));
                imageSlider.setImageList(slideModels);
            }

        }
    }


    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.category_recycler);
        adapter = new GenreAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

}