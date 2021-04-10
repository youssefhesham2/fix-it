package com.youssef.fixit.MyProjects.MyOpenProject;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.youssef.fixit.Models.Jobs.Datum;
import com.youssef.fixit.R;
import com.youssef.fixit.MyProjects.MyProjectViewModel;
import com.youssef.fixit.databinding.FragmentOpenProjectBinding;

import java.util.List;


public class OpenProjectFragment extends Fragment {
    FragmentOpenProjectBinding binding;
    public static int from_my_project = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_open_project, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitGetMyOpenProject();
    }

    private void InitGetMyOpenProject() {
        MyProjectViewModel viewModel = ViewModelProviders.of(this).get(MyProjectViewModel.class);
        viewModel.GetOpenoeProjects("pending");
        MyOpenProjectAdapter openprojects = new MyOpenProjectAdapter();
        binding.rvOpenProject.setAdapter(openprojects);
        viewModel.myprojectMutableLiveData.observe(this, new Observer<List<Datum>>() {
            @Override
            public void onChanged(List<Datum> List) {
                binding.tvLoading.setVisibility(View.GONE);
                binding.ltNoData.setVisibility(View.GONE);
                openprojects.setOpenProjectList(List);
            }
        });
        viewModel.error.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvLoading.setVisibility(View.GONE);
                binding.tvNotFound.setText("404 Not Found!");
                binding.internetImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_no_wifi));
            }
        });
        viewModel.NoData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvLoading.setVisibility(View.GONE);
            }
        });
        binding.btnPostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomnav);
               bottomNavigationView.setSelectedItemId(R.id.add_work);
            }
        });
    }
}