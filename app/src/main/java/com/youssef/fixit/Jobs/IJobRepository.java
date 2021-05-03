package com.youssef.fixit.Jobs;

import com.youssef.fixit.Models.Jobs.Jobs;

interface IJobRepository {

    void getJobs(String Search, IAPIListener listener);

}