package com.jdragon.practice1.adapter.viewpager;

import com.jdragon.practice1.fragment.AsymmetricFragment;
import com.jdragon.practice1.fragment.GitHubUsersFragment;
import com.jdragon.practice1.fragment.HomeMainFragment;

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
                return new HomeMainFragment();
            case 1:
                return new GitHubUsersFragment();
            case 2:
                return new AsymmetricFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}