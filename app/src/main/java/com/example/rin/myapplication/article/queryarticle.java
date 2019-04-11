package com.example.rin.myapplication.article;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.rin.myapplication.R;

/**
 * Created by Rin on 2018/12/28.
 */

public class queryarticle extends AppCompatActivity{
    private ListView listview;
    private DBHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor1,cursor2,cursor3;
    private SimpleCursorAdapter adapter;
    private EditText etquery;
    private Button btnquery0;
    String username;
    String et;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queryarticle);
        etquery= (EditText) findViewById(R.id.etquery);
        btnquery0= (Button) findViewById(R.id.btnquery0);
        listview= (ListView) findViewById(R.id.listview);
        helper = new DBHelper(this, 4);
        Bundle bundle=this.getIntent().getExtras();
        username=bundle.getString("用户名");
        db=helper.getReadableDatabase();

        btnquery0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qurey();
            }
        });
    }

    private void qurey() {
        et=etquery.getText().toString();
        Log.i("Test","et:"+et);
        cursor1=db.rawQuery("select * from article0 where name=?",new String[]{et});
        cursor2=db.rawQuery("select * from article0 where category=?",new String[]{et});
        cursor3=db.rawQuery("select * from article0 where title=?",new String[]{et});
        Log.i("Test","cursor:"+cursor1.getCount());
        if(cursor1.getCount()!=0){
            adapter = new SimpleCursorAdapter(this, R.layout.display, cursor1,
                    new String[]{DBHelper.A_ID,DBHelper.A_USERNAME, DBHelper.A_TITLE, DBHelper.A_CATEGORY},
                    new int[]{R.id.tvid,R.id.tvname, R.id.tvtitle, R.id.tvcategory}, 0);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    cursor1.moveToPosition(position);
                    String ids=cursor1.getString(cursor1.getColumnIndex(DBHelper.A_ID));
                    String name=cursor1.getString(cursor1.getColumnIndex(DBHelper.A_USERNAME));
                    String title=cursor1.getString(cursor1.getColumnIndex(DBHelper.A_TITLE));
                    String contents=cursor1.getString(cursor1.getColumnIndex(DBHelper.A_CONTENT));
                    String category=cursor1.getString(cursor1.getColumnIndex(DBHelper.A_CATEGORY));
                    String time=cursor1.getString(cursor1.getColumnIndex(DBHelper.A_TIME));
                    String img=cursor1.getString(cursor1.getColumnIndex(DBHelper.A_IMAGE));
                    Intent intent = null;
                    if(name.equals(username)) {
                        intent = new Intent(queryarticle.this, displaydetail.class);
                    }else{
                        intent=new Intent(queryarticle.this,displaydetail0.class);
                    }

                    //intent.putExtra("_id",ids);
                    intent.putExtra("name",name);
                    intent.putExtra("title",title);
                    intent.putExtra("contents",contents);
                    intent.putExtra("category",category);
                    intent.putExtra("time",time);
                    intent.putExtra("img",img);
                    intent.putExtra("ids",ids);
                    intent.putExtra("用户名",username);
                    startActivity(intent);
                }
            });
            if(Build.VERSION.SDK_INT < 14) {
                cursor2.close();
            }
        }else if (cursor2.getCount()!=0){
            adapter = new SimpleCursorAdapter(this, R.layout.display, cursor2,
                    new String[]{DBHelper.A_ID,DBHelper.A_USERNAME, DBHelper.A_TITLE, DBHelper.A_CATEGORY},
                    new int[]{R.id.tvid,R.id.tvname, R.id.tvtitle, R.id.tvcategory}, 0);
            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    cursor2.moveToPosition(position);
                    String ids=cursor2.getString(cursor2.getColumnIndex(DBHelper.A_ID));
                    String name=cursor2.getString(cursor2.getColumnIndex(DBHelper.A_USERNAME));
                    String title=cursor2.getString(cursor2.getColumnIndex(DBHelper.A_TITLE));
                    String contents=cursor2.getString(cursor2.getColumnIndex(DBHelper.A_CONTENT));
                    String category=cursor2.getString(cursor2.getColumnIndex(DBHelper.A_CATEGORY));
                    String time=cursor2.getString(cursor2.getColumnIndex(DBHelper.A_TIME));
                    String img=cursor2.getString(cursor2.getColumnIndex(DBHelper.A_IMAGE));
                    Intent intent = null;
                    if(name.equals(username)) {
                        intent = new Intent(queryarticle.this, displaydetail.class);
                    }else{
                        intent=new Intent(queryarticle.this,displaydetail0.class);
                    }

                    //intent.putExtra("_id",ids);
                    intent.putExtra("name",name);
                    intent.putExtra("title",title);
                    intent.putExtra("contents",contents);
                    intent.putExtra("category",category);
                    intent.putExtra("time",time);
                    intent.putExtra("img",img);
                    intent.putExtra("ids",ids);
                    intent.putExtra("用户名",username);
                    startActivity(intent);
                }
            });
            if(Build.VERSION.SDK_INT < 14) {
                cursor1.close();
            }
        }else if(cursor3.getCount()!=0){
            adapter = new SimpleCursorAdapter(this, R.layout.display, cursor3,
                    new String[]{DBHelper.A_ID,DBHelper.A_USERNAME, DBHelper.A_TITLE, DBHelper.A_CATEGORY},
                    new int[]{R.id.tvid,R.id.tvname, R.id.tvtitle, R.id.tvcategory}, 0);
            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    cursor3.moveToPosition(position);
                    String ids=cursor3.getString(cursor3.getColumnIndex(DBHelper.A_ID));
                    String name=cursor3.getString(cursor3.getColumnIndex(DBHelper.A_USERNAME));
                    String title=cursor3.getString(cursor3.getColumnIndex(DBHelper.A_TITLE));
                    String contents=cursor3.getString(cursor3.getColumnIndex(DBHelper.A_CONTENT));
                    String category=cursor3.getString(cursor3.getColumnIndex(DBHelper.A_CATEGORY));
                    String time=cursor3.getString(cursor3.getColumnIndex(DBHelper.A_TIME));
                    String img=cursor3.getString(cursor3.getColumnIndex(DBHelper.A_IMAGE));
                    Intent intent = null;
                    if(name.equals(username)) {
                        intent = new Intent(queryarticle.this, displaydetail.class);
                    }else{
                        intent=new Intent(queryarticle.this,displaydetail0.class);
                    }

                    //intent.putExtra("_id",ids);
                    intent.putExtra("name",name);
                    intent.putExtra("title",title);
                    intent.putExtra("contents",contents);
                    intent.putExtra("category",category);
                    intent.putExtra("time",time);
                    intent.putExtra("img",img);
                    intent.putExtra("ids",ids);
                    intent.putExtra("用户名",username);
                    startActivity(intent);
                }
            });
            if(Build.VERSION.SDK_INT < 14) {
                cursor3.close();
            }
        }else {
            Toast.makeText(this,"没有查询结果！",Toast.LENGTH_LONG).show();
        }

    }

}
