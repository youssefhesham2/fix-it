package com.youssef.fixit.Jobs;

import com.youssef.fixit.Models.Error;
import com.youssef.fixit.Models.Jobs.Jobs;

import retrofit2.Response;

interface DataProviderListener<T> {
    void onSuccess(T result);

    void onError(Error error);
}
