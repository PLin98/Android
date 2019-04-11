package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private final String SERVER_IP="172.18.237.8";
    private final int SERVER_PORT=7100;
    private Socket socket;
    private ListView list_tv;
    private EditText et_text;
    private Button btn_send;
    BufferedReader data=null;
    PrintWriter out;
    private ArrayAdapter<String> adapter;
    private List<String> list;
    String msg;
    Handler myHanlder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_tv= (ListView) findViewById(R.id.list);
        et_text= (EditText) findViewById(R.id.et_text);
        btn_send= (Button) findViewById(R.id.btn_send);
        list=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        list_tv.setAdapter(adapter);
        myHanlder=new Handler() ;

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //创建子线程1，建立客户端socket
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket=new Socket(SERVER_IP,SERVER_PORT);
                    //获取输入流
                    /*InputStream is=socket.getInputStream();
                    data=new BufferedReader(new InputStreamReader(is));*/
                    data = new BufferedReader(new InputStreamReader(socket.getInputStream(), "gb2312"));
                    //取得输出流 OutputStream--->PrintWriter
                    OutputStream os=socket.getOutputStream();
                    out=new PrintWriter(os);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //创建子线程2，接收服务器消息
        new Thread((new Runnable() {
            @Override
            public void run() {
                while(true){
                    //socket已连接，且输入流也存在
                    if(socket!=null&&socket.isConnected()&&!socket.isInputShutdown()){
                        //读入数据msg
                            try {
                                if(msg!=null) {
                                    msg = data.readLine();
                                    list.add("From:" + msg);//将消息添加到列表
                                }
                                //通知主线程更新主界面
                                myHanlder.post(new Runnable(){
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }



                }
            }
        })).start();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取编辑框中的输入msg
                msg=et_text.getText().toString();
                if(msg!=null&&socket!=null&&socket.isConnected()){
                    //将消息添加到消息列表
                    list.add("To:"+msg);
                    //更新消息列表
                    adapter.notifyDataSetChanged();
                    //清空消息输入框
                    et_text.setText("");
                    //向服务器发送消息
                    out.println(msg);
                    out.flush();
                    if(msg.equalsIgnoreCase("bye")){
                        //按钮变为不可用
                        btn_send.setEnabled(false);
                        //关闭流、关闭socket
                        try {
                            out.close();
                            data.close();

                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });
    }
}
