package com.example.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.myapplication.serviceActivity.BindServiceWithThreadActivity;

/**
 * Created by Rin on 2019/4/11.
 */

public class BindServiceWithThread extends Service {
    private String TAG="===========BindServiceWithThread";
    private Thread thread;
    private final IBinder myBinder=new MyBinder();
    public class MyBinder extends Binder {
        public BindServiceWithThread getService(){
            return BindServiceWithThread.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        thread.start();
        return myBinder;
    }
    public boolean onUnbind(Intent intent) {
        //Log.i(TAG,"onUnbind:AudioBindService");
        return super.onUnbind(intent);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<5;i++){
                    Log.i(TAG,"Task is running"+i);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Log.i(TAG,"Task running"+(i/5.0)*100+"%");
                    Handler handler= BindServiceWithThreadActivity.getMyhandler();
                    Message msg=handler.obtainMessage();
                    msg.obj="Task running  "+(i/5.0)*100+"%";
                    handler.sendMessage(msg);
                }
                Log.i(TAG,"Task has done!");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(thread.isAlive()){
            thread.interrupt();
        }
    }
}
