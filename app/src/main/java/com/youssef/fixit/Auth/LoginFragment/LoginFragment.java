package com.youssef.fixit.Auth.LoginFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.youssef.fixit.Home.HomeActivity;
import com.youssef.fixit.R;
import com.youssef.fixit.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment implements LoginView {
    FragmentLoginBinding binding;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ProgressDialog dialog;
    int showPassword;
    LoginPresenter loginPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSharedPreferences();
        initDialog();
        initViews();
        forgetPassword();
        showPassword();
    }

    private void initViews() {
        loginPresenter = new LoginPresenter(this);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = binding.etMail.getText().toString();
                String password = binding.etPassword.getText().toString();
                loginPresenter.onLogin(mail, password);
            }
        });
    }

    private void initSharedPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();
    }

    private void initDialog() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("please wait...");
        dialog.setTitle("Sign in");
        dialog.setCancelable(false);
    }

    private void forgetPassword() {
        binding.forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = binding.etMail.getText().toString();
                dialog.setTitle("");
                loginPresenter.onForgotPassword(mail);
            }
        });
    }

    private void showPassword() {
        binding.etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (binding.etPassword.getRight() - binding.etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (showpassword == 0) {
                            showpassword = 1;
                            binding.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        } else if (showpassword == 1) {
                            showpassword = 0;
                            binding.etPassword.setInputType(129);
                        }
                        return true;
                    }
                }
                return false;

            }
        });
    }

    private void saveDataInShared(String token, String role, int my_id) {
        editor.putString("token", token);
        editor.putString("role", role);
        editor.putInt("my_id", my_id);
        editor.commit();
        getActivity().startActivity(new Intent(getContext(), HomeActivity.class));
        getActivity().finish();
    }

    @Override
    public void onMailIsError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        binding.etMail.requestFocus();
    }

    @Override
    public void onPasswordIsError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        binding.etPassword.requestFocus();
    }

    @Override
    public void onLoginSuccessful(String token, String role, int my_id) {
        saveDataInShared(token, role, my_id);
    }

    @Override
    public void onFailure(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onForgotPassword() {
        Toast.makeText(getContext(), "Mail is Sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }
}