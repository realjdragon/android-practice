package com.cookandroid.practice1.api;

import com.android.volley.Response;
import com.cookandroid.practice1.MyApplication;
import com.cookandroid.practice1.entity.home.HomeMainApiResponse;

import java.util.HashMap;
import java.util.Map;

public class HomeMainApiClient {
    private String urlPrefix = "http://gmapi.gmarket.co.kr";

    public void getMobileHome(Response.Listener<HomeMainApiResponse> responseListener, Response.ErrorListener errorListener)
    {
        String url = urlPrefix
                + "/api/Home/GetMobileHomeMainV2";

        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");
        params.put("Authorization", "Basic IQB0AE4AdQArAHEAcwBWAHkAWgBXAFoAbABQAHQAKwBXAGUANwBaAHYAawBjAE4AeAArAG8AUAA2AHUAYwByACsAQwBrAEsANQBFADQAOABxAFEANgBlAGcASQB2ACsAagBRAGIATgBKAGEAMwAyAE8AUwBqAFcANQBJADYAawB2AG4ATAA4ACsASABaAGoAYQBkAE4AZAArAFUAVQBpAFYATABzAEkAMwBtAFgAbwBuAFQATwBRAGYAMwBjAHkAWABuAEkAMgBWAGUARABaAGIAWABHAE4AVgBTAGwAMABxAEcALwBoAGgAUgBGAFgAWABjAHIARgBaAHkANwBiADUAWABpAEgASgBMACsAVABNADcATgBXAGQAdgAvAG4AcgBCADIAMwBDAFYAZwA9AD0A");

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
