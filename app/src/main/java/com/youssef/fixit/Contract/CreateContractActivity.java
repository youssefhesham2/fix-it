package com.youssef.fixit.Contract;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Bids.CreateBid;
import com.youssef.fixit.Milestones.MilestonesActivity;
import com.youssef.fixit.R;
import com.youssef.fixit.Projects.ProjectActivity;
import com.youssef.fixit.databinding.ActivityContractBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateContractActivity extends AppCompatActivity {
    ActivityContractBinding binding;
    private ProgressDialog dialog;
    public static List<String> MillestonesTitleList = new ArrayList<>();
    public static List<String> MillestonesPriceList = new ArrayList<>();
    public static int my_position = 0;
    private int project_id, prof_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContractBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        InitDialog();
        InitViews();
        InitRecyclerView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        InitRecyclerView();
    }

    private void InitViews() {
        project_id = getIntent().getIntExtra("project_id", 0);
        prof_id = getIntent().getIntExtra("prof_id", 0);
        Log.e("hesham",project_id+"ttt");
        Log.e("hesham",prof_id+"rrr");
        binding.tvCurrency.setText(ProjectActivity.Project_currency);
        binding.btnCreateMilestones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rvMilestones.setVisibility(View.VISIBLE);
                binding.btnCreateMilestones.setVisibility(View.GONE);
                startActivity(new Intent(CreateContractActivity.this, MilestonesActivity.class));
            }
        });

        binding.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = binding.etDetails.getText().toString();
                String total_price = binding.etTotalPrice.getText().toString();
                String addrasse = binding.etAddress.getText().toString();
                String phone = binding.etPhone.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(CreateContractActivity.this, "Details filed is require", Toast.LENGTH_SHORT).show();
                    binding.etDetails.requestFocus();
                    return;
                }
                if (total_price.isEmpty()) {
                    Toast.makeText(CreateContractActivity.this, "price filed is require", Toast.LENGTH_SHORT).show();
                    binding.etTotalPrice.requestFocus();
                    return;
                }
                if (addrasse.isEmpty()) {
                    Toast.makeText(CreateContractActivity.this, "Addrasse filed is require", Toast.LENGTH_SHORT).show();
                    binding.etAddress.requestFocus();
                    return;
                }
                if (phone.isEmpty()) {
                    Toast.makeText(CreateContractActivity.this, "Phone filed is require", Toast.LENGTH_SHORT).show();
                    binding.etPhone.requestFocus();
                    return;
                }
                if (project_id == 0 ) {
                    Toast.makeText(CreateContractActivity.this, "project id is error", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (prof_id == 0 ) {
                    Toast.makeText(CreateContractActivity.this, "prof id is error", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (MillestonesTitleList.size() == 0) {
                    MillestonesTitleList.add("total Millestones");
                    MillestonesPriceList.add(total_price);
                }
                CreatContract(project_id, description, total_price, addrasse, phone, prof_id);
            }
        });
    }

    private void CreatContract(int project_id, String description, String total_price, String address, String phone, int profession_id) {
        dialog.show();
        RetrofitClient.getInstance().Createcontract(project_id, description, total_price, address, phone, profession_id, MillestonesTitleList, MillestonesPriceList).enqueue(new Callback<CreateBid>() {
            @Override
            public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                dialog.dismiss();
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        if(response.body().getData()!=null){
                                new SweetAlertDialog(CreateContractActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Accepted!")
                                        .setContentText("You clicked the button!")
                                        .show();
                                onBackPressed();

                        }
                        else {
                            Toast.makeText(CreateContractActivity.this,response.body().getErrors(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(CreateContractActivity.this, response.message()+"333", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(CreateContractActivity.this, response.toString()+response.message()+"11", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateBid> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(CreateContractActivity.this, t.getMessage() + "33", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void InitRecyclerView() {
        MilestonesAdapter milestonesAdapter = new MilestonesAdapter(MillestonesTitleList, MillestonesPriceList);
        binding.rvMilestones.setAdapter(milestonesAdapter);
    }

    public class MilestonesAdapter extends RecyclerView.Adapter<MilestonesAdapter.viewholder> {
        List<String> MillestonesTitleList2;
        List<String> MillestonesPriceList2;

        public MilestonesAdapter(List<String> millestonesTitleList, List<String> millestonesPriceList) {
            MillestonesTitleList2 = millestonesTitleList;
            MillestonesPriceList2 = millestonesPriceList;
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(CreateContractActivity.this).inflate(R.layout.millestone_item, parent, false);
            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
            my_position = position + 1;
            if(position==MillestonesPriceList2.size()-1){
                holder.btn_add_one.setVisibility(View.VISIBLE);
            }
            String title = MillestonesTitleList2.get(position);
            String price = MillestonesPriceList2.get(position);
            holder.tv_milestones_title.setText(title);
            holder.tv_milestones_price.setText(price);
            holder.btn_add_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(CreateContractActivity.this, MilestonesActivity.class));
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CreateContractActivity.this, MilestonesActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("amount", price);
                    intent.putExtra("position", position + 1);
                    startActivity(intent);
                }
            });
            holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(CreateContractActivity.this, position + "", Toast.LENGTH_SHORT).show();
                    MillestonesTitleList.remove(position);
                    MillestonesPriceList.remove(position);
                    if (MillestonesPriceList.size() == 0) {
                        binding.btnCreateMilestones.setVisibility(View.VISIBLE);
                    }
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return MillestonesPriceList2.size();
        }

        public class viewholder extends RecyclerView.ViewHolder {
            TextView tv_milestones_price, tv_milestones_title;
            Button btn_cancel, btn_add_one;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                tv_milestones_title = itemView.findViewById(R.id.tv_milestones_title);
                tv_milestones_price = itemView.findViewById(R.id.tv_milestones_price);
                btn_cancel = itemView.findViewById(R.id.btn_cancel);
                btn_add_one = itemView.findViewById(R.id.btn_add_one);
            }
        }
    }

    private void InitDialog() {
        dialog = new ProgressDialog(CreateContractActivity.this);
        dialog.setMessage("please wait...");
        dialog.setTitle("Create a Contract");
        dialog.setCancelable(false);
    }
}
