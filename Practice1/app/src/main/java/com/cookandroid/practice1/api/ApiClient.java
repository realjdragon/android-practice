package com.cookandroid.practice1.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ApiClient
{
    private static final String TAG = "ApiClient";
    private static ApiClient instance = null;

    private RequestQueue requestQueue;

    private ApiClient(Context context)
    {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized ApiClient getInstance(Context context)
    {
        if (null == instance)
            instance = new ApiClient(context);
        return instance;
    }

    public static synchronized ApiClient getInstance()
    {
        if (null == instance)
        {
            throw new IllegalStateException(ApiClient.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    public void addRequestQueue(Request request)
    {
        requestQueue.add(request);
    }
}