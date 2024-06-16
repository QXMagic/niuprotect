package com.niuniu.babyprotect.tools.easypermission;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
public final class PermissionRequestFragment extends Fragment implements Runnable {
    private boolean isBackCall;
    private PermissionRequestListener mPermissionRequestListener;
    private int mRequestCode;
    private HashMap<String, GrantResult> mPermissionGrantMap = new HashMap<>();
    private RequestCodeGenerater mRequestCodeGenerater = new RequestCodeGenerater();

    public static PermissionRequestFragment build(HashMap<String, GrantResult> permissionMap, PermissionRequestListener permissionRequestListener) {
        PermissionRequestFragment fragment = new PermissionRequestFragment();
        fragment.setPermissionGrantMap(permissionMap);
        fragment.setPermissionRequestListener(permissionRequestListener);
        return fragment;
    }

    public void setPermissionRequestListener(PermissionRequestListener permissionRequestListener) {
        this.mPermissionRequestListener = permissionRequestListener;
    }

    public void setPermissionGrantMap(HashMap<String, GrantResult> permissionGrantMap) {
        this.mPermissionGrantMap = permissionGrantMap;
    }

    public void go(Activity activity) {
        if (activity != null) {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                throw new RuntimeException("you must request permission in main thread!!");
            }
            activity.getFragmentManager().beginTransaction().add(this, activity.getClass().getName()).commit();
            return;
        }
        throw new RuntimeException("activity is null!!");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!this.isBackCall && requestCode == this.mRequestCode) {
            this.isBackCall = true;
            getActivity().getWindow().getDecorView().postDelayed(this, 500L);
        }
    }

    @Override
    public void run() {
        startRequestPermission();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mRequestCode = this.mRequestCodeGenerater.generate();
        if ((this.mPermissionGrantMap.containsKey("android.permission.REQUEST_INSTALL_PACKAGES") && this.mPermissionGrantMap.get("android.permission.REQUEST_INSTALL_PACKAGES") == GrantResult.DENIED) || (this.mPermissionGrantMap.containsKey("android.permission.SYSTEM_ALERT_WINDOW") && this.mPermissionGrantMap.get("android.permission.SYSTEM_ALERT_WINDOW") == GrantResult.DENIED)) {
            if (this.mPermissionGrantMap.containsKey("android.permission.REQUEST_INSTALL_PACKAGES")) {
                Intent intent = new Intent("android.settings.MANAGE_UNKNOWN_APP_SOURCES", Uri.parse("package:" + getActivity().getPackageName()));
                startActivityForResult(intent, this.mRequestCode);
            }
            if (this.mPermissionGrantMap.containsKey("android.permission.SYSTEM_ALERT_WINDOW")) {
                Intent intent2 = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getActivity().getPackageName()));
                startActivityForResult(intent2, this.mRequestCode);
                return;
            }
            return;
        }
        startRequestPermission();
    }

    private void startRequestPermission() {
        ArrayList<String> needRequestPermissionList = new ArrayList<>();
        Set<Map.Entry<String, GrantResult>> entrySet = this.mPermissionGrantMap.entrySet();
        for (Map.Entry<String, GrantResult> entry : entrySet) {
            if (entry.getValue() == GrantResult.DENIED) {
                needRequestPermissionList.add(entry.getKey());
            }
        }
        Iterator<String> it = needRequestPermissionList.iterator();
        while (it.hasNext()) {
            String permission = it.next();
            Log.i(EasyPermission.TAG, "需要申请的权限：" + permission);
        }
        if (needRequestPermissionList.isEmpty() && this.mPermissionRequestListener != null) {
            Log.i(EasyPermission.TAG, "没有需要申请的权限，直接回调");
            this.mPermissionRequestListener.onGrant(this.mPermissionGrantMap);
            return;
        }
        String[] permissionArray = (String[]) needRequestPermissionList.toArray(new String[needRequestPermissionList.size()]);
        requestPermissions(permissionArray, this.mRequestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != this.mRequestCode) {
            Log.i(EasyPermission.TAG, "requestCode不一致，不处理");
            return;
        }
        for (int i = 0; i < permissions.length; i++) {
            Log.i(EasyPermission.TAG, "onRequestPermissionsResult返回状态，权限：" + permissions[i] + "  是否授权：" + grantResults[i]);
            String permission = permissions[i];
            if ("android.permission.REQUEST_INSTALL_PACKAGES".equals(permission)) {
                if (PermissionUtils.isHasInstallPermission(getActivity().getApplicationContext())) {
                    this.mPermissionGrantMap.put(permission, GrantResult.GRANT);
                } else {
                    this.mPermissionGrantMap.put(permission, GrantResult.DENIED);
                }
            } else if ("android.permission.SYSTEM_ALERT_WINDOW".equals(permission)) {
                if (PermissionUtils.isHasOverlaysPermission(getActivity().getApplicationContext())) {
                    this.mPermissionGrantMap.put(permission, GrantResult.GRANT);
                } else {
                    this.mPermissionGrantMap.put(permission, GrantResult.DENIED);
                }
            } else {
                this.mPermissionGrantMap.put(permissions[i], grantResults[i] == 0 ? GrantResult.GRANT : GrantResult.DENIED);
            }
        }
        Set<Map.Entry<String, GrantResult>> entrySet = this.mPermissionGrantMap.entrySet();
        Log.i(EasyPermission.TAG, "打印最终返回结果：");
        for (Map.Entry<String, GrantResult> entry : entrySet) {
            Log.i(EasyPermission.TAG, "权限：" + entry.getKey() + "  状态：" + entry.getValue());
        }
        PermissionRequestListener permissionRequestListener = this.mPermissionRequestListener;
        if (permissionRequestListener != null) {
            permissionRequestListener.onGrant(this.mPermissionGrantMap);
        }
        getFragmentManager().beginTransaction().remove(this).commit();
    }

    private static class RequestCodeGenerater {
        private static volatile int FACTOR_REQUEST_CODE = 0;

        private RequestCodeGenerater() {
        }

        public synchronized int generate() {
            int i;
            i = FACTOR_REQUEST_CODE;
            FACTOR_REQUEST_CODE = i + 1;
            return i;
        }
    }
}
