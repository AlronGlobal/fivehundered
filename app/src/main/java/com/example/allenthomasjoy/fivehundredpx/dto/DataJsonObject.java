package com.example.allenthomasjoy.fivehundredpx.dto;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DataJsonObject {
    private JsonObject mJsonObject;

    public DataJsonObject(JsonObject jsonObject) {
        mJsonObject = jsonObject;
    }

    public String getString (String name) {
        if (!isEmpty(name) && mJsonObject.get(name).isJsonPrimitive() && mJsonObject.get(name).getAsJsonPrimitive().isString()) {
            return mJsonObject.get(name).getAsString();
        } else {
            return "";
        }
    }


    public String toString (){
        return mJsonObject.toString();
    }

    private boolean isEmpty(String name) {
        return mJsonObject == null || mJsonObject.isJsonNull() || mJsonObject.get(name) == null || mJsonObject.get(name).isJsonNull();
    }
}

