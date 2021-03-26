package com.youssef.fixit.Auth.LoginFragment;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.youssef.fixit.MainActivity.MainActivity;
import com.youssef.fixit.Models.Bids.CreateBid;
import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Register.Register;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class LoginPresenter {
    private static final String TAG = "LoginPresenter";
    LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    void OnLogin(String Mail, String Password) {
        Validation(Mail, Password);
    }

    void Validation(String Mail, String Password) {
        if (Mail.isEmpty()) {
            view.OnMailIsError("Please enter your Mail");
            return;
        }
        if (Password.isEmpty()) {
            view.OnPasswordIsError("Please enter your Password");
            return;
        }

        LoginRequest(Mail, Password);
    }

    void LoginRequest(String Mail, String Password) {
        view.ShowLoading();
        RetrofitClient.getInstance().Login(Mail, Password).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    if (response.body().getData() != null) {
                        List<String> roles = response.body().getData().getRoles();
                        MainActivity.MyToken = response.body().getData().getToken();
                        MainActivity.My_ID = response.body().getData().getId();
                        MainActivity.MyRole = roles.get(0);
                        Log.d(TAG, response.body().getData().getId() + "");
                        Log.d(TAG, MainActivity.MyToken);
                        String token = response.body().getData().getToken();
                        String role = response.body().getData().getRoles().get(0);
                        int my_id = response.body().getData().getId();
                        view.OnLoginSuccessful(token, role, my_id);
                    } else {
                        view.OnFailure("incorrect email or password!!");
                    }
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                view.OnFailure(t.getMessage());
            }
        });
    }

    void OnForgotPassword(String Mail) {
        if (Mail.isEmpty()) {
            view.OnMailIsError("Please enter your Mail");
            return;
        }
        ForgotPasswordRequest(Mail);
    }

    void ForgotPasswordRequest(String Mail) {
        view.ShowLoading();
        RetrofitClient.getInstance().ForgetPassword(Mail).enqueue(new Callback<CreateBid>() {
            @Override
            public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            if (response.body().getData() != null) {
                                view.OnForgotPassword();
                            }
                        } else {
                            view.OnFailure(response.body().getMessage());
                        }
                    } else {
                        view.OnFailure(response.message());
                    }

                } else {
                    view.OnFailure(response.message());
                }

            }

            @Override
            public void onFailure(Call<CreateBid> call, Throwable t) {
                view.OnFailure(t.getMessage());
            }
        });
    }
}
