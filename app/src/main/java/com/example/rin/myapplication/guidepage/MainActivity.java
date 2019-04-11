package com.example.rin.myapplication.guidepage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rin.myapplication.R;
import com.example.rin.myapplication.first.home;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Fragment[] fragments;
    private  FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_main);
        initview();
        fm=getSupportFragmentManager();
        viewPager.setAdapter(new MyAdaper(fm));

    }

    private void initview() {
        viewPager= (ViewPager) findViewById(R.id.viewpager);

        fragments=new Fragment[3];
        fragments[0]=new guide1();
        fragments[1]=new guide2();
        fragments[2]=new guide3();

    }
    public void click(View view) {
        startActivity(new Intent(MainActivity.this,home.class));
        //finish();
    }

    class MyAdaper extends FragmentPagerAdapter{

         public MyAdaper(FragmentManager fm) {
             super(fm);
         }

         @Override
         public Fragment getItem(int position) {
             return fragments[position];
         }

         @Override
         public int getCount() {
             return fragments.length;
         }
     }

}