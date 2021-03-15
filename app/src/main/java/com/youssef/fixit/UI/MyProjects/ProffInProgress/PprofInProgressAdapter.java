package com.youssef.fixit.UI.MyProjects.ProffInProgress;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.youssef.fixit.Models.Jobs.Datum;
import com.youssef.fixit.R;
import com.youssef.fixit.UI.InProgress.InProgressActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PprofInProgressAdapter extends RecyclerView.Adapter<PprofInProgressAdapter.viewholder> {
    List<com.youssef.fixit.Models.Jobs.Datum> jobsList = new ArrayList<>();
    public int Project_id;
    Context context;
    public static Datum jobs;

    @NonNull
    @Override
    public PprofInProgressAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_contract_item, parent, false);
        return new PprofInProgressAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PprofInProgressAdapter.viewholder holder, int position) {
        com.youssef.fixit.Models.Jobs.Datum job = jobsList.get(position);
        holder.tv_job_title.setText(job.getTitle());
        holder.tv_job_budget.setText(job.getPrice() + " ");
        holder.tv_bids_number.setText(job.getBidsCount() + " bids");
        holder.tv_job_addresse.setText(job.getAddress());
        holder.tv_closed_time.setText(job.getStatus());
        holder.tv_job_time.setVisibility(View.GONE);
        Log.e("hesham", job.getIsPaid() + "004564");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobs = job;
                Log.e("hesham", job.getTitle());
                AppCompatActivity activity = (AppCompatActivity) context;
                Project_id = job.getId();
                Intent intent = new Intent(context, InProgressActivity.class);
                Toast.makeText(activity, job.getContract().getId() + "", Toast.LENGTH_SHORT).show();
                intent.putExtra("conteact_id", job.getContract().getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    public void setJobsList(List<Datum> jobsList) {
        this.jobsList = jobsList;
        notifyDataSetChanged();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView tv_job_title, tv_closed_time, tv_bids_number, tv_job_budget, tv_job_addresse, tv_job_time, tv_employer_name;
        CircleImageView image_pp;
        RatingBar rt_emp;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            tv_job_title = itemView.findViewById(R.id.tv_job_title);
            tv_closed_time = itemView.findViewById(R.id.tv_closed_time);
            tv_bids_number = itemView.findViewById(R.id.tv_bids_number);
            tv_job_budget = itemView.findViewById(R.id.tv_job_budget);
            tv_job_addresse = itemView.findViewById(R.id.tv_job_addresse);
            tv_job_time = itemView.findViewById(R.id.tv_job_time);
            tv_employer_name = itemView.findViewById(R.id.tv_employer_name);
            image_pp = itemView.findViewById(R.id.image_pp);
            rt_emp = itemView.findViewById(R.id.rt_emp);
        }
    }
}
