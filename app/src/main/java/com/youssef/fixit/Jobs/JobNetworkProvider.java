package com.youssef.fixit.Jobs;

import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Error;
import com.youssef.fixit.Models.Jobs.Jobs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobNetworkProvider implements IJobRepository {
    Error error;

    public JobNetworkProvider() {
        error = new Error();
    }

    @Override
    public void getJobs(String searchTitle, DataProviderListener listener) {
        RetrofitClient.getInstance().GetJobs(searchTitle).enqueue(new Callback<Jobs>() {
            @Override
            public void onResponse(Call<Jobs> call, Response<Jobs> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getData().getData() != null) {
                        if (response.body().getData().getData().size() > 0) {
                            listener.onSuccess(response);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Jobs> call, Throwable t) {
                error.setMessage(t.getMessage());
                listener.onError(error);
            }
        });
    }
}
