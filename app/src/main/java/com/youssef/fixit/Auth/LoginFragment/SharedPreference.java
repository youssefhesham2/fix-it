package com.youssef.fixit.Auth.LoginFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

public class SharedPreference {
    private ISharedPreferenceInterface iSharedPreferenceInterface;

    public SharedPreference(ISharedPreferenceInterface iSharedPreferenceInterface) {
        this.iSharedPreferenceInterface = iSharedPreferenceInterface;
    }

    public void save(String key, Object value) {
        iSharedPreferenceInterface.save(key, value);
    }

    public Object get(String key) {
        return iSharedPreferenceInterface.get(key);
    }
}


