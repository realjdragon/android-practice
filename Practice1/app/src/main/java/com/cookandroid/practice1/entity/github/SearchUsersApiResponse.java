package com.cookandroid.practice1.entity.github;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchUsersApiResponse {
    @SerializedName("total_count")
    private long totalCount;
    @SerializedName("incomplete_results")
    private boolean incompleteResults;
    @SerializedName("items")
    private ArrayList<User> users;

    public long getTotalCount() {
        return totalCount;
    }

    public boolean getIncompleteResults() {
        return incompleteResults;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public class User {
        @SerializedName("avatar_url")
        private String avatarUrl;
        @SerializedName("url")
        private String url;

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public String getUrl() {
            return url;
        }
    }
}
