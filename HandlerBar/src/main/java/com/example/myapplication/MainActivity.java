package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static ImageView iv;
    String url="http://172.18.199.235:8080/dviz/1.png";
    private ProgressBar bar;
    private TextView tv_pro;
    private static String mFileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv=(ImageView)findViewById(R.id.iv);
        bar=(ProgressBar)findViewById(R.id.bar);
        tv_pro=(TextView)findViewById(R.id.tv_pro);
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }

    public void start(View view){
        new AsyncTask<String, Integer, Bitmap>() {
            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                bar.setProgress(values[0]);
                tv_pro.setText("当前进度："+values[0]+"%");
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                try {
                    URL url1=new URL(params[0]);
                    HttpURLConnection huc=(HttpURLConnection)url1.openConnection();
                    huc.setConnectTimeout(3000);
                    huc.connect();
                    if(huc.getResponseCode()==200){
                        InputStream inputStream = huc.getInputStream();
                        int contentLength = huc.getContentLength();//获取图片字节的总长度
                        int length=0;//当前下载了多少
                        byte[] b=new byte[1024];
                        int len=0;
                        ByteArrayOutputStream out=new ByteArrayOutputStream();
                        while ((len=inputStream.read(b))!=-1){
                            out.write(b,0,len);
                            length+=len;
                            int progress=(length*100)/contentLength;//进度百分比
                            publishProgress(progress);//把进度返回，调用onProgressUpdate
                        }
                        byte[] imgs=out.toByteArray();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imgs, 0, imgs.length);
                        saveFile(bitmap);
                        return bitmap;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }



            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                //INVISIBLE:不可见，但是占位置
                //VISIBLE:可见，默认
                //GONE:不可见，也不占位置，彻底隐藏
                bar.setVisibility(View.VISIBLE);
                tv_pro.setVisibility(View.VISIBLE);

            }
        }.execute(url);

    }
       public static void saveFile(Bitmap bm) throws IOException {
            File dirFile = new File(Environment.getExternalStorageDirectory().getPath());
            if(!dirFile.exists()){
                dirFile.mkdir();
            }
            mFileName = UUID.randomUUID().toString()+".png";
            File jia=new File(Environment.getExternalStorageDirectory().getPath() +"/DCIM/Camera/");
            if(!jia.exists()){   //判断文件夹是否存在，不存在则创建
                jia.mkdirs();
            }
            File myCaptureFile = new File(jia +"/"+ mFileName);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
           iv.setImageURI(Uri.fromFile(myCaptureFile));
           Log.i("+++++Path", String.valueOf(myCaptureFile));
            bos.flush();
            bos.close();

            //把图片保存后声明这个广播事件通知系统相册有新图片到来
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(myCaptureFile);
            intent.setData(uri);
           Context context = null;
           context.sendBroadcast(intent);
    }

}
