package com.youssef.fixit.MyProjects.MyRequests;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.youssef.fixit.Models.Jobs.Datum;
import com.youssef.fixit.R;
import com.youssef.fixit.Contract.DisplayContractActivity;

import java.util.ArrayList;
import java.util.List;

class MyRequestsAdapter extends RecyclerView.Adapter<MyRequestsAdapter.viewholder> {
    List<Datum> MyRequestsList=new ArrayList<>();
    public  int contract_id;
    Context context;
    @NonNull
    @Override
    public MyRequestsAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_item, parent, false);
        return new MyRequestsAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRequestsAdapter.viewholder holder, int position) {
        Datum job=MyRequestsList.get(position);
        holder.tv_job_title.setText(job.getTitle());
        holder.tv_job_budget.setText(job.getPrice()+" ");
        holder.tv_bids_number.setText(job.getBidsCount()+" bids");
        holder.tv_job_time.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)context;
                contract_id=job.getContract().getId();
                Intent intent=new Intent(context, DisplayContractActivity.class);
                intent.putExtra("from_my_project",2);
                intent.putExtra("contract_id",contract_id);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return MyRequestsList.size();
    }

    public void setMyRequestsList(List<Datum> MyRequestsList) {
        this.MyRequestsList = MyRequestsList;
        notifyDataSetChanged();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView tv_job_title,tv_closed_time,tv_bids_number,tv_job_budget,tv_job_time;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            tv_job_title=itemView.findViewById(R.id.tv_job_title);
            tv_closed_time=itemView.findViewById(R.id.tv_closed_time);
            tv_bids_number=itemView.findViewById(R.id.tv_bids_number);
            tv_job_budget=itemView.findViewById(R.id.tv_job_budget);
            tv_job_time=itemView.findViewById(R.id.tv_job_time);
        }
    }
}