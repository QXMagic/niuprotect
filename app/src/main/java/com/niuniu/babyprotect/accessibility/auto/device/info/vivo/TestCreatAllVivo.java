package com.niuniu.babyprotect.accessibility.auto.device.info.vivo;

import android.content.Context;
import com.niuniu.babyprotect.tools.ILog;
public class TestCreatAllVivo {
    public static void createAll(Context context) {
        ILog.d("----", "-----------------------------------");
        new Vivo_Info_12().createPage(context);
        new Vivo_Info_13().createPage(context);
        new Vivo_originOs10().createPage(context);
    }
}
