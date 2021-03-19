package com.youssef.fixit.Packages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.youssef.fixit.Models.Ads.Package.Datum;
import com.youssef.fixit.databinding.ActivitySubscribePackageBinding;

import java.util.ArrayList;
import java.util.List;

public class SubscribePackageActivity extends AppCompatActivity {
    ActivitySubscribePackageBinding binding;
    public static List<Datum> PackageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubscribePackageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        InitViewModel();
    }

    void InitViewModel(){
        PackageViewModel viewModel = ViewModelProviders.of(this).get(PackageViewModel.class);
        viewModel.GetPackage();
        viewModel.listMutableLiveData.observe(this, new Observer<List<Datum>>() {
            @Override
            public void onChanged(List<Datum> data) {
                PackageList=data;
                InitTabView();
            }
        });
        viewModel.error.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
    }

    private void InitTabView() {
        Viewpagadapter viewpagadapter = new Viewpagadapter(getSupportFragmentManager());
        for (int i=0;i<PackageList.size();i++){
            Datum datum=PackageList.get(i);
            viewpagadapter.addfragments(new BasicFragment(i), datum.getTitle());
        }
        binding.viewPager.setAdapter(viewpagadapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

    }

    class Viewpagadapter extends FragmentPagerAdapter {
        List<Fragment> fragments;
        List<String> title;

        public Viewpagadapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.title = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addfragments(Fragment fragment, String titlee) {
            fragments.add(fragment);
            title.add(titlee);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }
    }

}