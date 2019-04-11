package com.example.rin.myapplication.login;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rin.myapplication.R;

/**
 * Created by Rin on 2018/11/3.
 */

public class zhaohuiimima extends AppCompatActivity {
    private EditText mima,remima,yonghuming;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhaohuimima);
        yonghuming= (EditText) findViewById(R.id.yonghuming);
        mima= (EditText) findViewById(R.id.mima);
        remima= (EditText) findViewById(R.id.quedingmima);
        dbHelper = new DBHelper(this, 4);

    }

    public void click1(View view) {
        switch (view.getId()) {
            case R.id.btnyes:
                String m1 = mima.getText().toString();
                String m2 = remima.getText().toString();
                if(CheckIsDataAlreadyInDBorNot(yonghuming.getText().toString())) {
                    if(mima.length()<6&&remima.length()<6){
                        Toast.makeText(this, "密码最少6位", Toast.LENGTH_LONG).show();
                    }else {
                        if (m1.equals(m2)) {
                            registerUserInfo(yonghuming.getText().toString(), mima.getText().toString());
                            Toast.makeText(this, "成功找回密码!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(this, login.class));
                        } else {
                            Toast.makeText(this, "两次密码不一致!", Toast.LENGTH_LONG).show();
                        }
                    }
                }else {
                    Toast.makeText(this, "该用户不存在!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private boolean registerUserInfo(String username, String userpassword) {
           SQLiteDatabase db = dbHelper.getWritableDatabase();
           String Query = "update usertable set password =? where username =?";
           Cursor cursor = db.rawQuery(Query, new String[]{userpassword, username});
           if (cursor.getCount() > 0) {
               cursor.close();
               return true;
           }
       return false;
    }
    public boolean CheckIsDataAlreadyInDBorNot(String value) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String Query = "Select * from usertable where username =?";
        Cursor cursor = db.rawQuery(Query, new String[]{value});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
}
