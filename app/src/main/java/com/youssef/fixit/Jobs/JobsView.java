package com.youssef.fixit.Jobs;

import com.youssef.fixit.Models.Jobs.Jobs;

interface JobsView {
    void displayJobsData(Jobs jobs);

    void showMessage(String error);

}
