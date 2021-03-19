package com.youssef.fixit.MyProjects.MyOpenProject;

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
import com.youssef.fixit.Projects.ProjectActivity;

import java.util.ArrayList;
import java.util.List;

class MyOpenProjectAdapter extends RecyclerView.Adapter<MyOpenProjectAdapter.viewholder> {
    List<Datum> OpenProjectList=new ArrayList<>();
    public  int Project_id;
    Context context;
    @NonNull
    @Override
    public MyOpenProjectAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_item, parent, false);
        return new MyOpenProjectAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOpenProjectAdapter.viewholder holder, int position) {
        Datum job=OpenProjectList.get(position);
        holder.tv_job_title.setText(job.getTitle());
        holder.tv_job_budget.setText(job.getPrice()+" ");
        holder.tv_bids_number.setText(job.getBidsCount()+" bids");
        holder.tv_job_addresse.setText(job.getAddress());
        holder.tv_job_time.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)context;
                Project_id=job.getId();
                Intent intent=new Intent(context, ProjectActivity.class);
                intent.putExtra("from_my_project",1);
                intent.putExtra("project_id",Project_id);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return OpenProjectList.size();
    }

    public void setOpenProjectList(List<Datum> OpenProjectList) {
        this.OpenProjectList = OpenProjectList;
        notifyDataSetChanged();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView tv_job_title,tv_closed_time,tv_bids_number,tv_job_budget,tv_job_time,tv_job_addresse;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            tv_job_title=itemView.findViewById(R.id.tv_job_title);
            tv_closed_time=itemView.findViewById(R.id.tv_closed_time);
            tv_bids_number=itemView.findViewById(R.id.tv_bids_number);
            tv_job_budget=itemView.findViewById(R.id.tv_job_budget);
            tv_job_time=itemView.findViewById(R.id.tv_job_time);
            tv_job_addresse=itemView.findViewById(R.id.tv_job_addresse);
        }
    }
}
