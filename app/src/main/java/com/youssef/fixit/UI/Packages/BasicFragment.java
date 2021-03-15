package com.youssef.fixit.UI.Packages;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youssef.fixit.Models.Ads.Package.Datum;
import com.youssef.fixit.R;
import com.youssef.fixit.UI.PaymentTypes.Paypal.PaypalActivity;
import com.youssef.fixit.databinding.FragmentBasicBinding;

public class BasicFragment extends Fragment {
    int index;
    FragmentBasicBinding binding;

    public BasicFragment(int index) {
        this.index = index;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_basic, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Datum datum=SubscribePackageActivity.PackageList.get(index);
        binding.addTitle.setText("Fix it "+datum.getTitle());
        binding.addDetails.setText(datum.getDescription());
        binding.btnSubscribe.setText("Get "+datum.getTitle());
        binding.btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), PaypalActivity.class);
                intent.putExtra("ads_id",datum.getId());
                intent.putExtra("ads_price",datum.getPrice());
                intent.putExtra("ads_months",datum.getMonths());
             //   intent.putExtra("ads_currency",datum.getPrice());
                startActivity(intent);
            }
        });
    }
}