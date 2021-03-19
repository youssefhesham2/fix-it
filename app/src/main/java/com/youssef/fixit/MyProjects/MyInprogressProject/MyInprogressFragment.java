package com.youssef.fixit.MyProjects.MyInprogressProject;

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

import com.youssef.fixit.Models.Jobs.Datum;
import com.youssef.fixit.R;
import com.youssef.fixit.MyProjects.MyProjectViewModel;
import com.youssef.fixit.databinding.FragmentMyInprogressBinding;

import java.util.List;

public class MyInprogressFragment extends Fragment {
    FragmentMyInprogressBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_inprogress, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitGetMyOpenProject();
    }

    private void InitGetMyOpenProject() {
        MyProjectViewModel viewModel = ViewModelProviders.of(this).get(MyProjectViewModel.class);
        viewModel.GetOpenoeProjects("in progress");
        MyInprogressAdapter myInprogressAdapter = new MyInprogressAdapter();
        binding.rvOpenProject.setAdapter(myInprogressAdapter);
        viewModel.myprojectMutableLiveData.observe(this, new Observer<List<Datum>>() {
            @Override
            public void onChanged(List<Datum> List) {
                binding.ltNoData.setVisibility(View.GONE);
                binding.tvLoading.setVisibility(View.GONE);
                myInprogressAdapter.setOpenProjectList(List);
            }
        });
        viewModel.error.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvLoading.setVisibility(View.GONE);
                binding.tvNotFound.setText(s);
                binding.internetImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_no_wifi));
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