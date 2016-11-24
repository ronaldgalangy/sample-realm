package com.dynobjx.realm.helpers;

import android.util.Log;

import com.dynobjx.realm.BuildConfig;

public class LogHelper {

    public static void log(final String key, final String message) {
        if (BuildConfig.DEBUG) {
            Log.d(key, message);
        }
    }
}
