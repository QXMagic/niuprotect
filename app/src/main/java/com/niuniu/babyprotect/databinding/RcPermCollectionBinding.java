package com.niuniu.babyprotect.databinding;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.ViewDataBinding;

import im.niu.protect.R;
public abstract class RcPermCollectionBinding extends ViewDataBinding {
    public final LinearLayout llCheckBoxContainer;
    public final TextView tvPermTitle;

    public RcPermCollectionBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout llCheckBoxContainer, TextView tvPermTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.llCheckBoxContainer = llCheckBoxContainer;
        this.tvPermTitle = tvPermTitle;
    }
    //TODO error
//
//    public static RcPermCollectionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
//        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
//    }
//
//    @Deprecated
//    public static RcPermCollectionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
//        return (RcPermCollectionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.rc_perm_collection, root, attachToRoot, component);
//    }
//
//    public static RcPermCollectionBinding inflate(LayoutInflater inflater) {
//        return inflate(inflater, DataBindingUtil.getDefaultComponent());
//    }
//
//    @Deprecated
//    public static RcPermCollectionBinding inflate(LayoutInflater inflater, Object component) {
//        return (RcPermCollectionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.rc_perm_collection, null, false, component);
//    }
//
//    public static RcPermCollectionBinding bind(View view) {
//        return bind(view, DataBindingUtil.getDefaultComponent());
//    }

    @Deprecated
    public static RcPermCollectionBinding bind(View view, Object component) {
        return (RcPermCollectionBinding) bind(component, view, R.layout.rc_perm_collection);
    }
}
