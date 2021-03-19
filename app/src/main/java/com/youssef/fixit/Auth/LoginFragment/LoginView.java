package com.youssef.fixit.Auth.LoginFragment;

interface LoginView {
    void OnMailIsError(String Message);

    void OnPasswordIsError(String Message);

    void OnLoginSuccessful(String token, String role, int my_id);

    void OnFailure(String error);

    void OnForgotPassword();
}
