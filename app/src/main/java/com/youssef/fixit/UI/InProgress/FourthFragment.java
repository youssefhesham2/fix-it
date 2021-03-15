package com.youssef.fixit.UI.InProgress;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.youssef.fixit.Data.RetrofitClient;
import com.youssef.fixit.Models.Bids.CreateBid;
import com.youssef.fixit.Models.Contract.Contract;
import com.youssef.fixit.Models.Contract.Data;
import com.youssef.fixit.R;
import com.youssef.fixit.UI.Contract.ContractViewModel;
import com.youssef.fixit.UI.MyProjects.MyInprogressProject.MyInprogressAdapter;
import com.youssef.fixit.databinding.FragmentFourthBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FourthFragment extends Fragment {
    FragmentFourthBinding binding;
    Data contract1;
    private SweetAlertDialog loadingdialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fourth, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitloadingDialog();
        GetContract();
        SendFeedback();
    }

    void SendFeedback() {
       binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedback =binding.edtFeedback.getText().toString();
                if(!feedback.isEmpty()){
                    loadingdialog.show();
                    RetrofitClient.retrofitClient.Setfeedback(feedback,MyInprogressAdapter.jobs.getId(),contract1.getProfessionId()).enqueue(new Callback<CreateBid>() {
                        @Override
                        public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                            loadingdialog.dismiss();
                            if(response.isSuccessful()){
                                if(response.body()!=null){
                                    if(response.body().getData()!=null){
                                        SweetAlertDialog loadingdialog2= new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
                                                loadingdialog2.setTitleText("complete project");
                                                loadingdialog2.show();
                                        ReplaceFragment(new FifthStepsFragment());
                                        loadingdialog2.dismiss();
                                    }
                                    else {
                                        Toast.makeText(getContext(),response.body().getMessage()+ "", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(getContext(), response.toString()+response.message()+"", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CreateBid> call, Throwable t) {
                            loadingdialog.dismiss();
                            Toast.makeText(getContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void GetContract() {
        ContractViewModel viewModel = ViewModelProviders.of(this).get(ContractViewModel.class);
        viewModel.ShowContract(MyInprogressAdapter.jobs.getContract().getId());
        viewModel.contractMutableLiveData.observe(this, new Observer<Contract>() {
            @Override
            public void onChanged(Contract contract) {
                if (contract.getData() != null) {
                    contract1 = contract.getData();
                }
            }
        });
    }

    private void InitloadingDialog() {
        loadingdialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        loadingdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        loadingdialog.setTitleText("Loading");
        loadingdialog.setCancelable(false);

    }

    public void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}