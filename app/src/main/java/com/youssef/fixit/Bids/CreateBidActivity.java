package com.youssef.fixit.Bids;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Bids.CreateBid;
import com.youssef.fixit.Projects.ProjectActivity;
import com.youssef.fixit.databinding.ActivityCreateBidBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBidActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    ActivityCreateBidBinding binding;
    String description,time,price;
    String time2="days";
    int project_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBidBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        InitDialog();
        InitViews();
    }

    void InitViews(){
        project_id=getIntent().getIntExtra("project_id",0);
        binding.tvProjectCurrency.setText(ProjectActivity.Project_currency);
        if(ProjectActivity.Project_type.equals("hourly")){
            binding.tvDeliveryIn.setText("Weekly limit");
            binding.tvDaysHourly.setText("Hours");
            binding.tvProjectCurrency.setText(ProjectActivity.Project_currency+" /hr");
            time2="hrs / weak";
        }
        else {
            binding.tvProjectCurrency.setText(ProjectActivity.Project_currency);
        }
        binding.btnCreateBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateBid();
            }
        });
    }
    private void CreateBid(){
        price=binding.edSalary.getText().toString();
        time=binding.edDaysHourly.getText().toString();
        description=binding.etDesBid.getText().toString();
        if(price.isEmpty()){
            Toast.makeText(CreateBidActivity.this, "price is requird", Toast.LENGTH_SHORT).show();
            binding.edSalary.requestFocus();
            return;
        }
        if(time.isEmpty()){
            Toast.makeText(CreateBidActivity.this, "time is requird", Toast.LENGTH_SHORT).show();
            binding.edDaysHourly.requestFocus();
            return;
        }
        if(description.isEmpty()){
            Toast.makeText(CreateBidActivity.this, "description is requird", Toast.LENGTH_SHORT).show();
            binding.etDesBid.requestFocus();
            return;
        }
        String endtime=time+" "+time2;
        dialog.show();
        RetrofitClient.getInstance().CreateBid(project_id,description,endtime,price).enqueue(new Callback<CreateBid>() {
            @Override
            public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                dialog.dismiss();
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        if(response.body().getData()!=null){
                            if (response.body().getData()!=null)
                            {
                                onBackPressed();
                            }
                        }
                        else {
                            Toast.makeText(CreateBidActivity.this,response.body().getMessage()+ "", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(CreateBidActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(CreateBidActivity.this, response.toString()+response.message()+"", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateBid> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(CreateBidActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    private void InitDialog() {
        dialog = new ProgressDialog(CreateBidActivity.this);
        dialog.setMessage("please wait...");
        dialog.setTitle("Create a Bid");
        dialog.setCancelable(false);
    }
}