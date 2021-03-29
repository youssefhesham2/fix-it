package com.youssef.fixit.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.youssef.fixit.Auth.AuthActivity;
import com.youssef.fixit.Home.HomeActivity;
import com.youssef.fixit.R;

public class SplashScreen extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static String MyToken;
    public static String MyRole;
    public static int My_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        InitSharedPreferences();
        InitSplashScreen();
    }

    private void InitSplashScreen() {
        ImageView logo = findViewById(R.id.img_logo);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        logo.startAnimation(animation);
        Thread t = new Thread() {

            public void run() {

                try {
                    sleep(3000);
                    CheckLogin();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    private void CheckLogin() {
        SplashScreen.MyToken = preferences.getString("token", "");
        SplashScreen.MyRole = preferences.getString("role", "");
        SplashScreen.My_ID = preferences.getInt("my_id", 0);
        if (SplashScreen.MyToken.isEmpty()) {
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            Log.e("hesham", "home" + SplashScreen.MyToken);
        }
    }

    private void InitSharedPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
    }
}