package com.youssef.fixit.UI.ShowProfile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.youssef.fixit.Data.RetrofitClient;
import com.youssef.fixit.Models.ShowProfile.ShowProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowProfileViewModel extends ViewModel {
    public MutableLiveData<ShowProfile> showProfileMutableLiveData=new MutableLiveData<>();
    public MutableLiveData<ShowProfile> MyProfileMutableLiveData=new MutableLiveData<>();
    public MutableLiveData<String> error=new MutableLiveData<>();

    public void ShowProfile(int user_id){
        RetrofitClient.getInstance().Showprofile(user_id).enqueue(new Callback<ShowProfile>() {
            @Override
            public void onResponse(Call<ShowProfile> call, Response<ShowProfile> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        if (response.body().getData()!=null)
                        {
                            showProfileMutableLiveData.setValue(response.body());
                        }
                        else {
                            error.setValue(response.message());
                        }
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
            public void onFailure(Call<ShowProfile> call, Throwable t) {
                error.setValue(t.getMessage());
            }
        });
    }
    public void MyProfile(){
        RetrofitClient.getInstance().MyProfile().enqueue(new Callback<ShowProfile>() {
            @Override
            public void onResponse(Call<ShowProfile> call, Response<ShowProfile> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        if (response.body().getData()!=null)
                        {
                            MyProfileMutableLiveData.setValue(response.body());
                        }
                        else {
                            error.setValue(response.message());
                        }
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
            public void onFailure(Call<ShowProfile> call, Throwable t) {
                error.setValue(t.getMessage());
            }
        });
    }
}
