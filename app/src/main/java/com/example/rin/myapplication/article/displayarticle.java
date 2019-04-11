package com.example.rin.myapplication.article;

import android.content.ContentValues;
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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.rin.myapplication.R;
import com.example.rin.myapplication.login.*;

/**
 * Created by Rin on 2018/12/23.
 */

public class displayarticle extends AppCompatActivity{
    private ListView listview;
    private DBHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    String username;
    int poid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayarticle);
        listview= (ListView) findViewById(R.id.listview);
        helper = new DBHelper(this, 4);
        Bundle bundle=this.getIntent().getExtras();
        username=bundle.getString("用户名");
        db=helper.getReadableDatabase();
        displayallstudent();
        db.close();
    }
    private void displayallstudent() {
        final Cursor cursor=db.rawQuery("select * from article0 where name=?",new String[]{username});
        // cursor = db.query(DbHelper.TABLE_STUDENT, null, null, null, null, null, null);
        //from显示的列名，to 显示对应的控件
        adapter = new SimpleCursorAdapter(this, R.layout.display, cursor,
                new String[]{DBHelper.A_ID,DBHelper.A_USERNAME, DBHelper.A_TITLE, DBHelper.A_CATEGORY},
                new int[]{R.id.tvid,R.id.tvname, R.id.tvtitle, R.id.tvcategory}, 0);
        listview.setAdapter(adapter);
        if(Build.VERSION.SDK_INT < 14) {
            cursor.close();
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                //poid=position+1;

                String ids=cursor.getString(cursor.getColumnIndex(DBHelper.A_ID));
                String name=cursor.getString(cursor.getColumnIndex(DBHelper.A_USERNAME));
                String title=cursor.getString(cursor.getColumnIndex(DBHelper.A_TITLE));
                String contents=cursor.getString(cursor.getColumnIndex(DBHelper.A_CONTENT));
                String category=cursor.getString(cursor.getColumnIndex(DBHelper.A_CATEGORY));
                String time=cursor.getString(cursor.getColumnIndex(DBHelper.A_TIME));
                String img=cursor.getString(cursor.getColumnIndex(DBHelper.A_IMAGE));
                Intent intent=new Intent(displayarticle.this,displaydetail.class);

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
    }
}
