package com.sfusurge.app.primary.service;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sfusurge.app.primary.R;

public class Preferences {

    public static void setDefaults(Activity context) {
        PreferenceManager.setDefaultValues(context, R.xml.root_preferences, false);
    }

    public static String getString(Activity context, String key) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");
    }

}