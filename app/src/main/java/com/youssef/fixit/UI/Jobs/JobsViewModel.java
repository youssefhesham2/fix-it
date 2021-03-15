package com.youssef.fixit.UI.Jobs;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.youssef.fixit.Data.RetrofitClient;
import com.youssef.fixit.Models.Category.Category;
import com.youssef.fixit.Models.Jobs.Jobs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobsViewModel extends ViewModel {
    public MutableLiveData<Jobs> jobsMutableLiveData=new MutableLiveData<>();
    public void GetJobs(String search_title){
        RetrofitClient.getInstance().GetJobs(search_title).enqueue(new Callback<Jobs>() {
            @Override
            public void onResponse(Call<Jobs> call, Response<Jobs> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null&&response.body().getData().getData()!=null){
                        if(response.body().getData().getData().size()>0){
                            jobsMutableLiveData.setValue(response.body());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Jobs> call, Throwable t) {

            }
        });
    }
}
