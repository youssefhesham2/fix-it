package com.youssef.fixit.Contract;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Contract.Contract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractViewModel extends ViewModel {
    public MutableLiveData<Contract> contractMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();

    public void ShowContract(int contract_id) {
        RetrofitClient.getInstance().ShowContract(contract_id).enqueue(new Callback<Contract>() {
            @Override
            public void onResponse(Call<Contract> call, Response<Contract> response) {
                if (response.code() == 200) {
                    if (response.body().getData() != null) {
                        Log.e("hesham","done");
                        contractMutableLiveData.setValue(response.body());
                    } else {
                        error.setValue("No Data Found!");
                        Log.e("hesham",response.toString());
                    }
                } else {
                    error.setValue(response.body().getErrors());
                    Log.e("hesham", response.code() + response.toString() + response.message() + "111");
                }
            }

            @Override
            public void onFailure(Call<Contract> call, Throwable t) {
                error.setValue(t.getMessage());
                Log.e("hesham", t.getMessage());
            }
        });
    }

}
