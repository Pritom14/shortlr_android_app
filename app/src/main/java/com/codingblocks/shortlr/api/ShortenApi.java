package com.codingblocks.shortlr.api;

import android.util.Log;

import com.codingblocks.shortlr.models.PostBody;
import com.codingblocks.shortlr.models.Result;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by piyush0 on 11/04/17.
 */

public interface ShortenApi {
    @POST("shorten")
    Call<Result> getResult(@Body PostBody postBody);

    /**
     * Logging helpers for the key points of a shorten request's lifecycle.
     * Kept as a nested class (rather than interface default methods) so this
     * file stays compatible with the project's Java 7 source level.
     */
    class Logger {
        private static final String TAG = "ShortenApi";

        static ShortenApi init(Retrofit retrofit) {
            Log.d(TAG, "Initializing ShortenApi service via Retrofit");
            return retrofit.create(ShortenApi.class);
        }

        static void requestStart(PostBody postBody) {
            Log.d(TAG, "Starting shorten request for URL: " + postBody);
        }

        static void response(Response<Result> response) {
            if (response.isSuccessful()) {
                Log.i(TAG, "Shorten request succeeded: " + response.body());
            } else {
                Log.e(TAG, "Shorten request returned error code: " + response.code());
            }
        }

        static void failure(Throwable t) {
            Log.e(TAG, "Shorten request failed: " + t.getMessage(), t);
        }
    }
}
