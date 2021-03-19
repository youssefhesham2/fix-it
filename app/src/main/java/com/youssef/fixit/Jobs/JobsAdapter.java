package com.youssef.fixit.Jobs;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.viewholder> {
    List<Datum> jobsList=new ArrayList<>();
    public  int Project_id;
    Context context;
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_item, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Datum job=jobsList.get(position);
        holder.tv_job_title.setText(job.getTitle());
        holder.tv_job_budget.setText(job.getPrice()+" ");
        holder.tv_bids_number.setText(job.getBidsCount()+" bids");
        holder.tv_job_addresse.setText(job.getAddress());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            long time = sdf.parse(job.getCreatedAt()).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            holder.tv_job_time.setText(ago.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)context;
                Project_id=job.getId();
                Intent intent=new Intent(context,ProjectActivity.class);
                intent.putExtra("project_id",Project_id);
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

    public class viewholder extends RecyclerView.ViewHolder{
        TextView tv_job_title,tv_closed_time,tv_bids_number,tv_job_budget,tv_job_addresse,tv_job_time;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            tv_job_title=itemView.findViewById(R.id.tv_job_title);
            tv_closed_time=itemView.findViewById(R.id.tv_closed_time);
            tv_bids_number=itemView.findViewById(R.id.tv_bids_number);
            tv_job_budget=itemView.findViewById(R.id.tv_job_budget);
            tv_job_addresse=itemView.findViewById(R.id.tv_job_addresse);
            tv_job_time=itemView.findViewById(R.id.tv_job_time);
        }
    }
}
