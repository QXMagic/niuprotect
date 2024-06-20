package com.niu.protect.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.niu.protect.ui.map.TrackQueryActivity;

import com.niu.protect.R;
public class TrackAnalysisInfoLayout extends LinearLayout {
    public TextView key1;
    public TextView key2;
    public TextView key3;
    public TextView key4;
    public View mView;
    public TextView titleText;
    public TextView value1;
    public TextView value2;
    public TextView value3;
    public TextView value4;

    public TrackAnalysisInfoLayout(TrackQueryActivity parent, final BaiduMap baiduMap) {
        super(parent);
        this.titleText = null;
        this.key1 = null;
        this.key2 = null;
        this.key3 = null;
        this.key4 = null;
        this.value1 = null;
        this.value2 = null;
        this.value3 = null;
        this.value4 = null;
        this.mView = null;
        LayoutInflater inflater = (LayoutInflater) parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.dialog_track_analysis_info, (ViewGroup) null);
        this.mView = inflate;
        this.titleText = (TextView) inflate.findViewById(R.id.tv_dialog_title);
        this.key1 = (TextView) this.mView.findViewById(R.id.info_key_1);
        this.key2 = (TextView) this.mView.findViewById(R.id.info_key_2);
        this.key3 = (TextView) this.mView.findViewById(R.id.info_key_3);
        this.key4 = (TextView) this.mView.findViewById(R.id.info_key_4);
        this.value1 = (TextView) this.mView.findViewById(R.id.info_value_1);
        this.value2 = (TextView) this.mView.findViewById(R.id.info_value_2);
        this.value3 = (TextView) this.mView.findViewById(R.id.info_value_3);
        this.value4 = (TextView) this.mView.findViewById(R.id.info_value_4);
        setFocusable(true);
        this.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baiduMap.hideInfoWindow();
            }
        });
    }
}
