package com.youssef.fixit.Adds;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Ads.ShowAds;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAdsViewModel extends ViewModel {
    public MutableLiveData<ShowAds> HomeAdsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();

    public void GetHomeAds() {
        RetrofitClient.retrofitClient.HomeAds().enqueue(new Callback<ShowAds>() {
            @Override
            public void onResponse(Call<ShowAds> call, Response<ShowAds> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData().getImages() != null) {
                            if (response.body().getData().getImages().size() > 0) {
                                HomeAdsMutableLiveData.setValue(response.body());
                            } else {
                                error.setValue(response.body().getErrors().get(0) + "");
                            }
                        } else {
                            error.setValue(response.body().getErrors().get(0) + "");
                        }
                    } else {
                        error.setValue(response.body().getErrors().get(0) + "");

                    }
                } else {
                    error.setValue(response.body().getErrors().get(0) + "");
                }
            }

            @Override
            public void onFailure(Call<ShowAds> call, Throwable t) {
                error.setValue(t.getMessage() + "");
            }
        });
    }

}
