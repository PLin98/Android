package com.example.rin.myapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rin.myapplication.R;
import com.example.rin.myapplication.article.addarticle;
import com.example.rin.myapplication.article.displayarticle;
import com.example.rin.myapplication.article.queryarticle;
import com.example.rin.myapplication.first.home;
import com.example.rin.myapplication.notes.notedisplay;

/**
 * Created by Rin on 2018/11/2.
 */

public class dengluhou extends AppCompatActivity{
    private TextView tvyonghuming;
    String username="";
    private DrawerLayout mDrawerlayout;
    private ListView left_list;
    private String[] titles={"修改密码","修改信息","添加文章","我的文章","查询文章","随手记","退出登录"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dengluhou);
        tvyonghuming= (TextView) findViewById(R.id.yonghuming);
        Bundle bundle=this.getIntent().getExtras();
        username=bundle.getString("用户名");
        tvyonghuming.setText(username);
        mDrawerlayout= (DrawerLayout) findViewById(R.id.mdrewerlayout);
        left_list= (ListView) findViewById(R.id.left_list);
        left_list.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,android.R.id.text1,titles));
        left_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //动态插入一个fragment到framlayout
                if(titles[position].equals("修改密码")){
                    Intent intent2=new Intent();
                    Bundle bundle2=new Bundle();
                    bundle2.putString("用户名",username);
                    intent2.putExtras(bundle2);
                    intent2.setClass(dengluhou.this,password.class);
                    startActivity(intent2);
                    //finish();
                }
                else if(titles[position].equals("修改信息")){
                    Intent intent2=new Intent();
                    Bundle bundle2=new Bundle();
                    bundle2.putString("用户名",username);
                    intent2.putExtras(bundle2);
                    intent2.setClass(dengluhou.this,xiugai.class);
                    startActivity(intent2);
                    //finish();
                }
                else if(titles[position].equals("添加文章")){
                    Intent intent2=new Intent();
                    Bundle bundle2=new Bundle();
                    bundle2.putString("用户名",username);
                    intent2.putExtras(bundle2);
                    intent2.setClass(dengluhou.this,addarticle.class);
                    startActivity(intent2);
                    //finish();
                }
                else if(titles[position].equals("我的文章")){
                    Intent intent2=new Intent();
                    Bundle bundle2=new Bundle();
                    bundle2.putString("用户名",username);
                    intent2.putExtras(bundle2);
                    intent2.setClass(dengluhou.this,displayarticle.class);
                    startActivity(intent2);
                    //finish();
                }
                else if(titles[position].equals("查询文章")){
                    Intent intent2=new Intent();
                    Bundle bundle2=new Bundle();
                    bundle2.putString("用户名",username);
                    intent2.putExtras(bundle2);
                    intent2.setClass(dengluhou.this,queryarticle.class);
                    startActivity(intent2);
                    //finish();
                }
                else if(titles[position].equals("随手记")){
                    Intent intent2=new Intent();
                    Bundle bundle2=new Bundle();
                    bundle2.putString("用户名",username);
                    intent2.putExtras(bundle2);
                    intent2.setClass(dengluhou.this,notedisplay.class);
                    startActivity(intent2);
                    //finish();
                }else if(titles[position].equals("退出登录")){
                    Intent intent2=new Intent();
                    Bundle bundle2=new Bundle();
                    bundle2.putString("用户名",username);
                    intent2.putExtras(bundle2);
                    intent2.setClass(dengluhou.this,login.class);
                    startActivity(intent2);
                    //finish();
                }

                mDrawerlayout.closeDrawer(left_list);
            }
        });
    }

    /*cpublic void click(View view) {
        switch (view.getId()){
            case R.id.l1:
                Intent intent0=new Intent();
                Bundle bundle0=new Bundle();
                bundle0.putString("用户名",username);
                intent0.putExtras(bundle0);
                intent0.setClass(this,xiugai.class);
                startActivity(intent0);
                //finish();
                break;
            case R.id.l2:
                Intent intent1=new Intent();
                Bundle bundle1=new Bundle();
                bundle1.putString("用户名",username);
                intent1.putExtras(bundle1);
                intent1.setClass(this,password.class);
                startActivity(intent1);
                //finish();
                break;
            ase R.id.l3:
                Intent intent2=new Intent();
                Bundle bundle2=new Bundle();
                bundle2.putString("用户名",username);
                intent2.putExtras(bundle2);
                intent2.setClass(this,addarticle.class);
                startActivity(intent2);
                //finish();
                break;
            case R.id.l4:
                Intent intent3=new Intent();
                Bundle bundle3=new Bundle();
                bundle3.putString("用户名",username);
                intent3.putExtras(bundle3);
                intent3.setClass(this,displayarticle.class);
                startActivity(intent3);
                //finish();
                break;
            case R.id.l5:
                Intent intent4=new Intent();
                Bundle bundle4=new Bundle();
                bundle4.putString("用户名",username);
                intent4.putExtras(bundle4);
                intent4.setClass(this,queryarticle.class);
                startActivity(intent4);
                //finish();
                break;
            case R.id.l6:
                Intent intent5=new Intent();
                Bundle bundle5=new Bundle();
                bundle5.putString("用户名",username);
                intent5.putExtras(bundle5);
                intent5.setClass(this,notedisplay.class);
                startActivity(intent5);
                //finish();
                break;
            case R.id.tuichu:
                startActivity(new Intent(this,login.class));
                //finish();
                break;
        }
    }*/
}
