package com.youssef.fixit.Jobs;

interface IJobRepository {

    void getJobs(String Search, DataProviderListener listener);

}