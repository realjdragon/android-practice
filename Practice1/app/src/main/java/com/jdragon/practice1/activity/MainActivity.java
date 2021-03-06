package com.jdragon.practice1.activity;

import android.os.Bundle;

import com.jdragon.practice1.R;
import com.jdragon.practice1.adapter.viewpager.MainViewPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_title);

        ViewPager viewPager = findViewById(R.id.main_view_pager);
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // 페이지 넘겨도 살아있도록
        viewPager.setOffscreenPageLimit(adapter.getCount());
    }
}
