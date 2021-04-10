package com.youssef.fixit.SearchProff;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.SearchProff.SearchProff;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchViewModel  extends ViewModel {
    public MutableLiveData<SearchProff> searchProffMutableLiveData=new MutableLiveData<>();
    public MutableLiveData<String> error=new MutableLiveData<>();

    public void GetSearchProff(String search,int caegory_id) {
        RetrofitClient.getInstance().SearchProff(search,caegory_id).enqueue(new Callback<SearchProff>() {
            @Override
            public void onResponse(Call<SearchProff> call, Response<SearchProff> response) {
                if(response.isSuccessful()){
                    if(response.body().getData()!=null){
                        searchProffMutableLiveData.setValue(response.body());
                    }
                    else {
                        error.setValue(response.message()+"000");
                    }
                }
                else {
                    error.setValue(response.toString()+"111");
                }
            }

            @Override
            public void onFailure(Call<SearchProff> call, Throwable t) {
                error.setValue(t.getMessage()+"44445");
            }
        });
    }
}
