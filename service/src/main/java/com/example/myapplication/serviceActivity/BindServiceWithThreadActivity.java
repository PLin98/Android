package com.example.myapplication.serviceActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.service.BindServiceWithThread;


/**
 * Created by Rin on 2019/4/11.
 */

public class BindServiceWithThreadActivity extends AppCompatActivity{
    private BindServiceWithThread bindServiceWithThread;
    private boolean isBound=false;
    private static TextView tv_show;
    private static Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            tv_show.setText(tv_show.getText().toString()+"\n"+msg.obj.toString());
        }
    };
    public static Handler getMyhandler(){
        return myhandler;
    }

    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BindServiceWithThread.MyBinder myBinder= (BindServiceWithThread.MyBinder) service;
            bindServiceWithThread= myBinder.getService();
            //String msg=bindServiceWithThread.say();
            isBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound=false;
            bindServiceWithThread=null;
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
                Intent intent=new Intent(BindServiceWithThreadActivity.this,BindServiceWithThread.class);
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
    /*public static void show(int id){
        tv_show.setText("服务已启动"+id+"次");
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
