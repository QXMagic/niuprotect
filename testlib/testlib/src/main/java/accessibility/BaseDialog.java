package accessibility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.Objects;

public abstract class BaseDialog extends Dialog implements LifecycleEventObserver {
    public Context context;
    private float hPercent;
    public int height;
    private boolean isFinish;
    public InterfaceC3288 mDialogClickListener;
    private SparseArray<View> mViews;
    private int resID;
    public View view;
    private float wPercent;
    public int width;

    /* renamed from: com.zlfc.common.base.BaseDialog$肌緭 */
    /* loaded from: E:\apk\monitor\监控\classes5.dex */
    public interface InterfaceC3288 {
        /* renamed from: 刻槒唱镧詴 */
        void mo16830();

        /* renamed from: 肌緭 */
        void mo5649();
    }

    public BaseDialog(Context context) {
        super(context);
        this.mViews = new SparseArray<>();
        this.context = context;
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).getLifecycle().addObserver(this);
        }
    }

    public float getHeightPercent() {
        return this.hPercent;
    }

    @LayoutRes
    public abstract int getLayoutID();

    public int getRootResID() {
        return this.resID;
    }

    public View getView() {
        return this.view;
    }

    public <V extends View> V getViewByID(@IdRes int i) {
        V v = (V) this.mViews.get(i);
        if (v == null) {
            try {
                v = (V) this.view.findViewById(i);
                this.mViews.put(i, v);
            } catch (NullPointerException unused) {
                throw new NullPointerException("请确认资源文件不为空");
            }
        }
        return v;
    }

    public float getWidthPercent() {
        return this.wPercent;
    }

    public abstract void initEvent();

    public abstract void initView();

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LayoutInflater from = LayoutInflater.from(this.context);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        int layoutID = getLayoutID();
        if (layoutID != 0) {
            View inflate = from.inflate(layoutID, (ViewGroup) null);
            this.view = inflate;
            setContentView(inflate);
            Window window = getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            DisplayMetrics displayMetrics = this.context.getResources().getDisplayMetrics();
            int i = this.context.getResources().getConfiguration().orientation;
            this.height = displayMetrics.heightPixels;
            this.width = displayMetrics.widthPixels;
            if (getWidthPercent() != 0.0f) {
                attributes.width = (int) (this.width * this.wPercent);
            } else if (i == 2) {
                attributes.width = (int) (this.width * 0.6d);
            } else {
                attributes.width = (int) (this.width * 0.9d);
            }
            if (getHeightPercent() != 0.0f) {
                attributes.height = (int) (this.height * this.hPercent);
            }
            int i2 = this.resID;
            if (i2 != 0) {
                window.setBackgroundDrawableResource(i2);
            }
            window.setAttributes(attributes);
            initView();
            initEvent();
            return;
        }
        throw new NullPointerException(getClass().getSimpleName() + "没有设置资源文件");
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        if (Lifecycle.Event.ON_DESTROY == event) {
            dismiss();
            this.isFinish = true;
            ((AppCompatActivity) this.context).getLifecycle().removeObserver(this);
            return;
        }
        this.isFinish = false;
    }

    public void setBackgroundWindowTransparent() {
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(Color.parseColor("#00000000"));
        getWindow().setBackgroundDrawable(colorDrawable);
    }

    public BaseDialog setHeightPercent(float f) {
        this.hPercent = f;
        return this;
    }

    public void setOnClickListener(View.OnClickListener onClickListener, View... viewArr) {
        Objects.requireNonNull(onClickListener, "BaseFragment：#.setOnClickListener()方法参数 (View.OnClickListener listener) 为null；#附：监听都不给,你想做啥?");
        Objects.requireNonNull(viewArr, "BaseFragment：#.setOnClickListener()方法参数 (View... views) 为null；#附：View都不给,你想给谁设置点击事件啊?");
        for (View view : viewArr) {
            view.setOnClickListener(onClickListener);
        }
    }

    public BaseDialog setRootResID(int i) {
        this.resID = i;
        return this;
    }

    public BaseDialog setWidthpercent(float f) {
        this.wPercent = f;
        return this;
    }

    @Override // android.app.Dialog
    public void show() {
        if (this.isFinish) {
            return;
        }
        super.show();
    }

    public BaseDialog(Context context, int i) {
        super(context, i);
        this.mViews = new SparseArray<>();
        this.context = context;
    }

    public BaseDialog(Context context, boolean z, OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
        this.mViews = new SparseArray<>();
        this.context = context;
    }
}
