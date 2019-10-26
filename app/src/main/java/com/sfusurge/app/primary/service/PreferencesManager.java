package com.sfusurge.app.primary.service;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

@SuppressWarnings("unused")
public class PreferencesManager {

    public static void setDefaults(Activity context, int resourceId) {
        PreferenceManager.setDefaultValues(context, resourceId, false);
    }

    public static String getString(Activity context, String key) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");
    }

}