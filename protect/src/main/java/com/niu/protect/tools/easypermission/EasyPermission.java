package com.niu.protect.tools.easypermission;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
public class EasyPermission implements NextAction {
    public static final String TAG = "EasyPermission";
    private Activity mActivity;
    private String mCurPermission;
    private PermissionRequestListener mPermissionRequestListener;
    private LinkedList<String> mPermissionList = new LinkedList<>();
    private HashMap<String, RequestPermissionRationalListener> mRequestPermissionRationalListenerMap = new HashMap<>();
    private HashMap<String, GrantResult> mPermissionGrantMap = new HashMap<>();

    public EasyPermission(Activity activity) {
        this.mActivity = activity;
    }

    public static EasyPermission with(Activity activity) {
        return new EasyPermission(activity);
    }

    public EasyPermission addPermission(String permission) {
        if (TextUtils.isEmpty(permission)) {
            return this;
        }
        this.mPermissionList.add(permission);
        return this;
    }

    public EasyPermission addPermissions(String... permission) {
        if (permission == null || permission.length <= 0) {
            return this;
        }
        this.mPermissionList.addAll(Arrays.asList(permission));
        return this;
    }

    public EasyPermission addPermissions(String[]... permission) {
        if (permission == null || permission.length <= 0) {
            return this;
        }
        for (String[] group : permission) {
            this.mPermissionList.addAll(Arrays.asList(group));
        }
        return this;
    }

    public EasyPermission addPermissions(List<String> permission) {
        if (permission == null || permission.isEmpty()) {
            return this;
        }
        this.mPermissionList.addAll(permission);
        return this;
    }

    public EasyPermission addRequestPermissionRationaleHandler(String permission, RequestPermissionRationalListener listener) {
        if (TextUtils.isEmpty(permission) || listener == null) {
            return this;
        }
        this.mRequestPermissionRationalListenerMap.put(permission, listener);
        return this;
    }

    public static boolean isPermissionGrant(Context context, String... permissions) {
        for (String permission : permissions) {
            if (context.checkPermission(permission, Process.myPid(), Process.myUid()) != 0) {
                return false;
            }
        }
        return true;
    }

    public static void openSettingPage(Context context) {
        PermissionSettingPage.start(context, false);
    }

    public static void openSettingPage(Context context, boolean newTask) {
        PermissionSettingPage.start(context, newTask);
    }

    public void request(PermissionRequestListener listener) {
        if (listener == null) {
            return;
        }
        if (this.mPermissionList.isEmpty()) {
            throw new RuntimeException("must add some permission to request!!");
        }
        if (Build.VERSION.SDK_INT < 23) {
            Log.i(TAG, "targetSdk < 23 ,no need to request permission dynamic");
            HashMap<String, GrantResult> grantMap = new HashMap<>();
            Iterator<String> it = this.mPermissionList.iterator();
            while (it.hasNext()) {
                String permission = it.next();
                grantMap.put(permission, GrantResult.GRANT);
            }
            listener.onGrant(grantMap);
            return;
        }
        PermissionUtils.checkPermissions(this.mActivity, this.mPermissionList);
        this.mPermissionRequestListener = listener;
        pollPermission();
    }

    private void pollPermission() {
        if (this.mPermissionList.isEmpty()) {
            Log.i(TAG, "permission检查完成，开始申请权限");
            PermissionRequestFragment.build(this.mPermissionGrantMap, this.mPermissionRequestListener).go(this.mActivity);
            return;
        }
        String permission = this.mPermissionList.pollFirst();
        if ("android.permission.REQUEST_INSTALL_PACKAGES".equals(permission)) {
            if (PermissionUtils.isHasInstallPermission(this.mActivity)) {
                this.mPermissionGrantMap.put(permission, GrantResult.GRANT);
                pollPermission();
                return;
            }
            this.mPermissionGrantMap.put(permission, GrantResult.DENIED);
            pollPermission();
        } else if ("android.permission.SYSTEM_ALERT_WINDOW".equals(permission)) {
            if (PermissionUtils.isHasOverlaysPermission(this.mActivity)) {
                this.mPermissionGrantMap.put(permission, GrantResult.GRANT);
                pollPermission();
                return;
            }
            this.mPermissionGrantMap.put(permission, GrantResult.DENIED);
            pollPermission();
        } else if (this.mActivity.checkPermission(permission, Process.myPid(), Process.myUid()) == 0) {
            this.mPermissionGrantMap.put(permission, GrantResult.GRANT);
            pollPermission();
        } else {
            this.mPermissionGrantMap.put(permission, GrantResult.DENIED);
            if (this.mRequestPermissionRationalListenerMap.get(permission) != null) {
                this.mCurPermission = permission;
                this.mRequestPermissionRationalListenerMap.get(permission).onRequestPermissionRational(permission, this.mActivity.shouldShowRequestPermissionRationale(permission), this);
                return;
            }
            pollPermission();
        }
    }

    @Override
    public void next(NextActionType next) {
        if (next == null) {
            this.mPermissionGrantMap.put(this.mCurPermission, GrantResult.IGNORE);
            pollPermission();
            return;
        }
        int i = fun1.$SwitchMap$com$yiou$babyprotect$tools$easypermission$NextActionType[next.ordinal()];
        if (i == 1) {
            pollPermission();
        } else if (i == 2) {
            this.mPermissionGrantMap.put(this.mCurPermission, GrantResult.IGNORE);
            pollPermission();
        } else if (i == 3) {
            this.mPermissionRequestListener.onCancel(this.mCurPermission);
        }
    }

    static class fun1 {
        static final int[] $SwitchMap$com$yiou$babyprotect$tools$easypermission$NextActionType;

        static {
            int[] iArr = new int[NextActionType.values().length];
            $SwitchMap$com$yiou$babyprotect$tools$easypermission$NextActionType = iArr;
            try {
                iArr[NextActionType.NEXT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$yiou$babyprotect$tools$easypermission$NextActionType[NextActionType.IGNORE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$yiou$babyprotect$tools$easypermission$NextActionType[NextActionType.STOP.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }
}
