package com.youssef.fixit.MainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.youssef.fixit.R;
import com.youssef.fixit.Auth.AuthActivity;
import com.youssef.fixit.Home.HomeActivity;

public class MainActivity extends AppCompatActivity {
    public static String MyToken;
    public static String MyRole;
    public static int My_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentToSplashScreen();
    }

    private void IntentToSplashScreen() {
        startActivity(new Intent(this, SplashScreen.class));
        finish();
    }
}