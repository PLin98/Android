package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("Registered")
public class description extends AppCompatActivity {
    private TextView course_name,course_no,description,status,xianshi;
    private ListView list_stu;
    String response,strUrl;
    int course_no1;
    MyHandler myhandler;
    List<Choose> chooses=new ArrayList<Choose>();
    MyAdapter_stu adapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.des);

        course_name=(TextView)findViewById(R.id.course_name);
        course_no=(TextView)findViewById(R.id.course_no);
        description=(TextView)findViewById(R.id.description);
        status=(TextView)findViewById(R.id.status);
        xianshi=(TextView)findViewById(R.id.xianshi);
        list_stu=(ListView) findViewById(R.id.list_stu);

        strUrl="http://172.18.237.8:8080/ChooseCourse/chooseServlet";
        myhandler=new MyHandler();

        Intent intent = getIntent();
        String course_name1 = intent.getStringExtra("course_name");
        course_no1 = intent.getIntExtra("course_no",0);
        Log.i("输出course_no1",course_no1+"");
        String description1 = intent.getStringExtra("description");
        String status1 = intent.getStringExtra("status");
        String teacher_no = intent.getStringExtra("teacher_no");

        course_name.setText(course_name1);
        course_no.setText(course_no1+"");
        description.setText(description1);
        if(status1.equals("未审核"))
        {
            Log.i("未审核",""+course_no1);
            xianshi.setVisibility(View.INVISIBLE);
            status.setText("状态：未审核");
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        response = HttpUtils.doGet(strUrl, "course_no=" + course_no1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        response = null;
                    }
                    //向主线程发送Message
                    Message msg = myhandler.obtainMessage();
                    msg.obj = response;
                    myhandler.sendMessage(msg);
                }
            }).start();
        }
    }

    @SuppressLint("HandlerLeak")
    class MyHandler extends Handler {
        @SuppressLint("ShowToast")
        @Override
        public void handleMessage(Message msg) {
            if(response!=null){
                //解析JSON数据
                //JSONArrayString--》JSONArray
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject person = (JSONObject)jsonArray.get(i);
                        String student_no=person.getString("student_no");
                        String choose_time=person.getString("choose_time");
                        Choose choose=new Choose(student_no,choose_time);
                        chooses.add(choose);
                    }
                    //在LsitView中显示
                    adapter=new MyAdapter_stu(description.this, chooses);
                    list_stu.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(description.this,"网络请求失败，请重新尝试1",Toast.LENGTH_LONG);
                }
            }else{
                Toast.makeText(description.this,"网络请求失败，请重新尝试2",Toast.LENGTH_LONG);
            }
        }
    }
}
