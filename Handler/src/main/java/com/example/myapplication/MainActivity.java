package com.example.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tv_text;
    private MyHandler myHandler;
    MyThread mythread=new MyThread();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        tv_text= (TextView) findViewById(R.id.tv_text);
        myHandler=new MyHandler();
    }

    //内部类,定义Handler
    class MyHandler extends Handler {
        @Override
        //重写
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //取出Messsage中的数据，用来更新UI组件tv_text
            Bundle bundle=msg.getData();
            String str=bundle.getString("say");
            tv_text.setText(str);
        }


    }
    //添加按钮
    public void cal(View view){
        new Thread(mythread).start();
    }

    //定义子线程
    class MyThread implements Runnable{
        //1.定义在子线程要做的耗时操作
        @Override
        public void run() {
            //模拟耗时操作
           /* try{
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();*/

            //求素数0-100
            List<Integer> nums=new ArrayList<Integer>();
            //质数也是素数，除了i和它本身外，不能被其他整除
            outer:
            for (int i=2;i<=100;i++){
                for (int j=2;j<=Math.sqrt(i);j++){
                    //如果可以整除，说明不是质数
                    if(i!=2&&i%j==0){
                        continue outer;
                    }
                }
                nums.add(i);
            }


            //2.将线程的操作结果传递给主线程（操作结果封装到Message对象，再传到主线程中）
            //2.1 将数据封装到Message对象
            Message msg=myHandler.obtainMessage();//通过此方法创建message对象

            Bundle bundle=new Bundle();
            // bundle.putString("say","Android is not easy");
            bundle.putString("say",nums.toString());
            msg.setData(bundle);
            //2.2 利用myHandler对象想主线程发送数据
            myHandler.sendMessage(msg);
        }
    }
}
