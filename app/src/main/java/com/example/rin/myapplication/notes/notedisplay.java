package com.example.rin.myapplication.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.rin.myapplication.R;

/**
 * Created by Rin on 2018/12/27.
 */

public class notedisplay extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notedisplay);
        Bundle bundle=this.getIntent().getExtras();
        username=bundle.getString("用户名");
    }

    public void click(View view) {
        switch (view.getId()){
            case R.id.add:
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("用户名",username);
                intent.putExtras(bundle);
                intent.setClass(this,noteadd.class);
                startActivity(intent);
                break;
            case R.id.query:
                Intent intent0=new Intent();
                Bundle bundle0=new Bundle();
                bundle0.putString("用户名",username);
                intent0.putExtras(bundle0);
                intent0.setClass(this,notequery.class);
                startActivity(intent0);
                break;
        }
    }
}
