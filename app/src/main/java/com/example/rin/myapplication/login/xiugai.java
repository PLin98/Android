package com.example.rin.myapplication.login;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rin.myapplication.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rin on 2018/11/2.
 */

public class xiugai extends AppCompatActivity {
    private TextView tv1,tv2,tv3,tv4,tv5;
    private EditText etcontent;
    private Button btnok;
    private Button btn1,btn2;
    int picWhich;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xiugai);
        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        tv3=(TextView)findViewById(R.id.tv3);
        tv4=(TextView)findViewById(R.id.tv4);
        tv5=(TextView)findViewById(R.id.tv5);
        btn1=(Button)findViewById(R.id.btn1);
        //btn2=(Button)findViewById(R.id.btn2);
        Bundle bundle=this.getIntent().getExtras();
        username=bundle.getString("用户名");
        tv5.setText(username);
        SharedPreferences sp=getSharedPreferences(username,MODE_APPEND);
        String nicheng = sp.getString("昵称","");
        String xingbie = sp.getString("性别","");
        String shengri = sp.getString("生日","");
        String shoujihao= sp.getString("手机号","");
        tv1.setText(nicheng);
        tv2.setText(xingbie);
        tv3.setText(shengri);
        tv4.setText(shoujihao);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void click(View view) {
        switch (view.getId()){
            case R.id.layout1:
                view= LayoutInflater.from(this).inflate(R.layout.zidingyi,null);
                //把view 标识上
                etcontent= (EditText) view.findViewById(R.id.etcontent);
                btnok= (Button) view.findViewById(R.id.btnqd);
                final AlertDialog mydlg=new AlertDialog.Builder(this)
                        .setView(view)
                        .create();
                mydlg.show();
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv1.setText(etcontent.getText().toString());
                        mydlg.dismiss();
                    }
                });
                break;
            case R.id.layout2:
                final String[] sitems={"男","女"};
                new AlertDialog.Builder(this)
                        .setTitle("请选择性别")
                        .setIcon(R.mipmap.ic_launcher)
                        .setSingleChoiceItems(sitems, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                picWhich=which;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv2.setText(sitems[picWhich]);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv2.setText(sitems[1]);
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.layout3:
                Calendar calendar=Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv3.setText(year+"年"+(month+1)+"月"+dayOfMonth+"日");
                    }
                },2018,10,20);
                dpd.show();
                break;
            case R.id.layout4:
                view= LayoutInflater.from(this).inflate(R.layout.zidingyi,null);
                //把view 标识上
                etcontent= (EditText) view.findViewById(R.id.etcontent);
                btnok= (Button) view.findViewById(R.id.btnqd);
                final AlertDialog mydlg2=new AlertDialog.Builder(this)
                        .setView(view)
                        .create();
                mydlg2.show();
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv4.setText(etcontent.getText().toString());
                        mydlg2.dismiss();
                    }
                });
                break;
            case R.id.btn1:
                SharedPreferences sp=getSharedPreferences(username,MODE_APPEND);
                SharedPreferences.Editor editor=sp.edit();
                String key1="昵称";
                String value1=tv1.getText().toString().trim();
                editor.putString(key1,value1);
                String key2="性别";
                String value2=tv2.getText().toString().trim();
                editor.putString(key2,value2);
                String key3="生日";
                String value3=tv3.getText().toString().trim();
                editor.putString(key3,value3);
                String key4="手机号";
                String value4=tv4.getText().toString().trim();
                editor.putString(key4,value4);
                editor.commit();

                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("用户名",username);
                intent.putExtras(bundle);
                intent.setClass(this,dengluhou.class);
                startActivity(intent);
               // finish();
                break;
            /*case R.id.btn2:
                Intent intent1=new Intent();
                Bundle bundle1=new Bundle();
                bundle1.putString("用户名",username);
                intent1.putExtras(bundle1);
                intent1.setClass(this,dengluhou.class);
                startActivity(intent1);
                break;*/
        }
    }
}
