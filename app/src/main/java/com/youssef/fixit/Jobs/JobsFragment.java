package com.youssef.fixit.Jobs;

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
import android.widget.Toast;

import com.youssef.fixit.Models.Jobs.Jobs;
import com.youssef.fixit.R;
import com.youssef.fixit.databinding.FragmentJobsBinding;

public class JobsFragment extends Fragment implements JobsView {
    FragmentJobsBinding binding;
    String search_title;
    JobsAdapter jobsAdapter;
    JobsPresenter jobsPresenter;

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

    private void InitJobs(String search_title_) {
        jobsPresenter = new JobsPresenter(this);
        jobsPresenter.GetJobs(search_title_);
        jobsAdapter = new JobsAdapter();
        binding.jobsRv.setAdapter(jobsAdapter);
    }

    private void JobSearch() {
        binding.etJobSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Toast.makeText(getContext(), charSequence.toString()+"", Toast.LENGTH_SHORT).show();
                jobsPresenter.JobSearch(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void OnGetJobs(Jobs jobs) {
        binding.loadGraph.setVisibility(View.GONE);
        jobsAdapter.setJobsList(jobs.getData().getData());
    }

    @Override
    public void OnFailure(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}