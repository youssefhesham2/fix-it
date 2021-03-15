package com.youssef.fixit.UI.MainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.youssef.fixit.R;
import com.youssef.fixit.UI.Auth.AuthActivity;
import com.youssef.fixit.UI.Home.HomeActivity;

public class MainActivity extends AppCompatActivity {
    public static String MyToken;
    public static String MyRole;
    public static int My_ID;
    public SharedPreferences preferences;
    public  SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntialSharedPreferences();
        CheackLogin();
    }
    public void CheackLogin(){
        MyToken=preferences.getString("token","");
        MyRole=preferences.getString("role","");
        My_ID=preferences.getInt("my_id",0);
        if(MyToken.isEmpty()){
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }
        else {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            Log.e("hesham","home"+MyToken);
        }
    }

    public void IntialSharedPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        editor = preferences.edit();
    }
}