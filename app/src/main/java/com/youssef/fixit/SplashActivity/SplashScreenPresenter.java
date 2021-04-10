package com.youssef.fixit.SplashActivity;

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
