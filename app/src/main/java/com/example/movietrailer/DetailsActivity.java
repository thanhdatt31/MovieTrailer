package com.example.movietrailer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.movietrailer.adapter.ViewPagerAdapterTablayout;
import com.example.movietrailer.fragment.YoutubeFragment;
import com.example.movietrailer.fragment.details.InfoFragment;
import com.example.movietrailer.model.VideoTrailer;
import com.example.movietrailer.model.VideoTrailerResponse;
import com.example.movietrailer.remote.APIService;
import com.example.movietrailer.remote.RetrofitClient;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    private final APIService apiService = RetrofitClient
            .getClient()
            .create(APIService.class);
    private List<VideoTrailer> trailerList;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.viewpager);
        Intent intent = getIntent();
        String movie_id = intent.getStringExtra("movie_id");
        initTabLayout(movie_id);
        if (movie_id != null || !TextUtils.isEmpty(movie_id)) {
            loadTrailer(movie_id);// dat ten bien kieu gi day ?
        }

    }

    private void initTabLayout(String movie_id) {
        Bundle bundle = new Bundle();
        bundle.putString("movie_id", movie_id);
        ViewPagerAdapterTablayout viewpagerAdapter = new ViewPagerAdapterTablayout(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, bundle);
        mViewPager.setAdapter(viewpagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        viewpagerAdapter.getInfoFragment().setClickEvent(new InfoFragment.OnClickEvent() {
            @Override
            public void onDeleteMovie() {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
            }
        });

    }

    private void loadTrailer(String movie_id) {
        apiService.getVideoTrailer(movie_id).enqueue(new Callback<VideoTrailerResponse>() {
            @Override
            public void onResponse(Call<VideoTrailerResponse> call, Response<VideoTrailerResponse> response) {
                trailerList = response.body().getResults();
                    sendDataToYoutube(trailerList);
            }

            @Override
            public void onFailure(Call<VideoTrailerResponse> call, Throwable t) {

            }
        });
    }

    private void sendDataToYoutube(List<VideoTrailer> trailerList) {
        Bundle bundle = new Bundle();
        bundle.putString("movie_key", trailerList.get(0).getKey());
        YoutubeFragment youtubeFragment = new YoutubeFragment();
        youtubeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.layout_fragment, youtubeFragment).commit();

    }
}