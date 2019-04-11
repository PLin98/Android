package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main4Activity extends AppCompatActivity {

    private ImageView iv_pic;
    private TextView tv_picName;
    private MyHandler myHandler;
    private Button btn_down;
    String strurl;
    MyThread mythread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        strurl="http://172.18.199.235:8080/dviz/1.png";
        //strurl="http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655.jpg";
        iv_pic= (ImageView) findViewById(R.id.iv_pic);
        tv_picName= (TextView) findViewById(R.id.tv_picname);
        btn_down= (Button) findViewById(R.id.btn_down);
        myHandler = new MyHandler();
        mythread = new MyThread();
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(mythread).start();
            }
        });

    }

    //内部类,定义Handler
    class MyHandler extends Handler {
        @Override
        //重写
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //取出Messsage中的数据，用来更新UI组件tv_text
            //Uri uri = (Uri) msg.obj;
            if (iv_pic != null && msg.obj != null) {
                iv_pic.setImageBitmap((Bitmap)msg.obj);
            }

        }


    }
    //定义子线程
    class MyThread implements Runnable {
        //1.定义在子线程要做的耗时操作
        Bitmap bitmap=null;
        //114.247.92.91
        @Override
        public void run() {
            //模拟耗时操作
            try {
                URL url = new URL(strurl);
                InputStream is = url.openStream();
                bitmap = BitmapFactory.decodeStream(is);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg=myHandler.obtainMessage();
            //msg.what = SUCCESS_GET_IMAGE;
            msg.obj=bitmap;
            myHandler.sendMessage(msg);
        }
    }
}

