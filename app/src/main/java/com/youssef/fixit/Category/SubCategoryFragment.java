package com.youssef.fixit.Category;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.youssef.fixit.Models.Category.SubCategory;
import com.youssef.fixit.R;
import com.youssef.fixit.AddWork.AddWorkActivity;
import com.youssef.fixit.databinding.FragmentSubCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryFragment extends Fragment {
    FragmentSubCategoryBinding binding;
    List<SubCategory> subCategories;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subCategories = GradleAdapter.subCategories;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sub_category, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitGradleSubCategory();
    }

    private void InitGradleSubCategory() {
        GradleAdapterSubCategory gradleAdapter = new GradleAdapterSubCategory(subCategories);
        binding.gdHome.setAdapter(gradleAdapter);
    }


    class GradleAdapterSubCategory extends BaseAdapter {
        List<SubCategory> datumList = new ArrayList<>();

        @Override
        public int getCount() {
            return datumList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final int posstion = i;
            View view2 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gradle_item, null);
            ImageView image_item = view2.findViewById(R.id.image_item);
            TextView text_item = view2.findViewById(R.id.text_item);
            text_item.setText(datumList.get(i).getTitle());
            String image = "";
             image = datumList.get(i).getImage();
            if (image.isEmpty()) {
                Picasso.get().load(image).into(image_item);
            } else {
                Picasso.get().load(R.drawable.defult_image).into(image_item);
            }
            view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (datumList.get(posstion).getSubCategoryCount() > 0) {
                        AppCompatActivity activity = (AppCompatActivity) viewGroup.getContext();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        GradleAdapter.subCategories = datumList.get(posstion).getSubCategory();
                        Fragment fragment = new SubCategoryFragment();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    } else {
                        Intent intent=new Intent(getContext(), AddWorkActivity.class);
                        intent.putExtra("category_id",datumList.get(posstion).getId());
                        getActivity().startActivity(intent);
                    }
                }
            });
            return view2;
        }

        public GradleAdapterSubCategory(List<SubCategory> datumList) {
            this.datumList = datumList;
        }
    }

}