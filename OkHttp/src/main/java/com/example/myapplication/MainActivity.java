package com.example.myapplication;


import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button btn_post,btn_get,btn_posta,btn_geta;
    private EditText et_name,et_age;
    private String param,strUrl;
    private TextView tv_show;

    Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.obj!=null) {
                String result = msg.obj.toString();
                tv_show.setText(msg.obj.toString());
                tv_show.setTextColor(Color.RED);
            }else
                Toast.makeText(MainActivity.this,"网络请求失败，请稍后再试！",Toast.LENGTH_LONG).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_post= (Button) findViewById(R.id.btn_post);
        btn_get= (Button) findViewById(R.id.btn_get);
        btn_posta= (Button) findViewById(R.id.btn_posta);
        btn_geta= (Button) findViewById(R.id.btn_geta);
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
                            response= OkHttpUtils.doGetSync(strUrl,param);
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
                tv_show.setText("");
                String name=et_name.getText().toString();
                String age=et_age.getText().toString();
                /*final HashMap<String,String> params=new HashMap<String, String>();
                params.put("name",name);
                params.put("age",age);*/
                final Person person=new Person(name,Integer.parseInt(age));
                param=person.toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String response;
                        try {
                            response=OkHttpUtils.doPostSync(strUrl,person);
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
        btn_geta.setOnClickListener(new View.OnClickListener() {
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
                            response= OkHttpUtils.doGetSync(strUrl,param);
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
        btn_posta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_show.setText("");
                String name=et_name.getText().toString();
                String age=et_age.getText().toString();
                /*final HashMap<String,String> params=new HashMap<String, String>();
                params.put("name",name);
                params.put("age",age);*/
                final Person person=new Person(name,Integer.parseInt(age));
                param=person.toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String response;
                        try {
                            response=OkHttpUtils.doPostSync(strUrl,person);
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
