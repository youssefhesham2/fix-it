package com.youssef.fixit.Jobs;

import com.youssef.fixit.Models.Jobs.Jobs;

interface JobsView {
    void OnGetJobs(Jobs jobs);

    void OnFailure(String error);

}
