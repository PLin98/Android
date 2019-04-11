package com.example.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rin on 2019/3/17.
 */

public class JsonActivity extends AppCompatActivity{
    private TextView tv_json;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsonlayout);
        tv_json= (TextView) findViewById(R.id.tv_json);
        JSONObject per=generate();
        try {
            String name=per.getString("name");
            int age=per.getInt("age");
            JSONArray phoneArr=per.getJSONArray("phone");
            String phone="";
            for(int i=0;i<phoneArr.length();i++){
                phone+=phoneArr.getString(i)+"\n ";
            }
            tv_json.setText(name+"\n"+age+"\n"+phone);
            Log.i("====name",name);
            Log.i("====age",age+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject generate() {
        JSONObject person=new JSONObject();
        try {
            person.put("name","玛丽");
            person.put("age",18);
            JSONArray phone=new JSONArray();
            phone.put("18811613356");
            phone.put("010-86457812");
            person.put("phone",phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return person;
    }
}
