package com.youssef.fixit.UI.Auth;

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

import com.youssef.fixit.Data.RetrofitClient;
import com.youssef.fixit.Models.Bids.CreateBid;
import com.youssef.fixit.Models.Register.Register;
import com.youssef.fixit.R;
import com.youssef.fixit.UI.Bids.CreateBidActivity;
import com.youssef.fixit.UI.Home.HomeActivity;
import com.youssef.fixit.UI.MainActivity.MainActivity;
import com.youssef.fixit.databinding.FragmentLoginBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public ProgressDialog dialog;
    int showpassword;

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
    }

    void InitViews() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Mail = binding.etMail.getText().toString();
                String Password = binding.etPassword.getText().toString();
                if (Mail.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                    binding.etMail.requestFocus();
                    return;
                }
                if (Password.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter your Password", Toast.LENGTH_SHORT).show();
                    binding.etPassword.requestFocus();
                    return;
                }
                dialog.show();
                RetrofitClient.getInstance().Login(Mail, Password).enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> response) {
                        dialog.dismiss();
                        if (response.isSuccessful() && response.code() == 200) {
                            if (response.body().getData() != null) {
                                List<String> roles = response.body().getData().getRoles();
                                MainActivity.MyToken = response.body().getData().getToken();
                                MainActivity.My_ID = response.body().getData().getId();
                                MainActivity.MyRole = roles.get(0);
                                Log.e("hesham", response.body().getData().getId() + "");
                                Log.e("hesham", "y" + MainActivity.MyToken);
                                editor.putString("token", response.body().getData().getToken());
                                editor.putString("role", roles.get(0));
                                editor.putInt("my_id", response.body().getData().getId());
                                editor.commit();
                                getActivity().startActivity(new Intent(getContext(), MainActivity.class));
                                getActivity().finish();
                            } else {
                                Toast.makeText(getContext(), "incorrect email or password!!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Register> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });
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

        binding.forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.etMail.getText().toString();
                String Password = binding.etPassword.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(getContext(), "please enter your mail", Toast.LENGTH_SHORT).show();
                    binding.etMail.requestFocus();
                    return;
                }
                dialog.setTitle("");
                dialog.show();
                RetrofitClient.getInstance().ForgetPassword(email).enqueue(new Callback<CreateBid>() {
                    @Override
                    public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getData() != null) {
                                    if (response.body().getData() != null) {
                                        Toast.makeText(getContext(), response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getContext(), response.toString() + response.message() + "", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<CreateBid> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
    }
}