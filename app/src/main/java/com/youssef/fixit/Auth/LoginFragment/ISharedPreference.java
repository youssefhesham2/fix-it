package com.youssef.fixit.Auth.LoginFragment;

import android.content.SharedPreferences;

import com.google.gson.annotations.SerializedName;

interface ISharedPreference {

    void save(String key, String value);

    void save(String key, int value);

    void get(String key,String _default);

    void get(String key,int _default);
}
