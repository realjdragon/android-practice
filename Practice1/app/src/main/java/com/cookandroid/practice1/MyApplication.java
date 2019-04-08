package com.cookandroid.practice1;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyApplication extends Application {
    private static RequestQueue requestQueue;

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
    }
}
