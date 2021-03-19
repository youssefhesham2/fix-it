package com.youssef.fixit.Category;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Category.Category;
import com.youssef.fixit.Models.Category.SubCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradleViewModel extends ViewModel {
    public MutableLiveData<Category> CategoryMutableLiveData=new MutableLiveData<>();
    public MutableLiveData<List<SubCategory>> SubcategoryMutableLiveData=new MutableLiveData<>();
    //public MutableLiveData<List<>> GetAds=new MutableLiveData<>();
    MutableLiveData<String> error = new MutableLiveData<>();

    public void GetCategory(){
        RetrofitClient.getInstance().GetCategory().enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if(response.isSuccessful()&&response.body().getData()!=null){
                    if(response.body().getData().size()>0){
                        CategoryMutableLiveData.setValue(response.body());
                    }
                    else {
                        error.setValue(response.message());
                    }
                }
                else {
                    error.setValue(response.message());
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                error.setValue(t.getMessage());
            }
        });
    }

}
