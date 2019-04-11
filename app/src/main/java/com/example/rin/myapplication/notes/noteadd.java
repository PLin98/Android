package com.example.rin.myapplication.notes;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rin.myapplication.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Rin on 2018/12/27.
 */

public class noteadd extends AppCompatActivity{
    private TextView time;
    private EditText note;
    private static final int ff=1;
    String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noteadd);
        time= (TextView) findViewById(R.id.time);
        note= (EditText) findViewById(R.id.note);
        Bundle bundle=this.getIntent().getExtras();
        username=bundle.getString("用户名");
        checkFilePermission();
    }

    private void checkFilePermission() {
        //如果权限未能获取
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},ff);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void yes(View view) {
        switch (view.getId()){
            case R.id.lay1:
                Calendar calendar=Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        time.setText(year+"年"+(month+1)+"月"+dayOfMonth+"日");
                    }
                },2018,10,20);
                dpd.show();
                break;
            case R.id.btnyes:
                String content=note.getText().toString().trim();
                String notetime=time.getText().toString().trim();
                boolean succ=savetoexternalfile("note",username,notetime,content);

                break;
        }
    }

    private boolean savetoexternalfile(String dirname, String filename, String notetime, String content) {
        if (!checkstoragestate()) {
            return false;
        }
        String externalpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        FileOutputStream fos = null;
        File dir = new File(externalpath + "/" + dirname + "/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            fos = new FileOutputStream(new File(dir, filename),true);
            if(notetime.length()!=0&&content.length()!=0) {
                fos.write(notetime.getBytes());
                fos.write("\r\n".getBytes());
                fos.write(content.getBytes());
                fos.write("\r\n".getBytes());
                Toast.makeText(this, "写入成功！", Toast.LENGTH_SHORT).show();
                note.setText("");
                time.setText("");
            }else {
                Toast.makeText(this, "请完善信息！", Toast.LENGTH_SHORT).show();
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private boolean checkstoragestate() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    @Override
    //回调函数，系统提供,检查用户是否同意授权
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==ff&&grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"获取文件权限成功",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"获取文件权限不成功",Toast.LENGTH_SHORT).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
