package com.niu.protect.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import atmp.consts.AgooConstants;
import com.niu.protect.R;
import com.niu.protect.model.QxInfo;
import com.niu.protect.tools.RomUtil;
import java.util.ArrayList;
import java.util.List;
public class QxViewPagerAdapter extends PagerAdapter implements View.OnClickListener {
    Context mContext;
    QxOnClickListener qxOnClickListener;
    boolean showButton = true;
    private List<QxInfo> qxInfoList = new ArrayList();

    public void add(List<QxInfo> qxList, Context context) {
        if (this.qxInfoList.size() > 0) {
            this.qxInfoList.clear();
        }
        this.mContext = context;
        this.qxInfoList.addAll(qxList);
        notifyDataSetChanged();
    }

    public void setShowButton(boolean showButton) {
        this.showButton = showButton;
        notifyDataSetChanged();
    }

    public void setQxOnClickListener(QxOnClickListener qxOnClickListener) {
        this.qxOnClickListener = qxOnClickListener;
    }

    @Override
    public int getCount() {
        return this.qxInfoList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return -2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        QxInfo qxInfo = this.qxInfoList.get(position);
        View view = qxInfo.getView();
        TextView textView = (TextView) view.findViewById(R.id.tv);
        textView.setText(qxInfo.getTitle());
        TextView desView = (TextView) view.findViewById(R.id.destxt);
        desView.setText(qxInfo.getDes());
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        if (RomUtil.isVivo()) {
            Resources resources = this.mContext.getResources();
            int imageResId = resources.getIdentifier("nav" + String.valueOf(position + 1), "mipmap", this.mContext.getPackageName());
            iv.setImageResource(imageResId);
        } else if (RomUtil.isEmui() && position == 4) {
            Resources resources2 = this.mContext.getResources();
            int imageResId2 = resources2.getIdentifier(AgooConstants.MESSAGE_SYSTEM_SOURCE_HUAWEI + String.valueOf(position + 1), "mipmap", this.mContext.getPackageName());
            iv.setImageResource(imageResId2);
        } else {
            Resources resources3 = this.mContext.getResources();
            int imageResId3 = resources3.getIdentifier("onav" + String.valueOf(position + 1), "mipmap", this.mContext.getPackageName());
            iv.setImageResource(imageResId3);
        }
        TextView type = (TextView) view.findViewById(R.id.type);
        if (qxInfo.getIsok() == 1) {
            type.setText("已授权");
            type.setTextColor(-16711936);
        } else {
            type.setText("未授权");
            type.setTextColor(-65536);
        }
        int tag = position + 100;
        TextView xqActionBtn = (TextView) view.findViewById(R.id.xqActionBtn);
        if (textView.getText().toString().contains("进入桌面")) {
            xqActionBtn.setText("进入桌面");
        } else {
            xqActionBtn.setText("开始授权");
        }
        xqActionBtn.setTag(Integer.valueOf(tag));
        xqActionBtn.setOnClickListener(this);
        if (!this.showButton) {
            xqActionBtn.setVisibility(View.GONE);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void onClick(View view) {
        int tag = Integer.parseInt(view.getTag().toString()) - 100;
        this.qxOnClickListener.onClick(tag);
    }
}
