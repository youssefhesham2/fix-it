package com.youssef.fixit.UI.Packages;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.youssef.fixit.Data.RetrofitClient;
import com.youssef.fixit.Models.Ads.Package.Datum;
import com.youssef.fixit.Models.Ads.Package.Package;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageViewModel extends androidx.lifecycle.ViewModel {
    public MutableLiveData<List<Datum>> listMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();

    public void GetPackage(){
        RetrofitClient.getInstance().GetPackage().enqueue(new Callback<Package>() {
            @Override
            public void onResponse(Call<Package> call, Response<Package> response) {
                if (response.code() == 200) {
                    if (response.body().getData() != null) {
                        if (response.body().getData().size() > 0) {
                            listMutableLiveData.setValue(response.body().getData());
                        } else {
                            Log.e("hesham", response.message() + "33");
                        }
                    } else {
                        error.setValue(response.message());
                        Log.e("hesham", response.message() + "22");
                    }
                } else {
                    error.setValue(response.message());
                    Log.e("hesham", response.code() + response.toString() + response.message() + "111");
                }
            }

            @Override
            public void onFailure(Call<Package> call, Throwable t) {
                error.setValue(t.getMessage());
                Log.e("hesham", t.getMessage());
            }
        });
    }
}
