package com.youssef.fixit.UI.More;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import com.youssef.fixit.Models.ShowProfile.ShowProfile;
import com.youssef.fixit.Models.ShowProfile.User;
import com.youssef.fixit.R;
import com.youssef.fixit.UI.Auth.AuthActivity;
import com.youssef.fixit.UI.MainActivity.MainActivity;
import com.youssef.fixit.UI.MyProfile.MyProfileActivity;
import com.youssef.fixit.UI.Packages.SubscribePackageActivity;
import com.youssef.fixit.UI.PaymentTypes.Paypal.PaypalActivity;
import com.youssef.fixit.UI.ShowProfile.ShowProfileViewModel;
import com.youssef.fixit.databinding.FragmentMoreBinding;

/**
 * More fragment
 */
public class MoreFragment extends Fragment {
    FragmentMoreBinding binding;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    private ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitDialog();
        InitViews();
        IntialSharedPreferences();
        GetMyProfile();
    }

    private void InitViews() {
        if (MainActivity.MyRole.equals("client")) {
            binding.LtMemberships.setVisibility(View.GONE);
            binding.viewMembership.setVisibility(View.GONE);
            binding.accountsetting.setVisibility(View.GONE);
        } else {
            binding.ltPostProject.setVisibility(View.GONE);
            binding.viewPostProject.setVisibility(View.GONE);
        }
        binding.ltPostProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomnav);
                bottomNavigationView.setSelectedItemId(R.id.add_work);
            }
        });
        binding.LtMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getContext(), MyProfileActivity.class));
            }
        });
        binding.LtMemberships.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getContext(), SubscribePackageActivity.class));
            }
        });

        binding.LtDepostMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getContext(), PaypalActivity.class));
            }
        });
//y2eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiYzJmZjAwMTJlY2Y3Njg1Y2U1Yjk2NTMwOGNhY2QzMWUyZGNjOGI0OWEyM2NhNjRlZDJmNTdkODMyNGVlMzc1MjA2ODk3YTRiNTMzZDIyM2QiLCJpYXQiOjE2MTM2OTMwNzUsIm5iZiI6MTYxMzY5MzA3NSwiZXhwIjoxNjQ1MjI5MDc1LCJzdWIiOiI0MiIsInNjb3BlcyI6W119.KOvDSTKUt49aurZa6NyOwM5pYdmI3-mtOYoZskUJPO6lT3Zc_RzUrNi_Y73yp4MtP6C0EBNNIfGxLBOb4SVMAdaQ1MDyelfaPy_EzmQLXclZ6TqFx9FZTMhfFiipnYSsg-4hy7wDU8Uvkbur0Gn0TYKSPygSkdzS2-KcXow_rhfkksoxQkZGQw6fQliTONIDMI4eE-9Pf8U06Bj2WfXUpPMDx-lMnZO8SMp9-inHz21W7HCdpoPxFySHmn5hYwwT_yqkHrIAmIVhg8wYMHc22g5KNgrUP5L5wAKtn-mwSjy1nYFwHZVMPXs0SIYrPBJQUfoBCDmLdS_5xnWqZW3bPmYoZ6RkwHnbuhRXk4eVJ1B4lbFP20jGWSjSw12ByH5Fy3692FQmfW-0DCRa5RXwyfbrthLTjwX0mJCnXPDvrtShcf3LwUSEeSP2qNUBKcEs_BoZFJtUqAXdZng7ukf7OmQpPqRu5lik0TEQY6wASc073sTXDGt-LYhm8SDbIqi73m73ZL0mkl4oI28I0d5ujNgVNo576xGHGnJnU0AwC8tJqgX_9xi6no-EMRiND750BPJBgIygYl9jE8jNwJNSoffaAMJNe2jcviEpi7h3m7yt5dIHXbt9DBve4HpEGALejBCfYqpTWIOq_-8bk7mIkBEDGZZmiPCglmWmuAhozUQ
        //2021-02-19 02:06:17.833 16961-16961/com.youssef.fixit E/hesham: y2eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiYWEyZGMzNDM2ZTExZDQ5Y2YwZjU4ZmU5Y2NjYzY4MzI3ODEyY2M5NWEyNzRjOTk4MzVlZDViN2RiYTBkNjAyMjAwYmZmZGFkMDhhMGE4ZDEiLCJpYXQiOjE2MTM2OTMxNzcsIm5iZiI6MTYxMzY5MzE3NywiZXhwIjoxNjQ1MjI5MTc3LCJzdWIiOiIyNCIsInNjb3BlcyI6W119.N61TFwM_eB5SHBFUiYhy4-mesXapU42O0DyrKvOteJVenjMJNt7zlQ00aPt0yBezGnKub5y0e6MmlYcS4tQHZmtReqSN7FftZ6iSUFQTA8_YPx3Qh5Rvt8Y9RL9Ysi7wyfSl-Vn7TlyjQKsgxCcVjoGUIPx3Qf7ORN-3UvkVcOxW6gO5IAYICyUwtn76NppUvPxfW-B-inlFw1sClWnA9S4WmWL27SfaDFSGm64ZiOzsLDTW3Ry3LDWKmuPTsTplBfNB1R06Yq9_wA63XAEDX6RpF2kRV3xgR5xVgTATfGq3XVIff-GM_wyx-ZjQFjICA7zjNF3G-WAe9M2GNNrWf5u32eOBcg8GogsWFGcO9E6NbtMwERmgpkq5xRnHvbuTskIdE-yoCwjDT6VAXh3pkO6cWBrIX2-WDeSGufYAccENMaU4nioaWcVDK1ix79a_3DcyvnAhVkMW2xKrXVr_PrLSo4HOl0Fi8OPsWDfqqGGGLIAQbm1d5WMPIAeXH_Et4BzxQgpf3hBj-hnLPUx1Dk-ssxK5L4pc0tVjvAY87_QHSU8hr0thzv7e9rBH0T9LapguWI0Q-VkxgclccFLoAx1zyIX2XmPXQSm2uKwEEANT9JMaQGKskfNZVoj_jwbKQc3t0zjA36Tavya9MNOfnFQEmDhuyBEg1wbYWRqVrIc
        //eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiYWEyZGMzNDM2ZTExZDQ5Y2YwZjU4ZmU5Y2NjYzY4MzI3ODEyY2M5NWEyNzRjOTk4MzVlZDViN2RiYTBkNjAyMjAwYmZmZGFkMDhhMGE4ZDEiLCJpYXQiOjE2MTM2OTMxNzcsIm5iZiI6MTYxMzY5MzE3NywiZXhwIjoxNjQ1MjI5MTc3LCJzdWIiOiIyNCIsInNjb3BlcyI6W119.N61TFwM_eB5SHBFUiYhy4-mesXapU42O0DyrKvOteJVenjMJNt7zlQ00aPt0yBezGnKub5y0e6MmlYcS4tQHZmtReqSN7FftZ6iSUFQTA8_YPx3Qh5Rvt8Y9RL9Ysi7wyfSl-Vn7TlyjQKsgxCcVjoGUIPx3Qf7ORN-3UvkVcOxW6gO5IAYICyUwtn76NppUvPxfW-B-inlFw1sClWnA9S4WmWL27SfaDFSGm64ZiOzsLDTW3Ry3LDWKmuPTsTplBfNB1R06Yq9_wA63XAEDX6RpF2kRV3xgR5xVgTATfGq3XVIff-GM_wyx-ZjQFjICA7zjNF3G-WAe9M2GNNrWf5u32eOBcg8GogsWFGcO9E6NbtMwERmgpkq5xRnHvbuTskIdE-yoCwjDT6VAXh3pkO6cWBrIX2-WDeSGufYAccENMaU4nioaWcVDK1ix79a_3DcyvnAhVkMW2xKrXVr_PrLSo4HOl0Fi8OPsWDfqqGGGLIAQbm1d5WMPIAeXH_Et4BzxQgpf3hBj-hnLPUx1Dk-ssxK5L4pc0tVjvAY87_QHSU8hr0thzv7e9rBH0T9LapguWI0Q-VkxgclccFLoAx1zyIX2XmPXQSm2uKwEEANT9JMaQGKskfNZVoj_jwbKQc3t0zjA36Tavya9MNOfnFQEmDhuyBEg1wbYWRqVrIc
        binding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("token", "");
                editor.putString("role", "");
                editor.putInt("my_id", 0);
                MainActivity.MyToken = "";
                MainActivity.MyRole = "";
                MainActivity.My_ID = 0;
                editor.commit();
                startActivity(new Intent(getContext(), AuthActivity.class));
                getActivity().finish();
            }
        });
    }

    private void GetMyProfile() {
        ShowProfileViewModel showProfileViewModel = ViewModelProviders.of(this).get(ShowProfileViewModel.class);
        showProfileViewModel.MyProfile();
        Log.e("hesham", "11111");
        showProfileViewModel.MyProfileMutableLiveData.observe(this, new Observer<ShowProfile>() {
            @Override
            public void onChanged(ShowProfile showProfile) {
                if (showProfile.getData() != null) {
                    User user = showProfile.getData().getUser();
                    if (user.getImage() != null) {
                        Log.e("hesham", "2222");
                        Picasso.get().load(user.getImage()).error(R.mipmap.ic_launcher).into(binding.imgProfilePic);
                    }
                    binding.tvUserName.setText(user.getName());
                    binding.tvWallet.setText("$ " + user.getWallet() + "USD");
                    binding.LtMyProfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getContext(), MyProfileActivity.class);
                            intent.putExtra("user_id", user.getId());
                            getActivity().startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    private void InitDialog() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("please wait...");
        dialog.setTitle("Log out");
        dialog.setCancelable(false);
    }

    public void IntialSharedPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();
    }
}