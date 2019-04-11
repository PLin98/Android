package com.example.myapplication;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Rin on 2019/3/16.
 */

public class OkHttpUtils {
    private static String result;
    public static String doGetSync(String srtURL,String param) throws IOException {

        if (param!=null){
            srtURL += "?"+param;
        }
        //创建OKHTTPClient客户端
        OkHttpClient client=new OkHttpClient.Builder()
                            .connectTimeout(2, TimeUnit.SECONDS)
                            .build();
        //创建request对象
        Request request=new Request.Builder()
                .get()
                .url(srtURL)
                .addHeader("Connection","keep-alive")
                .build();
        //创建Call对象:执行请求和获取响应
        Call call=client.newCall(request);
        Response respose=call.execute();
        if(respose.isSuccessful())
            //响应结果：字符串，字节，字符流，字节流
            result=respose.body().string();
        else
            Log.i("Failed",respose.code()+":"+respose.message());
        return result;
    }
   public static String doPostSync(String srtURL,Person person) throws IOException {
       String result=null;
       //创建表单对象
       RequestBody formbody=new FormBody.Builder()
               .add("name",person.getName())
               .add("age",person.getAge()+"")
               .build();
       //创建request对象，封装表单数据
       Request request=new Request.Builder()
               .url(srtURL)
               .post(formbody)
               .build();
       //创建OKHTTPClient客户端
       OkHttpClient client=new OkHttpClient.Builder()
               .connectTimeout(2, TimeUnit.SECONDS)
               .build();
        //创建Call对象:执行请求和获取响应
       Call call=client.newCall(request);
       Response respose=call.execute();
       if(respose.isSuccessful())
           //响应结果：字符串，字节，字符流，字节流
           result=respose.body().string();
       else
           Log.i("Failed",respose.code()+":"+respose.message());
       return result;
   }
    public static void doGetASync(String srtURL, String param, final Handler myhandler) throws IOException {

        if (param!=null){
            srtURL += "?"+param;
        }
        //创建OKHTTPClient客户端
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .build();
        //创建request对象
        final Request request=new Request.Builder()
                .get()
                .url(srtURL)
                .addHeader("Connection","keep-alive")
                .build();
        //创建Call对象:执行请求和获取响应
        Call call=client.newCall(request);
       call.enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               e.printStackTrace();
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful())
                    result=response.body().string();
               else
                   Log.i("+++++++",null);
               Message msg=myhandler.obtainMessage();
               msg.obj=result;
               myhandler.sendMessage(msg);
           }
       });
    }
    public static void doPostASync(String srtURL, Person person, final Handler myhandler) throws IOException {

        //创建表单对象
        RequestBody formbody=new FormBody.Builder()
                .add("name",person.getName())
                .add("age",person.getAge()+"")
                .build();
        //创建request对象，封装表单数据
        Request request=new Request.Builder()
                .url(srtURL)
                .post(formbody)
                .build();
        //创建OKHTTPClient客户端
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .build();
        //创建Call对象:执行请求和获取响应
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful())
                    result=response.body().string();
                else
                    Log.i("+++++++",null);
                Message msg=myhandler.obtainMessage();
                msg.obj=result;
                myhandler.sendMessage(msg);
            }
        });
    }
}
