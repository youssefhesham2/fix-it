package com.youssef.fixit.Auth.LoginFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceInt implements ISharedPreferenceInterface {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public PreferenceInt(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = preferences.edit();
    }

    @Override
    public void save(String key, Object value) {
        editor.putInt(key, Integer.parseInt(value.toString()));
        editor.commit();
    }

    @Override
    public Object get(String key) {
        return 0;
    }
}
