package com.youssef.fixit.UI.MyProjects;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.youssef.fixit.R;
import com.youssef.fixit.UI.MainActivity.MainActivity;
import com.youssef.fixit.UI.MyProjects.MyBids.MyBidsFragment;
import com.youssef.fixit.UI.MyProjects.MyContracts.MyContractsFragment;
import com.youssef.fixit.UI.MyProjects.MyInprogressProject.MyInprogressFragment;
import com.youssef.fixit.UI.MyProjects.MyInvoice.MyInvoiceFragment;
import com.youssef.fixit.UI.MyProjects.MyOpenProject.OpenProjectFragment;
import com.youssef.fixit.UI.MyProjects.MyRequests.MyRequestsFragment;
import com.youssef.fixit.UI.MyProjects.ProffInProgress.ProfInprogressFragment;
import com.youssef.fixit.databinding.FragmentMyProjectBinding;

import java.util.ArrayList;
import java.util.List;

public class MyProjectFragment extends Fragment {
    FragmentMyProjectBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_project, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitTabView();
    }

    private void InitTabView() {
        if (MainActivity.MyRole.equals("client")) {
            Viewpagadapter viewpagadapter=new Viewpagadapter(getChildFragmentManager());
            viewpagadapter.addfragments(new OpenProjectFragment(),"Open");
            viewpagadapter.addfragments(new MyInprogressFragment(),"In progress");
            viewpagadapter.addfragments(new MyContractsFragment(),"My Contracts");
           // viewpagadapter.addfragments(new OpenProjectFragment(),"Past");

            binding.viewPager.setAdapter(viewpagadapter);
            binding.tabLayout.setupWithViewPager(binding.viewPager);
        }
        else {
            Viewpagadapter viewpagadapter=new Viewpagadapter(getChildFragmentManager());
            viewpagadapter.addfragments(new MyBidsFragment(),"My Bids");
            viewpagadapter.addfragments(new MyRequestsFragment(),"Requests");
            viewpagadapter.addfragments(new MyInvoiceFragment(),"My Invoice");
            viewpagadapter.addfragments(new ProfInprogressFragment(),"In Progress");

            binding.viewPager.setAdapter(viewpagadapter);
            binding.tabLayout.setupWithViewPager(binding.viewPager);
        }

    }

//    private void OnTabSelected() {
//        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                String tabselectedname = tab.getText().toString();
//                if (tabselectedname.equals("Open")) {
//                    ReplaceFragment(new OpenProjectFragment());
//                } else if (tabselectedname.equals("My Bids")) {
//                    ReplaceFragment(new MyBidsFragment());
//                } else if (tabselectedname.equals("Requests")) {
//                    ReplaceFragment(new MyRequestsFragment());
//                } else if (tabselectedname.equals("In progress")) {
//                    ReplaceFragment(new MyInprogressFragment());
//                }else if (tabselectedname.equals("My Invoice")) {
//                    ReplaceFragment(new MyInvoiceFragment());
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//    }

//    public void ReplaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getChildFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frame, fragment);
//        fragmentTransaction.addToBackStack("");
//        fragmentTransaction.commit();
//    }

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
        public void addfragments(Fragment fragment,String titlee){
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