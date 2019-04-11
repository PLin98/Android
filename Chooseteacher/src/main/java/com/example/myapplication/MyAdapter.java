package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class MyAdapter extends BaseAdapter {
    private List<Teacher> mList;//数据源
    private LayoutInflater mInflater;//布局装载器对象
    Context mcontext;
    private Teacher bean;
    public MyAdapter(Context context, List<Teacher> list) {
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
            convertView = mInflater.inflate(R.layout.list, null);

            //对viewHolder的属性进行赋值
            viewHolder.course_name = (TextView) convertView.findViewById(R.id.course_name);
            viewHolder.status = (TextView) convertView.findViewById(R.id.status);

            //通过setTag将convertView与viewHolder关联
            convertView.setTag(viewHolder);
        }else{//如果缓存池中有对应的view缓存，则直接通过getTag取出viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 取出bean对象
        bean = mList.get(position);

        // 设置控件的数据
        viewHolder.course_name.setText(bean.getCourse_name());
        viewHolder.status.setText(bean.getStatus());

        return convertView;
    }

    // ViewHolder用于缓存控件，三个属性分别对应item布局文件的三个控件
    private class ViewHolder{
        public TextView course_name;
        public TextView status;
    }
}