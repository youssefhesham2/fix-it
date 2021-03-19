package com.youssef.fixit.MyProjects.MyInvoice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.youssef.fixit.Models.InVoice.Datum;
import com.youssef.fixit.R;
import com.youssef.fixit.MyProjects.MyProjectViewModel;
import com.youssef.fixit.databinding.FragmentMyInvoiceBinding;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyInvoiceFragment extends Fragment {
    FragmentMyInvoiceBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_invoice, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyInvoice();
    }

    void MyInvoice(){
        MyProjectViewModel viewModel = ViewModelProviders.of(this).get(MyProjectViewModel.class);
        viewModel.MyInVoice();
        viewModel.MyInvoice.observe(this, new Observer<List<Datum>>() {
            @Override
            public void onChanged(List<Datum> data) {
                if(data.size()>0){
                    binding.tvLoading.setVisibility(View.GONE);
                    binding.ltNoData.setVisibility(View.GONE);
                    Adapter adapter=new Adapter(data);
                    binding.rvMyInvoice.setAdapter(adapter);
                }
            }
        });
        viewModel.error.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvNotFound.setText(s);
                binding.internetImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_no_wifi));
                binding.tvLoading.setVisibility(View.GONE);
            }
        });

        viewModel.NoData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvLoading.setVisibility(View.GONE);
            }
        });
    }

    class Adapter extends RecyclerView.Adapter<Adapter.viewholder>{
        List<Datum> datumList;

        public Adapter(List<Datum> datumList) {
            this.datumList = datumList;
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_item, parent, false);
            return new viewholder(view);        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
            Datum datum=datumList.get(position);
            holder.ed_currency.setText(datum.getCurrency());
            holder.ed_total_price.setText(datum.getPrice()+"");
            holder.ed_discount.setText(datum.getDiscount());
            holder.tv_status.setText("Read at: "+datum.getReadAt());
            holder.ed_balance.setText(datum.getBalance()+"");
            holder.tv_employer_name.setText(datum.getTo().getName());
            Picasso.get().load(datum.getTo().getImage()).into(holder.circle_image);
            holder.rt_emp.setRating(datum.getTo().getRate().floatValue());
        }

        @Override
        public int getItemCount() {
            return datumList.size();
        }

        class viewholder extends RecyclerView.ViewHolder{
            EditText ed_currency,ed_total_price,ed_discount,tv_status,ed_balance;
            CircleImageView circle_image;
            TextView tv_employer_name;
            RatingBar rt_emp;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                ed_currency=itemView.findViewById(R.id.ed_currency);
                ed_total_price=itemView.findViewById(R.id.ed_total_price);
                ed_discount=itemView.findViewById(R.id.ed_discount);
                tv_status=itemView.findViewById(R.id.tv_status);
                ed_balance=itemView.findViewById(R.id.ed_balance);
                circle_image=itemView.findViewById(R.id.circle_image);
                tv_employer_name=itemView.findViewById(R.id.tv_employer_name);
                rt_emp=itemView.findViewById(R.id.rt_emp);
            }
        }
    }
}