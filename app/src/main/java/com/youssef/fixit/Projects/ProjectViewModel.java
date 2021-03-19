package com.youssef.fixit.Projects;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Bids.Bids;
import com.youssef.fixit.Models.Project.Project;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectViewModel extends ViewModel {
    public MutableLiveData<Project> projectMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Bids> bidsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();

    public void GetProject(int project_id) {
        RetrofitClient.getInstance().GetProject(project_id).enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            projectMutableLiveData.setValue(response.body());
                        }
                        else {
                            error.setValue(response.message());
                        }
                    }
                    else {
                        error.setValue(response.message());
                    }
                }
                else {
                    error.setValue(response.message());
                }
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                error.setValue(t.getMessage());
            }
        });
    }

    public void GetBids(int project_id) {
        RetrofitClient.getInstance().GetBids(project_id).enqueue(new Callback<Bids>() {
            @Override
            public void onResponse(Call<Bids> call, Response<Bids> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            if (response.body().getData().size() > 0) {
                                bidsMutableLiveData.setValue(response.body());
                            } else {
                                Log.e("youssefh", "jjjj");
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Bids> call, Throwable t) {
                Log.e("youssefh", "jjjj2");

            }
        });
    }
}
