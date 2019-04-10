package com.example.allenthomasjoy.fivehundredpx.dto;

import android.content.Context;

import com.example.allenthomasjoy.fivehundredpx.R;
import com.google.gson.JsonObject;

import java.io.Serializable;

public class DataImage implements Serializable {

    private String mFormat;
    private String mURL;
    private String mName;

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

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
