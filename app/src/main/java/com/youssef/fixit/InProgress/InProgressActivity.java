package com.youssef.fixit.InProgress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.youssef.fixit.Models.Contract.Contract;
import com.youssef.fixit.Models.Contract.Data;
import com.youssef.fixit.R;
import com.youssef.fixit.Contract.ContractScreenShotActivity;
import com.youssef.fixit.Contract.ContractViewModel;
import com.youssef.fixit.MainActivity.MainActivity;
import com.youssef.fixit.MyProjects.MyInprogressProject.MyInprogressAdapter;
import com.youssef.fixit.MyProjects.ProffInProgress.PprofInProgressAdapter;

import params.com.stepview.StatusViewScroller;

public class InProgressActivity extends AppCompatActivity {
    StatusViewScroller viewScroller;
    public static Data MyContract;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitViews();
        GetContract();
    }

    public void InitViews(){
        setContentView(R.layout.activity_in_progress);
        viewScroller = findViewById(R.id.step_view);
        Button download_contracr=findViewById(R.id.btn_download_contract);
        download_contracr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InProgressActivity.this, ContractScreenShotActivity.class));
            }
        });
    }

    public void Steps() {
        if(MainActivity.MyRole.equals("client")) {
            if (MyInprogressAdapter.jobs.getIsPaid() == 0) {
                viewScroller.getStatusView().setCurrentCount(1);
                if (MainActivity.MyRole.equals("client")) {
                    ReplaceFragment(new FirstStepFragment());
                } else {
                    ReplaceFragment(new waitFragment());
                }
            } else if (MyInprogressAdapter.jobs.getUnpaidMilestonesCount() > 0) {
                viewScroller.getStatusView().setCurrentCount(2);
                ReplaceFragment(new SeconedStepFragment());
            } else if (MyInprogressAdapter.jobs.getHaveRate() == null) {
                viewScroller.getStatusView().setCurrentCount(3);
                ReplaceFragment(new ThirdStepFragment());
            } else {
                viewScroller.getStatusView().setCurrentCount(4);
                ReplaceFragment(new FourthFragment());
            }
        }
        else {
            if (PprofInProgressAdapter.jobs.getIsPaid() == 0) {
                viewScroller.getStatusView().setCurrentCount(1);
                if (MainActivity.MyRole.equals("client")) {
                    ReplaceFragment(new FirstStepFragment());
                } else {
                    ReplaceFragment(new waitFragment());
                }
            } else if (PprofInProgressAdapter.jobs.getUnpaidMilestonesCount() > 0) {
                viewScroller.getStatusView().setCurrentCount(2);
                ReplaceFragment(new SeconedStepFragment());
            } else if (PprofInProgressAdapter.jobs.getHaveRate() == null) {
                viewScroller.getStatusView().setCurrentCount(3);
                ReplaceFragment(new ThirdStepFragment());
            } else {
                viewScroller.getStatusView().setCurrentCount(4);
                ReplaceFragment(new FourthFragment());
            }
        }
    }

    public void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }


    public void GetContract() {
        ContractViewModel viewModel = ViewModelProviders.of(this).get(ContractViewModel.class);
        if(MainActivity.MyRole.equals("client")){
            viewModel.ShowContract(MyInprogressAdapter.jobs.getContract().getId());
        }
        else {
            viewModel.ShowContract(PprofInProgressAdapter.jobs.getContract().getId());
        }
        viewModel.contractMutableLiveData.observe(this, new Observer<Contract>() {
            @Override
            public void onChanged(Contract contract) {
                if (contract.getData() != null) {
                    MyContract = contract.getData();
                    Steps();
                    Toast.makeText(InProgressActivity.this, "done", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.error.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(InProgressActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}