package com.cookandroid.practice1.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.cookandroid.practice1.MyApplication;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeMainApiClient {
    private String urlPrefix = "";

    public void getMobileHome(Response.Listener<String> responseListener, Response.ErrorListener errorListener)
    {
        String url = urlPrefix
            + "";

        // Volley는 onResponse, onErrorResponse 리스너를 정의하는걸 강제하고 있음.
        StringRequest request = new StringRequest(
                url,
                responseListener,
                errorListener
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        MyApplication.getRequestQueue().add(request);
    }
}
