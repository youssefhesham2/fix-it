package com.youssef.fixit.UI.Chat;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.youssef.fixit.Data.RetrofitClient;
import com.youssef.fixit.Models.chat.Massage.Chat;
import com.youssef.fixit.Models.chat.Rooms;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomsViewModel extends ViewModel {
    public MutableLiveData<Rooms> RoomsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Chat> MassagesMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();

    public void GetRooms() {
        RetrofitClient.retrofitClient.Rooms().enqueue(new Callback<Rooms>() {
            @Override
            public void onResponse(Call<Rooms> call, Response<Rooms> response) {
                if (response.code() == 200) {
                    if (response.body().getData() != null) {
                        if (response.body().getData().size() > 0) {
                            RoomsMutableLiveData.setValue(response.body());
                        } else {
                            error.setValue(response.message());
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
            public void onFailure(Call<Rooms> call, Throwable t) {
                error.setValue(t.getMessage());
                Log.e("hesham", t.getMessage());
            }
        });
    }

    public void GetMassages(int user_id) {
        RetrofitClient.getInstance().Massage(user_id).enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                if (response.body().getData() != null) {
                    MassagesMutableLiveData.setValue(response.body());
                } else {
                    error.setValue(response.message());
                    Log.e("hesham", response.message() + "22");
                }

            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                error.setValue(t.getMessage());
                Log.e("hesham", t.getMessage());
            }
        });
    }
}
