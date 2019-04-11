package com.example.rin.myapplication.article;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rin.myapplication.R;
import com.example.rin.myapplication.login.dengluhou;

/**
 * Created by Rin on 2018/12/24.
 */

public class displaydetail extends AppCompatActivity{
    private TextView aname,atitle,acategory,atime,acontents;
    private ImageView image;
    private DBHelper helper;
    private SQLiteDatabase db;
    String username;
    String ids;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displaydetail);
        Bundle bundle=this.getIntent().getExtras();
        username=bundle.getString("用户名");
        aname= (TextView) findViewById(R.id.aname);
        atitle= (TextView) findViewById(R.id.atitle);
        acategory= (TextView) findViewById(R.id.acategory);
        atime= (TextView) findViewById(R.id.atime);
        acontents= (TextView)findViewById(R.id.acontents);
        image= (ImageView) findViewById(R.id.aimage);

        Intent intent=getIntent();
        aname.setText(intent.getStringExtra("name"));
        atitle.setText(intent.getStringExtra("title"));
        acategory.setText(intent.getStringExtra("category"));
        atime.setText(intent.getStringExtra("time"));
        acontents.setText(intent.getStringExtra("contents"));
        image.setImageURI(Uri.parse(intent.getStringExtra("img")));
        ids=intent.getStringExtra("ids");
        //Log.i("Test","id"+ids);
        helper = new DBHelper(this, 4);
        db=helper.getReadableDatabase();
    }

    public void change(View view) {
        switch (view.getId()){
            case R.id.btndelete:
                //db.execSQL("delete from article0 where _id=?",new String[]{id});

                String whereClause="_id=?";
                String[] whereArgs={ids};
                int line = db.delete(DBHelper.TABLE_ARTICLE, whereClause, whereArgs);
                if(line>0){
                    Intent intent0=new Intent();
                    Bundle bundle0=new Bundle();
                    bundle0.putString("用户名",username);
                    Log.i("Test","name:"+username);
                    intent0.putExtras(bundle0);
                    intent0.setClass(this,dengluhou.class);
                    startActivity(intent0);
                    Toast.makeText(this,"删除成功！"+line,Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.btnupdate:
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("ids",ids);
                bundle.putString("用户名",username);
                Log.i("Test","name:"+username);
                intent.putExtras(bundle);
                intent.setClass(this,updatearticle.class);
                startActivity(intent);
                break;
        }
    }

}
