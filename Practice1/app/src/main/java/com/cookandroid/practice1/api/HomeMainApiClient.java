package com.cookandroid.practice1.api;

import com.android.volley.Response;
import com.cookandroid.practice1.MyApplication;
import com.cookandroid.practice1.entity.HomeMainApiResponse;

import java.util.HashMap;
import java.util.Map;

public class HomeMainApiClient {
    private String urlPrefix = "";

    public void getMobileHome(Response.Listener<HomeMainApiResponse> responseListener, Response.ErrorListener errorListener)
    {
        String url = urlPrefix
                + "";

        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");
        params.put("Authorization", "");

        // Volley는 onResponse, onErrorResponse 리스너를 정의하는걸 강제하고 있음.
        GsonRequest<HomeMainApiResponse> request = new GsonRequest<HomeMainApiResponse>(
                url,
                HomeMainApiResponse.class,
                params,
                responseListener,
                errorListener
        );

        MyApplication.getRequestQueue().add(request);
    }
}
