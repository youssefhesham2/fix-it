package com.youssef.fixit.UI.Category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.squareup.picasso.Picasso;
import com.youssef.fixit.Models.Category.Datum;
import com.youssef.fixit.Models.Category.SubCategory;
import com.youssef.fixit.R;
import com.youssef.fixit.UI.AddWork.AddWorkActivity;

import java.util.ArrayList;
import java.util.List;

public class GradleAdapter extends BaseAdapter {
    public static List<SubCategory> subCategories;
    List<Datum> datumList = new ArrayList<>();

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
                    subCategories = datumList.get(posstion).getSubCategory();
                    Fragment fragment = new SubCategoryFragment();
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                } else {
                    AppCompatActivity activity = (AppCompatActivity) viewGroup.getContext();
                    Intent intent=new Intent(viewGroup.getContext(), AddWorkActivity.class);
                    intent.putExtra("category_id",datumList.get(posstion).getId());
                    activity.startActivity(intent);
                }
            }
        });
        return view2;
    }

    public void setDatumList(List<Datum> datumList) {
        this.datumList = datumList;
        datumList = datumList;
        notifyDataSetChanged();
    }
}
