package com.example.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private ListView show_list;
    private ArrayAdapter adapter;
    private List<String> arrlist=new ArrayList<String>();
    private ProgressDialog progressDialog;
    private String arrcity[]={"北京","天津","上海","重庆"};
    private Button btn_yes;
    private Spinner spinner;
    private String choice,city,time,str;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show_list= (ListView) findViewById(R.id.show_list);
        spinner= (Spinner) findViewById(R.id.spinner);
        btn_yes= (Button) findViewById(R.id.btn_yes);

        sharedPreferences= getSharedPreferences("city", MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("北京","101010100");
        editor.putString("天津","101030100");
        editor.putString("上海","101020100");
        editor.putString("重庆","101040100");
        editor.commit();
        ArrayAdapter adaptercity = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, arrcity);
        spinner.setAdapter(adaptercity);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice = arrcity[position];
                Log.i("++++++++city",choice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                choice = arrcity[0];
            }
        });



        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrlist);
        show_list.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("提示");
                progressDialog.setMessage("正在获取网络上的天气预报信息");
                progressDialog.show();
                city=sharedPreferences.getString(choice,"");
                Log.i("++++++++city",city);
                downloadWeatherDataInfo();
            }
        });

    }


    /**
     * 访问https://www.sojson.com/open/api/weather/json.shtml?city=北京
     * 获取北京天气预报数据信息
     */
    public void downloadWeatherDataInfo() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    //String str="101030100";
                    // 把字符串地址包装成网络地址
                    str="http://t.weather.sojson.com/api/weather/city/"+"101030100";
                    Log.i("+++++++++++++++++",str);
                    URL url = new URL(str);
                    // 打开连接对象
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    // 设置连接超时时长
                    httpURLConnection.setConnectTimeout(5000);
                    // 设置请求方式Get
                    httpURLConnection.setRequestMethod("GET");

                    // 注意：是执行httpURLConnection.getResponseCode()的时候，才开始向服务器发送请求
                    int code = httpURLConnection.getResponseCode();
                    Log.d(TAG, ">>> run: code:" + code);

                    /**
                     * 现在由于访问受限，只能模拟去读文件里面的JSON数据，实际上和读取网络数据一模一样
                     */
                    code = 200;

                    if (code == HttpURLConnection.HTTP_OK) {
                        //实时数据
                        InputStream in=httpURLConnection.getInputStream();
                        // 下面对获取到的输入流进行读取
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        System.out.println("response=" + response.toString());
                        Message message1 = new Message();
                        message1.what = REQUEST_SUCCESS;
                        // 将服务器返回的结果存放到Message中
                        message1.obj = response.toString();
                        mHandler.sendMessage(message1);

                        //静态数据
                        /*InputStream fis = getAssets().open("weather.北京.json");
                        byte[] bytes = new byte[fis.available()];
                        fis.read(bytes);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        baos.write(bytes, 0, bytes.length);
                        Message message = mHandler.obtainMessage();
                        message.what = REQUEST_SUCCESS;
                        message.obj = new String(baos.toByteArray());

                        mHandler.sendMessageDelayed(message, 1500);*/ // delayMillis 是为了模仿网络弱
                    } else {
                        mHandler.sendEmptyMessageDelayed(REQUEST_ERROR, 1500); // delayMillis 是为了模仿网络弱
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(REQUEST_ERROR);
                }
            }
        }.start();
    }

    private final int REQUEST_SUCCESS = 200;
    private final int REQUEST_ERROR = 400;
    private final int CLOSE = 1000;

    /**
     * Handler与子线程通讯
     */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REQUEST_ERROR:
                    progressDialog.setMessage("请求错误！");
                    mHandler.sendEmptyMessageDelayed(CLOSE, 1500); // delayMillis 是为了模仿网络弱
                    Log.d(TAG, "请求错误！");
                    break;
                case REQUEST_SUCCESS:
                    progressDialog.setMessage("恭喜，请求成功");
                    String successResult = (String) msg.obj;
                    handlerSuccessMethod(successResult);
                    arrlist.add("time:"+time);
                    mHandler.sendEmptyMessageDelayed(CLOSE, 1500); // delayMillis 是为了模仿网络弱
                    Log.d(TAG, "恭喜，请求成功");
                    break;
                case CLOSE:
                    progressDialog.dismiss();
                    Log.d(TAG, "CLOSE");
                    break;
            }
        }
    };

    /**
     * 请求成功后，拿到JSON数据
     * @param successResult JSON数据
     */
    private void handlerSuccessMethod(String successResult) {
        Log.d(TAG, "handlerSuccessMethod successResult:" + successResult);

        try{
            // 整体最大的JSON对象
            JSONObject jsonObjectALL = new JSONObject(successResult);
            time=jsonObjectALL.optString("time", null);
            String date=jsonObjectALL.optString("date", null);
            String message=jsonObjectALL.optString("message", null);
            String status=jsonObjectALL.optString("status", null);

            arrlist.add("time:"+time);
            arrlist.add("date:"+date);
            arrlist.add("message:"+message);
            arrlist.add("status:"+status);
            Log.d(TAG, "解析后的数据：time：" + jsonObjectALL.optString("time", null));
            Log.d(TAG, "解析后的数据：date：" + jsonObjectALL.optString("date", null));
            Log.d(TAG, "解析后的数据：message：" + jsonObjectALL.optString("message", null));
            Log.d(TAG, "解析后的数据：status：" + jsonObjectALL.optString("status", null)); // 真实开发中要判断status==200




            // data JSON 对象
            String cityInfo = jsonObjectALL.optString("cityInfo", null);
            JSONObject cityInfoJSONObject = new JSONObject(cityInfo);
            Log.d(TAG, "解析后的数据：city：" + cityInfoJSONObject.optString("city", null));
            Log.d(TAG, "解析后的数据：cityId：" + cityInfoJSONObject.optString("cityId", null));
            Log.d(TAG, "解析后的数据：parent：" + cityInfoJSONObject.optString("parent", null));
            Log.d(TAG, "解析后的数据：updateTime：" + cityInfoJSONObject.optString("updateTime", null));
            String city=jsonObjectALL.optString("city", null);
            String cityId=jsonObjectALL.optString("cityId", null);
            String parent=jsonObjectALL.optString("parent", null);
            String updateTime=jsonObjectALL.optString("updateTime", null);
            arrlist.add("city:"+city);
            arrlist.add("cityId:"+cityId);
            arrlist.add("updateTime:"+updateTime);
            arrlist.add("parent:"+parent);

            // data JSON 对象
            String data = jsonObjectALL.optString("data", null);
            JSONObject dataJSONObject = new JSONObject(data);
            Log.d(TAG, "解析后的数据：shidu：" + dataJSONObject.optString("shidu", null));
            Log.d(TAG, "解析后的数据：pm25：" + dataJSONObject.optString("pm25", null));
            Log.d(TAG, "解析后的数据：pm10：" + dataJSONObject.optString("pm10", null));
            Log.d(TAG, "解析后的数据：quality：" + dataJSONObject.optString("quality", null));
            Log.d(TAG, "解析后的数据：wendu：" + dataJSONObject.optString("wendu", null));
            Log.d(TAG, "解析后的数据：ganmao：" + dataJSONObject.optString("ganmao", null));
            String shidu=jsonObjectALL.optString("shidu", null);
            String pm25=jsonObjectALL.optString("pm25", null);
            String pm10=jsonObjectALL.optString("pm10", null);
            String quality=jsonObjectALL.optString("quality", null);
            String wendu=jsonObjectALL.optString("wendu", null);
            String ganmao=jsonObjectALL.optString("ganmao", null);
            arrlist.add("shidu:"+shidu);
            arrlist.add("pm25:"+pm25);
            arrlist.add("pm10:"+pm10);
            arrlist.add("quality:"+quality);
            arrlist.add("wendu:"+wendu);
            arrlist.add("ganmao:"+ganmao);

            // yesterday JSON 对象
            String yesterday = dataJSONObject.optString("yesterday",null);
            JSONObject yesterdayJSONObject = new JSONObject(yesterday);

            Log.d(TAG, "解析后的数据：date：" + yesterdayJSONObject.optString("date", null));
            Log.d(TAG, "解析后的数据：sunrise：" + yesterdayJSONObject.optString("sunrise", null));
            Log.d(TAG, "解析后的数据：high：" + yesterdayJSONObject.optString("high", null));
            Log.d(TAG, "解析后的数据：low：" + yesterdayJSONObject.optString("low", null));
            Log.d(TAG, "解析后的数据：sunset：" + yesterdayJSONObject.optString("sunset", null));
            Log.d(TAG, "解析后的数据：aqi：" + yesterdayJSONObject.optString("aqi", null));
            Log.d(TAG, "解析后的数据：ymd：" + yesterdayJSONObject.optString("ymd", null));
            Log.d(TAG, "解析后的数据：week：" + yesterdayJSONObject.optString("week", null));
            Log.d(TAG, "解析后的数据：fx：" + yesterdayJSONObject.optString("fx", null));
            Log.d(TAG, "解析后的数据：fl：" + yesterdayJSONObject.optString("fl", null));
            Log.d(TAG, "解析后的数据：type：" + yesterdayJSONObject.optString("type", null));
            Log.d(TAG, "解析后的数据：notice：" + yesterdayJSONObject.optString("notice", null));
            String date1=jsonObjectALL.optString("date", null);
            String sunrise=jsonObjectALL.optString("sunrise", null);
            String high=jsonObjectALL.optString("high", null);
            String low=jsonObjectALL.optString("low", null);
            String sunset=jsonObjectALL.optString("sunset", null);
            String aqi=jsonObjectALL.optString("aqi", null);
            String ymd=jsonObjectALL.optString("ymd", null);
            String week=jsonObjectALL.optString("week", null);
            String fx=jsonObjectALL.optString("fx", null);
            String fl=jsonObjectALL.optString("fl", null);
            String type=jsonObjectALL.optString("type", null);
            String notice=jsonObjectALL.optString("notice", null);
            arrlist.add("date:"+date1);
            arrlist.add("sunrise:"+sunrise);
            arrlist.add("high:"+high);
            arrlist.add("low:"+low);
            arrlist.add("sunset:"+sunset);
            arrlist.add("aqi:"+aqi);
            arrlist.add("ymd:"+ymd);
            arrlist.add("week:"+week);
            arrlist.add("fx:"+fx);
            arrlist.add("fl:"+quality);
            arrlist.add("type:"+type);
            arrlist.add("notice:"+notice);

            // forecast JSON [数组] 这种有规律的 JSON数组，我决定用 Gson 来得到集合
            // 注意：forecast 的标记key 是data
            String forecastJSONArray = dataJSONObject.optString("forecast", null);
            Gson gson = new Gson();
            List<Forecast> list = gson.fromJson(forecastJSONArray, new TypeToken<List<Forecast>>(){}.getType());
            for (int i=0;i<list.size();i++) {
                Log.d(TAG, "解析后的数据 f.toString:" + list.get(i).toString());
                //arrlist.add(list.get(i).toString());
            }
            /*for (Forecast f:list){
                Log.d(TAG, "解析后的数据 f.toString:" + f.toString());
            }*/
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
