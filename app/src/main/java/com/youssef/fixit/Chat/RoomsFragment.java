package com.youssef.fixit.Chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.youssef.fixit.MainActivity.SplashScreen;
import com.youssef.fixit.Models.chat.CreatedBy;
import com.youssef.fixit.Models.chat.Datum;
import com.youssef.fixit.Models.chat.Rooms;
import com.youssef.fixit.Models.chat.Users;
import com.youssef.fixit.R;
import com.youssef.fixit.databinding.FragmentUsersChatBinding;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomsFragment extends Fragment {
    FragmentUsersChatBinding binding;
    Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_users_chat, container, false);
        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GetRooms();
    }

    private void GetRooms() {
        RoomsViewModel roomsViewModel = ViewModelProviders.of(this).get(RoomsViewModel.class);
        roomsViewModel.GetRooms();
        roomsViewModel.RoomsMutableLiveData.observe(this, new Observer<Rooms>() {
            @Override
            public void onChanged(Rooms rooms) {
                adapter = new Adapter(rooms.getData());
                binding.rvChatRooms.setAdapter(adapter);
                binding.tvLoading.setVisibility(View.GONE);
            }
        });
        roomsViewModel.error.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvLoading.setText(s);
            }
        });

    }

    public class Adapter extends RecyclerView.Adapter<Adapter.viewholder> {
        List<Datum> rooms;

        public Adapter(List<Datum> rooms) {
            this.rooms = rooms;
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.users_item, parent, false);
            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
            if(SplashScreen.MyRole.equals("profession")){
                CreatedBy user = rooms.get(position).getCreatedBy_();
                Picasso.get().load(user.getImage()).error(R.mipmap.ic_launcher).into(holder.ci_user_image);
                holder.tv_user_name.setText(user.getName());
                if (rooms.get(position).getMessages() != null) {
                    if (rooms.get(position).getMessages().size() > 0) {
                        holder.tv_last_msg.setText(rooms.get(position).getMessages().get(0).getMessage());
                    }
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra("user_id", user.getId());
                        getActivity().startActivity(intent);
                    }
                });
            }
            else {
                Users user = rooms.get(position).getUsers();
                Picasso.get().load(user.getImage()).error(R.mipmap.ic_launcher).into(holder.ci_user_image);
                holder.tv_user_name.setText(user.getName());
                if (rooms.get(position).getMessages() != null) {
                    if (rooms.get(position).getMessages().size() > 0) {
                        holder.tv_last_msg.setText(rooms.get(position).getMessages().get(0).getMessage());
                    }
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra("user_id", user.getId());
                        getActivity().startActivity(intent);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return rooms.size();
        }

        class viewholder extends RecyclerView.ViewHolder {
            CircleImageView ci_user_image;
            TextView tv_user_name, tv_last_msg;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                ci_user_image = itemView.findViewById(R.id.ci_user_image);
                tv_user_name = itemView.findViewById(R.id.tv_user_name);
                tv_last_msg = itemView.findViewById(R.id.tv_last_msg);
            }
        }
    }
}