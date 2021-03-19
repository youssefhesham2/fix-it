package com.youssef.fixit.AddWork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Bids.CreateBid;
import com.youssef.fixit.Models.Cities.Cites;
import com.youssef.fixit.Models.Cities.Datum;
import com.youssef.fixit.R;
import com.youssef.fixit.databinding.ActivityAddWorkBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddWorkActivity extends AppCompatActivity {
    ActivityAddWorkBinding binding;
    private ProgressDialog dialog;
    List<String> Countery = new ArrayList<>();
    List<String> Cities = new ArrayList<>();
    List<String> Area = new ArrayList<>();
    int Country_id, Area_id, City_id;
    int category_id;
    String Budget, Currency, pay_type, city, area;
    int price_from, price_to;
    ArrayList<Uri> Images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddWorkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        category_id = getIntent().getIntExtra("category_id", 0);
        InitDialog();
        InitTabLayout();
        InitBudget(0);
        InitCurrency();
        GetCountry();
        InitSelectImages();
        PostJob();
    }

    private void InitCurrency() {
        binding.sppCurrencyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Currency = adapterView.getAdapter().getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void InitTabLayout() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Fixed Price"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Hourly"));
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                InitBudget(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void InitBudget(int PriceType) {
        ArrayAdapter<String> adapter = null;
        List<String> strings = new ArrayList<>();
        if (PriceType == 0) {
            //fixed price
            adapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.fixed_budget));
        }

        if (PriceType == 1) {
            adapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.hourly_budget));
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sppBudget.setAdapter(adapter);
        binding.sppBudget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Budget = adapterView.getAdapter().getItem(i).toString();
                if (PriceType == 0) {
                    pay_type = "fixed";
                    if (i == 0) {
                        price_from = 10;
                        price_to = 30;
                    } else if (i == 1) {
                        price_from = 30;
                        price_to = 250;
                    } else if (i == 2) {
                        price_from = 250;
                        price_to = 750;
                    } else if (i == 3) {
                        price_from = 750;
                        price_to = 1500;
                    } else if (i == 4) {
                        price_from = 1500;
                        price_to = 3000;
                    } else if (i == 5) {
                        price_from = 3000;
                        price_to = 5000;
                    } else if (i == 6) {
                        price_from = 5000;
                        price_to = 10000;
                    } else if (i == 7) {
                        price_from = 10000;
                        price_to = 20000;
                    } else if (i == 8) {
                        price_from = 20000;
                        price_to = 50000;
                    } else if (i == 8) {
                        price_from = 50000;
                        price_to = 5000000;
                    }
                } else if (PriceType == 1) {
                    pay_type = "hourly";
                    if (i == 0) {
                        price_from = 2;
                        price_to = 8;
                    } else if (i == 1) {
                        price_from = 8;
                        price_to = 15;
                    } else if (i == 2) {
                        price_from = 15;
                        price_to = 25;
                    } else if (i == 3) {
                        price_from = 25;
                        price_to = 50;
                    } else if (i == 4) {
                        price_from = 50;
                        price_to = 500000;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void InitSelectImages() {
        binding.addAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 506);
            }
        });
    }

    private void PostJob() {
        binding.btnPostProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=binding.edTitle.getText().toString();
                String description=binding.edDescription.getText().toString();
                String all_price=Budget+" "+Currency;
                String address=area+"-"+city;
                if(category_id==0){
                    Toast.makeText(AddWorkActivity.this, "please select category", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(title.isEmpty()){
                    Toast.makeText(AddWorkActivity.this, "title field is reuired", Toast.LENGTH_SHORT).show();
                    binding.edTitle.requestFocus();
                    return;
                }
                if(description.isEmpty()){
                    Toast.makeText(AddWorkActivity.this, "description field is reuired", Toast.LENGTH_SHORT).show();
                    binding.edDescription.requestFocus();
                    return;
                }
                if(City_id==0){
                    Toast.makeText(AddWorkActivity.this, "please select city", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Area_id==0){
                    Toast.makeText(AddWorkActivity.this, "please select Area", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.show();
                Log.e("hesham",""+title+description+pay_type+price_from+price_to+address+"2 weak"+Area_id+category_id+all_price);
                RetrofitClient.getInstance().CreateProject(title,description,pay_type,price_from,price_to,address,"2 weak",Area_id,category_id,all_price,Currency).enqueue(new Callback<CreateBid>() {
                    @Override
                    public void onResponse(Call<CreateBid> call, Response<CreateBid> response) {
                        dialog.dismiss();
                        if(response.isSuccessful()){
                            if(response.body()!=null){
                                if(response.body().getData()!=null){
                                    new SweetAlertDialog(AddWorkActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Successful Add Project")
                                            .show();
                                    onBackPressed();
                                }
                                else {
                                    Toast.makeText(AddWorkActivity.this,response.body().getMessage()+ "111", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(AddWorkActivity.this,"ggg", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            Toast.makeText(AddWorkActivity.this, response.toString()+response.message()+"333", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateBid> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(AddWorkActivity.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void InitRv() {
        Toast.makeText(this, Images.size() + "77", Toast.LENGTH_SHORT).show();
        if (Images.size() > 0) {
            binding.rv.setVisibility(View.VISIBLE);
            Adapter adapter = new Adapter();
            binding.rv.setAdapter(adapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 506 && resultCode == RESULT_OK && data != null) {
            if (requestCode == 506) {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri imageUri = item.getUri();
                        Images.add(imageUri);
                        InitRv();
                    }
                } else if (data.getData() != null) {
                    Uri imageUri = data.getData();
                    Images.add(imageUri);
                    InitRv();
                }
            }
        }
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.viewholder> {
        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
            Picasso.get().load(Images.get(position)).into(holder.image);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Images.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return Images.size();
        }

        public class viewholder extends RecyclerView.ViewHolder {
            ImageView delete, image;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                delete = itemView.findViewById(R.id.delete);
                image = itemView.findViewById(R.id.img);
            }
        }
    }

    private void GetCountry() {
        dialog.show();
        Countery.add("select Country");
        Cities.add("select City");
        Area.add("select Area");
        RetrofitClient.getInstance().GetCountry().enqueue(new Callback<Cites>() {
            @Override
            public void onResponse(Call<Cites> call, Response<Cites> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            final List<Datum> Country1 = response.body().getData();
                            List<String> Country2 = new ArrayList<>();
                            for (int i = 0; i < Country1.size(); i++) {
                                Countery.add(Country1.get(i).getName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (AddWorkActivity.this, android.R.layout.simple_spinner_item, Countery);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            binding.sppCuntery.setAdapter(adapter);
                            binding.sppCuntery.setSelection(1);
                            binding.sppCuntery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (i != 0) {
                                        Country_id = Country1.get(i - 1).getId();
                                        binding.sppCity.setVisibility(View.VISIBLE);
                                        GetCity(Country_id);
                                        binding.sppCuntery.setVisibility(View.GONE);
                                    } else if (i == 0) {
                                        Country_id = 0;
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Cites> call, Throwable t) {
                Toast.makeText(AddWorkActivity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void GetCity(int id) {
        dialog.show();
        RetrofitClient.getInstance().GetCity(id).enqueue(new Callback<Cites>() {
            @Override
            public void onResponse(Call<Cites> call, Response<Cites> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            final List<Datum> City1 = response.body().getData();
                            List<String> City2 = new ArrayList<>();
                            for (int i = 0; i < City1.size(); i++) {
                                Cities.add(City1.get(i).getName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (AddWorkActivity.this, android.R.layout.simple_spinner_item, Cities);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            binding.sppCity.setAdapter(adapter);
                            binding.sppCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (i != 0) {
                                        City_id = City1.get(i - 1).getId();
                                        city = City1.get(i - 1).getName();
                                        binding.sppArea.setVisibility(View.VISIBLE);
                                        GetArea(City_id);
                                    } else if (i == 0) {
                                        City_id = 0;
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Cites> call, Throwable t) {
                Toast.makeText(AddWorkActivity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void GetArea(int id) {
        dialog.show();
        RetrofitClient.getInstance().GetArea(id).enqueue(new Callback<Cites>() {
            @Override
            public void onResponse(Call<Cites> call, Response<Cites> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            final List<Datum> Area1 = response.body().getData();
                            List<String> Area2 = new ArrayList<>();
                            for (int i = 0; i < Area1.size(); i++) {
                                Area.add(Area1.get(i).getName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (AddWorkActivity.this, android.R.layout.simple_spinner_item, Area);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            binding.sppArea.setAdapter(adapter);
                            binding.sppArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (i != 0) {
                                        Area_id = Area1.get(i - 1).getId();
                                        area = Area1.get(i - 1).getName();
                                        //GetArea(City_id);
                                    } else if (i == 0) {
                                        Area_id = 0;
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Cites> call, Throwable t) {
                Toast.makeText(AddWorkActivity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void InitDialog() {
        dialog = new ProgressDialog(AddWorkActivity.this);
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);
    }

}

