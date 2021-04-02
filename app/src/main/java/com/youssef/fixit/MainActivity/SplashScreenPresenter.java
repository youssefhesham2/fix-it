package com.youssef.fixit.MainActivity;

import android.content.Intent;
import android.util.Log;

import com.youssef.fixit.Auth.AuthActivity;
import com.youssef.fixit.Home.HomeActivity;

class SplashScreenPresenter {
    private SplashView view;

    SplashScreenPresenter(SplashView view) {
        this.view = view;
    }

    void checkUserIsLoggedInOrNot(String token) {
        if (token.isEmpty()) {
            view.isLoggedIn();
        } else {
            view.notLoggedIn();
        }
    }
}
