package com.example.myapplication.serviceActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.service.AudioBindService;

/**
 * Created by Rin on 2019/4/11.
 */

public class AudioBindServiceActivity extends AppCompatActivity{
    private AudioBindService audioBindService=null;
    private boolean isBound=false;
    private static TextView tv_show=null;
    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AudioBindService.MyBinder myBinder= (AudioBindService.MyBinder) service;
            audioBindService= (AudioBindService) ((AudioBindService.MyBinder) service).getService();
            String msg=audioBindService.say();
            isBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound=false;
            audioBindService=null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show= (TextView) findViewById(R.id.tv_show);
        Button btn_test= (Button) findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AudioBindServiceActivity.this,AudioBindService.class);
                bindService(intent,conn, Service.BIND_AUTO_CREATE);//绑定服务
            }
        });
        Button btn_stop= (Button) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBound){
                    unbindService(conn);//解绑
                }
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
