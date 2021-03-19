package com.youssef.fixit.InProgress;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alespero.expandablecardview.ExpandableCardView;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.squareup.picasso.Picasso;
import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Bids.CreateBid;
import com.youssef.fixit.Models.Contract.Contract;
import com.youssef.fixit.Models.Contract.Data;
import com.youssef.fixit.Models.Contract.Milestone;
import com.youssef.fixit.Models.chat.SendMassage.SendMassage;
import com.youssef.fixit.R;
import com.youssef.fixit.Chat.ChatActivity;
import com.youssef.fixit.Contract.ContractViewModel;
import com.youssef.fixit.Contract.DisplayAllMillestonesActivity;
import com.youssef.fixit.MainActivity.MainActivity;
import com.youssef.fixit.Milestones.MilestonesActivity;
import com.youssef.fixit.MyProjects.MyInprogressProject.MyInprogressAdapter;
import com.youssef.fixit.databinding.FragmentSeconedStepBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeconedStepFragment extends Fragment {
    FragmentSeconedStepBinding binding;
    public static Data contract1;
    MilestonesAdapter milestonesAdapter;
    private SweetAlertDialog loadingdialog;
    public ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_seconed_step, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitloadingDialog();
        InitloadingDialog();
        GetContract();
    }

    public void Expandable() {
        binding.details.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                Firststep(v);
            }
        });
        binding.viewProposals.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                seconedStep(v);
            }
        });
        binding.management.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                Thirdstep(v);
            }
        });
    }

    public void seconedStep(View v) {
        TextView tv_user_name, tv_price;
        RatingBar rating_bar;
        ImageView img_profile_pic;
        Button btn_chat;

        tv_user_name = v.findViewById(R.id.tv_user_name);
        tv_price = v.findViewById(R.id.tv_price);
        rating_bar = v.findViewById(R.id.rating_bar);
        img_profile_pic = v.findViewById(R.id.img_profile_pic);
        btn_chat = v.findViewById(R.id.btn_chat);

        tv_price.setText("$ " + contract1.getTotalPrice() + " USD");
        if (MainActivity.MyRole == ("client")) {
            tv_user_name.setText(contract1.getProfession().getName() + "");
            rating_bar.setRating(contract1.getProfession().getRate().floatValue());
            Picasso.get().load(contract1.getProfession().getImage()).error(R.mipmap.ic_launcher).into(img_profile_pic);
            btn_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Makeroom(contract1.getProfession().getId());
                }
            });
        } else {
            tv_user_name.setText(contract1.getCreatedBy().getName() + "");
            rating_bar.setRating(contract1.getCreatedBy().getRate().floatValue());
            Picasso.get().load(contract1.getCreatedBy().getImage()).error(R.mipmap.ic_launcher).into(img_profile_pic);
            btn_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Makeroom(contract1.getCreatedBy().getId());
                }
            });
        }

    }

    public void Firststep(View v) {
        TextView tv_prof_name, tv_price_details;
        tv_prof_name = v.findViewById(R.id.tv_prof_name);
        tv_price_details = v.findViewById(R.id.tv_price_details);

        tv_prof_name.setText("project for " + contract1.getProfession().getName());
        tv_price_details.setText("$ " + contract1.getTotalPrice() + " USD");
    }

    public void Thirdstep(View v) {
        RecyclerView recyclerView;
        TextView tv_milestones_cont, tv_see_more;
        recyclerView = v.findViewById(R.id.rv);
        tv_milestones_cont = v.findViewById(R.id.tv_milestones_cont);
        tv_see_more = v.findViewById(R.id.tv_see_more);
        recyclerView.setAdapter(milestonesAdapter);
        if (milestonesAdapter.milestones.size() > 0) {
            tv_see_more.setVisibility(View.VISIBLE);
            tv_milestones_cont.setText("Total Milestones ( " + milestonesAdapter.milestones.size() + " )");
            tv_see_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), DisplayAllMillestonesActivity.class);
                    intent.putExtra("from_second_step", 1);
                    startActivity(intent);
                }
            });
        }
    }

    public void GetContract() {
        ContractViewModel viewModel = ViewModelProviders.of(this).get(ContractViewModel.class);
        viewModel.ShowContract(MyInprogressAdapter.jobs.getContract().getId());
        viewModel.contractMutableLiveData.observe(this, new Observer<Contract>() {
            @Override
            public void onChanged(Contract contract) {
                if (contract.getData() != null) {
                    contract1 = contract.getData();
                    Expandable();
                    if (contract1.getMilestones() != null) {
                        if (contract1.getMilestones().size() > 0) {
                            milestonesAdapter = new MilestonesAdapter(contract1.getMilestones());
                        }
                    }
                }
            }
        });
        viewModel.error.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class MilestonesAdapter extends RecyclerView.Adapter<SeconedStepFragment.MilestonesAdapter.viewholder> {
        List<Milestone> milestones;

        public MilestonesAdapter(List<Milestone> milestones) {
            this.milestones = milestones;
        }

        @NonNull
        @Override
        public SeconedStepFragment.MilestonesAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.milestones_item2, parent, false);
            return new SeconedStepFragment.MilestonesAdapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SeconedStepFragment.MilestonesAdapter.viewholder holder, int position) {
            Milestone milestone = milestones.get(position);
            holder.tv_milestones_title.setText(milestone.getDetails());
            holder.tv_milestones_price.setText("$ " + milestone.getPrice() + " USD");
            if (MainActivity.MyRole.equals("client")) {
                holder.send_milestone.setVisibility(View.VISIBLE);
                holder.add_milestone.setVisibility(View.VISIBLE);

                holder.send_milestone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AcceptMilestone(milestone.getId());
                    }
                });
                holder.add_milestone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), MilestonesActivity.class);
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
            return 1;
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
        Toast.makeText(getContext(), milestone_id + "", Toast.LENGTH_SHORT).show();
        RetrofitClient.getInstance().ReleaseMilestone(milestone_id).enqueue(new Callback<CreateBid>() {
            @Override
            public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                loadingdialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Released")
                                    .show();

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
                Toast.makeText(getContext(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
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
                            if (response.body().getData() != null) {
                                if (response.body().getSuccess() == true) {
                                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Sent successful")
                                            .show();
                                    Intent refresh = new Intent(getContext(), InProgressActivity.class);
                                    startActivity(refresh);
                                    getActivity().finish();
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

                } else {
                    Toast.makeText(getContext(), response.body().getErrors() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateBid> call, Throwable t) {
                loadingdialog.dismiss();
                Toast.makeText(getContext(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            Intent refresh = new Intent(getContext(), InProgressActivity.class);
            startActivity(refresh);
            getActivity().finish();
        }
    }

    private void Makeroom(int prof_id) {
        dialog.show();
        RetrofitClient.getInstance().MakeRoom(prof_id).enqueue(new Callback<SendMassage>() {
            @Override
            public void onResponse(Call<SendMassage> call, Response<SendMassage> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Intent intent = new Intent(getContext(), ChatActivity.class);
                        intent.putExtra("user_id", prof_id);
                        intent.putExtra("room_id", response.body().getData().getId());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), response.message() + "", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), response.message() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SendMassage> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void InitDialog() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);
    }

}