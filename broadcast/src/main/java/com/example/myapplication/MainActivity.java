package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String MYACTION = "edu.cwu.broadcast";
    private Button btn_send;
    private static TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_demo);

        tv_show = (TextView)findViewById(R.id.tv_show);
        btn_send = (Button)findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {  // 发送本应用的广播
                Intent intent = new Intent();

                intent.setAction(MYACTION);
                //intent.setComponent(new ComponentName("com.example.myapplication", "com.example.myapplication.Receiver"));
                sendBroadcast(intent);
            }

        });
    }

    public static void update(String msg) {
        String text = tv_show.getText().toString();
        String newText = text + "\n" + msg;
        tv_show.setText(newText);
    }
}
