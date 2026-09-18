package com.codingblocks.shortlr.models;

import android.util.Log;

/**
 * Created by piyush0 on 11/04/17.
 */

public class Result {

    private static final String TAG = "Result";

    private String shortcode;
    private Boolean existed;
    private String longURL;

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        Log.d(TAG, "setShortcode: " + shortcode);
        this.shortcode = shortcode;
    }

    public Boolean getExisted() {
        return existed;
    }

    public void setExisted(Boolean existed) {
        Log.d(TAG, "setExisted: " + existed);
        this.existed = existed;
    }

    public String getLongURL() {
        return longURL;
    }

    public void setLongURL(String longURL) {
        Log.d(TAG, "setLongURL: " + longURL);
        this.longURL = longURL;
    }
}

