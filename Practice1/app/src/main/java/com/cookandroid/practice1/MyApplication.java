package com.cookandroid.practice1;

import android.app.Application;

import com.cookandroid.practice1.api.ApiClient;

public class MyApplication extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();
        ApiClient.getInstance(this);
    }
}
