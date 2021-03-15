package com.youssef.fixit.UI.MyProjects.MyBids;

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
import android.widget.Toast;

import com.youssef.fixit.Models.Jobs.Datum;
import com.youssef.fixit.Models.Jobs.Jobs;
import com.youssef.fixit.R;
import com.youssef.fixit.UI.Jobs.JobsAdapter;
import com.youssef.fixit.UI.Jobs.JobsViewModel;
import com.youssef.fixit.UI.MyProjects.MyProjectViewModel;
import com.youssef.fixit.databinding.FragmentMyBidsBinding;

import java.util.List;

public class MyBidsFragment extends Fragment {
    // TODO: Rename and change types and number of parameters
    FragmentMyBidsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_bids, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GetMyBids();
    }
    private void GetMyBids() {
        MyProjectViewModel myProjectViewModel = ViewModelProviders.of(this).get(MyProjectViewModel.class);
        myProjectViewModel.GetMyBids("pending");
        MyBidsAdapter adapter = new MyBidsAdapter();
        binding.rvMyBids.setAdapter(adapter);
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