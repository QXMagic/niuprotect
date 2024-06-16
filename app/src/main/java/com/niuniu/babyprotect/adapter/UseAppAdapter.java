package com.niuniu.babyprotect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import im.niu.protect.R;
import com.niuniu.babyprotect.model.UsePackageInfo;
import com.niuniu.babyprotect.tools.Tools;
import java.util.Date;
import java.util.List;
public class UseAppAdapter extends BaseAdapter {
    private Context context;
    private List<UsePackageInfo> list;
    private LayoutInflater mInflater;

    public UseAppAdapter(Context context, List<UsePackageInfo> list) {
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
        String timeStr;
        if (convertView != null) {
            convertView2 = convertView;
        } else {
            convertView2 = this.mInflater.inflate(R.layout.item_parent_app, (ViewGroup) null);
        }
        ImageView imageView = (ImageView) convertView2.findViewById(R.id.appimg);
        TextView appname = (TextView) convertView2.findViewById(R.id.appname);
        TextView appid = (TextView) convertView2.findViewById(R.id.appid);
        TextView appType = (TextView) convertView2.findViewById(R.id.appType);
        UsePackageInfo otherTimeInfo = this.list.get(position);
        appname.setText(otherTimeInfo.getmAppName());
        appid.setText(Tools.timeFormat(new Date(otherTimeInfo.getStaTime()), "yyyy-MM-dd HH:mm:ss") + "---" + Tools.timeFormat(new Date(otherTimeInfo.getEndTime()), "yyyy-MM-dd HH:mm:ss"));
        long ss = otherTimeInfo.getmUsedTime() / 1000;
        long hour = ss / 3600;
        long min = ss / 60;
        long mis = ss % 60;
        if (hour <= 0) {
            timeStr = "";
        } else {
            String timeStr2 = hour + "时";
            timeStr = timeStr2;
        }
        if (min > 0) {
            timeStr = timeStr + min + "分";
        }
        appType.setText(timeStr + mis + "秒");
        return convertView2;
    }
}
