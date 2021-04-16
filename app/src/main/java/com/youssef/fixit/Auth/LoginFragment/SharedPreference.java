package com.youssef.fixit.Auth.LoginFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SharedPreference implements SharedPreferenceInterface {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedPreference(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    @Override
    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public void saveInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

}
