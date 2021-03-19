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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Bids.CreateBid;
import com.youssef.fixit.Models.Register.Register;
import com.youssef.fixit.R;
import com.youssef.fixit.MainActivity.MainActivity;
import com.youssef.fixit.databinding.FragmentLoginBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements LoginView {
    FragmentLoginBinding binding;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public ProgressDialog dialog;
    int showpassword;
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
        IntialSharedPreferences();
        InitDialog();
        InitViews();
        ForgetPassword();
        ShowPassword();
    }

    void InitViews() {
        loginPresenter = new LoginPresenter(this);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Mail = binding.etMail.getText().toString();
                String Password = binding.etPassword.getText().toString();
                dialog.show();
                loginPresenter.OnLogin(Mail, Password);
            }
        });
    }

    public void IntialSharedPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();
    }

    private void InitDialog() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("please wait...");
        dialog.setTitle("Sign in");
        dialog.setCancelable(false);
    }

    private void ForgetPassword() {
        binding.forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = binding.etMail.getText().toString();
                dialog.setTitle("");
                dialog.show();
                loginPresenter.OnForgotPassword(mail);
            }
        });
    }

    private void ShowPassword() {
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

    @Override
    public void OnMailIsError(String Message) {
        dialog.dismiss();
        Toast.makeText(getContext(), Message, Toast.LENGTH_SHORT).show();
        binding.etMail.requestFocus();
    }

    @Override
    public void OnPasswordIsError(String Message) {
        dialog.dismiss();
        Toast.makeText(getContext(), Message, Toast.LENGTH_SHORT).show();
        binding.etPassword.requestFocus();
    }

    @Override
    public void OnLoginSuccessful(String token, String role, int my_id) {
        dialog.dismiss();
        SaveDataInShared(token, role, my_id);
    }

    @Override
    public void OnFailure(String error) {
        dialog.dismiss();
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnForgotPassword() {
        dialog.dismiss();
        Toast.makeText(getContext(), "Mail is Sent", Toast.LENGTH_SHORT).show();
    }

    void SaveDataInShared(String token, String role, int my_id) {
        editor.putString("token", token);
        editor.putString("role", role);
        editor.putInt("my_id", my_id);
        editor.commit();
        getActivity().startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }
}