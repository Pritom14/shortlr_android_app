package com.codingblocks.shortlr.models;

import android.util.Log;

/**
 * Created by piyush0 on 11/04/17.
 */

public class PostBody {

    private static final String TAG = "PostBody";

    String url;
    String secret;
    String code;

    public PostBody(String url, String secret, String code) {
        this.url = url;
        this.secret = secret;
        this.code = code;
        Log.d(TAG, "PostBody: " + url);
    }
}

