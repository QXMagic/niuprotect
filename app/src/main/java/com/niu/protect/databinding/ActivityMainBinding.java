package com.niu.protect.databinding;

import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.niu.protect.ui.NavigationBar;

import de.hdodenhof.circleimageview.CircleImageView;
import com.niu.protect.R;

public abstract class ActivityMainBinding extends ViewDataBinding {
    public final TextView appMoreTxt;
    public final TextView appStepTxt;
    public final TextView appTimeTxt;
    public final TextView bingptxt;
    public final TextView bingttxt;
    public final LinearLayout childmsgview;
    public final LinearLayout clockbtn;
    public final RelativeLayout goParentBtn;
    public final RelativeLayout goTeacherBtn;
    public final TextView gytxt;
    public final LinearLayout layoutLoginCenter;
    public final LinearLayout mybtn;
    public final NavigationBar navigationbar;
    public final LinearLayout scanbtn;
    public final LinearLayout staAppBtn;
    public final TextView timelal;
    public final TextView txt1;
    public final TextView txt2;
    public final LinearLayout userDesBtn;
    public final CircleImageView userHead;
    public final TextView username;

    public ActivityMainBinding(Object _bindingComponent, View _root, int _localFieldCount, TextView appMoreTxt, TextView appStepTxt, TextView appTimeTxt, TextView bingptxt, TextView bingttxt, LinearLayout childmsgview, LinearLayout clockbtn, RelativeLayout goParentBtn, RelativeLayout goTeacherBtn, TextView gytxt, LinearLayout layoutLoginCenter, LinearLayout mybtn, NavigationBar navigationbar, LinearLayout scanbtn, LinearLayout staAppBtn, TextView timelal, TextView txt1, TextView txt2, LinearLayout userDesBtn, CircleImageView userHead, TextView username) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appMoreTxt = appMoreTxt;
        this.appStepTxt = appStepTxt;
        this.appTimeTxt = appTimeTxt;
        this.bingptxt = bingptxt;
        this.bingttxt = bingttxt;
        this.childmsgview = childmsgview;
        this.clockbtn = clockbtn;
        this.goParentBtn = goParentBtn;
        this.goTeacherBtn = goTeacherBtn;
        this.gytxt = gytxt;
        this.layoutLoginCenter = layoutLoginCenter;
        this.mybtn = mybtn;
        this.navigationbar = navigationbar;
        this.scanbtn = scanbtn;
        this.staAppBtn = staAppBtn;
        this.timelal = timelal;
        this.txt1 = txt1;
        this.txt2 = txt2;
        this.userDesBtn = userDesBtn;
        this.userHead = userHead;
        this.username = username;
    }
    public static ActivityMainBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot);
    }

////    @SuppressLint("RestrictedApi")
//    public static ActivityMainBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
////        return (ActivityMainBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_main, root, attachToRoot, component);
//        return (ActivityMainBinding)DataBindingUtil.inflate(inflater,R.layout.activity_main,root,attachToRoot,DataBindingUtil.getDefaultComponent());
//    }

    public static ActivityMainBinding inflate(LayoutInflater inflater) {
        try {
            return DataBindingUtil.inflate(inflater, R.layout.activity_main, null, false, DataBindingUtil.getDefaultComponent());
        }catch (InflateException e){
            e.printStackTrace();
            Log.e("binding",e.getMessage());
        }
        return null;
//        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

//    public static ActivityMainBinding inflate(LayoutInflater inflater, Object component) {
//        return (ActivityMainBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_main, null, false, component);
//    }

//    public static ActivityMainBinding bind(View view) {
//        return bind(view, DataBindingUtil.getDefaultComponent());
//    }
//
//    public static ActivityMainBinding bind(View view, Object component) {
//        return (ActivityMainBinding) bind(component, view, R.layout.activity_main);
//    }
}
