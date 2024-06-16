package com.niuniu.babyprotect.tools.easypermission;

import java.util.Map;
public abstract class PermissionRequestListener {
    public abstract void onCancel(String str);

    public abstract void onGrant(Map<String, GrantResult> map);
}
