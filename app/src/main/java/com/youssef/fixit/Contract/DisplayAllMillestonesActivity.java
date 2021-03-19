package com.youssef.fixit.Contract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
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
import com.youssef.fixit.Models.Contract.Milestone;
import com.youssef.fixit.R;
import com.youssef.fixit.InProgress.InProgressActivity;
import com.youssef.fixit.InProgress.SeconedStepFragment;
import com.youssef.fixit.MainActivity.MainActivity;
import com.youssef.fixit.Milestones.MilestonesActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayAllMillestonesActivity extends AppCompatActivity {
    private SweetAlertDialog loadingdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_millestones);
        InitloadingDialog();
        int from_second_step = getIntent().getIntExtra("from_second_step", 0);
        if (from_second_step == 1) {
            InitViews2();
        } else {
            InitViews();
        }
    }

    private void InitViews() {
        RecyclerView rv = findViewById(R.id.rv_milestones);
        MilestonesAdapter adapter = new MilestonesAdapter(DisplayContractActivity.milestones);
        rv.setAdapter(adapter);
    }

    private void InitViews2() {
        RecyclerView rv = findViewById(R.id.rv_milestones);
        MilestonesAdapter2 adapter = new MilestonesAdapter2(SeconedStepFragment.contract1.getMilestones());
        rv.setAdapter(adapter);
    }

    public class MilestonesAdapter extends RecyclerView.Adapter<DisplayAllMillestonesActivity.MilestonesAdapter.viewholder> {
        List<Milestone> milestones;

        public MilestonesAdapter(List<Milestone> milestones) {
            this.milestones = milestones;
        }

        @NonNull
        @Override
        public DisplayAllMillestonesActivity.MilestonesAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(DisplayAllMillestonesActivity.this).inflate(R.layout.millestone_item, parent, false);
            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DisplayAllMillestonesActivity.MilestonesAdapter.viewholder holder, int position) {
            Milestone milestone = milestones.get(position);
            holder.btn_cancel.setVisibility(View.GONE);
            holder.tv_milestones_title.setText(milestone.getDetails());
            holder.tv_milestones_price.setText("$ " + milestone.getPrice() + " USD");
        }

        @Override
        public int getItemCount() {
            return milestones.size();
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


    public class MilestonesAdapter2 extends RecyclerView.Adapter<DisplayAllMillestonesActivity.MilestonesAdapter2.viewholder> {
        List<Milestone> milestones;

        public MilestonesAdapter2(List<Milestone> milestones) {
            this.milestones = milestones;
        }

        @NonNull
        @Override
        public DisplayAllMillestonesActivity.MilestonesAdapter2.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(DisplayAllMillestonesActivity.this).inflate(R.layout.milestones_item2, parent, false);
            return new DisplayAllMillestonesActivity.MilestonesAdapter2.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DisplayAllMillestonesActivity.MilestonesAdapter2.viewholder holder, int position) {
            Milestone milestone = milestones.get(position);
            holder.tv_milestones_title.setText(milestone.getDetails());
            holder.tv_milestones_price.setText("$ " + milestone.getPrice() + " USD");
            if (MainActivity.MyRole.equals("client")) {
                if(position==milestones.size()-1){
                    holder.add_milestone.setVisibility(View.VISIBLE);
                }
                holder.send_milestone.setVisibility(View.VISIBLE);

                holder.send_milestone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AcceptMilestone(milestone.getId());
                    }
                });
                holder.add_milestone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(DisplayAllMillestonesActivity.this, MilestonesActivity.class);
                        intent.putExtra("contract_id", milestone.getContractId());
                        startActivityForResult(intent, 2);
                    }
                });
            } else {
                holder.btn_request_milestone.setVisibility(View.VISIBLE);
                holder.btn_request_milestone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RaleaseMilestone(milestone.getId());
                    }
                });
            }

            if (milestone.getStatus().equals("released")) {
                holder.send_milestone.setVisibility(View.GONE);
                holder.btn_request_milestone.setVisibility(View.GONE);
                holder.tv_milestones_status.setVisibility(View.VISIBLE);
                holder.view_line.setVisibility(View.VISIBLE);
                holder.tv_milestones_status.setText(milestone.getStatus());
            }
        }

        @Override
        public int getItemCount() {
            return milestones.size();
        }

        class viewholder extends RecyclerView.ViewHolder {
            TextView tv_milestones_price, tv_milestones_title, tv_milestones_status;
            Button btn_request_milestone, send_milestone, add_milestone;
            View view_line;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                tv_milestones_price = itemView.findViewById(R.id.tv_milestones_price);
                tv_milestones_title = itemView.findViewById(R.id.tv_milestones_title);
                btn_request_milestone = itemView.findViewById(R.id.btn_request_milestone);
                send_milestone = itemView.findViewById(R.id.send_milestone);
                add_milestone = itemView.findViewById(R.id.add_milestone);
                view_line = itemView.findViewById(R.id.view_line);
                tv_milestones_status = itemView.findViewById(R.id.tv_milestones_status);
            }
        }
    }

    void RaleaseMilestone(int milestone_id) {
        loadingdialog.show();
        RetrofitClient.getInstance().ReleaseMilestone(milestone_id).enqueue(new Callback<CreateBid>() {
            @Override
            public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                loadingdialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            if (response.body().getSuccess() == true) {
                                new SweetAlertDialog(DisplayAllMillestonesActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Released")
                                        .show();
                                Log.e("hesham", response.body().getMessage() + "555");
                            }
                            else {
                                Log.e("hesham",response.body().getMessage()+"666");
                                Toast.makeText(DisplayAllMillestonesActivity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("hesham",response.body().getMessage()+"111");
                            Toast.makeText(DisplayAllMillestonesActivity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("hesham",response.message()+"222");
                        Toast.makeText(DisplayAllMillestonesActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.e("hesham",response.message()+"333");
                    Toast.makeText(DisplayAllMillestonesActivity.this, response.toString() + response.message() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateBid> call, Throwable t) {
                loadingdialog.dismiss();
                Log.e("hesham",t.getMessage()+"44");
                Toast.makeText(DisplayAllMillestonesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void AcceptMilestone(int milestone_id) {
        loadingdialog.show();
        RetrofitClient.getInstance().AcceptReleaseMilestone(milestone_id).enqueue(new Callback<CreateBid>() {
            @Override
            public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                loadingdialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            if (response.body().getSuccess() == true) {
                                new SweetAlertDialog(DisplayAllMillestonesActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Sent successful")
                                        .show();
                                Intent refresh = new Intent(DisplayAllMillestonesActivity.this, InProgressActivity.class);
                                startActivity(refresh);
                                finish();
                            } else {
                                Log.e("hesham",response.body().getErrors()+"111");
                                Toast.makeText(DisplayAllMillestonesActivity.this,response.body().getErrors()+ "", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("hesham",response.body().getErrors()+"111");
                            Toast.makeText(DisplayAllMillestonesActivity.this,response.body().getErrors()+ "", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("hesham",response.body().getErrors()+"111");
                        Toast.makeText(DisplayAllMillestonesActivity.this,response.body().getErrors()+ "", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.e("hesham",response.body().getErrors()+"111");
                    Toast.makeText(DisplayAllMillestonesActivity.this,response.body().getErrors()+ "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateBid> call, Throwable t) {
                Log.e("hesham",t.getMessage()+"555");
                loadingdialog.dismiss();
                Toast.makeText(DisplayAllMillestonesActivity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void InitloadingDialog() {
        loadingdialog = new SweetAlertDialog(DisplayAllMillestonesActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        loadingdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        loadingdialog.setTitleText("Loading");
        loadingdialog.setCancelable(false);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            Intent refresh = new Intent(DisplayAllMillestonesActivity.this, InProgressActivity.class);
            startActivity(refresh);
            finish();
        }
    }
}