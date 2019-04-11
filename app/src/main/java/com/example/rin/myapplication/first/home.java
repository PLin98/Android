package com.example.rin.myapplication.first;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rin.myapplication.R;
import com.example.rin.myapplication.login.login;

/**
 * Created by Rin on 2018/12/19.
 */

public class home extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }
    public void login(View view) {
        startActivity(new Intent(this,login.class));
        //finish();
    }
}
