package com.niuniu.babyprotect.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import im.niu.protect.R;
import com.niuniu.babyprotect.model.OtherTimeInfo;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.squareup.picasso.Picasso;
//import com.xiaomi.mipush.sdk.Constants;
import java.util.List;
public class ParentAppAdapter extends BaseAdapter {
    private Context context;
    int intoType;
    private List<OtherTimeInfo> list;
    private LayoutInflater mInflater;

    public ParentAppAdapter(Context context, List<OtherTimeInfo> list, int intoType) {
        this.intoType = intoType;
        this.list = list;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2;
        if (convertView != null) {
            convertView2 = convertView;
        } else {
            convertView2 = this.mInflater.inflate(R.layout.item_parent_app, (ViewGroup) null);
        }
        ImageView headimg = (ImageView) convertView2.findViewById(R.id.appimg);
        TextView appname = (TextView) convertView2.findViewById(R.id.appname);
        TextView appid = (TextView) convertView2.findViewById(R.id.appid);
        TextView appType = (TextView) convertView2.findViewById(R.id.appType);
        OtherTimeInfo otherTimeInfo = this.list.get(position);
        appname.setText(otherTimeInfo.getName());
        appid.setText(otherTimeInfo.getPackageName());
        Picasso picasso = Picasso.get();
        picasso.load(StudentBaseUrl.baseweb + otherTimeInfo.getImage()).into(headimg);
        Log.e("xxx", otherTimeInfo.getImage());
        int i = this.intoType;
        if (i == 1) {
            if (otherTimeInfo.getDailyStatus() == 1) {
                appType.setText("禁用");
                appType.setTextColor(this.context.getResources().getColor(R.color.red));
            } else if (otherTimeInfo.getDailyStatus() == 2) {
                appType.setText("不限制");
                appType.setTextColor(this.context.getResources().getColor(R.color.green));
            } else {
                appType.setText(otherTimeInfo.getDailyStartTime() + "-" + otherTimeInfo.getDailyEndTime());
                appType.setTextColor(this.context.getResources().getColor(R.color.blue));
            }
        } else if (i == 2) {
            if (otherTimeInfo.getWeekendStatus() == 1) {
                appType.setText("禁用");
                appType.setTextColor(this.context.getResources().getColor(R.color.red));
            } else if (otherTimeInfo.getWeekendStatus() == 2) {
                appType.setText("不限制");
                appType.setTextColor(this.context.getResources().getColor(R.color.green));
            } else {
                appType.setText(otherTimeInfo.getWeekendStartTime() + "-" + otherTimeInfo.getWeekendEndTime());
                appType.setTextColor(this.context.getResources().getColor(R.color.blue));
            }
        } else if (i == 3) {
            if (otherTimeInfo.getHolidayStatus() == 1) {
                appType.setText("禁用");
                appType.setTextColor(this.context.getResources().getColor(R.color.red));
            } else if (otherTimeInfo.getHolidayStatus() == 2) {
                appType.setText("不限制");
                appType.setTextColor(this.context.getResources().getColor(R.color.green));
            } else {
                appType.setText(otherTimeInfo.getHolidayStartTime() +"-" + otherTimeInfo.getHolidayEndTime());
                appType.setTextColor(this.context.getResources().getColor(R.color.blue));
            }
        }
        return convertView2;
    }
}
