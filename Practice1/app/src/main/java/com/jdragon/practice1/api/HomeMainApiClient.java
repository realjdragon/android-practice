package com.jdragon.practice1.api;

import com.android.volley.Response;
import com.jdragon.practice1.MyApplication;
import com.jdragon.practice1.entity.api.home.HomeMainApiResponse;

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
        GsonRequest<HomeMainApiResponse> request = new GsonRequest<>(
                url,
                HomeMainApiResponse.class,
                params,
                responseListener,
                errorListener
        );

        MyApplication.getRequestQueue().add(request);
    }
}
