package com.youssef.fixit.UI.Contract;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.youssef.fixit.Data.RetrofitClient;
import com.youssef.fixit.Models.Bids.CreateBid;
import com.youssef.fixit.Models.Contract.Contract;
import com.youssef.fixit.Models.Contract.Data;
import com.youssef.fixit.Models.Contract.Milestone;
import com.youssef.fixit.R;
import com.youssef.fixit.UI.Bids.CreateBidActivity;
import com.youssef.fixit.UI.MyProjects.MyProjectViewModel;
import com.youssef.fixit.databinding.ActivityDisplayContractBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayContractActivity extends AppCompatActivity {
    ActivityDisplayContractBinding binding;
    private  SweetAlertDialog loadingdialog;
    int ContractID;
    public static List<Milestone> milestones=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayContractBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        InitloadingDialog();
        ShowContract();
    }

    private void InitViews(){
        binding.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(DisplayContractActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be rejected this project!")
                        .setConfirmText("Yes,reject it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                AcceptedOrRejected("rejected");
                            }
                        })
                        .show();
            }
        });

        binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              AcceptedOrRejected("accepted");
            }
        });
    }
    private void ShowContract() {
        ContractID = getIntent().getIntExtra("contract_id", 0);
        ContractViewModel viewModel = ViewModelProviders.of(this).get(ContractViewModel.class);
        viewModel.ShowContract(ContractID);
        viewModel.contractMutableLiveData.observe(this, new Observer<Contract>() {
            @Override
            public void onChanged(Contract contract) {
                if (contract.getData() != null) {
                    InitViews();
                    Data contract1 = contract.getData();
                    binding.tvTotalPrice.setText(contract1.getTotalPrice() + "");
                    binding.tvAddress.setText(contract1.getAddress() + "");
                    binding.tvPhone.setText(contract1.getPhone() + "");
                    binding.tvDetails.setText(contract1.getDetails() + "");
                    if (contract1.getMilestones() != null) {
                        if (contract1.getMilestones().size() > 0) {
                            binding.rvMilestones.setVisibility(View.VISIBLE);
                            MilestonesAdapter adapter = new MilestonesAdapter(contract1.getMilestones());
                            binding.rvMilestones.setAdapter(adapter);
                            binding.tvMilestonesCont.setText("Total Milestones ("+contract1.getMilestones().size()+")");
                            if(contract1.getMilestones().size()>1){
                                milestones=contract1.getMilestones();
                                binding.tvSeeMore.setVisibility(View.VISIBLE);
                                binding.tvSeeMore.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(DisplayContractActivity.this,DisplayAllMillestonesActivity.class));
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
    }

    private void AcceptedOrRejected(String status){
        loadingdialog.show();
        RetrofitClient.getInstance().Acceptorreject(ContractID,status).enqueue(new Callback<CreateBid>() {
            @Override
            public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                loadingdialog.dismiss();
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        if(response.body().getData()!=null){
                            if(status.equals("accepted")){
                                new SweetAlertDialog(DisplayContractActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Accepted!")
                                        .setContentText("You clicked the button!")
                                        .show();
                                onBackPressed();
                            }
                            else {
                                new SweetAlertDialog(DisplayContractActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("rejected!")
                                        .setContentText("You clicked the button!")
                                        .show();
                                onBackPressed();
                            }
                        }
                        else {
                            Toast.makeText(DisplayContractActivity.this,response.body().getMessage()+ "", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(DisplayContractActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(DisplayContractActivity.this, response.toString()+response.message()+"", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateBid> call, Throwable t) {
                loadingdialog.dismiss();
                Toast.makeText(DisplayContractActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class MilestonesAdapter extends RecyclerView.Adapter<MilestonesAdapter.viewholder> {
        List<Milestone> milestones;

        public MilestonesAdapter(List<Milestone> milestones) {
            this.milestones = milestones;
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(DisplayContractActivity.this).inflate(R.layout.millestone_item, parent, false);
            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
            Milestone milestone = milestones.get(position);
            holder.btn_cancel.setVisibility(View.GONE);
            holder.tv_milestones_title.setText(milestone.getDetails());
            holder.tv_milestones_price.setText("$ "+milestone.getPrice() + " USD");
        }

        @Override
        public int getItemCount() {
            return 1;
        }

        class viewholder extends RecyclerView.ViewHolder {
            TextView tv_milestones_price, tv_milestones_title;
            Button btn_cancel;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                tv_milestones_price = itemView.findViewById(R.id.tv_milestones_price);
                tv_milestones_title = itemView.findViewById(R.id.tv_milestones_title);
                btn_cancel = itemView.findViewById(R.id.btn_cancel);
            }
        }
    }

    private void InitloadingDialog() {
        loadingdialog = new SweetAlertDialog(DisplayContractActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        loadingdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        loadingdialog.setTitleText("Loading");
        loadingdialog.setCancelable(false);

    }
}