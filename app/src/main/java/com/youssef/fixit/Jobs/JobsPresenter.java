package com.youssef.fixit.Jobs;

import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Jobs.Jobs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class JobsPresenter {
    JobsView view;

    public JobsPresenter(JobsView view) {
        this.view = view;
    }

    void GetJobs(String SearchTitle) {
        RetrofitClient.getInstance().GetJobs(SearchTitle).enqueue(new Callback<Jobs>() {
            @Override
            public void onResponse(Call<Jobs> call, Response<Jobs> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getData().getData() != null) {
                        if (response.body().getData().getData().size() > 0) {
                            view.OnGetJobs(response.body());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Jobs> call, Throwable t) {
                view.OnFailure(t.getMessage());
            }
        });
    }

    void JobSearch(CharSequence SearchTitle) {
        if (SearchTitle != null) {
            if (SearchTitle.length() > 0) {
                GetJobs(SearchTitle.toString());
            } else {
                GetJobs("");
            }
        }
    }
}
