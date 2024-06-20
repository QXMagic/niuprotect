package com.niuniu.babyprotect.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;

import com.niuniu.babyprotect.ui.NavigationBar;

import de.hdodenhof.circleimageview.CircleImageView;
import im.niu.protect.R;
public class ActivityMainBindingImpl extends ActivityMainBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final RelativeLayout mboundView0;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_login_center, 1);
        sViewsWithIds.put(R.id.navigationbar, 2);
        sViewsWithIds.put(R.id.childmsgview, 3);
        sViewsWithIds.put(R.id.userHead, 4);
        sViewsWithIds.put(R.id.username, 5);
        sViewsWithIds.put(R.id.goParentBtn, 6);
        sViewsWithIds.put(R.id.bingptxt, 7);
        sViewsWithIds.put(R.id.goTeacherBtn, 8);
        sViewsWithIds.put(R.id.bingttxt, 9);
        sViewsWithIds.put(R.id.scanbtn, 10);
        sViewsWithIds.put(R.id.staAppBtn, 11);
        sViewsWithIds.put(R.id.clockbtn, 12);
        sViewsWithIds.put(R.id.mybtn, 13);
        sViewsWithIds.put(R.id.gytxt, 14);
        sViewsWithIds.put(R.id.appTimeTxt, 15);
        sViewsWithIds.put(R.id.appMoreTxt, 16);
        sViewsWithIds.put(R.id.appStepTxt, 17);
        sViewsWithIds.put(R.id.userDesBtn, 18);
        sViewsWithIds.put(R.id.timelal, 19);
        sViewsWithIds.put(R.id.txt1, 20);
        sViewsWithIds.put(R.id.txt2, 21);
    }

    public ActivityMainBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 22, sIncludes, sViewsWithIds));
    }

    private ActivityMainBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (TextView) bindings[16], (TextView) bindings[17], (TextView) bindings[15], (TextView) bindings[7], (TextView) bindings[9], (LinearLayout) bindings[3], (LinearLayout) bindings[12], (RelativeLayout) bindings[6], (RelativeLayout) bindings[8], (TextView) bindings[14], (LinearLayout) bindings[1], (LinearLayout) bindings[13], (NavigationBar) bindings[2], (LinearLayout) bindings[10], (LinearLayout) bindings[11], (TextView) bindings[19], (TextView) bindings[20], (TextView) bindings[21], (LinearLayout) bindings[18], (CircleImageView) bindings[4], (TextView) bindings[5]);
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
