package com.niuniu.babyprotect.third.bugly;

import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
//import com.tencent.bugly.crashreport.CrashReport;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class BuglyTools {
    public static void initBugly(Context context) {
        context.getPackageName();
        getProcessName(Process.myPid());
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
//        CrashReport.initCrashReport(context, "e5d1b2abd2", false, strategy);
    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            try {
                reader.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            return processName;
        } catch (Throwable throwable) {
            try {
                throwable.printStackTrace();
                if (reader != null) {
                    try {
                        reader.close();
                        return null;
                    } catch (IOException exception2) {
                        exception2.printStackTrace();
                        return null;
                    }
                }
                return null;
            } catch (Throwable th) {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException exception3) {
                        exception3.printStackTrace();
                    }
                }
                throw th;
            }
        }
    }
}
