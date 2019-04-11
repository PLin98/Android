package com.example.myapplication;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {

    private TextView tv_text;
    private EditText etNum;
    private CalThread calThread;
    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv_text= (TextView) findViewById(R.id.tv_text);
        etNum = (EditText) findViewById(R.id.etNum);
        calThread =new CalThread();
        myHandler =new MyHandler();
        calThread.start();

    }
    class MyHandler extends Handler{
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle=msg.getData();
                String str=bundle.getString("res");
                tv_text.setText(str);
            }
    }

    //添加按钮
    public void cal(View view) {//按钮的click事件处理器
        //创建消息对象
        Message msg = new Message();
        msg.what = 0x123;//设置消息标识
        //将EditText中输入的数制N存入msg对象
        Bundle bundle = new Bundle();
        bundle.putInt("N", Integer.parseInt(etNum.getText().toString()));
        msg.setData(bundle);
        tv_text.setText("");
        //用子线程中定义的Handler对象发送消息
        calThread.mHandler.sendMessage(msg);
    }

    //定义线程类
    class CalThread extends Thread {
        public Handler mHandler;
        public void run() {
            //创建looper对象，每一个线程使用Handler都要关联一个looper对象
            Looper.prepare();
            //子线程中定义handler获取处理消息
            mHandler = new Handler() {
                //定义处理信息的方法
                public void handleMessage(Message msg) {
                    if (msg.what == 0x123) {
                        //从msg中取出N值
                        Bundle bundle = msg.getData();
                        int N = bundle.getInt("N");
                        //计算0--N之间的素数
                        List<Integer> nums = new ArrayList<Integer>();
                        //质数也是素数，除了i和它本身外，不能被其他整除
                        outer:
                        for (int i = 2; i <= N; i++) {
                            for (int j = 2; j <= Math.sqrt(i); j++) {
                                //如果可以整除，说明不是质数
                                if (i != 2 && i % j == 0) {
                                    continue outer;
                                }
                            }
                            nums.add(i);
                        }
                        Log.i("===cal===", String.valueOf(nums));
                        Message msg1=myHandler.obtainMessage();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("res",nums.toString());
                        msg1.setData(bundle1);
                        myHandler.sendMessage(msg1);
                    }
                }
            };
            Looper.loop();//启动lopper
        }
    }
}

