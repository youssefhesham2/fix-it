package com.youssef.fixit.Jobs;

import com.youssef.fixit.Models.Error;
import com.youssef.fixit.Models.Jobs.Jobs;

class JobsPresenter implements DataProviderListener<Jobs> {
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
    public void onSuccess(Jobs result) {
        view.displayJobsData(result);
    }

    @Override
    public void onError(Error error) {
        view.showMessage(error.getMessage());
    }

    void onDestroy() {
        view = null;
        jobRepository = null;
    }
}
