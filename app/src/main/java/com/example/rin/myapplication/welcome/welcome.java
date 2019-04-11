package com.example.rin.myapplication.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.rin.myapplication.R;
import com.example.rin.myapplication.first.home;
import com.example.rin.myapplication.guidepage.MainActivity;

/**
 * Created by Rin on 2018/12/22.
 */

public class welcome extends AppCompatActivity implements Runnable{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        //启动一个延迟线程
        new  Thread(this).start();
    }

    @Override
    public void run(){
        try{
            //延迟3秒时间
            Thread.sleep(5000);
            SharedPreferences preferences= getSharedPreferences("count2", 0); // 存在则打开它，否则创建新的Preferences
            int count = preferences.getInt("count2", 0); // 取出数据
            /**
             *如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
             */
            if (count == 0) {
                Intent intent1 = new Intent();
                intent1.setClass(this, MainActivity.class);
                startActivity(intent1);
            } else {
                Intent intent2 = new Intent();
                intent2.setClass(this, home.class);
                startActivity(intent2);
            }
            //实例化Editor对象
            SharedPreferences.Editor editor = preferences.edit();
            //存入数据
            editor.putInt("count1", 1); // 存入数据
            //提交修改
            editor.commit();
        } catch (InterruptedException e) {

        }

    }

}
