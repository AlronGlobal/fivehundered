package com.example.allenthomasjoy.fivehundredpx.dto;

import android.content.Context;

import com.example.allenthomasjoy.fivehundredpx.R;
import com.google.gson.JsonObject;

public class DataImage {

    private String mFormat;
    private String mURL;

    public DataImage (Context context, JsonObject imageJson) {
        DataJsonObject image = new DataJsonObject(imageJson);
        mURL = image.getString(context.getString(R.string.data_url));
        mFormat = image.getString(context.getString(R.string.data_format));
    }

    public String getFormat() {
        return mFormat;
    }

    public void setFormat(String format) {
        this.mFormat = format;
    }

    public String getUrl() {
        return mURL;
    }

    public void setUrl(String url) {
        this.mURL = url;
    }
}
