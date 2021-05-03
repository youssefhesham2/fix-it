package com.youssef.fixit.Jobs;

import com.youssef.fixit.Models.Jobs.Jobs;

import retrofit2.Response;

interface IAPIListener {
    void onSuccess(Response<Jobs> response);

    void onError(Response<Jobs> response);

    void onFailure(Throwable t);
}
