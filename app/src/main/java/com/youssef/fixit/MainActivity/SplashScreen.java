package com.youssef.fixit.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.youssef.fixit.Auth.AuthActivity;
import com.youssef.fixit.Home.HomeActivity;
import com.youssef.fixit.R;

public class SplashScreen extends AppCompatActivity implements SplashView {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static String MyToken;
    public static String MyRole;
    public static int My_ID;
    private SplashScreenPresenter presenter;
    private ImageView logo;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initViews();
        initSharedPreferences();
        InitSplashScreen();
    }

    private void InitSplashScreen() {
        logo.startAnimation(animation);
        Thread t = new Thread() {

            public void run() {

                try {
                    sleep(3000);
                    checkLogin();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    private void initViews() {
        logo = findViewById(R.id.img_logo);
        animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        presenter = new SplashScreenPresenter(this);
    }

    private void checkLogin() {
        MyToken = preferences.getString("token", "");
        MyRole = preferences.getString("role", "");
        My_ID = preferences.getInt("my_id", 0);

        presenter.checkUserIsLoggedInOrNot(MyToken);
    }

    private void initSharedPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
    }

    @Override
    public void isLoggedIn() {
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

    @Override
    public void notLoggedIn() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}