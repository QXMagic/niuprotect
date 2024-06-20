package com.niu.protect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.niu.protect.model.AppRecInfo;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.tools.Tools;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.niu.protect.R;
public class StaAppAdapter extends BaseAdapter {
    public int alltime;
    private Context context;
    private List<AppRecInfo> list;
    private LayoutInflater mInflater;

    public StaAppAdapter(Context context, List<AppRecInfo> list) {
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
            convertView = this.mInflater.inflate(R.layout.item_sta_app, (ViewGroup) null);
        }
        AppRecInfo appRecInfo = this.list.get(position);
        ImageView imageview = (ImageView) convertView.findViewById(R.id.imageview);
        TextView txt1 = (TextView) convertView.findViewById(R.id.txt1);
        TextView txt2 = (TextView) convertView.findViewById(R.id.txt2);
        ProgressBar probar = (ProgressBar) convertView.findViewById(R.id.probar);
        Picasso.get().load(StudentBaseUrl.BASE_URL + appRecInfo.getAppImage()).memoryPolicy(MemoryPolicy.NO_STORE, new MemoryPolicy[0]).into(imageview);
        if (!Tools.objIsNullStr(appRecInfo.getAppName())) {
            String timeStr = "";
            int hour = (appRecInfo.getUseTime() / 60) / 60;
            int min = (appRecInfo.getUseTime() / 60) % 60;
            int s = appRecInfo.getUseTime() % 60;
            if (hour > 0) {
                timeStr = hour + "时";
            }
            txt1.setText(appRecInfo.getAppName());
            txt2.setText(timeStr + min + "分" + s + "秒");
        }
        probar.setMax(this.alltime);
        probar.setProgress(appRecInfo.getUseTime());
        return convertView;
    }
}
