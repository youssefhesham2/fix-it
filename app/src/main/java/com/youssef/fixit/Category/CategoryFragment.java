package com.youssef.fixit.Category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;
import com.youssef.fixit.Models.Ads.Image;
import com.youssef.fixit.Models.Ads.ShowAds;
import com.youssef.fixit.Models.Category.Category;
import com.youssef.fixit.R;
import com.youssef.fixit.Adds.ShowAdsViewModel;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {
    com.youssef.fixit.databinding.FragmentCategoryBinding binding;
    public static   GradleViewModel gradleViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitGrideView();
        GetAds();
    }

    private void InitViewPager(List<Image> imageobject) {
        List<String> images=new ArrayList<>();
        for (int i=0;i<imageobject.size();i++){
            images.add(imageobject.get(i).getImage());
        }
        List<String> texts=new ArrayList<>();
        SpringDotsIndicator dotsIndicator =binding.dotsIndicator;
        ViewPager vpImageSlider=binding.vpImageSlider;
        PagerAdapter pagerAdapter = new SliderAdapter(images,texts);
        vpImageSlider.setAdapter(pagerAdapter);
        dotsIndicator.setViewPager(vpImageSlider);
    }
    private void GetAds(){
        ShowAdsViewModel viewModel= ViewModelProviders.of(this).get(ShowAdsViewModel.class);
         viewModel.GetHomeAds();
         viewModel.HomeAdsMutableLiveData.observe(this, new Observer<ShowAds>() {
             @Override
             public void onChanged(ShowAds showAds) {
                 InitViewPager(showAds.getData().getImages());
             }
         });

         viewModel.error.observe(this, new Observer<String>() {
             @Override
             public void onChanged(String s) {
                 Toast.makeText(getContext(),s+ "", Toast.LENGTH_SHORT).show();
             }
         });
    }
    public void InitGrideView(){
         gradleViewModel = ViewModelProviders.of(this).get(GradleViewModel.class);
        gradleViewModel.GetCategory();
        GradleAdapter gradleAdapter = new GradleAdapter();
        binding.gdHome.setAdapter(gradleAdapter);
        gradleViewModel.CategoryMutableLiveData.observe(this, new Observer<Category>() {
            @Override
            public void onChanged(Category categories) {
                binding.loadGraph.setVisibility(View.GONE);
                gradleAdapter.setDatumList(categories.getData());
            }
        });

    }

    public class SliderAdapter extends PagerAdapter {
        List<String> Images;
        List<String> texts;
        public SliderAdapter(List<String> images, List<String> texts) {
            Images = images;
            this.texts = texts;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.viewpager_item, container, false);
            ImageView image = view.findViewById(R.id.Image);
            Picasso.get().load(Images.get(position)).into(image);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return Images.size();
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

}
