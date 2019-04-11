package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    private TextView tv_show;
    private InputStream in;
    private List<Person> list;
    private Button btn_sax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=new ArrayList<Person>();
        //in=getResources().openRawResource(R.raw.person);
        try {
            in=getAssets().open("person.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        tv_show= (TextView) findViewById(R.id.tv_show);
        btn_sax= (Button) findViewById(R.id.btn_sax);
        btn_sax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    list=new PersonService().getPersons(in);
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                show_result("SAX");
            }
        });
    }
    private void show_result(String tag){
        String str=tag+":\n\n";
        for(int i=0;i<list.size();i++){
            String message = "id = " + list.get(i).getId() + " , name = " + list.get(i).getName() + " , age = " + list.get(i).getAge() + ".\n";
            str += message;
            //str+=per.toString();
        }
        tv_show.setText(str);
    }
}
