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
    // code nhu nay la hong roi
    private List<Fragment> mFragmentList = new ArrayList<>();
    public ViewPagerAdapterTablayout(@NonNull FragmentManager fm, int behavior, Bundle bundle) {
        super(fm, behavior);
        this.bundle = bundle;

        InfoFragment  fragment = new InfoFragment();
        fragment.setArguments(bundle);

        SimilarFragment  fragment2 = new SimilarFragment();
        fragment2.setArguments(bundle);

        CastFragment  fragment3 = new CastFragment();
        fragment3.setArguments(bundle);


        mFragmentList.add(fragment);
        mFragmentList.add(fragment2);
        mFragmentList.add(fragment3);


    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";
        switch (position) {
            case 0:
                title = "DETAIL";
                break;
            case 1:
                title = "SIMILAR";
                break;
            case 2:
                title = "CAST";
                break;
        }
        return title;
    }

    public InfoFragment getInfoFragment() {
        return (InfoFragment) mFragmentList.get(0);
    }
}
