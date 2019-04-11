package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText shuru;
    ImageButton btn;
    TextView textview,shidu,pm2,pm10,quality,wendu,ganmao,updateTime;
    TextView shidu2,pm22,pm102,quality2,wendu2,ganmao2,forecast;
    String strUrl, response, shuru1, resultString, citycode1;
    MyHandler myHandler;
    ListView lv_city;
    myAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shuru = (EditText) findViewById(R.id.shuru);
        btn = (ImageButton) findViewById(R.id.btn);
        textview = (TextView) findViewById(R.id.textview);
        shidu = (TextView) findViewById(R.id.shidu);
        pm2 = (TextView) findViewById(R.id.pm2);
        pm10 = (TextView) findViewById(R.id.pm10);
        quality = (TextView) findViewById(R.id.quality);
        wendu = (TextView) findViewById(R.id.wendu);
        ganmao = (TextView) findViewById(R.id.ganmao);
        updateTime=(TextView)findViewById(R.id.updateTime);
        shidu2=(TextView)findViewById(R.id.shidu2);
        pm22=(TextView)findViewById(R.id.pm22);
        pm102=(TextView)findViewById(R.id.pm102);
        quality2=(TextView)findViewById(R.id.quality2);
        wendu2=(TextView)findViewById(R.id.wendu2);
        ganmao2=(TextView)findViewById(R.id.ganmao2);
        forecast=(TextView)findViewById(R.id.forecast);
        lv_city=(ListView)findViewById(R.id.lv_city);

        textview.setVisibility(View.INVISIBLE);
        shidu.setVisibility(View.INVISIBLE);
        pm2.setVisibility(View.INVISIBLE);
        pm10.setVisibility(View.INVISIBLE);
        quality.setVisibility(View.INVISIBLE);
        wendu.setVisibility(View.INVISIBLE);
        ganmao.setVisibility(View.INVISIBLE);
        updateTime.setVisibility(View.INVISIBLE);
        shidu2.setVisibility(View.INVISIBLE);
        pm22.setVisibility(View.INVISIBLE);
        pm102.setVisibility(View.INVISIBLE);
        quality2.setVisibility(View.INVISIBLE);
        wendu2.setVisibility(View.INVISIBLE);
        ganmao2.setVisibility(View.INVISIBLE);
        forecast.setVisibility(View.INVISIBLE);

        strUrl = "http://t.weather.sojson.com/api/weather/city/";

        myHandler = new MyHandler();

        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                shuru1 = shuru.getText().toString();
                if ("".equals(shuru1)) {
                    textview.setText("请输入城市");
                } else {
                    try {
                        citycode1 = readTextFromSDcard(shuru1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (citycode1 != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    response = HttpUtils.doGet(strUrl, citycode1);
                                    Log.i("输出response", response + "");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Message msg = myHandler.obtainMessage();
                                msg.obj = response;
                                Log.i("输出msg", msg.obj + "");
                                myHandler.sendMessage(msg);
                            }
                        }).start();
                    } else {
                        textview.setText("暂无城市信息");
                    }
                }
            }
        });
    }

    @SuppressLint("HandlerLeak")
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (response != null) {
                try {
                    //最外层json
                    JSONObject jsonObjectALL = new JSONObject(response);
                    //cityinfo层json
                    String cityInfo=jsonObjectALL.getString("cityInfo");
                    JSONObject cityy=new JSONObject(cityInfo);
                    String city=cityy.getString("city");
                    String updateTime1=cityy.getString("updateTime");
                    //data层json
                    String data=jsonObjectALL.getString("data");
                    JSONObject dataObject = new JSONObject(data);
                    String shidu1=dataObject.getString("shidu");
                    String pm251=dataObject.getString("pm25");
                    String pm101=dataObject.getString("pm10");
                    String quality1=dataObject.getString("quality");
                    String wendu1=dataObject.getString("wendu");
                    String ganmao1=dataObject.getString("ganmao");

                    //forecast层json
                    String forecastJSONArray = dataObject.optString("forecast", null);
                    Gson gson = new Gson();
                    List<forecast> list = gson.fromJson(forecastJSONArray, new TypeToken<List<forecast>>(){}.getType());

                    textview.setText(city);
                    shidu.setText(shidu1);
                    pm2.setText(pm251);
                    pm10.setText(pm101);
                    quality.setText(quality1);
                    wendu.setText(wendu1+"°");
                    ganmao.setText(ganmao1);
                    updateTime.setText("更新时间："+updateTime1);

                    textview.setVisibility(View.VISIBLE);
                    shidu.setVisibility(View.VISIBLE);
                    pm2.setVisibility(View.VISIBLE);
                    pm10.setVisibility(View.VISIBLE);
                    quality.setVisibility(View.VISIBLE);
                    wendu.setVisibility(View.VISIBLE);
                    ganmao.setVisibility(View.VISIBLE);
                    updateTime.setVisibility(View.VISIBLE);
                    shidu2.setVisibility(View.VISIBLE);
                    pm22.setVisibility(View.VISIBLE);
                    pm102.setVisibility(View.VISIBLE);
                    quality2.setVisibility(View.VISIBLE);
                    wendu2.setVisibility(View.VISIBLE);
                    ganmao2.setVisibility(View.VISIBLE);
                    forecast.setVisibility(View.VISIBLE);

                    adapter=new myAdapter(MainActivity.this,list);
                    lv_city.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //将传入的is一行一行解析读取出来出来
    private String readTextFromSDcard(String cityname) throws JSONException {
        InputStreamReader inputStreamReader;
        String citycode = null;
        try {
            inputStreamReader = new InputStreamReader(getAssets().open("city.json"), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStreamReader.close();
            bufferedReader.close();

            resultString = stringBuilder.toString();
            Log.i("TAG", stringBuilder.toString());

            JSONArray jsonArray = new JSONArray(resultString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject city = (JSONObject) jsonArray.get(i);
                String city_code = city.getString("city_code");
                String city_name = city.getString("city_name");
                if (cityname.equals(city_name)) {
                    citycode = city_code;
                    Log.i("citycode", city_code);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return citycode;
    }
}
