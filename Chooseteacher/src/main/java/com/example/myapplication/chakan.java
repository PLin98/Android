package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class chakan extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String strUrl, name, password, param, response;
    private ListView list;
    private SharedPreferences sp;
    List<Teacher> teacherList=new ArrayList<Teacher>();
    MyAdapter adapter;
    MyHandler myhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chakan);

        strUrl="http://172.18.237.8:8080/ChooseCourse/teacherServlet";
        list=(ListView)findViewById(R.id.list);
        list.setOnItemClickListener(this);
        sp = this.getSharedPreferences("username", 0);
        name=sp.getString("USER_NAME", "");

        myhandler=new MyHandler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = HttpUtils.doGet(strUrl,"username="+name);
                } catch (Exception e) {
                    e.printStackTrace();
                    response=null;
                }
                //向主线程发送Message
                Message msg = myhandler.obtainMessage();
                msg.obj = response;
                myhandler.sendMessage(msg);

//                myhandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(response!=null){
//                            //解析JSON数据
//                            //JSONArrayString--》JSONArray
//                            try {
//                                JSONArray jsonArray=new JSONArray(response);
//                                for(int i=0;i<jsonArray.length();i++){
//                                    JSONObject person = (JSONObject)jsonArray.get(i);
//                                    String teacher_no=person.getString("Teacher_no");
//                                    int course_no=person.getInt("course_no");
//                                    String course_name=person.getString("Course_name");
//                                    String Description=person.getString("Description");
//                                    String Status=person.getString("Status");
//                                    Teacher teacher=new Teacher(teacher_no,course_name,course_no,Description,Status);
//                                    teacherList.add(teacher);
//                                }
//                                //在LsitView中显示
//                                adapter=new MyAdapter(chakan.this, teacherList);
//                                list.setAdapter(adapter);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                                Toast.makeText(chakan.this,"网络请求失败，请重新尝试1",Toast.LENGTH_LONG);
//                            }
//                        }else{
//                            Toast.makeText(chakan.this,"网络请求失败，请重新尝试2",Toast.LENGTH_LONG);
//                        }
//                    }
//                });
            }
        }).start();


    }

    @SuppressLint("HandlerLeak")
    class MyHandler extends Handler{
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
                        String teacher_no=person.getString("Teacher_no");
                        Log.i("输出Teacher_no",teacher_no);
                        int course_no=person.getInt("course_no");
                        String course_name=person.getString("Course_name");
                        String Description=person.getString("Description");
                        String Status=person.getString("Status");
                        Teacher teacher=new Teacher(teacher_no,course_name,course_no,Description,Status);
                        teacherList.add(teacher);
                        }
                        //在LsitView中显示
                    adapter=new MyAdapter(chakan.this, teacherList);
                    list.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(chakan.this,"网络请求失败，请重新尝试1",Toast.LENGTH_LONG);
                }
            }else{
                Toast.makeText(chakan.this,"网络请求失败，请重新尝试2",Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(chakan.this, description.class);
        intent.putExtra("course_name", teacherList.get(position).getCourse_name());
        intent.putExtra("course_no", teacherList.get(position).getCourse_no());
        intent.putExtra("description",teacherList.get(position).getDescription());
        intent.putExtra("status",teacherList.get(position).getStatus());
        intent.putExtra("teacher_no",teacherList.get(position).getTeacher_no());
        Log.i("course_name", teacherList.get(position).getCourse_name());
        Log.i("course_no", ""+teacherList.get(position).getCourse_no());
        startActivity(intent);
    }
}