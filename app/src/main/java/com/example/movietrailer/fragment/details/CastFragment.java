package com.example.movietrailer.fragment.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietrailer.R;
import com.example.movietrailer.adapter.CastAdapter;
import com.example.movietrailer.adapter.SimilarAdapter;
import com.example.movietrailer.model.Cast;
import com.example.movietrailer.model.CastResponse;
import com.example.movietrailer.remote.APIService;
import com.example.movietrailer.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CastFragment extends Fragment {
    private final APIService apiService = RetrofitClient
            .getClient()
            .create(APIService.class);
    private RecyclerView recyclerView;
    private CastAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cast, container, false);
        String movie_id = getArguments().getString("movie_id");
        recyclerView = view.findViewById(R.id.recyclerview);
        apiService.getCast(movie_id).enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                List<Cast> listCast = response.body().getCast();
                initView(listCast);
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {

            }
        });
        return view;
    }

    private void initView(List<Cast> listCast) {
        adapter = new CastAdapter();
        adapter.setData(listCast);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
    }
}