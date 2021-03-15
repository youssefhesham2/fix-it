package com.youssef.fixit.UI.Bids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.youssef.fixit.Models.ShowProfile.ShowProfile;
import com.youssef.fixit.Models.chat.SendMassage.SendMassage;
import com.youssef.fixit.R;
import com.youssef.fixit.UI.Chat.ChatActivity;
import com.youssef.fixit.UI.Contract.CreateContractActivity;
import com.youssef.fixit.UI.Projects.ProjectActivity;
import com.youssef.fixit.UI.ShowProfile.ShowProfileActivity;
import com.youssef.fixit.databinding.ActivityBidsListBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BidsListActivity extends AppCompatActivity {
    ActivityBidsListBinding binding;
    public ProgressDialog dialog;
    int from_my_project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBidsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        InitDialog();
        from_my_project = getIntent().getIntExtra("from_my_project", 0);
        InitRv();
    }

    private void InitRv() {
        binding.toolbar.setTitle(ProjectActivity.project_title);
        BidsAdapter bidsAdapter = new BidsAdapter(ProjectActivity.BidsList);
        binding.rvBids.setAdapter(bidsAdapter);
        binding.rvBids.addItemDecoration(new DividerItemDecoration(BidsListActivity.this, DividerItemDecoration.VERTICAL));
    }

    public class BidsAdapter extends RecyclerView.Adapter<BidsListActivity.BidsAdapter.viewholder> {
        List<Datum> BidsList = new ArrayList<>();

        public BidsAdapter(List<Datum> bidsList) {
            BidsList = bidsList;
        }

        @NonNull
        @Override
        public com.youssef.fixit.UI.Bids.BidsListActivity.BidsAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bids_item, parent, false);
            return new com.youssef.fixit.UI.Bids.BidsListActivity.BidsAdapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull com.youssef.fixit.UI.Bids.BidsListActivity.BidsAdapter.viewholder holder, int position) {
            if (from_my_project == 1) {
                holder.ly.setVisibility(View.VISIBLE);
            }
            Datum bid = BidsList.get(position);
            if (bid.getUser().getImage() != null) {
                Picasso.get().load(bid.getUser().getImage()).into(holder.img_user_img);
            }
            holder.tv_user_name.setText(bid.getUser().getName());
            holder.rb.setRating(bid.getUser().getRate().floatValue());
            DecimalFormat df = new DecimalFormat("#.#");
            holder.tv_rb.setText(df.format(bid.getUser().getRate()) + "");
            holder.tv_reviews_numbers.setText("(" + bid.getUser().getTotalReviews() + " reviews)");
            holder.tv_budget_time.setText("$ " + bid.getPrice() + " " + ProjectActivity.Project_currency + " in " + bid.getTime());
            holder.tv_desc.setText(bid.getDescription());
            holder.btn_approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(BidsListActivity.this, CreateContractActivity.class);
                    intent.putExtra("project_id", bid.getProjectId());
                    intent.putExtra("prof_id", bid.getUser().getId());
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
                    Intent intent = new Intent(BidsListActivity.this, ShowProfileActivity.class);
                    intent.putExtra("user_id", bid.getUser().getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return BidsList.size();
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
                        Intent intent = new Intent(BidsListActivity.this, ChatActivity.class);
                        intent.putExtra("user_id", prof_id);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BidsListActivity.this, response.message() + "", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BidsListActivity.this, response.message() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SendMassage> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(BidsListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void InitDialog() {
        dialog = new ProgressDialog(BidsListActivity.this);
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);
    }
}