package com.example.myapplication.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private TextView tv_name,tv_age;
    private Button btn_parse;
    private ListView list_person;
    private String strUrl;
    private Handler myhandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        strUrl="http://172.18.237.8:8080/Android/jsonServlet";
        tv_name= (TextView) findViewById(R.id.et_name);
        tv_age= (TextView) findViewById(R.id.et_age);
        list_person= (ListView) findViewById(R.id.list_person);
        btn_parse= (Button) findViewById(R.id.btn_parse);
        btn_parse.setOnClickListener(new View.OnClickListener() {
            String response=null;
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            response= HttpUtils.doGet(strUrl,null);
                        } catch (IOException e) {
                            e.printStackTrace();
                            response=null;
                        }
                       myhandler.post(new Runnable() {
                           @Override
                           public void run() {
                                if(response!=null){
                                    try {

                                        JSONObject person=new JSONObject(response);
                                        String name=person.getString("name");
                                        int age=person.getInt("age");
                                        tv_name.setText(name);
                                        tv_age.setText(age);

                                       /*List<String> persons=new ArrayList<String>();
                                        JSONArray jsonArray=new JSONArray(response);
                                        for(int i=0;i<jsonArray.length();i++){
                                            JSONObject person= (JSONObject) jsonArray.get(i);
                                            String name=person.getString("name");
                                            int age=person.getInt("age");
                                            String p="name="+name+",age="+age;
                                            persons.add(p);
                                        }
                                        ArrayAdapter<String> spAdapter=new ArrayAdapter<String>(Main2Activity.this,android.R.layout.simple_spinner_item,persons);
                                        list_person.setAdapter(spAdapter);*/

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    Toast.makeText(Main2Activity.this,"网络请求失败，请稍后再试！",Toast.LENGTH_LONG).show();
                                }
                           }
                       });
                    }
                }).start();
            }
        });
    }
}
