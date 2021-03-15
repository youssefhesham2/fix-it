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

import android.util.Log;
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
import com.youssef.fixit.UI.MainActivity.MainActivity;
import com.youssef.fixit.UI.MyProjects.MyInprogressProject.MyInprogressAdapter;
import com.youssef.fixit.databinding.FragmentThirdStepBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThirdStepFragment extends Fragment {
    FragmentThirdStepBinding binding;
    Data contract1;
    private SweetAlertDialog loadingdialog;
    Float friendly, quality, time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_third_step, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitView();
        InitloadingDialog();
    }

    void InitView() {
        if (MainActivity.MyRole.equals("profession")) {
            binding.tvFriendly.setText("Fair");
            binding.tvQuality.setText("Friendly");
            binding.tvTime.setText("Pay on time");
            SetRatForClient();
        } else {
            SetRatForProff();
        }
    }

    public void SetRatForProff() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendly = binding.friendlyRate.getRating();
                quality = binding.friendlyQuality.getRating();
                time = binding.timeRate.getRating();
                if (friendly == 0) {
                    Toast.makeText(getContext(), "please set friendly rate ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (quality == 0) {
                    Toast.makeText(getContext(), "please set quality rate ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (time == 0) {
                    Toast.makeText(getContext(), "please set time rate ", Toast.LENGTH_SHORT).show();
                    return;
                }

                loadingdialog.show();
                SendRateforproff(friendly, "Friendly");
            }
        });
    }

    public void SetRatForClient() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendly = binding.friendlyRate.getRating();
                quality = binding.friendlyQuality.getRating();
                time = binding.timeRate.getRating();
                if (friendly == 0) {
                    Toast.makeText(getContext(), "please set Fair rate ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (quality == 0) {
                    Toast.makeText(getContext(), "please set Friendly rate ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (time == 0) {
                    Toast.makeText(getContext(), "please set Pay on time rate ", Toast.LENGTH_SHORT).show();
                    return;
                }

                loadingdialog.show();
                SendRateforclient(friendly, "Fair");
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

    void SendRateforproff(float rate, String type) {
        RetrofitClient.getInstance().SetRate(type, friendly, MyInprogressAdapter.jobs.getId(), InProgressActivity.MyContract.getProfessionId()).enqueue(new Callback<CreateBid>() {
            @Override
            public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            if (type.equals("Friendly")) {
                                SendRateforproff(quality, "Quality");
                            } else if (type.equals("Quality")) {
                                SendRateforproff(time, "Time");
                            } else if (type.equals("Time")) {
                                SweetAlertDialog loadingdialog2= new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
                                        loadingdialog2.setTitleText("Done");
                                        loadingdialog2.show();
                                loadingdialog2.dismiss();
                                ReplaceFragment(new FourthFragment());
                            }
                        } else {
                            loadingdialog.dismiss();
                            Log.e("hesham",response.toString()+" "+response.body().getMessage()+"11");
                            Toast.makeText(getContext(), response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        loadingdialog.dismiss();
                        Log.e("hesham",response.toString()+" "+response.message()+"22");
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    loadingdialog.dismiss();
                    Log.e("hesham",response.message()+"333");
                    Toast.makeText(getContext(), response.toString() + response.message() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateBid> call, Throwable t) {
                loadingdialog.dismiss();
                Log.e("hesham",t.getMessage()+"0000");
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void SendRateforclient(float rate, String type) {
        RetrofitClient.getInstance().SetRate(type, friendly, MyInprogressAdapter.jobs.getId(), InProgressActivity.MyContract.getProfessionId()).enqueue(new Callback<CreateBid>() {
            @Override
            public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            if (type.equals("Fair")) {
                                SendRateforclient(quality, "Friendly");
                            } else if (type.equals("Friendly")) {
                                SendRateforclient(time, "Pay on time");
                            } else if (type.equals("Pay on time")) {
                                SweetAlertDialog loadingdialog2= new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
                                        loadingdialog2.setTitleText("Done");
                                        loadingdialog2.show();
                                        loadingdialog2.dismiss();
                                ReplaceFragment(new FourthFragment());
                            }
                        } else {
                            loadingdialog.dismiss();
                            Toast.makeText(getContext(), response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        loadingdialog.dismiss();
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    loadingdialog.dismiss();
                    Toast.makeText(getContext(), response.toString() + response.message() + "", Toast.LENGTH_SHORT).show();
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

    public void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}