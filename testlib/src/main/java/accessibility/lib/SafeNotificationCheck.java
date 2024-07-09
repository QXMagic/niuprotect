package accessibility.lib;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ObjectUtils;
import accessibility.AccessibilityEventImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SafeNotificationCheck extends ContextWrapper {

    /* compiled from: SafeNotificationCheck.java */
    /* renamed from: 攳艪抣.鞈鵚主瀭孩濣痠閕讠陲檓敐$肌緭 */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public static final class C3713 {

        /* renamed from: 刻槒唱镧詴 */
        @NonNull
        public final Notification f21270;

        /* renamed from: 肌緭 */
        @Nullable
        public String f13098;

        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        @Nullable
        public String f21271;

        public C3713(@NonNull Notification notification, @NonNull String str, String str2) {
            this.f21270 = notification;
            this.f21271 = str;
            this.f13098 = str2;
        }

        @Nullable
        /* renamed from: 刻槒唱镧詴 */
        public final String m23171() {
            return this.f21271;
        }

        @Nullable
        /* renamed from: 肌緭 */
        public final String m14527() {
            return this.f13098;
        }

        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        public C3713 m23172() {
            RemoteViews remoteViews;
            Notification notification = this.f21270;
            Integer num = null;
            if (notification == null || (remoteViews = notification.contentView) == null) {
                return null;
            }
            Class<?> cls = remoteViews.getClass();
            try {
                HashMap hashMap = new HashMap();
                Field[] declaredFields = cls.getDeclaredFields();
                int length = declaredFields.length;
                int i = 0;
                while (i < length) {
                    Field field = declaredFields[i];
                    if (field.getName().equals("mActions")) {
                        boolean z = true;
                        field.setAccessible(true);
                        Iterator it = ((ArrayList) field.get(remoteViews)).iterator();
                        int i2 = 0;
                        while (it.hasNext()) {
                            Object next = it.next();
                            Field[] declaredFields2 = next.getClass().getDeclaredFields();
                            int length2 = declaredFields2.length;
                            Integer num2 = num;
                            Object obj = num2;
                            int i3 = 0;
                            while (i3 < length2) {
                                Field field2 = declaredFields2[i3];
                                field2.setAccessible(z);
                                if (field2.getName().equals("value")) {
                                    obj = field2.get(next);
                                } else if (field2.getName().equals("type")) {
                                    num2 = Integer.valueOf(field2.getInt(next));
                                }
                                i3++;
                                z = true;
                            }
                            if (num2 != null && (num2.intValue() == 9 || num2.intValue() == 10)) {
                                if (i2 == 0) {
                                    this.f21271 = obj != null ? obj.toString() : "";
                                } else if (i2 == 1) {
                                    this.f13098 = obj != null ? obj.toString() : "";
                                } else {
                                    hashMap.put(Integer.toString(i2), obj != null ? obj.toString() : null);
                                }
                                i2++;
                            }
                            num = null;
                            z = true;
                        }
                    }
                    i++;
                    num = null;
                }
                if (!ObjectUtils.isEmpty((CharSequence) this.f21271)) {
                    ObjectUtils.isEmpty((CharSequence) this.f13098);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }
    }

    public SafeNotificationCheck(Context context) {
        super(context);
    }

    /* renamed from: 刻槒唱镧詴 */
    public final boolean m23169(String str) throws PackageManager.NameNotFoundException {
        if (str == null) {
            return false;
        }
        try {
            return (getPackageManager().getApplicationInfo(str, 0).flags & 1) > 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* renamed from: 肌緭 */
    public final void m14526(AccessibilityEventImpl accessibilityEventImpl, @Nullable Notification notification) {
        try {
            if (accessibilityEventImpl.getPackageName() != null) {
                if ((notification != null ? notification.contentIntent : null) == null || ObjectUtils.equals(accessibilityEventImpl.getPackageName(), getPackageName()) || !m23169(accessibilityEventImpl.getPackageName().toString())) {
                    return;
                }
                String appName = AppUtils.getAppName();
                try {
                    for (CharSequence charSequence : accessibilityEventImpl.m15021()) {
                        if (charSequence.toString().contains(appName) || charSequence.toString().contains("耗电")) {
                            PendingIntent pendingIntent = notification.contentIntent;
                            if (pendingIntent != null) {
                                pendingIntent.cancel();
                            }
                        }
                    }
                    String[] m23170 = m23170(notification);
                    if (m23170 != null) {
                        String str = m23170[0];
                        String str2 = m23170[1];
                        if (str.contains(appName) || str2.contains(appName) || str.contains("耗电")) {
                            notification.contentIntent.cancel();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public final String[] m23170(Notification notification) {
        String str;
        if (notification == null) {
            return null;
        }
        int i = Build.VERSION.SDK_INT;
        Bundle bundle = notification.extras;
        String str2 = "";
        if (i < 19 || bundle == null) {
            str = "";
        } else {
            str2 = bundle.getCharSequence(NotificationCompat.EXTRA_TITLE, "").toString();
            str = bundle.getCharSequence(NotificationCompat.EXTRA_TEXT, "").toString();
        }
        if (TextUtils.isEmpty(str2)) {
            C3713 m23172 = new C3713(notification, str2, str).m23172();
            if (TextUtils.isEmpty(m23172.m23171()) || TextUtils.isEmpty(m23172.m14527())) {
                return null;
            }
            str2 = m23172.m23171();
            str = m23172.m14527();
        }
        return new String[]{str2, str};
    }
}
