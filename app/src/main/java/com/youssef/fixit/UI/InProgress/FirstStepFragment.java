package com.youssef.fixit.UI.InProgress;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.youssef.fixit.Data.RetrofitClient;
import com.youssef.fixit.Models.Bids.CreateBid;
import com.youssef.fixit.Models.Contract.Data;
import com.youssef.fixit.R;
import com.youssef.fixit.UI.MyProjects.MyInprogressProject.MyInprogressAdapter;
import com.youssef.fixit.UI.PaymentTypes.Paypal.PaypalActivity;
import com.youssef.fixit.databinding.FragmentFirstStepBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstStepFragment extends Fragment {
    FragmentFirstStepBinding binding;
    private SweetAlertDialog loadingdialog;
    Data contract;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_step, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitloadingDialog();
        InitViews();
    }

    public void InitViews() {
        contract = InProgressActivity.MyContract;
        binding.tvProjectTitle.setText(MyInprogressAdapter.jobs.getTitle());
        binding.tvProjectPrice.setText("$" + contract.getTotalPrice() + MyInprogressAdapter.jobs.getCurrency());
        binding.tvProjectMilestones.setText(MyInprogressAdapter.jobs.getUnpaidMilestonesCount() + " Milestone");
        binding.btnActivateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActiveProject();
            }
        });
    }

    private void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    private void ActiveProject() {
        loadingdialog.show();
        RetrofitClient.getInstance().activeproject(MyInprogressAdapter.jobs.getContract().getId()).enqueue(new Callback<CreateBid>() {
            @Override
            public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                loadingdialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            if (response.body().getData() != null) {
                                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Activate successful")
                                        .show();
                                getActivity().onBackPressed();
                            } else {
                                Toast.makeText(getContext(), response.body().getErrors() + "", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), response.body().getErrors() + "", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), response.body().getErrors() + "", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), response.body().getErrors() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateBid> call, Throwable t) {
                loadingdialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void InitloadingDialog() {
        loadingdialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        loadingdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        loadingdialog.setTitleText("Loading");
        loadingdialog.setCancelable(false);

    }
}