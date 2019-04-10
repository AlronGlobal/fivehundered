package com.example.allenthomasjoy.fivehundredpx.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.allenthomasjoy.fivehundredpx.R;

import java.util.HashMap;
import java.util.Map;

public class RequestPhotos extends StringRequest {

    private Map<String, String> mHeaders;
    private Map<String, String> mParams;

    public RequestPhotos(Context context, int currentPage, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(ServerEndPoints.PHOTOS_POPULAR.getRequestMethod(), ServerEndPoints.PHOTOS_POPULAR.getUrl()+"?"+context.getString(R.string.network_consumer_key) + "=" + context.getString(R.string.network_api_key) + "&" +context.getString(R.string.data_page) + "=" + currentPage, listener, errorListener);
        mHeaders = new HashMap<>();
        mHeaders.put("Content-Type","application/x-www-form-urlencoded");
        mHeaders.put("Authorization", "Basic cGdsOmJldGEyMDE2");
        mParams = new HashMap<>();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    @Override
    public Map<String, String> getParams() {
        return mParams;
    }
}
