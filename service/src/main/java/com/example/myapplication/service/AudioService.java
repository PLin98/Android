package com.example.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.myapplication.R;

public class AudioService extends Service {
    //private String TAG="===========";
    private MediaPlayer mediaPlayer;
    @Override
    public void onCreate() {
        super.onCreate();
        this.mediaPlayer=MediaPlayer.create(this, R.raw.music);
        this.mediaPlayer.start();
        //Log.i(TAG,"onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //String msg = intent.getStringExtra("msg");
       // Log.i(TAG,"onStartCommand：intent:" + msg + ",flags:" + flags + ",startId:" + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //Log.i(TAG,"onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mediaPlayer.stop();
        this.mediaPlayer.release();
        //Log.i(TAG, "onDestroy");
    }
}
