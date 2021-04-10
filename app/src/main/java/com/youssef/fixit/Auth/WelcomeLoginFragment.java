package com.youssef.fixit.Auth;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;
import com.youssef.fixit.Auth.LoginFragment.LoginFragment;
import com.youssef.fixit.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;


public class WelcomeLoginFragment extends Fragment {
    private ProgressDialog dialog;
    View view;
    Button Register, login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_welcome_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitSlider();
        InitViews();
    }

    public void InitViews() {
        Register = view.findViewById(R.id.btn_register);
        login = view.findViewById(R.id.btn_login);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplaceFragment(new RegisterFragment());
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplaceFragment(new LoginFragment());
            }
        });
    }

    void InitSlider() {
        List<String> color = new ArrayList<>();
        List<String> texts = new ArrayList<>();
        texts.add("Whatever your needs, there will be a Professional to get it done: from Electricians, plumber's, Painters, General Contractors,  Realtors, Roofers,, web design, mobile app development, virtual assistants, product manufacturing, and graphic design (and a whole lot more).");
        texts.add("1. Post a job ( its free) tell us what are you looking to get help with and our system will do the work for you, to locate the best professionals in the category you needed help with.\n" +
                "\n" +
                "2. Professionals will find your post they will bid on your job, you than compare bids, reviews and previous work history and do the hire based on your best choice.");
        texts.add("1. Register your business, create your profile that will showcase your business and what you do best. Choose the categories that represent your profession and wait for your leads to come.\n" +
                "\n" +
                "2. Users can hire you based on location or reviews or previous work history. You will receive notifications of new job listing when someone posts a new job that matches your category that you selected when you created your profile.");

         SpringDotsIndicator dotsIndicator =view.findViewById(R.id.dots_indicator);
        ViewPager vpImageSlider = view.findViewById(R.id.vp);
        PagerAdapter pagerAdapter = new SliderAdapter(texts, color);
        vpImageSlider.setAdapter(pagerAdapter);
         dotsIndicator.setViewPager(vpImageSlider);
    }

    public class SliderAdapter extends PagerAdapter {
        List<String> texts;
        List<String> color;

        public SliderAdapter(List<String> texts, List<String> color) {
            this.texts = texts;
            this.color = color;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.auth_view_pager_item, container, false);
            ImageView image = view.findViewById(R.id.image);
            TextView textView = view.findViewById(R.id.tv_desc);
            TextView tv_title = view.findViewById(R.id.tv_title);
            LinearLayout lt_main = view.findViewById(R.id.lt_main);
            if (position == 0) {
                image.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.woek_img2_preview_rev_1));
                lt_main.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.app_color));
                tv_title.setVisibility(View.GONE);
            }
            if (position == 1) {
                image.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.client_intro));
                lt_main.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.back_2));

            }
            if (position == 2) {
                image.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.prof_intro));
                tv_title.setText("Professional");
            }
            textView.setText(texts.get(position));
            Button btn_login = view.findViewById(R.id.btn_login);
            Button btn_register = view.findViewById(R.id.btn_register);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return texts.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view.equals(object);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    public void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fram, fragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }
}