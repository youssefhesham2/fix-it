package com.youssef.fixit.Jobs;

import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Jobs.Jobs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class JobsPresenter implements IAPIListener {
    JobsView view;
    IJobRepository jobRepository;

    public JobsPresenter(JobsView view, IJobRepository jobRepository) {
        this.view = view;
        this.jobRepository = jobRepository;
    }

    void GetJobs(String searchTitle) {
        jobRepository.getJobs(searchTitle, this);
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

    @Override
    public void onSuccess(Response<Jobs> response) {
        view.displayJobsData(response.body());
    }

    @Override
    public void onError(Response<Jobs> response) {
        view.showMessage(response.message());
    }

    @Override
    public void onFailure(Throwable t) {
        view.showMessage(t.getMessage());
    }

    void onDestroy() {
        view = null;
        jobRepository = null;
    }
}
