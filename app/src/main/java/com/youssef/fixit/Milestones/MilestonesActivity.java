package com.youssef.fixit.Milestones;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Bids.CreateBid;
import com.youssef.fixit.Contract.CreateContractActivity;
import com.youssef.fixit.databinding.ActivityMilestonesBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MilestonesActivity extends AppCompatActivity {
    ActivityMilestonesBinding binding;
    String title, amount;
    int possiton, contract_id;
    private SweetAlertDialog loadingdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMilestonesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        InitloadingDialog();
        InitViews();
    }

    void InitViews() {
        title = getIntent().getStringExtra("title");
        amount = getIntent().getStringExtra("amount");
        possiton = getIntent().getIntExtra("position", 0);
        contract_id = getIntent().getIntExtra("contract_id", 0);
        if (possiton > 0) {
            binding.etDescription.setText(CreateContractActivity.MillestonesTitleList.get(possiton - 1));
            binding.etAmount.setText(CreateContractActivity.MillestonesPriceList.get(possiton - 1));
        }
        binding.btnCreateMilestone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contract_id != 0) {
                    AddMillestone();
                } else {
                    OnCreateMilestone();
                }
            }
        });
    }

    // add millestone when project in progess
    void AddMillestone() {
        String Amount, Description;
        Amount = binding.etAmount.getText().toString();
        Description = binding.etDescription.getText().toString();

        if (Amount.isEmpty()) {
            Toast.makeText(this, "Amount is required", Toast.LENGTH_SHORT).show();
            binding.etAmount.requestFocus();
            return;
        }
        if (Description.isEmpty()) {
            Toast.makeText(this, "Description is required", Toast.LENGTH_SHORT).show();
            binding.etDescription.requestFocus();
            return;
        }
        Toast.makeText(this, contract_id + "", Toast.LENGTH_SHORT).show();
        loadingdialog.show();
        RetrofitClient.retrofitClient.CreateMilestone(contract_id, Description, Amount).enqueue(new Callback<CreateBid>() {
            @Override
            public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                loadingdialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            if (response.body().getSuccess() == true) {
                                new SweetAlertDialog(MilestonesActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Sent successful")
                                        .show();
                                MilestonesActivity.super.onBackPressed();
                            } else {
                                Toast.makeText(MilestonesActivity.this, response.body().getErrors() + "", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MilestonesActivity.this, response.body().getErrors() + "", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MilestonesActivity.this, response.body().getErrors() + "", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MilestonesActivity.this, response.body().getErrors() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateBid> call, Throwable t) {
                loadingdialog.dismiss();
                Toast.makeText(MilestonesActivity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // add millestone when make contract
    void OnCreateMilestone() {
        String Amount, Description;
        Amount = binding.etAmount.getText().toString();
        Description = binding.etDescription.getText().toString();

        if (Amount.isEmpty()) {
            Toast.makeText(this, "Amount is required", Toast.LENGTH_SHORT).show();
            binding.etAmount.requestFocus();
            return;
        }
        if (Description.isEmpty()) {
            Toast.makeText(this, "Description is required", Toast.LENGTH_SHORT).show();
            binding.etDescription.requestFocus();
            return;
        }
        if (possiton > 0) {
            CreateContractActivity.MillestonesTitleList.set(possiton - 1, Description);
            CreateContractActivity.MillestonesPriceList.set(possiton - 1, Amount);
            super.onBackPressed();
        } else {
            CreateContractActivity.MillestonesTitleList.add(Description);
            CreateContractActivity.MillestonesPriceList.add(Amount);
            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void InitloadingDialog() {
        loadingdialog = new SweetAlertDialog(MilestonesActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        loadingdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        loadingdialog.setTitleText("Loading");
        loadingdialog.setCancelable(false);

    }

}