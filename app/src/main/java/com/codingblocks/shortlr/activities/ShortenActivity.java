package com.codingblocks.shortlr.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codingblocks.shortlr.models.PostBody;
import com.codingblocks.shortlr.R;
import com.codingblocks.shortlr.models.Result;
import com.codingblocks.shortlr.api.ShortenApi;
import com.codingblocks.shortlr.Utils;
import com.codingblocks.shortlr.services.CBWatcherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShortenActivity extends Activity {
    public static final String TAG = "ShortenAct";
    public static final String ACTION_MAIN = "android.intent.action.MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ShortenActivity created");
        setContentView(R.layout.activity_shorten);
        startWatcherService();

        if (getIntent().getAction().equals(ACTION_MAIN)) {
            // This is the for the first time app is launched.
            finish();
        } else {
            String url = getIntent()
                    .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString();
            boolean readOnly = getIntent()
                    .getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false);
            if (Utils.isUrl(url)) {
                Log.d(TAG, "onCreate: shorten action triggered for url=" + url);
                shorten(readOnly, url);
            } else {
                Toast.makeText(this, getString(R.string.not_a_url), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ShortenActivity resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ShortenActivity paused");
    }

    private void shorten(final Boolean readOnly, String urlToShort) {
        PostBody postBody = new PostBody(urlToShort, "", "");
        String urlToPost = getString(R.string.api_endpoint);

        Retrofit retrofit = new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(urlToPost)
                .build();

        ShortenApi shortenApi = retrofit.create(ShortenApi.class);
        Log.i(TAG, "shorten: calling API at " + urlToPost);
        shortenApi.getResult(postBody).enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.i(TAG, "shorten: API call succeeded with code " + response.code());
                String replacementText = getString(R.string.short_code_prepend) + response.body().getShortcode();
                if (readOnly) {
                    Utils.saveToClipboard(replacementText, ShortenActivity.this);
                }
                Intent intent = new Intent();
                intent.putExtra(Intent.EXTRA_PROCESS_TEXT, replacementText);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e(TAG, "shorten: API call failed", t);
                t.printStackTrace();
            }
        });
    }

    private void startWatcherService() {
        Intent i = new Intent(this, CBWatcherService.class);
        startService(i);
    }
}
