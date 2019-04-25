package com.cookandroid.practice1.adapter.viewpager;

import com.cookandroid.practice1.fragment.AsymmetricFragment;
import com.cookandroid.practice1.fragment.GitHubUsersFragment;
import com.cookandroid.practice1.fragment.HomeMainFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new AsymmetricFragment();
            case 1:
                return new HomeMainFragment();
            case 2:
                return new GitHubUsersFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}