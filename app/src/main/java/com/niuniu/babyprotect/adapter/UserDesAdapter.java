package com.niuniu.babyprotect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import im.niu.protect.R;
import com.niuniu.babyprotect.model.AppInfo;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class UserDesAdapter extends BaseAdapter {
    public int alltime;
    private Context context;
    private JSONArray list;
    private LayoutInflater mInflater;

    public UserDesAdapter(Context context, JSONArray list) {
        this.list = list;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.list.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return this.list.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
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
            convertView2 = this.mInflater.inflate(R.layout.item_user_des, (ViewGroup) null);
        }
        try {
            JSONObject mlist = this.list.getJSONObject(position);
            String userTime = mlist.getString("useTime");
            JSONArray alist = mlist.getJSONArray("appList");
            TextView timelal = (TextView) convertView2.findViewById(R.id.timelal);
            timelal.setText(userTime);
            for (int i = 0; i < 3; i++) {
                LinearLayout appview = (LinearLayout) convertView2.findViewWithTag(((i + 1) * 1000) + "");
                if (i < alist.length()) {
                    appview.setVisibility(View.VISIBLE);
                } else {
                    appview.setVisibility(View.GONE);
                }
            }
            int j = 0;
            while (j < alist.length() && j <= 2) {
                int tag = (j + 1) * 1000;
                LinearLayout linearLayout = (LinearLayout) convertView2.findViewWithTag(tag + "");
                AppInfo appInfo = (AppInfo) alist.get(j);
                ImageView img1 = (ImageView) convertView2.findViewWithTag((tag + 1) + "");
                TextView txt1 = (TextView) convertView2.findViewWithTag((tag + 2) + "");
                TextView txt2 = (TextView) convertView2.findViewWithTag((tag + 3) + "");
                StringBuilder sb = new StringBuilder();
                JSONObject mlist2 = mlist;
                sb.append(tag + 4);
                sb.append("");
                ProgressBar probar = (ProgressBar) convertView2.findViewWithTag(sb.toString());
                String userTime2 = userTime;
                Picasso.get().load("http://139.9.121.96:8281" + appInfo.getAppImage()).memoryPolicy(MemoryPolicy.NO_STORE, new MemoryPolicy[0]).into(img1);
                probar.setMax(1440);
                probar.setProgress(appInfo.getUseTime());
                String timeStr = "";
                int hour = appInfo.getUseTime() / 60;
                int min = appInfo.getUseTime() % 60;
                if (hour > 0) {
                    timeStr = hour + "时";
                }
                String timeStr2 = timeStr + min + "分";
                String timeStr3 = appInfo.getAppName();
                txt1.setText(timeStr3);
                txt2.setText(timeStr2);
                j++;
                userTime = userTime2;
                mlist = mlist2;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView2;
    }
}
