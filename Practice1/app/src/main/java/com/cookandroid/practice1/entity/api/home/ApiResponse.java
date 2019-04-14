package com.cookandroid.practice1.entity.api.home;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("ServerTime")
    private String serverTime;
    @SerializedName("ResultCode")
    private int resultCode;
    @SerializedName("Message")
    private String message;

    public String getServerTime() {
        return serverTime;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getMessage() {
        return message;
    }
}


