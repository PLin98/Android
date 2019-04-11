package com.example.myapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    public static String doGet(String strUrl, String param) throws Exception {
        String result = null;
        //1.1构造URL字符串，创建URL对象
        if (param != null) {
            strUrl += param;
            Log.i("param!=null输出strURl", strUrl + "");
        }
        URL url = new URL(strUrl);
        //1.2得到HttpURLConnection对象，处理http请求
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        //2.1设置请求头部信息
        conn.setReadTimeout(5 * 1000);//超时
        //根据需要设置其他请求属性

        //2.2根据上述配置生成请求头部信息
        ////所有头部配置都必须在connect()之前完成，之后的配置无效
        conn.connect();

        //3、连接成功，则读取响应结果
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            //3.1建立输入流：发送http请求，读取服务器响应结果
            InputStream is = conn.getInputStream();

            //3.2将响应结果转换为需要的数据格式
            //将输入流转换为字符串result
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                result = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            reader.close();//关闭流
        }
        //4、断开连接
        conn.disconnect();
        return result;
    }

    public static String doPost(String strUrl, String param) throws Exception {
        String result = null;
        //1.1创建URl对象
        URL url = new URL(strUrl);
        //1.2得到HttpURLConnection对象，处理http请求。
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        //2.1设置请求头部信息
        ////设置是否允许输出。post请求参数放在http正文内，必须设为true（默认false）
        conn.setDoOutput(true);
        ////设置是否从httpUrlConnection读入（默认true）
        conn.setDoInput(true);
        ////Post请求不能使用缓存
        conn.setUseCaches(false);
        ////设置请求超时时间
        conn.setReadTimeout(5 * 1000);
        ////设置请求方法为“POST”，默认是GET
        conn.setRequestMethod("POST");

        //2.1根据上述配置生成请求头部信息
        ////所有的头部配置都必须在connect()之前完成，之后的配置无效
        conn.connect();

        //2.3Post请求需要利用OutputStream将请求参数写入http正文
        if (param != null) {
            OutputStream os = conn.getOutputStream();
            PrintWriter out = new PrintWriter(os);
            out.print(param);//按字符串写
            out.flush();//刷新对象输出流，将输入的字节写入潜在的流中（此处为ObjectOutputStream）
            //关闭输出流。此时不能再向输出流写入任何数据，先前写入的数据存在于内存缓冲区中
            // 在调用下面的getInputStream()时才把准备好的htpp请求正式发送到服务器
            // 根据输出流中的内容生成http正文
            out.close();
        }

        //3、连接成功，则读取响应结果
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            //3.1建立输入流：发送http请求，读取服务器响应结果
            InputStream is = conn.getInputStream();
            //3.2将响应结果转换为需要的数据格式

            //将输入流转换为字符串result
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                result = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            reader.close();//关闭流
        }
        conn.disconnect();//断开连接
        return result;
    }
}



















