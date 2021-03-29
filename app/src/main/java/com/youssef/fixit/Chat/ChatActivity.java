package com.youssef.fixit.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.util.HttpAuthorizer;
import com.youssef.fixit.MainActivity.SplashScreen;
import com.youssef.fixit.Models.Data.RetrofitClient;
import com.youssef.fixit.Models.chat.Massage.Chat;
import com.youssef.fixit.Models.chat.Massage.Datum;
import com.youssef.fixit.Models.chat.SendMassage.SendMassage;
import com.youssef.fixit.R;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    int user_id, room_id;
    RecyclerView recyclerView;
    EditText et_sendmassage;
    TextView tv_loading;
    FloatingActionButton sendButton;
    List<Datum> chatlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        InitViews();
        GetChatMassages();
        SendMassage();
    }

    void InitViews() {
        user_id = getIntent().getIntExtra("user_id", 0);
        room_id = getIntent().getIntExtra("room_id", 0);
        recyclerView = findViewById(R.id.recyclerViewChat);
    }

    void SendMassage() {
        sendButton = findViewById(R.id.sendButton);
        et_sendmassage = findViewById(R.id.et_sendmassage);
        tv_loading = findViewById(R.id.tv_loading);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Massage = et_sendmassage.getText().toString();
                if (Massage.isEmpty()) {
                    Toast.makeText(ChatActivity.this, "please enter message first", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("hesham", "room_id:" + room_id + "Massage:" + Massage + "user_id" + user_id);
                RetrofitClient.getInstance().sendmassage(room_id, Massage, user_id).enqueue(new Callback<SendMassage>() {
                    @Override
                    public void onResponse(Call<SendMassage> call, Response<SendMassage> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getMessage().equals("success") && response.body().getErrors() == null) {
                                et_sendmassage.setText("");
                            } else
                                Toast.makeText(ChatActivity.this, response.message() + "2222", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChatActivity.this, response.message() + "1111", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SendMassage> call, Throwable t) {
                        Log.e("hesham",t.getMessage());
                        Toast.makeText(ChatActivity.this, t.getMessage() + "7777", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void GetChatMassages() {
        RoomsViewModel roomsViewModel = ViewModelProviders.of(this).get(RoomsViewModel.class);
        if (user_id != 0) {
            roomsViewModel.GetMassages(user_id);
            roomsViewModel.MassagesMutableLiveData.observe(this, new Observer<Chat>() {
                @Override
                public void onChanged(Chat chat) {
                    if (room_id == 0) {
                        if (chat.getData() != null) {
                            if (chat.getData().size() > 0) {
                                room_id = chat.getData().get(0).getRoomId();
                                Pusher();
                            }
                        }
                    }
                    else {
                        Pusher();
                    }
                    chatlist = chat.getData();
                    adapter adapter = new adapter(chatlist);
                    recyclerView.setAdapter(adapter);
                    tv_loading.setVisibility(View.GONE);
                }
            });
            roomsViewModel.error.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    tv_loading.setText(s);
                }
            });
        }
    }

    public class adapter extends RecyclerView.Adapter<adapter.viewholder> {
        List<Datum> massages;

        public adapter(List<Datum> massages) {
            this.massages = massages;
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ChatActivity.this).inflate(R.layout.msg_item, parent, false);
            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
            Datum massage = massages.get(position);
            if (massage.getFrom().getId() == SplashScreen.My_ID) {
                holder.tv_my_message.setText(massage.getMessage());
                holder.other_user.setVisibility(View.GONE);
            } else {
                holder.tv_other_message.setText(massage.getMessage());
                holder.other_username.setText(massage.getFrom().getName());
                holder.my_user.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return massages.size();
        }

        public void setMassages(List<Datum> massages) {
            this.massages = massages;
            notifyDataSetChanged();
        }

        class viewholder extends RecyclerView.ViewHolder {
            ConstraintLayout my_user, other_user;
            TextView tv_my_message, tv_my_message_time, other_username, tv_other_message, tv_other_message_time;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                tv_my_message = itemView.findViewById(R.id.tv_my_message);
                tv_my_message_time = itemView.findViewById(R.id.tv_my_message_time);
                other_username = itemView.findViewById(R.id.other_username);
                tv_other_message = itemView.findViewById(R.id.tv_other_message);
                tv_other_message_time = itemView.findViewById(R.id.tv_other_message_time);
                my_user = itemView.findViewById(R.id.my_user);
                other_user = itemView.findViewById(R.id.other_user);
            }
        }
    }


    private void Pusher() {
        PusherOptions options = new PusherOptions();
        //pushercluster
        options.setCluster("eu");
        HttpAuthorizer authorizer = new HttpAuthorizer("https://backend.helpmefixit.ca/api/chat/authorize");
        HashMap<String, String> hashMap = new HashMap<>();
        Log.e("Pusher", room_id + "");
        hashMap.put("Authorization", "Bearer " + SplashScreen.MyToken);
        authorizer.setHeaders(hashMap);
        options.setAuthorizer(authorizer);
        //pusher key
        Pusher pusher = new Pusher("dd91e70566784f9bb381", options);

        //connect to channel
        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                Log.e("Pusher", "State changed from " + change.getPreviousState() +
                        " to " + change.getCurrentState());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                Log.e("Pusher", "There was a problem connecting! " +
                        "\ncode: " + code +
                        "\nmessage: " + message +
                        "\nException: " + e
                );
            }
        }, ConnectionState.ALL);

        //channel name
        Channel channel = pusher.subscribePrivate("private-room." + room_id);
        //event name
        channel.bind("new-message", new PrivateChannelEventListener() {
            @Override
            public void onAuthenticationFailure(String s, Exception e) {
                Log.e("Pusher", e.getMessage());

            }

            @Override
            public void onSubscriptionSucceeded(String s) {
                Log.e("Pusher", "succeeded");
            }

            @Override
            public void onEvent(String s, String s1, String s2) {
              /*  Log.e("Pusher", "event"+s2+"0000");
                try {
                    JSONObject jsonObject=new JSONObject(s2);
                    int id=jsonObject.getJSONObject("message").getInt("id");
                    int room_id=jsonObject.getJSONObject("message").getInt("room_id");
                    int created_by=jsonObject.getJSONObject("message").getInt("created_by");
                    int to=jsonObject.getJSONObject("message").getInt("to");
                    String message=jsonObject.getJSONObject("message").getString("message");
                    String created_at=jsonObject.getJSONObject("message").getString("created_at");
                    Datum datum22=new Datum(id,,room_id,created_by,to,message,created_at,"");
                    chatlist.add(datum22);
                    Log.e("Pusher",message+"222222");
                }catch (Exception e){}*/
                RetrofitClient.getInstance().Massage(user_id).enqueue(new Callback<Chat>() {
                    @Override
                    public void onResponse(Call<Chat> call, Response<Chat> response) {
                        if (response.body().getData() != null) {
                            adapter adapter = new adapter(response.body().getData());
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.e("hesham", response.message() + "22");
                        }

                    }

                    @Override
                    public void onFailure(Call<Chat> call, Throwable t) {
                        Log.e("hesham", t.getMessage());
                    }
                });
            }
        });
    }
}