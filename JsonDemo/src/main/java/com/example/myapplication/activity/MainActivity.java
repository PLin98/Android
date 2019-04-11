package com.example.myapplication.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.domain.Person;
import com.example.myapplication.utils.HttpUtils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button btn_post,btn_get;
    private EditText et_name,et_age;
    private String param,strUrl;
    private TextView tv_show;

    Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            tv_show.setText(msg.obj.toString());
            tv_show.setTextColor(Color.RED);
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_post= (Button) findViewById(R.id.btn_post);
        btn_get= (Button) findViewById(R.id.btn_get);
        et_age= (EditText) findViewById(R.id.et_age);
        et_name= (EditText) findViewById(R.id.et_name);
        tv_show= (TextView) findViewById(R.id.tv_show);
        strUrl="http://172.18.237.8:8080/Android/testServlet";
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=et_name.getText().toString();
                String age=et_age.getText().toString();
                //param="name="+name+"&age="+age;
                Person person=new Person(name,Integer.parseInt(age));
                param=person.toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String response;
                        try {
                            response= HttpUtils.doGet(strUrl,param);
                        } catch (IOException e) {
                            e.printStackTrace();
                            response="网络请求失败，请稍后再试！";
                        }
                        Message message=myhandler.obtainMessage();
                        message.obj=response;
                        myhandler.sendMessage(message);
                    }
                }).start();
            }
        });
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=et_name.getText().toString();
                String age=et_age.getText().toString();
                Person person=new Person(name,Integer.parseInt(age));
                param=person.toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String response;
                        try {
                            response=HttpUtils.doPost(strUrl,param);
                        } catch (IOException e) {
                            e.printStackTrace();
                            response="网络请求失败，请稍后再试！";
                        }
                        Message message=myhandler.obtainMessage();
                        message.obj=response;
                        myhandler.sendMessage(message);
                    }
                }).start();
            }
        });
    }
}
