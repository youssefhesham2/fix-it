package com.youssef.fixit.ShowProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.youssef.fixit.MainActivity.SplashScreen;
import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.ShowProfile.Category;
import com.youssef.fixit.Models.ShowProfile.Feedback;
import com.youssef.fixit.Models.ShowProfile.Image;
import com.youssef.fixit.Models.ShowProfile.ShowProfile;
import com.youssef.fixit.Models.ShowProfile.User;
import com.youssef.fixit.Models.chat.SendMassage.SendMassage;
import com.youssef.fixit.R;
import com.youssef.fixit.Chat.ChatActivity;
import com.youssef.fixit.Invoice.InvoiceActivity;
import com.youssef.fixit.databinding.ActivityShowProfileBinding;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowProfileActivity extends AppCompatActivity {
    ActivityShowProfileBinding binding;
    int UserId;
    List<Feedback> feedbackList;
    public ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        InitDialog();
        InitTabLayout();
        InitGeData();
        InitViews();
    }

    private void InitTabLayout() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Profile"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Reviews"));
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equals("Reviews")) {
                    binding.tvFeddback.setVisibility(View.VISIBLE);
                    binding.ltContainer.setVisibility(View.GONE);
                    InitGetFeedback();
                } else {
                    binding.tvFeddback.setVisibility(View.GONE);
                    binding.rvReviews.setVisibility(View.GONE);
                    binding.ltContainer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void InitGeData() {
        UserId = getIntent().getIntExtra("user_id", 0);
        ShowProfileViewModel showProfileViewModel = ViewModelProviders.of(this).get(ShowProfileViewModel.class);
        showProfileViewModel.ShowProfile(UserId);
        showProfileViewModel.showProfileMutableLiveData.observe(this, new Observer<ShowProfile>() {
            @Override
            public void onChanged(ShowProfile showProfile) {
                if (showProfile.getData() != null) {
                    User user = showProfile.getData().getUser();
                    if (user.getImage() != null) {
                        Picasso.get().load(user.getImage()).error(R.mipmap.ic_launcher).into(binding.imgPp);
                    }
                    binding.tvName.setText(user.getName());
                    binding.tvMail.setText(user.getEmail());
                    binding.tvAbout.setText(user.getDetails() + "");
                    binding.tvPhone.setText(user.getPhone());
                    binding.rb.setRating(user.getRate());

                    if (user.getImages() != null) {
                        if (user.getImages().size() > 0) {
                            binding.imgPortfolio.setVisibility(View.GONE);
                            binding.tvPortfolio.setVisibility(View.GONE);
                            binding.rvPortfolioSlider.setVisibility(View.VISIBLE);
                            InitViewRv(user.getImages());
                        }
                    }

                    if (user.getCategories() != null) {
                        if (user.getCategories().size() > 0) {
                            binding.rvMyCategory.setVisibility(View.VISIBLE);
                            binding.tvHaveCategory.setVisibility(View.GONE);
                            ShowProfileActivity.CategoryAdapter adapter = new ShowProfileActivity.CategoryAdapter(user.getCategories());
                            binding.rvMyCategory.setAdapter(adapter);
                        }
                    }

                    feedbackList = showProfile.getData().getFeedback();
                }
            }
        });
        showProfileViewModel.error.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvError.setVisibility(View.VISIBLE);
                binding.main.setVisibility(View.GONE);
            }
        });
    }

    private void InitViews() {
        if (SplashScreen.MyRole.equals("client")) {
            binding.btnChat.setVisibility(View.VISIBLE);
            //binding.btnHireThis.setVisibility(View.VISIBLE);
        } else {
            binding.btnInvoice.setVisibility(View.VISIBLE);
        }
        binding.btnInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowProfileActivity.this, InvoiceActivity.class);
                intent.putExtra("client_id", UserId);
                startActivity(intent);
            }
        });
        binding.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Makeroom(UserId);
            }
        });
    }

    private void InitGetFeedback() {
        if (feedbackList != null) {
            if (feedbackList.size() > 0) {
                Toast.makeText(this, "000", Toast.LENGTH_SHORT).show();
                binding.tvFeddback.setVisibility(View.GONE);
                binding.rvReviews.setVisibility(View.VISIBLE);
                FeedbackAdapter adapter = new FeedbackAdapter(feedbackList);
                binding.rvReviews.setAdapter(adapter);
            }
        }
    }

    class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {
        List<Category> categoryList;

        public CategoryAdapter(List<Category> categoryList) {
            this.categoryList = categoryList;
        }

        @NonNull
        @Override
        public CategoryAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myskills_item, parent, false);
            return new ShowProfileActivity.CategoryAdapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShowProfileActivity.CategoryAdapter.viewholder holder, int position) {
            holder.category_title.setText(" -" + categoryList.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return categoryList.size();
        }

        class viewholder extends RecyclerView.ViewHolder {
            TextView category_title;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                category_title = itemView.findViewById(R.id.tv_category_name);
            }
        }
    }

    private void InitViewRv(List<com.youssef.fixit.Models.ShowProfile.Image> images) {
        ShowProfileActivity.ProtoflioSliderAdapter protoflioSliderAdapter = new ShowProfileActivity.ProtoflioSliderAdapter(images);
        binding.rvPortfolioSlider.setAdapter(protoflioSliderAdapter);
    }

    class ProtoflioSliderAdapter extends RecyclerView.Adapter<ShowProfileActivity.ProtoflioSliderAdapter.viewholder> {
        List<Image> images;

        public ProtoflioSliderAdapter(List<Image> images) {
            this.images = images;
        }

        @NonNull
        @Override
        public ShowProfileActivity.ProtoflioSliderAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.protoflio_slider_item, parent, false);
            return new ShowProfileActivity.ProtoflioSliderAdapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShowProfileActivity.ProtoflioSliderAdapter.viewholder holder, int position) {
            Picasso.get().load(images.get(position).getImage()).error(R.mipmap.ic_launcher).into(holder.img);
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        class viewholder extends RecyclerView.ViewHolder {
            ImageView img;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.img);
            }
        }
    }

    class FeedbackAdapter extends RecyclerView.Adapter<ShowProfileActivity.FeedbackAdapter.viewholder> {
        List<Feedback> feedbacks;

        public FeedbackAdapter(List<Feedback> feedbacks) {
            this.feedbacks = feedbacks;
        }

        @NonNull
        @Override
        public ShowProfileActivity.FeedbackAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item, parent, false);
            return new ShowProfileActivity.FeedbackAdapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShowProfileActivity.FeedbackAdapter.viewholder holder, int position) {
            Feedback feedback = feedbackList.get(position);
            holder.tv_user_name.setText(feedback.getName());
            holder.tv_feddback.setText(feedback.getComment());
            holder.rt.setRating(Float.parseFloat(feedback.getRate()));
            // Picasso.get().load(feedback)
        }

        @Override
        public int getItemCount() {
            return feedbacks.size();
        }

        class viewholder extends RecyclerView.ViewHolder {
            CircleImageView c_img;
            TextView tv_user_name, tv_feddback;
            RatingBar rt;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                c_img = itemView.findViewById(R.id.c_img);
                tv_user_name = itemView.findViewById(R.id.tv_user_name);
                tv_feddback = itemView.findViewById(R.id.tv_feddback);
                rt = itemView.findViewById(R.id.rt);
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
                        Intent intent = new Intent(ShowProfileActivity.this, ChatActivity.class);
                        intent.putExtra("user_id", prof_id);
                        intent.putExtra("room_id", response.body().getData().getId());
                        startActivity(intent);
                    } else {
                        Toast.makeText(ShowProfileActivity.this, response.message() + "", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ShowProfileActivity.this, response.message() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SendMassage> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(ShowProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void InitDialog() {
        dialog = new ProgressDialog(ShowProfileActivity.this);
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);
    }

}