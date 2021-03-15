package com.youssef.fixit.UI.Projects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.youssef.fixit.Data.RetrofitClient;
import com.youssef.fixit.Models.Bids.Bids;
import com.youssef.fixit.Models.Bids.Datum;
import com.youssef.fixit.Models.Project.Data;
import com.youssef.fixit.Models.Project.Project;
import com.youssef.fixit.Models.chat.SendMassage.SendMassage;
import com.youssef.fixit.R;
import com.youssef.fixit.UI.Bids.BidsListActivity;
import com.youssef.fixit.UI.Bids.CreateBidActivity;
import com.youssef.fixit.UI.Chat.ChatActivity;
import com.youssef.fixit.UI.Contract.CreateContractActivity;
import com.youssef.fixit.UI.MainActivity.MainActivity;
import com.youssef.fixit.UI.ShowProfile.ShowProfileActivity;
import com.youssef.fixit.databinding.ActivityProjectBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectActivity extends AppCompatActivity {
    ActivityProjectBinding binding;
    public static List<Datum> BidsList = new ArrayList<>();
    public static String project_title, Project_currency, Project_type;
    int from_my_project;
    int project_id;
    public ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        InitDialog();
//        from_my_project ;= getIntent().getIntExtra("from_my_project", 0);
        InitViews();
        GetProject();
    }

    void InitViews() {
        project_id=getIntent().getIntExtra("project_id",0);
        Log.e("hesham","project_id "+project_id);
        binding.btnPlaceBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProjectActivity.this, CreateBidActivity.class);
                intent.putExtra("project_id",project_id);
                startActivity(intent);
            }
        });
    }

    void GetProject() {
        ProjectViewModel projectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        projectViewModel.GetProject(project_id);
        projectViewModel.projectMutableLiveData.observe(this, new Observer<Project>() {
            @Override
            public void onChanged(Project project) {
                if(MainActivity.MyRole.equals("client")){
                    binding.btnPlaceBid.setVisibility(View.GONE);
                    binding.tvItsFree.setVisibility(View.GONE);
                    binding.bottom.setVisibility(View.GONE);
                }
                Data data = project.getData();
                if(data.getUser().getId()== MainActivity.My_ID){
                    from_my_project=1;
                    binding.btnPlaceBid.setVisibility(View.GONE);
                }
                GetBids();
                project_title = data.getTitle();
                Project_currency = data.getCurrency();
                Project_type = data.getPayType();
                binding.tvProjectTitle.setText(data.getTitle());
                binding.tvProjectDeadLine.setText("Closed in " + data.getTimeLeft());
                binding.tvDesc.setText(data.getDescription());
                binding.tvProjectCurrency.setText("Project Budget (" + data.getCurrency() + ")");
                binding.tvProjectBudget.setText("$" + data.getPriceFrom() + " - " + "$" + data.getPriceTo());
                binding.tvBidsNumber.setText(data.getBidsCount() + "");
                binding.tvArea.setText(data.getAddress() + "");
                binding.tvBidsNumber2.setText("Total Bids (" + data.getBidsCount() + ")");
                binding.rtEmp.setRating(data.getUser().getRate().floatValue());
                Picasso.get().load(data.getUser().getImage()).error(R.mipmap.ic_launcher).into(binding.imagePp);
                binding.tvSeeMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ProjectActivity.this, BidsListActivity.class);
                        intent.putExtra("from_my_project", from_my_project);
                        startActivity(intent);
                    };
                });
                binding.imagePp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ProjectActivity.this, ShowProfileActivity.class);
                        intent.putExtra("user_id",data.getUser().getId());
                        intent.putExtra("Currency",data.getCurrency());
                        startActivity(intent);
                    }
                });
            }

        });

        projectViewModel.error.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("hesham",s);
            }
        });
    }

    void GetBids() {
        BidsList = null;
        ProjectViewModel projectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        projectViewModel.GetBids(project_id);
        projectViewModel.bidsMutableLiveData.observe(this, new Observer<Bids>() {
            @Override
            public void onChanged(Bids bids) {
                BidsAdapter bidsAdapter = new BidsAdapter();
                BidsList = bids.getData();
                binding.tvSeeMore.setVisibility(View.VISIBLE);
                bidsAdapter.setBidsList(BidsList);
                binding.rvBids.setAdapter(bidsAdapter);
                binding.rvBids.addItemDecoration(new DividerItemDecoration(ProjectActivity.this, DividerItemDecoration.VERTICAL));
            }
        });
    }


    public class BidsAdapter extends RecyclerView.Adapter<BidsAdapter.viewholder> {
        List<com.youssef.fixit.Models.Bids.Datum> BidsList = new ArrayList<>();

        @NonNull
        @Override
        public com.youssef.fixit.UI.Projects.ProjectActivity.BidsAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bids_item, parent, false);
            return new com.youssef.fixit.UI.Projects.ProjectActivity.BidsAdapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull com.youssef.fixit.UI.Projects.ProjectActivity.BidsAdapter.viewholder holder, int position) {
            if (from_my_project == 1) {
                holder.ly.setVisibility(View.VISIBLE);
            }
            Datum bid = BidsList.get(position);
            if (bid.getUser().getImage() != null) {
                Picasso.get().load(bid.getUser().getImage()).error(R.mipmap.ic_launcher).into(holder.img_user_img);
            }
            holder.tv_user_name.setText(bid.getUser().getName());
            holder.rb.setRating(bid.getUser().getRate().floatValue());
            DecimalFormat df=new DecimalFormat("#.#");
            holder.tv_rb.setText(df.format(bid.getUser().getRate())+ "");
            holder.tv_reviews_numbers.setText("("+bid.getUser().getTotalReviews()+" reviews)");
            holder.tv_budget_time.setText("$ " + bid.getPrice() +" "+Project_currency+ " in " + bid.getTime());
            holder.tv_desc.setText(bid.getDescription());
            holder.btn_approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(ProjectActivity.this, CreateContractActivity.class);
                    intent.putExtra("project_id",bid.getProjectId());
                    intent.putExtra("prof_id",bid.getUser().getId());
                    startActivity(intent);
                }
            });

            holder.btn_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Makeroom(bid.getUser().getId());
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(ProjectActivity.this, ShowProfileActivity.class);
                    intent.putExtra("user_id",bid.getUser().getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            int size = 4;
            if (BidsList.size() < 4) {
                size = BidsList.size();
            }
            return size;
        }

        public void setBidsList(List<com.youssef.fixit.Models.Bids.Datum> bidsList) {
            BidsList = bidsList;
            notifyDataSetChanged();
        }

        public class viewholder extends RecyclerView.ViewHolder {
            ImageView img_user_img;
            RatingBar rb;
            TextView tv_user_name, tv_rb, tv_reviews_numbers, tv_budget_time, tv_desc;
            Button btn_approve, btn_chat;
            LinearLayout ly;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                img_user_img = itemView.findViewById(R.id.img_user_img);
                tv_rb = itemView.findViewById(R.id.tv_rb);
                tv_reviews_numbers = itemView.findViewById(R.id.tv_reviews_numbers);
                tv_user_name = itemView.findViewById(R.id.tv_user_name);
                tv_budget_time = itemView.findViewById(R.id.tv_budget_time);
                tv_desc = itemView.findViewById(R.id.tv_desc);
                rb = itemView.findViewById(R.id.rb);
                btn_approve = itemView.findViewById(R.id.btn_approve);
                btn_chat = itemView.findViewById(R.id.btn_chat);
                ly = itemView.findViewById(R.id.ly);
            }
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
                        Intent intent = new Intent(ProjectActivity.this, ChatActivity.class);
                        intent.putExtra("user_id", prof_id);
                        intent.putExtra("room_id", response.body().getData().getId());
                        startActivity(intent);
                    } else {
                        Toast.makeText(ProjectActivity.this, response.message() + "", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProjectActivity.this, response.message() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SendMassage> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(ProjectActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void InitDialog() {
        dialog = new ProgressDialog(ProjectActivity.this);
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);
    }
}