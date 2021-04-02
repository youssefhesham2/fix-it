package com.youssef.fixit.Auth.LoginFragment;

interface LoginView {
    void onMailIsError(String message);

    void onPasswordIsError(String message);

    void onLoginSuccessful(String token, String role, int myId);

    void onFailure(String error);

    void onForgotPassword();

    void showLoading();

    void hideLoading();
}
