package com.example.rin.myapplication.notes;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rin.myapplication.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Rin on 2018/12/27.
 */

public class notequery extends AppCompatActivity {
    private TextView tvcontent;
    private Button btnquery;
    String username;
    private static final int ff=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notequery);
        tvcontent= (TextView) findViewById(R.id.tvcontent);
        btnquery= (Button) findViewById(R.id.btnquery);
        Bundle bundle=this.getIntent().getExtras();
        username=bundle.getString("用户名");
        btnquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=readfromexternalfile("note",username);
                tvcontent.setText(content);
            }
        });
    }

    private String readfromexternalfile(String dirname, String filename) {
        if(!checkstoragestate()){
            return null;
        }
        String  externalpath= Environment.getExternalStorageDirectory().getAbsolutePath();
        //Log.i("Test",externalpath);
        File dir=new File(externalpath+"/"+dirname+"/");
        if(!dir.exists()){
            return null;
        }
        FileInputStream fis=null;
        BufferedReader reader=null;
        try {
            fis=new FileInputStream(new File(dir,filename));
            reader=new BufferedReader(new InputStreamReader(fis));
            StringBuilder  builder=new StringBuilder();
            String line;
            while ((line=reader.readLine())!=null){
                builder.append(line+"\n");
            }
            return builder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private boolean checkstoragestate() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    @Override
    //回调函数，系统提供,检查用户是否同意授权
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==ff&&grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"获取文件权限成功",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"获取文件权限不成功",Toast.LENGTH_SHORT).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
