package com.example.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
public class  MainActivity extends AppCompatActivity{
    //声明两个控件
    private Button btn_start;
    private ProgressBar barShow;
    private TextView tvTip;
    private int i=0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
    }
    //通过控件ID得到控件
    private void findViews() {
        btn_start= (Button) findViewById(R.id.btn_start);
        barShow= (ProgressBar) findViewById(R.id.barShow);
        tvTip= (TextView) findViewById(R.id.tvTip);
        //为控件添加监听器；
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barShow.setVisibility(View.VISIBLE);
                //将线程添加到Handler消息队列
                updateBarHandler.post(updateThread);
            }
        });
    }
    //使用匿名内部类
    Handler updateBarHandler = new Handler(){
        public void handleMessage(Message msg){
            barShow.setProgress(msg.arg1);
            if(msg.arg1<100){
                tvTip.setText("下载进度"+(msg.arg1)+"%");
            }else {
                tvTip.setText("下载完毕");
            }

            updateBarHandler.post(updateThread);
        }
    };
    //使用匿名内部类声明线程类
    Runnable updateThread = new Runnable(){
        int i = 0;
        public void run(){
            //Log.i("SWORD", "Begin Thread");
            i+=10;
            //得到一个消息对象
            Message msg = updateBarHandler.obtainMessage();
            //将msg对象的arg1参数的值设置为i;
            msg.arg1 = i;
            try {
                //设置当前线程休眠1秒
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //将msg对象加入到消息队列当中（尾部）
            updateBarHandler.sendMessage(msg);
            if(msg.arg1==100){
                //如果当i的值为100时将当前线程从handler中移除

                updateBarHandler.removeCallbacks(updateThread);

            }
        }
    };
}
