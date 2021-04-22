package com.youssef.fixit.Auth.LoginFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceString implements ISharedPreferenceInterface {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public PreferenceString(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = preferences.edit();
    }

    @Override
    public void save(String key, Object value) {
        editor.putString(key, value.toString());
        editor.commit();
    }

    @Override
    public Object get(String key) {
        return 0;
    }
}
