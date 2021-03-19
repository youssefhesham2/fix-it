package com.youssef.fixit.MyProjects.MyContracts;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.youssef.fixit.Models.Contract.Datum;
import com.youssef.fixit.R;
import com.youssef.fixit.Projects.ProjectActivity;

import java.util.ArrayList;
import java.util.List;
    class MyContractAdapter extends RecyclerView.Adapter<MyContractAdapter.viewholder> {
        List<Datum> OpenProjectList = new ArrayList<>();
        public int Project_id;
       public Context context;

        @NonNull
        @Override
        public MyContractAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_contract_item, parent, false);
            return new MyContractAdapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyContractAdapter.viewholder holder, int position) {
            com.youssef.fixit.Models.Contract.Datum MyContract = OpenProjectList.get(position);
            if(MyContract.getProject()!=null){

            Log.e("hesham",MyContract.getProject()+"001");
            holder.tv_job_title.setText(MyContract.getProject().getTitle());
        //    holder.tv_job_budget.setText(MyContract.getProject().getPrice() + " ");
            holder.tv_bids_number.setText(MyContract.getProject().getBidsCount() + " bids");
            holder.tv_job_addresse.setText(MyContract.getAddress());
        //    holder.tv_job_addresse.setVisibility(View.GONE);
            holder.tv_job_time.setVisibility(View.GONE);
            holder.tv_employer_name.setText(MyContract.getProfession().getName());
            holder.rt_emp.setRating(MyContract.getProfession().getRate().floatValue());
            Picasso.get().load(MyContract.getProfession().getImage()).into(holder.image_pp);
            holder.tv_closed_time.setText(MyContract.getStatus());
            String Price=" $ "+MyContract.getTotalPrice()+" "+MyContract.getProject().getCurrency();
            holder.tv_job_budget.setText(Price);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) context;
                    Project_id = MyContract.getProject().getId();
                    Intent intent = new Intent(context, ProjectActivity.class);
                    intent.putExtra("from_my_project", 1);
                    intent.putExtra("project_id", Project_id);
                    activity.startActivity(intent);
                }
            });
        }
        }

        @Override
        public int getItemCount() {
            return OpenProjectList.size();
        }

        public void setOpenProjectList(List<com.youssef.fixit.Models.Contract.Datum> OpenProjectList) {
            this.OpenProjectList = OpenProjectList;
            notifyDataSetChanged();
        }

        public class viewholder extends RecyclerView.ViewHolder {
            TextView tv_job_title, tv_closed_time, tv_bids_number, tv_job_budget, tv_job_time, tv_job_addresse,tv_employer_name;
            RatingBar rt_emp;
            ImageView image_pp;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                tv_job_title = itemView.findViewById(R.id.tv_job_title);
                tv_closed_time = itemView.findViewById(R.id.tv_closed_time);
                tv_bids_number = itemView.findViewById(R.id.tv_bids_number);
                tv_job_budget = itemView.findViewById(R.id.tv_job_budget);
                tv_job_time = itemView.findViewById(R.id.tv_job_time);
                tv_job_addresse = itemView.findViewById(R.id.tv_job_addresse);
                tv_employer_name = itemView.findViewById(R.id.tv_employer_name);
                rt_emp = itemView.findViewById(R.id.rt_emp);
                image_pp = itemView.findViewById(R.id.image_pp);
            }
        }
}