package accessibility.lib;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Process;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ThreadUtils;
import accessibility.LogHelper;

import java.util.ArrayList;
import java.util.List;

public class LauncherAppGetHelper {
    public boolean f23429;

    public Context f14296;


    public class C4220 extends ThreadUtils.SimpleTask<List<LauncherBean>> {

        /* renamed from: 肌緭 */
        public final /* synthetic */ InterfaceC6005 f14297;

        public C4220(InterfaceC6005 interfaceC6005) {
            this.f14297 = interfaceC6005;
        }

        @Override // com.blankj.utilcode.util.ThreadUtils.SimpleTask, com.blankj.utilcode.util.ThreadUtils.Task
        public void onFail(Throwable th) {
            LauncherAppGetHelper.this.f23429 = false;
            this.f14297.mo5628("");
            LogHelper.instance().m16303("APP管理", "获取手机App失败:" + th.getMessage());
        }

        @Override // com.blankj.utilcode.util.ThreadUtils.Task
        public void onSuccess(List<LauncherBean> list) {
            LauncherAppGetHelper.this.f23429 = false;
            this.f14297.mo16738(list);
        }

        @Override // com.blankj.utilcode.util.ThreadUtils.Task
        public List<LauncherBean> doInBackground() {
            LauncherAppsCompat m25586 = LauncherAppsCompat.m25586(LauncherAppGetHelper.this.f14296);
            ArrayList arrayList = new ArrayList();
            List<LauncherActivityInfo> mo15434 = m25586.mo15434(null, Process.myUserHandle());
            if (mo15434 != null && !mo15434.isEmpty()) {
                for (int i = 0; i < mo15434.size(); i++) {
                    LauncherActivityInfo launcherActivityInfo = mo15434.get(i);
                    PackageManager packageManager = LauncherAppGetHelper.this.f14296.getPackageManager();
                    String packageName = launcherActivityInfo.getComponentName().getPackageName();
                    if (!AppUtils.getAppPackageName().equals(packageName) && !"com.android.settings".equals(packageName)) {
                        ApplicationInfo applicationInfo = launcherActivityInfo.getApplicationInfo();
                        Drawable loadIcon = applicationInfo.loadIcon(packageManager);
                        LauncherBean launcherBean = new LauncherBean();
                        launcherBean.appName = applicationInfo.loadLabel(packageManager).toString();
                        launcherBean.packageName = applicationInfo.packageName;
                        launcherBean.mIconBitmap = LauncherAppGetHelper.this.m26065(loadIcon);
                        arrayList.add(launcherBean);
                    }
                }
            }
            return arrayList;
        }
    }

    public LauncherAppGetHelper(Context context) {
        this.f14296 = context;
    }

    public void m15584(InterfaceC6005<List<LauncherBean>> interfaceC6005) {
        if (this.f23429) {
            return;
        }
        ThreadUtils.executeByCached(new C4220(interfaceC6005));
    }

    public List<LauncherBean> m26064() {
        LauncherAppsCompat m25586 = LauncherAppsCompat.m25586(this.f14296);
        ArrayList arrayList = new ArrayList();
        List<LauncherActivityInfo> mo15434 = m25586.mo15434(null, Process.myUserHandle());
        if (mo15434 != null && !mo15434.isEmpty()) {
            for (int i = 0; i < mo15434.size(); i++) {
                LauncherActivityInfo launcherActivityInfo = mo15434.get(i);
                PackageManager packageManager = this.f14296.getPackageManager();
                String packageName = launcherActivityInfo.getComponentName().getPackageName();
                if (!AppUtils.getAppPackageName().equals(packageName) && !"com.android.settings".equals(packageName)) {
                    ApplicationInfo applicationInfo = launcherActivityInfo.getApplicationInfo();
                    Drawable loadIcon = applicationInfo.loadIcon(packageManager);
                    LauncherBean launcherBean = new LauncherBean();
                    launcherBean.appName = applicationInfo.loadLabel(packageManager).toString();
                    launcherBean.packageName = applicationInfo.packageName;
                    launcherBean.mIconBitmap = m26065(loadIcon);
                    arrayList.add(launcherBean);
                }
            }
        }
        return arrayList;
    }

    public final Bitmap m26065(Drawable drawable) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, drawable.getOpacity() != PixelFormat.UNKNOWN ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(canvas);
        return createBitmap;
    }

}

