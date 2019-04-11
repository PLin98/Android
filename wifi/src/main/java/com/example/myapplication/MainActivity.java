package com.example.myapplication;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static TextView tv_wifiState,tv_ipAddress,tv_macAddress,tv_ssid,tv_linkSpeed;
    private static Button btn_enableWiFi,btn_disableWiFi;
    private WifiManager wifiManager = null; //Wi-Fi管理对象
    private static MyWifiInfo myWiFi = null; //记录Wi-Fi信息的对象
    private Thread myWifiInfoThread = null; //查询WiFi状态信息的线程
    private static Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        tv_wifiState= (TextView) findViewById(R.id.tv_wifiState);
        tv_ipAddress= (TextView) findViewById(R.id.tv_ipAddress);
        tv_ssid= (TextView) findViewById(R.id.tv_ssid);
        tv_linkSpeed= (TextView) findViewById(R.id.tv_linkSpeed);
        btn_enableWiFi= (Button) findViewById(R.id.btn_enableWiFi);
        btn_disableWiFi= (Button) findViewById(R.id.btn_disableWiFi);

        btn_enableWiFi.setOnClickListener(btnListener);
        btn_disableWiFi.setOnClickListener(btnListener);

        //获取WIFI_SERVICE系统服务，得到WiFi管理对象
        wifiManager= (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        //创建查询WiFi状态的线程

        myWifiInfoThread=new Thread(null,inquireWifi,"InquireWifiThread");



    }
    protected  void onStart(){
        super.onStart();
        //启动线程
        if(!myWifiInfoThread.isAlive()){
            myWifiInfoThread.start();
        }
    }
    protected  void onDestroy(){
        super.onDestroy();
        //启动线程
       myWifiInfoThread.interrupt();
    }
    Button.OnClickListener btnListener=new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_enableWiFi:
                    wifiManager.setWifiEnabled(true);
                    break;
                case R.id.btn_disableWiFi:
                    wifiManager.setWifiEnabled(false);
                    break;
            }
        }
    };
    //查询WiFi状态的线程
    private Runnable inquireWifi=new Runnable() {
        @Override
        public void run() {
                try {
                    while (!Thread.interrupted()){
                        //查询WiFi状态及信息
                        MyWifiInfo theWFInfo=getMyWifiInfo(MainActivity.this);
                        //将查询后的WIFI状态更新到主界面控件
                        UpdateWifiInfo(theWFInfo);
                        //休眠1秒
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();

            }
        }
    };

    private void UpdateWifiInfo(MyWifiInfo object) {

        myWiFi=object;

        handler.post(new Runnable() {
            @Override
            public void run() {
                tv_ipAddress.setText("");
                tv_linkSpeed.setText("");
                tv_linkSpeed.setText("");
                switch (myWiFi.wifiState){
                    case WifiManager.WIFI_STATE_DISABLING:
                        tv_wifiState.setText("WIFI状态：正在关闭...");
                        break;
                    case WifiManager.WIFI_STATE_DISABLED:
                        tv_wifiState.setText("WIFI状态：关闭");
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        tv_wifiState.setText("WIFI状态：正在开启...");
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        tv_wifiState.setText("WIFI状态：开启");
                        tv_ipAddress.setText(myWiFi.ipAddress);
                        tv_ssid.setText(myWiFi.ssid);
                        tv_linkSpeed.setText(Integer.toString(myWiFi.linkSpeed)+"Mbps");
                        break;
                }
            }
        });
    }
    //获取WiFi信息
    public MyWifiInfo getMyWifiInfo(Context context){
        MyWifiInfo myWFInfo=new MyWifiInfo();
        //获得WIFI连接状态
        myWFInfo.wifiState=wifiManager.getWifiState();
        if(myWFInfo.wifiState==WifiManager.WIFI_STATE_ENABLED){ //WIFI 可用
            //获得WIFI信息对象
            WifiInfo info=wifiManager.getConnectionInfo();
            myWFInfo.ssid=info.getSSID();
            //获得本地IP地址：本机在WIFI状态下路由分配给的IP地址，返回的事一个整数
            int ipAddress=info.getIpAddress();
            myWFInfo.ipAddress=intToIp(ipAddress);
            myWFInfo.linkSpeed=info.getLinkSpeed();
        }
        return myWFInfo;
    }
    //将整数的IP地址转换为点分十进制表示的IP地址
    public String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 24) & 0xFF);
    }
    public class MyWifiInfo{
        public int wifiState;
        public String ipAddress;
        public String ssid;
        public int linkSpeed;
    }
}
