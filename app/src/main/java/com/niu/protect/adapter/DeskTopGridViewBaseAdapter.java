package com.niu.protect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.niu.protect.R;
import com.niu.protect.model.AppInfo;

import java.util.ArrayList;
import java.util.List;
public class DeskTopGridViewBaseAdapter extends BaseAdapter {
    List<AppInfo> appInfos;
    Context context;

    public DeskTopGridViewBaseAdapter(List<AppInfo> appInfos, Context context) {
        this.appInfos = new ArrayList();
        this.appInfos = appInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.appInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.appInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.desktop_gridview_item, (ViewGroup) null);
            holder = new Holder();
            holder.ico = (ImageView) convertView.findViewById(R.id.iv);
            holder.Name = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.ico.setImageDrawable(this.appInfos.get(position).ico);
        holder.Name.setText(this.appInfos.get(position).name);
        return convertView;
    }

    static class Holder {
        TextView Name;
        ImageView ico;

        Holder() {
        }
    }
}
