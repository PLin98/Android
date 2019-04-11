package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {


    final String ACTION_1 = "android.intent.action.ACTION_POWER_CONNECTED";
    final String ACTION_2 = "android.intent.action.ACTION_POWER_DISCONNECTED";

    @Override
    public void onReceive(Context context, Intent intent) {

        switch(intent.getAction()){
            case ACTION_1:
                MainActivity.update("系统广播：外部电源连接");
                break;
            case ACTION_2:
                MainActivity.update("系统广播：外部电源断开");
                break;
            case MainActivity.MYACTION:
                MainActivity.update("我的广播：from myApp");
                break;
        }

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
