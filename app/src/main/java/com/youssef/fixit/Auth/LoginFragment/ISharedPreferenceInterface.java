package com.youssef.fixit.Auth.LoginFragment;

import android.content.SharedPreferences;

import com.google.gson.annotations.SerializedName;

interface ISharedPreferenceInterface {

    void save(String key, Object value);

    Object get(String key);

}
