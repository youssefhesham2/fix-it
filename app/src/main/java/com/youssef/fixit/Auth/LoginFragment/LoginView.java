package com.youssef.fixit.Auth.LoginFragment;

interface LoginView {
    void onMailIsError(String message);

    void onPasswordIsError(String message);

    void onLoginSuccessful();

    void onFailure(String error);

    void onForgotPassword();

    void showLoading();

    void hideLoading();
}
