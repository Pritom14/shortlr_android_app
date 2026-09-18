package com.codingblocks.shortlr;

import android.app.Application;

import timber.log.Timber;

public class ShortlrApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
