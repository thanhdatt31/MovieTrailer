package com.example.movietrailer.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.movietrailer.fragment.details.CastFragment;
import com.example.movietrailer.fragment.details.InfoFragment;
import com.example.movietrailer.fragment.details.ProducerFragment;
import com.example.movietrailer.fragment.details.SimilarFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapterTablayout extends FragmentStatePagerAdapter {
//    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final Bundle bundle;
    public ViewPagerAdapterTablayout(@NonNull FragmentManager fm, int behavior, Bundle bundle) {
        super(fm, behavior);
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new InfoFragment();
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new SimilarFragment();
                fragment.setArguments(bundle);
                break;
            case 2:
                fragment =  new CastFragment();
                fragment.setArguments(bundle);
                break;
            case 3:
                fragment = new ProducerFragment();
                fragment.setArguments(bundle);
                break;
            default:
                fragment = new InfoFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "INFORMATION";
                break;
            case 1:
                title = "SIMILAR";
                break;
            case 2:
                title = "CAST";
                break;
            case 3:
                title = "PRODUCER";
                break;
        }
        return title;
    }
}
