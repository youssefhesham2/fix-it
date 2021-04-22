package com.youssef.fixit.Auth.LoginFragment;

import android.util.Log;

import com.youssef.fixit.Models.Register.Data;
import com.youssef.fixit.SplashActivity.SplashScreen;
import com.youssef.fixit.Models.Bids.CreateBid;
import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Register.Register;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class LoginPresenter {
    private static final String TAG = "LoginPresenter";
    private LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    void onLogin(String mail, String password) {
        validation(mail, password);
    }

    void validation(String mail, String Password) {
        if (mail.isEmpty()) {
            view.onMailIsError("Please enter your Mail");
            return;
        }
        if (Password.isEmpty()) {
            view.onPasswordIsError("Please enter your Password");
            return;
        }

        loginRequest(mail, Password);
    }

    void loginRequest(String mail, String password) {
        view.showLoading();
        RetrofitClient.getInstance().Login(mail, password).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.code() == 200) {
                    if (response.body().getData() != null) {
                        Data data = response.body().getData();
                        List<String> roles = response.body().getData().getRoles();
                        SplashScreen.MyToken = response.body().getData().getToken();
                        SplashScreen.My_ID = response.body().getData().getId();
                        SplashScreen.MyRole = roles.get(0);
                        Log.d(TAG, response.body().getData().getId() + "");
                        Log.d(TAG, SplashScreen.MyToken);
                        view.saveString("token", data.getToken());
                        view.saveString("role", data.getRoles().get(0));
                        view.saveInt("my_id", data.getId());
                        view.onLoginSuccessful();
                    } else {
                        view.onFailure("incorrect email or password!!");
                    }
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                view.hideLoading();
                view.onFailure(t.getMessage());
            }
        });
    }

    void onForgotPassword(String Mail) {
        if (Mail.isEmpty()) {
            view.onMailIsError("Please enter your Mail");
            return;
        }
        forgotPasswordRequest(Mail);
    }

    void forgotPasswordRequest(String Mail) {
        view.showLoading();
        RetrofitClient.getInstance().ForgetPassword(Mail).enqueue(new Callback<CreateBid>() {
            @Override
            public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                view.hideLoading();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            if (response.body().getData() != null) {
                                view.onForgotPassword();
                            }
                        } else {
                            view.onFailure(response.body().getMessage());
                        }
                    } else {
                        view.onFailure(response.message());
                    }

                } else {
                    view.onFailure(response.message());
                }

            }

            @Override
            public void onFailure(Call<CreateBid> call, Throwable t) {
                view.hideLoading();
                view.onFailure(t.getMessage());
            }
        });
    }
}
