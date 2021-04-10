package com.youssef.fixit.SearchProff;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.Category.Category;
import com.youssef.fixit.Models.SearchProff.Datum;
import com.youssef.fixit.Models.SearchProff.SearchProff;
import com.youssef.fixit.R;
import com.youssef.fixit.databinding.FragmentSearchProfBinding;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchProfFragment extends Fragment {
    FragmentSearchProfBinding binding;
    private ProgressDialog dialog;
    int Category_id;
    String  SearchWord="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_prof, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitDialog();
        GetCategory();
        GetSearch();
    }

    private void GetSearch(){
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence!=null){
                    if(charSequence.length()>0){
                        SearchWord=charSequence.toString();
                        GetSeachResult();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void GetCategory() {
        List<String> Categories = new ArrayList<>();
        Categories.add("Choose Category");
        dialog.show();
        RetrofitClient.getInstance().GetCategory().enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body().getData() != null) {
                    if (response.body().getData().size() > 0) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            com.youssef.fixit.Models.Category.Datum datum = response.body().getData().get(i);
                            Categories.add(datum.getTitle());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getContext(), android.R.layout.simple_spinner_item, Categories);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.sppCategory.setAdapter(adapter);
                        binding.sppCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i > 0) {
                                    Category_id = response.body().getData().get(i - 1).getId();
                                    GetSeachResult();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                    }
                }
                else {
                    Toast.makeText(getContext(), response.message()+"", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.youssef.fixit.Models.Category.Category> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(), t.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetSeachResult(){
        binding.tvWaite.setVisibility(View.VISIBLE);
        binding.tvWaite.setText("Please waite...");
        SearchViewModel searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.GetSearchProff(SearchWord,Category_id);
        searchViewModel.searchProffMutableLiveData.observe(this, new Observer<SearchProff>() {
            @Override
            public void onChanged(SearchProff searchProff) {
                binding.tvWaite.setVisibility(View.GONE);
                ProffAdapter adapter=new ProffAdapter(searchProff.getData().getData());
                binding.rv.setAdapter(adapter);
            }
        });

        searchViewModel.error.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvWaite.setText(s);
            }
        });
    }

    class ProffAdapter extends RecyclerView.Adapter<SearchProfFragment.ProffAdapter.viewholder> {
        List<Datum> Profss;

        public ProffAdapter(List<Datum> Profss) {
            this.Profss = Profss;
        }

        @NonNull
        @Override
        public SearchProfFragment.ProffAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item, parent, false);
            return new SearchProfFragment.ProffAdapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchProfFragment.ProffAdapter.viewholder holder, int position) {
            Datum proff=Profss.get(position);
            holder.tv_user_name.setText(proff.getName());
            holder.tv_feddback.setText(proff.getDetails());
            holder.rt.setRating((proff.getRate().floatValue()));
             Picasso.get().load(proff.getImage()).into(holder.c_img);
        }

        @Override
        public int getItemCount() {
            return Profss.size();
        }

        class viewholder extends RecyclerView.ViewHolder {
            CircleImageView c_img;
            TextView tv_user_name,tv_feddback;
            RatingBar rt;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                c_img = itemView.findViewById(R.id.c_img);
                tv_user_name = itemView.findViewById(R.id.tv_user_name);
                tv_feddback = itemView.findViewById(R.id.tv_feddback);
                rt = itemView.findViewById(R.id.rt);
            }
        }
    }
    private void InitDialog() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);
    }
}