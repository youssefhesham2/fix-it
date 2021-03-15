package com.youssef.fixit.UI.MyProjects.MyRequests;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youssef.fixit.Models.Contract.Data;
import com.youssef.fixit.Models.Jobs.Datum;
import com.youssef.fixit.R;
import com.youssef.fixit.UI.MyProjects.MyProjectViewModel;
import com.youssef.fixit.databinding.FragmentMyRequestsBinding;

import java.util.List;

public class MyRequestsFragment extends Fragment {
    FragmentMyRequestsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_requests, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitGetMyRequests();
    }

    private void InitGetMyRequests() {
        MyProjectViewModel viewModel = ViewModelProviders.of(this).get(MyProjectViewModel.class);
        viewModel.GetMyRequests("pending");
        MyRequestsAdapter myRequestsAdapter = new MyRequestsAdapter();
        binding.rvMyRequests222.setAdapter(myRequestsAdapter);
        viewModel.MyRequestsMutableLiveData.observe(this, new Observer<List<Datum>>() {
            @Override
            public void onChanged(List<Datum> List) {
                binding.ltNoData.setVisibility(View.GONE);
                binding.tvLoading.setVisibility(View.GONE);
                myRequestsAdapter.setMyRequestsList(List);
            }
        });
        viewModel.error.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvNotFound.setText(s);
                binding.internetImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_no_wifi));
                binding.tvLoading.setVisibility(View.GONE);
            }
        });
        viewModel.NoData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvLoading.setVisibility(View.GONE);
            }
        });
    }
}
