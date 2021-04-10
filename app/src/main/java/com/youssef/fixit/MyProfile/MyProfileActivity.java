package com.youssef.fixit.MyProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.youssef.fixit.SplashActivity.SplashScreen;
import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Bids.CreateBid;
import com.youssef.fixit.Models.ShowProfile.Category;
import com.youssef.fixit.Models.ShowProfile.Feedback;
import com.youssef.fixit.Models.ShowProfile.Image;
import com.youssef.fixit.Models.ShowProfile.ShowProfile;
import com.youssef.fixit.Models.ShowProfile.User;
import com.youssef.fixit.R;
import com.youssef.fixit.ShowProfile.ShowProfileViewModel;
import com.youssef.fixit.databinding.ActivityMyProfileBinding;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends AppCompatActivity {
    ActivityMyProfileBinding binding;
    int UserId;
    List<Feedback> feedbackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        InitTabLayout();
        InitGeData();
        EditeProfile();
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
        UserId = SplashScreen.My_ID;//getIntent().getIntExtra("user_id", 0);
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
                            CategoryAdapter adapter = new CategoryAdapter(user.getCategories());
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
                Log.e("hesham", s);
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

    private void EditeProfile() {
        binding.tvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvSaveEditProfile.setVisibility(View.VISIBLE);
                binding.tvEditProfile.setVisibility(View.GONE);
            }
        });
        binding.tvSaveEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.tvName.getText().toString();
                String email = binding.tvMail.getText().toString();
                String phone = binding.tvPhone.getText().toString();
                String detials = binding.tvAbout.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(MyProfileActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.isEmpty()) {
                    Toast.makeText(MyProfileActivity.this, "Please enter your mail", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.isEmpty()) {
                    Toast.makeText(MyProfileActivity.this, "Please enter your phone", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (detials.isEmpty()) {
                    Toast.makeText(MyProfileActivity.this, "Please enter your about", Toast.LENGTH_SHORT).show();
                    return;
                }
                RetrofitClient.getInstance().EditeProfile(name,email,phone,detials).enqueue(new Callback<CreateBid>() {
                    @Override
                    public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                        if(response.isSuccessful()&&response.body()!=null){
                            if(response.body().getData()!=null){
                                if ((response.body().getSuccess()==true)){
                                    Toast.makeText(MyProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                    binding.tvEditProfile.setVisibility(View.VISIBLE);
                                    binding.tvSaveEditProfile.setVisibility(View.GONE);
                                    onBackPressed();
                                }
                                else {
                                    Toast.makeText(MyProfileActivity.this,response.body().getMessage()+ "", Toast.LENGTH_SHORT).show();

                                }
                            }
                            else {
                                Toast.makeText(MyProfileActivity.this,response.body().getMessage()+ "", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(MyProfileActivity.this,response.message()+ "", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateBid> call, Throwable t) {
                        Toast.makeText(MyProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {
        List<Category> categoryList;

        public CategoryAdapter(List<Category> categoryList) {
            this.categoryList = categoryList;
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myskills_item, parent, false);
            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
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
        ProtoflioSliderAdapter protoflioSliderAdapter = new ProtoflioSliderAdapter(images);
        binding.rvPortfolioSlider.setAdapter(protoflioSliderAdapter);
    }

    class ProtoflioSliderAdapter extends RecyclerView.Adapter<ProtoflioSliderAdapter.viewholder> {
        List<Image> images;

        public ProtoflioSliderAdapter(List<Image> images) {
            this.images = images;
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.protoflio_slider_item, parent, false);
            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
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

    class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.viewholder> {
        List<Feedback> feedbacks;

        public FeedbackAdapter(List<Feedback> feedbacks) {
            this.feedbacks = feedbacks;
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item, parent, false);
            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
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

}