package com.example.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btn_start;
    private ProgressBar barShow;
    private TextView tvTip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start= (Button) findViewById(R.id.btn_start);
        barShow= (ProgressBar) findViewById(R.id.barShow);
        tvTip= (TextView) findViewById(R.id.tvTip);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTask().execute(100);//方法的参数作为doInBackground()的输入参数
            }
        });

    }
    class MyTask extends AsyncTask<Integer,Integer,Integer>{
        //execute()运行时，由UI线程回调，通常用于初始化操作
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            barShow.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            //onPreExecute()结束后，有后台线程回调，完成耗时的任务
            //可以调用publishProgress()向UI线程发布后台任务的执行状态
            for(int i=0;i<=100;i+=10){
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return 100;//返回值作为onPostExecute()的输入参数
        }

        //后台线程调用publishProgress()后，由UI线程回调，可用中间结果更新UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            barShow.setProgress(values[0]);
            tvTip.setText("正在下载 "+values[0]+"%");
        }

        //doInBackground()全部执行结束后，由UI线程回调，可用最终结果更新UI
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            tvTip.setText("下载完毕！");
        }
    }
}
