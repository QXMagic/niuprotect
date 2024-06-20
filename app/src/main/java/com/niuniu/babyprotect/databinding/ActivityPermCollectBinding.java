package com.niuniu.babyprotect.databinding;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.niuniu.babyprotect.ui.NavigationBar;

import im.niu.protect.R;
public abstract class ActivityPermCollectBinding extends ViewDataBinding {
    public final Button btnStart;
    public final LinearLayout llContact;
    public final NavigationBar navigationbar;
    public final RecyclerView rcContainer;
    public final TextView textInfo;
    public final TextView tvPhone;

    public ActivityPermCollectBinding(Object _bindingComponent, View _root, int _localFieldCount, Button btnStart, LinearLayout llContact, NavigationBar navigationbar, RecyclerView rcContainer, TextView textInfo, TextView tvPhone) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btnStart = btnStart;
        this.llContact = llContact;
        this.navigationbar = navigationbar;
        this.rcContainer = rcContainer;
        this.textInfo = textInfo;
        this.tvPhone = tvPhone;
    }

    public static ActivityPermCollectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @SuppressLint("RestrictedApi")
    public static ActivityPermCollectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityPermCollectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_perm_collect, root, attachToRoot, component);
    }

    public static ActivityPermCollectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @SuppressLint("RestrictedApi")
    public static ActivityPermCollectBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityPermCollectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_perm_collect, null, false, component);
    }

    public static ActivityPermCollectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityPermCollectBinding bind(View view, Object component) {
        return (ActivityPermCollectBinding) bind(component, view, R.layout.activity_perm_collect);
    }
}
