package com.example.movietrailer.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietrailer.R;
import com.example.movietrailer.adapter.SearchAdapter;
import com.example.movietrailer.model.Movie;
import com.example.movietrailer.model.MovieResponse;
import com.example.movietrailer.remote.APIService;
import com.example.movietrailer.remote.RetrofitClient;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private final APIService apiService = RetrofitClient
            .getClient()
            .create(APIService.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        EditText edt_search = view.findViewById(R.id.edt_search);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchAdapter();
        edt_search.setOnEditorActionListener((TextView.OnEditorActionListener) (v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchMovieByKeyword(edt_search.getText().toString().trim());
                hideKeyboard(((Activity) Objects.requireNonNull(getActivity())));
                return true;
            }
            return false;
        });
        return view;
    }

    private void searchMovieByKeyword(String keyword) {
        apiService.getResultMovie(keyword).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.body() != null) {
                    List<Movie> movieList = response.body().getResults();
                    adapter.setData(movieList);
                    recyclerView.setAdapter(adapter);
                }


            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}