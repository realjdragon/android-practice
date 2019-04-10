package com.cookandroid.practice1.activity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import com.cookandroid.practice1.R;
import com.cookandroid.practice1.fragment.HomeMainFragment;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_title);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        HomeMainFragment homeMainFragment = new HomeMainFragment();
        fragmentTransaction.add(R.id.fragment_holder, homeMainFragment);
        fragmentTransaction.commit();

//        ViewPager viewPager = findViewById(R.id.view_pager);
//        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

//        TabLayout tabLayout = findViewById(R.id.tab_layout);
//        tabLayout.setUpWithViewPager(viewPager);
    }
}
