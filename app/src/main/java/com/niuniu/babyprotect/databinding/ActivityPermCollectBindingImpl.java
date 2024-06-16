package com.niuniu.babyprotect.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.niuniu.babyprotect.ui.NavigationBar;

import im.niu.protect.R;
public class ActivityPermCollectBindingImpl extends ActivityPermCollectBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final RelativeLayout mboundView0;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.navigationbar, 1);
        sViewsWithIds.put(R.id.text_info, 2);
        sViewsWithIds.put(R.id.rc_container, 3);
        sViewsWithIds.put(R.id.btn_start, 4);
        sViewsWithIds.put(R.id.ll_contact, 5);
        sViewsWithIds.put(R.id.tv_phone, 6);
    }

    public ActivityPermCollectBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root,null);
        //TODO error
//        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private ActivityPermCollectBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (Button) bindings[4], (LinearLayout) bindings[5], (NavigationBar) bindings[1], (RecyclerView) bindings[3], (TextView) bindings[2], (TextView) bindings[6]);
        this.mDirtyFlags = -1L;
        RelativeLayout relativeLayout = (RelativeLayout) bindings[0];
        this.mboundView0 = relativeLayout;
        relativeLayout.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean setVariable(int variableId, Object variable) {
        return true;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    @Override
    protected void executeBindings() {
        synchronized (this) {
            long j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
    }
}
