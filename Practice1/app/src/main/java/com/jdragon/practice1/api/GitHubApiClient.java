package com.jdragon.practice1.api;

import com.android.volley.Response;
import com.jdragon.practice1.MyApplication;
import com.jdragon.practice1.entity.api.github.SearchUsersApiResponse;

public class GitHubApiClient {
    private String urlPrefix = "https://api.github.com";

    public void searchUsers(String q, int page, Response.Listener<SearchUsersApiResponse> responseListener, Response.ErrorListener errorListener)
    {
        String url = urlPrefix
                + "/search/users";

        url += "?q=" + q;
        url += "&page=" + String.valueOf(page);

        GsonRequest<SearchUsersApiResponse> request = new GsonRequest<>(
                url,
                SearchUsersApiResponse.class,
                null,
                responseListener,
                errorListener
        );

        MyApplication.getRequestQueue().add(request);
    }
}
