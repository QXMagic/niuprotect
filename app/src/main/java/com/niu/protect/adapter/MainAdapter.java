package com.niu.protect.adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.niu.protect.R;
import java.util.List;
public class MainAdapter extends BaseAdapter {
    private Context context;
    private List<PackageInfo> list;
    private LayoutInflater mInflater;

    public MainAdapter(Context context, List<PackageInfo> list) {
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
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.item_main, (ViewGroup) null);
        }
        ImageView headimg = (ImageView) convertView.findViewById(R.id.appimg);
        TextView appname = (TextView) convertView.findViewById(R.id.appname);
        TextView appid = (TextView) convertView.findViewById(R.id.appid);
        PackageInfo packageInfo = this.list.get(position);
        appname.setText(packageInfo.applicationInfo.loadLabel(this.context.getPackageManager()).toString());
        appid.setText(packageInfo.packageName);
        headimg.setImageDrawable(packageInfo.applicationInfo.loadIcon(this.context.getPackageManager()));
        return convertView;
    }
}
