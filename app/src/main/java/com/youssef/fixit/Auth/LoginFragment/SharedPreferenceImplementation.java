package com.youssef.fixit.Auth.LoginFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceImplementation implements ISharedPreference {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static SharedPreferenceImplementation sharedPreferenceImplementation;

    private SharedPreferenceImplementation(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public static SharedPreferenceImplementation getInstance(Context context) {
        if (sharedPreferenceImplementation == null) {
            sharedPreferenceImplementation = new SharedPreferenceImplementation(context);
        }
        return sharedPreferenceImplementation;
    }

    @Override
    public void save(String key, String value) {
        editor.putString(key,value);
        editor.commit();
    }

    @Override
    public void save(String key, int value) {
        editor.putInt(key,value);
        editor.commit();
    }

    @Override
    public void get(String key, String _default) {
        preferences.getString(key,_default);
    }

    @Override
    public void get(String key, int _default) {
        preferences.getInt(key,_default);
    }

}


