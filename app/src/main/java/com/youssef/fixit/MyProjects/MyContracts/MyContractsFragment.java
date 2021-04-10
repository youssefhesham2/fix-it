package com.youssef.fixit.MyProjects.MyContracts;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youssef.fixit.Models.Contract.Datum;
import com.youssef.fixit.R;
import com.youssef.fixit.MyProjects.MyProjectViewModel;
import com.youssef.fixit.databinding.FragmentMyContractsBinding;

import java.util.List;

public class MyContractsFragment extends Fragment {
    FragmentMyContractsBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_contracts, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GetMyContract();
    }

    void GetMyContract(){
        MyProjectViewModel viewModel = ViewModelProviders.of(this).get(MyProjectViewModel.class);
        viewModel.GetMyContract();

        MyContractAdapter adapter = new MyContractAdapter();
        binding.rvOpenProject.setAdapter(adapter);
        viewModel.MyContractListMutableLiveData.observe(this, new Observer<List<Datum>>() {
            @Override
            public void onChanged(List<Datum> data) {
                binding.tvLoading.setVisibility(View.GONE);
                binding.ltNoData.setVisibility(View.GONE);
                adapter.setOpenProjectList(data);
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