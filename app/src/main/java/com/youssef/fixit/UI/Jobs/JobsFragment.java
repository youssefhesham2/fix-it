package com.youssef.fixit.UI.Jobs;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youssef.fixit.Models.Jobs.Jobs;
import com.youssef.fixit.R;
import com.youssef.fixit.UI.Category.GradleViewModel;
import com.youssef.fixit.databinding.FragmentJobsBinding;

public class JobsFragment extends Fragment {
    FragmentJobsBinding binding;
    String search_title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_jobs, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitJobs(search_title);
        JobSearch();
    }

    private void InitJobs(String search_title_){
        JobsViewModel jobsViewModel = ViewModelProviders.of(this).get(JobsViewModel.class);
        jobsViewModel.GetJobs(search_title_);
        JobsAdapter jobsAdapter=new JobsAdapter();
        binding.jobsRv.setAdapter(jobsAdapter);
        jobsViewModel.jobsMutableLiveData.observe(this, new Observer<Jobs>() {
            @Override
            public void onChanged(Jobs jobs) {
                binding.loadGraph.setVisibility(View.GONE);
                jobsAdapter.setJobsList(jobs.getData().getData());
            }
        });
    }

    private void JobSearch(){
        binding.etJobSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence!=null){
                    if(charSequence.length()>0){
                        search_title=charSequence.toString();
                        InitJobs(search_title);
                    }
                    else {
                        InitJobs("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}