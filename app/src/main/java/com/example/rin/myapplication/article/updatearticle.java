package com.example.rin.myapplication.article;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rin.myapplication.R;
import com.example.rin.myapplication.login.dengluhou;

/**
 * Created by Rin on 2018/12/27.
 */

public class updatearticle extends AppCompatActivity {
    private TextView aname,atitle,acontents,atime;
    private Spinner spacategory;
    private Button btnok;
    private Cursor cursor;
    private EditText etcontent;
    private DBHelper helper;
    private SQLiteDatabase db;
    private String categorychoice;
    private String acategory[]={"旅游分享","情感随笔","美食日记","其它"};
    private TextView tvimage;
    private ImageView image;
    private FrameLayout frlayout;
    String username,ids;
    Uri imguri=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatearticle);
        Bundle bundle=this.getIntent().getExtras();
        username=bundle.getString("用户名");
        ids=bundle.getString("ids");
        Log.i("Test","name:"+username+"id:"+ids);
        helper = new DBHelper(this, 4);
        db=helper.getReadableDatabase();
        intiview();

        db.close();
    }

    private void intiview() {
        aname= (TextView) findViewById(R.id.aname);
        atitle= (TextView) findViewById(R.id.atitle);
        acontents= (TextView) findViewById(R.id.acontents);
        atime= (TextView) findViewById(R.id.atime);
        spacategory= (Spinner) findViewById(R.id.acategory);
        frlayout = (FrameLayout) findViewById(R.id.frlayout);
        image = (ImageView) findViewById(R.id.image);
        tvimage = (TextView) findViewById(R.id.tvaddimg);
        String whereClause="_id=?";
        String[] whereArgs={ids};

        cursor=db.rawQuery("select * from article0 where " +
                whereClause,whereArgs);

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(DBHelper.A_USERNAME));
            String title = cursor.getString(cursor.getColumnIndex(DBHelper.A_TITLE));
            String contents = cursor.getString(cursor.getColumnIndex(DBHelper.A_CONTENT));
            //String category=cursor.getString(cursor.getColumnIndex(DBHelper.A_CATEGORY));
            String time = cursor.getString(cursor.getColumnIndex(DBHelper.A_TIME));
            String img = cursor.getString(cursor.getColumnIndex(DBHelper.A_IMAGE));

            aname.setText(name);
            atitle.setText(title);
            acontents.setText(contents);
            atime.setText(time);
            image.setImageURI(Uri.parse(img));
            frlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImagePickDialog();
                }
            });
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, acategory);
            spacategory.setAdapter(adapter);
            spacategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    categorychoice = acategory[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        if(Build.VERSION.SDK_INT < 14) {
            cursor.close();
        }

    }

    private void showImagePickDialog() {
        new AlertDialog.Builder(this)
                .setTitle("选择获取图片方式")
                .setItems(new String[]{"相册", "拍照"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                pickImageFormAlbum();
                                break;
                            case 1:

                                break;
                        }
                    }
                })
                .show();
    }
    private void pickImageFormAlbum() {
        Intent intent = new Intent();
        //获取某个特殊类型的数据
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        }
        startActivityForResult(intent, 0x0001);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_CANCELED) {
            return;
        }
        if(requestCode==0x001){
            if(data==null){
                return ;
            }
            imguri = data.getData();
            Log.i("Test",imguri+"imguri1");
            image.setImageURI(imguri);
            tvimage.setVisibility(View.INVISIBLE);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updatearticle(View view) {
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
                        aname.setText(etcontent.getText().toString());
                        mydlg.dismiss();
                    }
                });
                break;
            case R.id.layout2:
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
                        atitle.setText(etcontent.getText().toString());
                        mydlg2.dismiss();
                    }
                });
                break;
            case R.id.layout3:
                view= LayoutInflater.from(this).inflate(R.layout.zidingyi,null);
                //把view 标识上
                etcontent= (EditText) view.findViewById(R.id.etcontent);
                btnok= (Button) view.findViewById(R.id.btnqd);
                final AlertDialog mydlg3=new AlertDialog.Builder(this)
                        .setView(view)
                        .create();
                mydlg3.show();
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        acontents.setText(etcontent.getText().toString());
                        mydlg3.dismiss();
                    }
                });
                break;
            case R.id.layout5:
                Calendar calendar=Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        atime.setText(year+"年"+(month+1)+"月"+dayOfMonth+"日");
                    }
                },2018,10,20);
                dpd.show();
                break;
            case R.id.btnupdate:
                db=helper.getReadableDatabase();
                Update();
                db.close();
                break;
    }
}

    private void Update() {
        String name = aname.getText().toString();
        String title = atitle.getText().toString().trim();
        String contents= acontents.getText().toString().trim();
        String time=atime.getText().toString().trim();
        String img=imguri.toString();
        Log.i("Test","img:"+img);
        String img1 = cursor.getString(cursor.getColumnIndex(DBHelper.A_IMAGE));
        ContentValues values = new ContentValues();
        values.put(DBHelper.A_USERNAME, name);
        values.put(DBHelper.A_TITLE, title);
        values.put(DBHelper.A_CONTENT, contents);
        values.put(DBHelper.A_CATEGORY,categorychoice);
        values.put(DBHelper.A_TIME,time);
        if(img.equals(img1)){
            values.put(DBHelper.A_IMAGE,img1);
        }else {
            values.put(DBHelper.A_IMAGE,img);
        }
        if(title.length()==0&&contents.length()==0&&time.length()==0){
            Toast.makeText(this, "请输入正确的信息！", Toast.LENGTH_SHORT).show();
        }
        else {
            if (name.equals(username)){
                String whereClause="_id=?";
                String[] whereArgs={ids};
                db.update(DBHelper.TABLE_ARTICLE,values,whereClause,whereArgs);

                Toast.makeText(this, "数据更新成功！", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    Bundle bundle1=new Bundle();
                    bundle1.putString("用户名",username);
                    intent.putExtras(bundle1);
                    intent.setClass(this,dengluhou.class);
                    startActivity(intent);
                    //finish();

            }else {
                Toast.makeText(this, "请检查作者信息！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
