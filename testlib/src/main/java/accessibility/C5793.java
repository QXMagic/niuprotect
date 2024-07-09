package accessibility;

import androidx.annotation.NonNull;

import accessibility.lib.PermissionNextState;
import accessibility.lib.SafeScreenCheck;

public class C5793 implements PermissionNextState.StateCall {

    public class RunnableC3253 implements Runnable {

        public class RunnableC5794 implements Runnable {
            public RunnableC5794() {
            }

            @Override // java.lang.Runnable
            public void run() {

            }
        }

        public class RunnableC5795 implements Runnable {
            public RunnableC5795() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (PermissionAutoManager.checkIndex(null, 23)) {
                    return;
                }
                LogUtil.m13850("截屏授权失败");
                SafeScreenCheck.m23159();
                PermissionEvent permissionEvent = new PermissionEvent();
                permissionEvent.setCode(101);
                permissionEvent.post();

            }
        }

        /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$刻槒唱镧詴$肌緭$葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
        /* loaded from: E:\apk\monitor\监控\classes5.dex */
        public class RunnableC5796 implements Runnable {
            public RunnableC5796() {
            }

            @Override // java.lang.Runnable
            public void run() {
//                PermissionCheckActivity.this.f11946.f19327 = false;
//                if (PermissionCheckActivity.this.f11946.f19328 != null) {
//                    PermissionCheckActivity.this.m20058(PermissionCheckActivity.this.f11946.f19328.getPermissionName() + "开始自动授权");
//                    if (PermissionAutoManager.m22514(PermissionCheckActivity.this.f11946.f19328.getId())) {
//                        return;
//                    }
//                    if (PermissionCheckActivity.this.f11946.f19328.getId() != 23 && PermissionCheckActivity.this.f11946.f19328.getId() != 4) {
//                        LogUtil.m13850("自动授权失败添加权限:" + PermissionCheckActivity.this.f11946.f19328.getPermissionName());
//                        PermissionCheckActivity.this.f11946.f19328.setManual(true);
//                        PermissionCheckActivity.this.f11946.f19324.add(PermissionCheckActivity.this.f11946.f19328);
//                    }
//                    PermissionCheckActivity.this.m20053("无自动实现类，手动开启");
//                }
            }
        }

        public RunnableC3253() {
        }

        @Override // java.lang.Runnable
        public void run() {
//            if (PermissionCheckActivity.this.f11946.f19328 == null) {
//                return;
//            }
//            PermissionCheckActivity permissionCheckActivity = PermissionCheckActivity.this;
//            PermissionAutoManager.m22513(permissionCheckActivity, permissionCheckActivity.f11946.f19328, true);
//            PermissionCheckActivity.this.m20058(PermissionCheckActivity.this.f11946.f19328.getPermissionName() + "未授权");
//            PermissionCheckActivity.this.f11946.f19327 = true;
//            if (PermissionCheckActivity.this.f11946.f19328.getId() == 23) {
//                PermissionCheckActivity.this.f11946.f19327 = false;
//                new Handler().postDelayed(new RunnableC5795(), 5000L);
//            } else if (PermissionCheckActivity.this.f11946.f19328.getId() == 41) {
//                PermissionCheckActivity.this.f11946.f19327 = false;
//                new Handler().postDelayed(new RunnableC5794(), 5000L);
//            } else {
//                PermissionAutoManager.m14282(new RunnableC5796(), 500L);
//            }
        }
    }

    public C5793() {
    }

    @Override // com.zlfc.child.bean.PermissionNextState.StateCall
    public void call(@NonNull PermissionNextState permissionNextState) {
//        PermissionCheckActivity.this.f11946.f19321 = permissionNextState.getNextState();
//        if (PermissionCheckActivity.this.f11946.f19321 == null) {
//            PermissionCheckActivity.this.m20052();
//            return;
//        }
//        PermissionCheckActivity.this.f11946.f19328 = PermissionCheckActivity.this.f11946.f19321.getCurr();
//        if (PermissionCheckActivity.this.f11946.f19328 == null) {
//            PermissionCheckActivity.this.m20053("viewModel.mCurrentBean为空");
//            return;
//        }
//        PermissionCheckActivity permissionCheckActivity = PermissionCheckActivity.this;
//        permissionCheckActivity.m20058(permissionCheckActivity.f11946.f19328.toString());
//        if (!PermissionAutoManager.m22512(PermissionCheckActivity.this.f12051, PermissionCheckActivity.this.f11946.f19328.getId())) {
//            PermissionCheckActivity.this.f19306.postDelayed(new RunnableC3253(), 1000L);
//        } else {
//            PermissionCheckActivity.this.m20053("权限已开启");
//        }
    }
}
