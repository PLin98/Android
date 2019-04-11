package com.example.myapplication.serviceActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.service.AudioService;

/**
 * Created by Rin on 2019/4/11.
 */

public class AudioServiceActivity extends AppCompatActivity {
    private Intent intent=null;
    private static TextView tv_show=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent=new Intent(this,AudioService.class);
        tv_show= (TextView) findViewById(R.id.tv_show);
        Button btn_test= (Button) findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("msg","hellohello");
                startService(intent);
            }
        });
        Button btn_stop= (Button) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });

    }
    public static void show(int id){
        tv_show.setText("服务已启动"+id+"次");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
