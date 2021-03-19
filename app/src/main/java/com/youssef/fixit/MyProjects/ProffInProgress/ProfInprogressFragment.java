package com.youssef.fixit.MyProjects.ProffInProgress;

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
import com.youssef.fixit.databinding.FragmentProfInprogressBinding;

import java.util.List;

public class ProfInprogressFragment extends Fragment {
    FragmentProfInprogressBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prof_inprogress, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GetProfInprogress();
    }

    private void GetProfInprogress() {
        MyProjectViewModel myProjectViewModel = ViewModelProviders.of(this).get(MyProjectViewModel.class);
        myProjectViewModel.GetMyBids("in progress");
        PprofInProgressAdapter adapter = new PprofInProgressAdapter();
        binding.rvOpenProject.setAdapter(adapter);
        myProjectViewModel.MyBidsMutableLiveData.observe(this, new Observer<List<Datum>>() {
            @Override
            public void onChanged(List<Datum> jobs) {
                binding.tvLoading.setVisibility(View.GONE);
                binding.ltNoData.setVisibility(View.GONE);
                adapter.setJobsList(jobs);
            }
        });
        myProjectViewModel.error.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvLoading.setVisibility(View.GONE);
                binding.internetImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_no_wifi));
                binding.tvNotFound.setText(s);
            }
        });
        myProjectViewModel.NoData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvLoading.setVisibility(View.GONE);
            }
        });
    }
}