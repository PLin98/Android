package com.example.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.myapplication.R;

public class AudioBindService extends Service {
    private String TAG="========AudioBindService";
    private MediaPlayer mediaPlayer;
    private final IBinder myBinder=new MyBinder();
    public class MyBinder extends Binder{
        public AudioBindService getService(){
            return AudioBindService.this;//返回AudioBindService类的实例
        }
    }
    @Override
    public void onCreate() {
        Log.i(TAG,"onCreate:play");
        super.onCreate();
        this.mediaPlayer=MediaPlayer.create(this, R.raw.music);
        this.mediaPlayer.start();

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind:AudioBindService");
        return myBinder;//返回IBinder接口实例
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG,"onUnbind:AudioBindService");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        if(this.mediaPlayer.isPlaying()){
            this.mediaPlayer.stop();
        }
        this.mediaPlayer.release();

    }
    public String say(){//自定义的其他业务操作
        return "say:hello";
    }
}
