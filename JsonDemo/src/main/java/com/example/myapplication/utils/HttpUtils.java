package com.example.myapplication.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Rin on 2019/3/16.
 */

public class HttpUtils {
    public static String doGet(String srtURL,String param) throws IOException {
        HttpURLConnection conn=null;
        String line,result="";
        if (param!=null){
            srtURL += "?"+param;
        }
            URL url=new URL(srtURL);
            conn= (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5*1000);
            conn.connect();
            if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream is=conn.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                while((line=reader.readLine())!=null){
                    result+=line+"\n";
                }
                is.close();
                reader.close();
        }
        conn.disconnect();
        return result;
    }
   public static String doPost(String srtURL,String param) throws IOException {
        HttpURLConnection conn=null;
        String line,result="";
        URL url=new URL(srtURL);
       conn= (HttpURLConnection) url.openConnection();
       conn.setDoInput(true);
       conn.setDoOutput(true);
       conn.setUseCaches(false);
       conn.setReadTimeout(5*1000);
       conn.setRequestMethod("POST");
       conn.connect();
        if (param!=null) {
            OutputStream os = conn.getOutputStream();
            PrintWriter out = new PrintWriter(os);
            out.print(param);
            out.flush();
            out.close();
        }
       if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
           InputStream is=conn.getInputStream();
           BufferedReader reader=new BufferedReader(new InputStreamReader(is));
           while((line=reader.readLine())!=null){
               result+=line+"\n";
           }
           is.close();
           reader.close();
       }
       conn.disconnect();
       return result;
   }
}
