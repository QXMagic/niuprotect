package accessibility;

import androidx.annotation.RequiresPermission;

import com.blankj.utilcode.util.NetworkUtils;
import com.niu.protect.tools.ILog;

public class LogHelper {
    private static final String TAG = "logger";
    private static LogHelper _instance = new LogHelper();
    public static LogHelper instance() {
        return _instance;
    }

    public static void m27987(String s) {
        ILog.d(TAG,s);
    }

    public static void m27985(String s) {
        ILog.d(TAG,s);
    }

    public static void m27994(String s) {
        ILog.d(TAG,s);
    }

    public void m27999(String msg) {
        ILog.d(TAG,msg);
    }

    public void m16303(String tag, String s) {
        ILog.d(tag,s);
    }

    public void info(String message) {
        ILog.d(TAG,message);
    }

    public void m27998(String tag, String s) {
    }
}


class NetWorkUtils{
    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    /* renamed from: 肌緭 */
    public static boolean m14317() {
        return NetworkUtils.isConnected();
    }

}

