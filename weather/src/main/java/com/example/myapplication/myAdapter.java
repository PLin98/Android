package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class myAdapter extends BaseAdapter {
    private List<forecast> mList;//数据源
    private LayoutInflater mInflater;//布局装载器对象
    Context mcontext;
    private forecast bean;
    public myAdapter(Context context, List<forecast> list) {
        mList = list;
        mcontext=context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //如果view未被实例化过，缓存池中没有对应的缓存
        if (convertView == null) {
            viewHolder = new ViewHolder();
            // 由于我们只需要将XML转化为View，并不涉及到具体的布局，所以第二个参数通常设置为null
            convertView = mInflater.inflate(R.layout.list_forecast, null);

            //对viewHolder的属性进行赋值
            viewHolder.week = (TextView) convertView.findViewById(R.id.week);
            viewHolder.high = (TextView) convertView.findViewById(R.id.high);
            viewHolder.low = (TextView) convertView.findViewById(R.id.low);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.img);

            //通过setTag将convertView与viewHolder关联
            convertView.setTag(viewHolder);
        }else{//如果缓存池中有对应的view缓存，则直接通过getTag取出viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 取出bean对象
        bean = mList.get(position);

        // 设置控件的数据
        viewHolder.week.setText(bean.getWeek());
        String high=bean.getHigh();
        String high1=high.replaceAll("高温 ","");
        String high2=high1.replaceAll(".0℃","");
        viewHolder.high.setText(high2+"°");
        String low=bean.getLow();
        String low1=low.replaceAll("低温 ","");
        String low2=low1.replaceAll(".0℃","");
        viewHolder.low.setText(low2+"°");
        String weather=bean.gettype();
        Log.i("wearth",weather);
        if("晴".equals(weather))
            viewHolder.img.setImageResource(R.drawable.sunny);
        if ("多云".equals(weather))
            viewHolder.img.setImageResource(R.drawable.cloudysunny);
        if("阴".equals(weather))
            viewHolder.img.setImageResource(R.drawable.cloudy);
        if("小雨".equals(weather))
            viewHolder.img.setImageResource(R.drawable.rainlight);
        if("大雨".equals(weather))
            viewHolder.img.setImageResource(R.drawable.rainheavy);
        return convertView;
    }

    // ViewHolder用于缓存控件，三个属性分别对应item布局文件的三个控件
    private class ViewHolder{
        public TextView week;
        public TextView high;
        public TextView low;
        public ImageView img;
    }
}