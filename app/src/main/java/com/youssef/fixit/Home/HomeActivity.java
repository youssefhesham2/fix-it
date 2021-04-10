package com.youssef.fixit.Home;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.youssef.fixit.SplashActivity.SplashScreen;
import com.youssef.fixit.R;
import com.youssef.fixit.Category.CategoryFragment;
import com.youssef.fixit.Chat.RoomsFragment;
import com.youssef.fixit.Jobs.JobsFragment;
import com.youssef.fixit.More.MoreFragment;
import com.youssef.fixit.MyProjects.MyProjectFragment;
import com.youssef.fixit.SearchProff.SearchProfFragment;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        InitBottomNavigation();
    }

    public void InitBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.my_profile:
                        menuItem.setChecked(true);
                        ReplaceFragment(new MoreFragment());
                        break;
                    case R.id.add_work:
                        if(SplashScreen.MyRole.equals("client")){
                            ReplaceFragment(new CategoryFragment());
                        }
                        else {
                            ReplaceFragment(new JobsFragment());
                        }
                        menuItem.setChecked(true);
                        break;
                    case R.id.my_projects:
                        menuItem.setChecked(true);
                        ReplaceFragment(new MyProjectFragment());
                        break;
                    case R.id.chat:
                        menuItem.setChecked(true);
                        ReplaceFragment(new RoomsFragment());
                        break;
                    case R.id.search:
                        menuItem.setChecked(true);
                        ReplaceFragment(new SearchProfFragment());
                        break;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.add_work);
    }

    public void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
//        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }
}