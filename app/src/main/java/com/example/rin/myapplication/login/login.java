package com.example.rin.myapplication.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rin.myapplication.R;
import com.example.rin.myapplication.first.home;

public class login extends AppCompatActivity implements View.OnClickListener{
    private EditText etusername,etpassword;
    private AppCompatButton btnlogin,btnzhuce,btnmima;
    private DBHelper dbHelper;
    private CheckBox remember,auto;
    String username="";
    String password="";
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        initview();
        btnlogin.setOnClickListener(this);
        btnzhuce.setOnClickListener(this);
        btnmima.setOnClickListener(this);
        dbHelper = new DBHelper(this, 4);
        //获得实例对象
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        //判断记住密码多选框的状态
        if(sp.getBoolean("rememberchecked", false))
        {
            //设置默认是记录密码状态
            remember.setChecked(true);
            etusername.setText(sp.getString("username", ""));
            etpassword.setText(sp.getString("password", ""));
            //判断自动登陆多选框状态
            /*if(sp.getBoolean("autochecked", false))
            {
                //设置默认是自动登录状态
                auto.setChecked(true);
                //跳转界面
                Intent intent = new Intent(login.this,dengluhou.class);
                this.startActivity(intent);

            }*/
        }
        //监听记住密码多选框按钮事件
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (remember.isChecked()) {
                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("rememberchecked", true).commit();

                }else {
                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("rememberchecked", false).commit();

                }

            }
        });
        //监听自动登录多选框事件
        auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (auto.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("autochecked", true).commit();

                } else {
                    System.out.println("自动登录没有选中");
                    sp.edit().putBoolean("autochecked", false).commit();
                }
            }
        });
    }

    private void initview() {
        etusername= (EditText) findViewById(R.id.editusername);
        etpassword= (EditText) findViewById(R.id.editpassword);
        btnlogin= (AppCompatButton) findViewById(R.id.btnlogin);
        btnzhuce= (AppCompatButton) findViewById(R.id.btnzhuce);
        btnmima= (AppCompatButton) findViewById(R.id.btnmima);
        remember= (CheckBox) findViewById(R.id.remember);
        auto= (CheckBox) findViewById(R.id.auto);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnlogin:
                username = etusername.getText().toString();
                password = etpassword.getText().toString();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(login.this, "用户名或密码为空", Toast.LENGTH_LONG).show();
                } else {
                    readUserInfo();
                }
                break;
            case R.id.btnzhuce:
                startActivity(new Intent(this, zhuce.class));
                //();finish
                break;
            case R.id.btnmima:
                startActivity(new Intent(this, zhaohuiimima.class));
                //finish();
                break;
        }
    }
        /**
         * 读取SharedPreferences存储的键值对
         * */
    public void readUsersInfo(){
        SharedPreferences sharedPreferences = getSharedPreferences("UsersInfo",MODE_PRIVATE);
        username = sharedPreferences.getString("username","");
        password = sharedPreferences.getString("password","");
    }
    /**
     * 读取UserData.db中的用户信息
     * */
    protected void readUserInfo() {
        if (login(etusername.getText().toString(), etpassword.getText().toString())) {
            Toast.makeText(this, "登陆成功！", Toast.LENGTH_SHORT).show();
            //登录成功和记住密码框为选中状态才保存用户信息

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("用户名",etusername.getText().toString());
            intent.putExtras(bundle);
            intent.setClass(this, dengluhou.class);
            startActivity(intent);
            //finish();
        } else {
            Toast.makeText(this, "账户或密码错误，请重新输入！！", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 验证登录信息
     * */
    public boolean login(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "Select * from usertable where username=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username, password});
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        if(remember.isChecked())
        {
            //记住用户名、密码、
            editor.putString("password",password);
        }
        editor.commit();
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }

}

