package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {

    private TextView tvMain;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();
    }

    private void initView() {
        tvMain = (TextView) findViewById(R.id.tv_json);
        tvMain.setMovementMethod(ScrollingMovementMethod.getInstance());
        mRequestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(
                "http://t.weather.sojson.com/api/weather/city/101030100",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String time = response.getString("time") + "\n";
                            //Log.i("======time",time);
                            JSONObject cityInfo = new JSONObject(response.getString("cityInfo"));
                            String date0 =response.getString("date") + "\n";
                           // Log.i("======date0",date0);
                            String message = response.getString("message") + "\n";
                            //Log.i("======message",message);
                            String status = response.getString("status") + "\n";
                            //Log.i("======status",status);
                            JSONObject data = new JSONObject(response.getString("data"));

                            String city = cityInfo.getString("city") + "\n";
                            //Log.i("======city",city);
                            String cityId = cityInfo.getString("cityId") + "\n";
                            //Log.i("======cityId",cityId);
                            String parent = cityInfo.getString("parent") + "\n";
                           // Log.i("======parent",parent);
                            String updateTime = cityInfo.getString("updateTime") + "\n";
                           // Log.i("======updateTime",updateTime);

                            String shidu = data.getString("shidu") + "\n";
                           // Log.i("======shidu",shidu);
                            String pm25 = data.getString("pm25") + "\n";
                           // Log.i("======pm25",pm25);
                            String pm10 = data.getString("pm10") + "\n";
                           // Log.i("======pm10",pm10);
                            String quality = data.getString("quality") + "\n";
                           // Log.i("======quality",quality);
                            String wendu = data.getString("wendu") + "\n";
                           // Log.i("======wendu",wendu);
                            String ganmao = data.getString("ganmao") + "\n";
                           // Log.i("======ganmao",ganmao);

                            String yesterday = data.getString("yesterday") + "\n";
                            JSONObject yesterday1 = new JSONObject(yesterday) ;
                            String date = yesterday1.getString("date") + "\n";
                            String sunrise = yesterday1.getString("sunrise") + "\n";
                            String high = yesterday1.getString("high") + "\n";
                            String low = yesterday1.getString("low") + "\n";
                            String sunset = yesterday1.getString("sunset") + "\n";
                            String aqi = yesterday1.getString("aqi") + "\n";
                            String ymd = yesterday1.getString("ymd") + "\n";
                            String week = yesterday1.getString("week") + "\n";
                            String fx = yesterday1.getString("fx") + "\n";
                            String fl = yesterday1.getString("fl") + "\n";
                            String type = yesterday1.getString("type") + "\n";
                            String notice = yesterday1.getString("notice") + "\n";



                            JSONArray forecast = data.getJSONArray("forecast");
                            for(int i=0;i<forecast.length();i++){

                                JSONObject forecast1 = forecast.getJSONObject(i);
                                //forecast.getJSONObject(i).getString("date")+ "\n"
                                String date1 = forecast1.getString("date") + "\n";
                                String sunrise1 = forecast1.getString("sunrise") + "\n";
                                String high1 = forecast1.getString("high") + "\n";
                                String low1 = forecast1.getString("low") + "\n";
                                String sunset1 = forecast1.getString("sunset") + "\n";
                                String aqi1 = forecast1.optString("aqi") + "\n";
                                String ymd1 = forecast1.getString("ymd") + "\n";
                                String week1 = forecast1.getString("week") + "\n";
                                String fx1 = forecast1.getString("fx") + "\n";
                                String fl1 = forecast1.getString("fl") + "\n";
                                String type1 = forecast1.getString("type")+ "\n";
                                String notice1 = forecast1.getString("notice") + "\n";
                                String str = ("date:"+date1+"sunrise:"+sunrise1+"high:"+high1+"low:"+low1+"sunset:"+sunset1+"aqi:"+aqi1+"ymd:"+ymd1+"week:"+week1+"fx:"+fx1+"fl:"+fl1+"type:"+type1+"notice:"+notice1);
                                //tvMain.setText(str+"\n");
                                //tvMain.setText("date1:"+date1+"sunrise1:"+sunrise1+"high1:"+high1+"low1:"+low1+"sunset1:"+sunset1+"aqi1:"+aqi1+"ymd1:"+ymd1+"week1:"+week1+"fx1:"+fx1+"fl1:"+fl1+"type1:"+type1+"notice1:"+notice1);
                                /*tvMain.setText("date:"+forecast.getJSONObject(i).getString("date")+ "\n"+"sunrise:"+forecast.getJSONObject(i).getString("sunrise")+ "\n"+"high:"+forecast.getJSONObject(i).getString("high")+ "\n"+"low:"+forecast.getJSONObject(i).getString("low")+ "\n"+
                                        "sunset:"+forecast.getJSONObject(i).getString("sunset")+ "\n"+"aqi:"+forecast.getJSONObject(i).optString("aqi")+ "\n"+"ymd:"+forecast.getJSONObject(i).getString("ymd")+ "\n"+"week:"+forecast.getJSONObject(i).getString("week")+ "\n"+"fx:"+forecast.getJSONObject(i).getString("fx")+ "\n"+"fl:"+forecast.getJSONObject(i).getString("fl")+ "\n"+
                                        "type:"+forecast.getJSONObject(i).getString("type")+ "\n"+"notice:"+forecast.getJSONObject(i).getString("notice")+ "\n");*/
                                Log.i("++++++++++++","date:"+date1+"sunrise:"+sunrise1+"high:"+high1+"low:"+low1+"sunset:"+sunset1+"aqi:"+aqi1+"ymd:"+ymd1+"week:"+week1+"fx:"+fx1+"fl:"+fl1+"type:"+type1+"notice:"+notice1);
                            }
                            tvMain.setText("time:"+time +"city:"+ city+"cityId:" + cityId+"parent:" + parent+"updateTime:" + updateTime+"date0:"+date0+"message:"+message+
                                    "status:"+status+"shidu:"+shidu+"pm25:"+pm25+"pm10:"+pm10+"quality:"+quality+"wendu:"+wendu+"ganmao:"+ganmao+"date:"+date+"sunrise:"+sunrise
                                    +"high:"+high+"low:"+low+"sunset:"+sunset+"aqi:"+aqi+"ymd:"+ymd+"week:"+week+"fx:"+fx+"fl:"+fl+"type:"+type+"notice:"+notice);
                            Log.i("================","time:"+time +"city:"+ city+"cityId:" + cityId+"parent:" + parent+"updateTime:" + updateTime+"date0:"+date0+"message:"+message+
                                    "status:"+status+"shidu:"+shidu+"pm25:"+pm25+"pm10:"+pm10+"quality:"+quality+"wendu:"+wendu+"ganmao:"+ganmao+"date:"+date+"sunrise:"+sunrise
                                    +"high:"+high+"low:"+low+"sunset:"+sunset+"aqi:"+aqi+"ymd:"+ymd+"week:"+week+"fx:"+fx+"fl:"+fl+"type:"+type+"notice:"+notice);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });

        mRequestQueue.add(mJsonObjectRequest);
    }

}


