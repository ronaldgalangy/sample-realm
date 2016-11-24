package com.dynobjx.realm.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/** Initialize on App level, by calling PrefsHelper.init(prefsName, context); */

public class PrefsHelper {

    private static SharedPreferences prefs;
    private static String prefsName;
    private static Context context;

    public static void init(String prefsName, Context context) {
        prefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        PrefsHelper.prefsName = prefsName;
        PrefsHelper.context = context;
    }

    private static SharedPreferences getPrefs() {
        return context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
    }

    public static String getString(String key) {
        return getPrefs().getString(key, "");
    }

    public static int getInt(String key) {
        return getPrefs().getInt(key, 0);
    }

    public static float getFloat(String key) {
        return getPrefs().getFloat(key, 0f);
    }

    public static long getLong(String key) {
        return getPrefs().getLong(key, 0L);
    }

    public static boolean getBoolean(String key) {
        return getPrefs().getBoolean(key, false);
    }

    public static void setBoolean(String key, boolean value) {
        getPrefs().edit().putBoolean(key, value).commit();
    }

    public static void setString(String key, String value) {
            getPrefs().edit().putString(key, value).commit();
    }

    public static void setInt(String key, int value) {
        getPrefs().edit().putInt(key, value).commit();
    }

    public static void setFloat(String key, Float value) {
        getPrefs().edit().putFloat(key, value).commit();
    }

    public static void setLong(String key, Long value) {
        getPrefs().edit().putLong(key, value).commit();
    }

    public static void removePref(String key){
        getPrefs().edit().remove(key);
    }
}
