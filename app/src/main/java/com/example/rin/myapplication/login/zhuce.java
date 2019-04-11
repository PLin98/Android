package com.example.rin.myapplication.login;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rin.myapplication.R;

/**
 * Created by Rin on 2018/11/2.
 */

public class zhuce extends AppCompatActivity {
    private EditText yonghuming,mima,remima;
    private Button zhuce;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_zhuce);
        yonghuming= (EditText) findViewById(R.id.yonghuming);
        yonghuming.setFilters(new InputFilter[]{new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,Spanned dest, int dstart, int dend) {
                        for (int i = start; i < end; i++) {
                            if (!Character.isLetterOrDigit(source.charAt(i)) && !Character.toString(source.charAt(i)).equals("_")) {
                                Toast.makeText(zhuce.this, "只能使用'_'、字母、数字、汉字注册！", Toast.LENGTH_SHORT).show();
                                return "";
                            }
                        }
                        return null;
                    }
                }
        });
        yonghuming.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {yonghuming.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(yonghuming.getWindowToken(), 0);
                }
                return false;
            }
        });
        mima= (EditText) findViewById(R.id.mima);
        remima= (EditText) findViewById(R.id.remima);
        dbHelper = new DBHelper(this, 4);
    }

    public void write(View view) {
        if (CheckIsDataAlreadyInDBorNot(yonghuming.getText().toString())) {
            Toast.makeText(this, "该用户名已被注册，注册失败!", Toast.LENGTH_SHORT).show();
        } else if (yonghuming.getText().toString()==null) {
            Toast.makeText(this, "用户名为空!", Toast.LENGTH_SHORT).show();
        }else if(mima.length()<6&&remima.length()<6) {
            Toast.makeText(this, "密码最少6位!", Toast.LENGTH_SHORT).show();
        }else{
            if (mima.getText().toString().trim().equals(remima.getText().toString())) {
                registerUserInfo(yonghuming.getText().toString(),mima.getText().toString());
                Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                Intent register_intent = new Intent(this,login.class);
                startActivity(register_intent);
            } else {
                Toast.makeText(this, "两次输入密码不同，请重新输入！",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    /**
     * 利用SharedPreferences进行默认登陆设置
     */
    private void saveUsersInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("UsersInfo", MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", yonghuming.getText().toString());
        //判断注册时的两次密码是否相同
        if (mima.getText().toString().equals(remima.getText().toString())) {
            editor.putString("password",mima.getText().toString());
        }
        editor.commit();
    }

    /**
     * 利用sql创建嵌入式数据库进行注册访问
     */
    private void registerUserInfo(String username, String userpassword) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", userpassword);
        db.insert("usertable", null, values);
        db.close();
    }

    /**
     * 检验用户名是否已经注册
     */
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
