package com.youssef.fixit.UI.MyProjects;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.youssef.fixit.Data.RetrofitClient;
import com.youssef.fixit.Models.Contract.Contract;
import com.youssef.fixit.Models.Contract.Data;
import com.youssef.fixit.Models.Contract.MyContract;
import com.youssef.fixit.Models.InVoice.MyInvoice;
import com.youssef.fixit.Models.Jobs.Datum;
import com.youssef.fixit.Models.MyBids.MyBids;
import com.youssef.fixit.Models.MyProjects.MyProject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProjectViewModel extends androidx.lifecycle.ViewModel {
    public MutableLiveData<List<Datum>> myprojectMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<Datum>> MyBidsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<Datum>> MyRequestsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<com.youssef.fixit.Models.Contract.Datum>> MyContractListMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<com.youssef.fixit.Models.InVoice.Datum>> MyInvoice = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();
    public MutableLiveData<String> NoData = new MutableLiveData<>();

    public void GetOpenoeProjects(String status) {
        RetrofitClient.getInstance().MyOpenProjects(status).enqueue(new Callback<MyProject>() {
            @Override
            public void onResponse(Call<MyProject> call, Response<MyProject> response) {
                if (response.code() == 200) {
                    if (response.body().getData() != null) {
                        if (response.body().getData().size() > 0) {
                            myprojectMutableLiveData.setValue(response.body().getData());
                        } else {
                            NoData.setValue("No Data Found");
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
            public void onFailure(Call<MyProject> call, Throwable t) {
                error.setValue(t.getMessage());
                Log.e("hesham", t.getMessage());
            }
        });
    }

    public void GetMyBids(String status) {
        RetrofitClient.getInstance().GetMyBids(status).enqueue(new Callback<MyBids>() {
            @Override
            public void onResponse(Call<MyBids> call, Response<MyBids> response) {
                if (response.code() == 200) {
                    if (response.body().getData() != null) {
                        if (response.body().getData().size() > 0) {
                            MyBidsMutableLiveData.setValue(response.body().getData());
                        } else {
                            NoData.setValue("No Data Found");
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
            public void onFailure(Call<MyBids> call, Throwable t) {
                error.setValue(t.getMessage());
                Log.e("hesham", t.getMessage());
            }
        });
    }

    public void GetMyContract(){
    RetrofitClient.getInstance().MyContract().enqueue(new Callback<com.youssef.fixit.Models.Contract.MyContract>() {
        @Override
        public void onResponse(Call<com.youssef.fixit.Models.Contract.MyContract> call, Response<com.youssef.fixit.Models.Contract.MyContract> response) {
            if (response.code() == 200) {
                if (response.body().getData() != null) {
                    if (response.body().getData().size() >0) {
                        MyContractListMutableLiveData.setValue(response.body().getData());
                        Log.e("hesham","001");
                    } else {
                        NoData.setValue("No Data Found");
                        Log.e("hesham", response.message() + "33333");
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
        public void onFailure(Call<com.youssef.fixit.Models.Contract.MyContract> call, Throwable t) {
            error.setValue(t.getMessage());
        }
    });
    }

    public void GetMyRequests(String status) {
        RetrofitClient.getInstance().GetMyRequests(status).enqueue(new Callback<MyBids>() {
            @Override
            public void onResponse(Call<MyBids> call, Response<MyBids> response) {
                if (response.code() == 200) {
                    if (response.body().getData() != null) {
                        if (response.body().getData().size() > 0) {
                            Log.e("hesham", response.message() + "334444");
                            MyRequestsMutableLiveData.setValue(response.body().getData());
                        } else {
                            NoData.setValue("No Data Found");
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
            public void onFailure(Call<MyBids> call, Throwable t) {
                error.setValue(t.getMessage());
                Log.e("hesham", t.getMessage());
            }
        });
    }

    public void MyInVoice(){
        RetrofitClient.getInstance().MyInvoice().enqueue(new Callback<com.youssef.fixit.Models.InVoice.MyInvoice>() {
            @Override
            public void onResponse(Call<com.youssef.fixit.Models.InVoice.MyInvoice> call, Response<com.youssef.fixit.Models.InVoice.MyInvoice> response) {
                if (response.code() == 200) {
                    if (response.body().getData() != null) {
                        if (response.body().getData().size() > 0) {
                            MyInvoice.setValue(response.body().getData());
                        } else {
                            NoData.setValue("No Data Found");
                            Log.e("hesham", response.body().getErrors() + "33");
                        }
                    } else {
                        error.setValue(response.message());
                        Log.e("hesham", response.body().getErrors() + "22");
                    }
                } else {
                    error.setValue(response.message());
                    Log.e("hesham", response.code() + response.body().getErrors() + response.message() + "111");
                }
            }

            @Override
            public void onFailure(Call<com.youssef.fixit.Models.InVoice.MyInvoice> call, Throwable t) {
                error.setValue(t.getMessage());
                Log.e("hesham", t.getMessage());
            }
        });
    }
}