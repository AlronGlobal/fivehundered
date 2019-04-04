package com.example.allenthomasjoy.fivehundredpx.network;

import com.android.volley.Request;

public enum ServerEndPoints {


    PHOTOS_POPULAR(Request.Method.GET, "/v1/photos");


    private final String SERVER_URL = "https://api.500px.com";
    private final int mRequestMethod;
    private final String mUrl;

    ServerEndPoints(int requestMethod, String url) {
        mRequestMethod = requestMethod;
        mUrl = url;
    }

    public int getRequestMethod(){
        return mRequestMethod;
    }

    public String getUrl(){
        return SERVER_URL + mUrl;
    }

    @Override
    public String toString() {
        return SERVER_URL + mUrl;
    }
}
