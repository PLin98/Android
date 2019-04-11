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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText userNameTxt;
    EditText passwordTxt;
    Button loginBtn;
    TextView tipsTv;
    Button button;
    Button findpassword;
    private String strUrl,name,password,param,response;
    int flag=0;
//    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameTxt = (EditText)findViewById(R.id.userNameTxt);
        passwordTxt = (EditText)findViewById(R.id.passwordTxt);
        tipsTv = (TextView) findViewById(R.id.tipsTxt);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        button = (Button)findViewById(R.id.button);
        findpassword=(Button)findViewById(R.id.findpassword);
        strUrl = "http://172.18.237.8:8080/ChooseCourse/loginServlet";

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=userNameTxt.getText().toString();
                password=passwordTxt.getText().toString();
                Log.i("输出",name+"");
                TeacherUser teacherUser = new TeacherUser(name,password);
                param=teacherUser.toString();
                Log.i("输出param",param+"");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            response=HttpUtils.doGet(strUrl,param);
                            Log.i("输出response",response+"");

                            if (response.equals("1"))
                            {
                                flag=1;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //2、UI线程显示服务器相应结果
                        Message msg = myhandler.obtainMessage();
                        msg.what = flag;
                        Log.i("输出msg",msg.what+"");
                        myhandler.sendMessage(msg);
                    }
                }).start();
            }
        });

    }

    @SuppressLint("HandlerLeak")
    Handler myhandler = new Handler(){
        @SuppressLint({"ShowToast", "ApplySharedPref"})
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    SharedPreferences sp = getSharedPreferences("username", 0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("USER_NAME", name);
                    editor.putString("PASSWORD", password);
                    editor.apply();
                    Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_LONG);
                    Intent intent=new Intent(MainActivity.this,chakan.class);
                    startActivity(intent);
                    break;
                case 0:
                    String str=(String)msg.obj;
                    tipsTv.setText(str+"用户名不存在或密码不正确");
                    break;
            }
        }
    };
}
