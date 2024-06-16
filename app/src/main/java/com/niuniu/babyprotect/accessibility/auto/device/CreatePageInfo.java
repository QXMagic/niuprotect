package com.niuniu.babyprotect.accessibility.auto.device;
public class CreatePageInfo {
    public static void creatPage(String systermOs) {
        String str;
        int hashCode = systermOs.hashCode();
        if (hashCode == -1790778101) {
            str = "ColorOSV7.1";
        } else if (hashCode != 876983523) {
            return;
        } else {
            str = "Funtouch12.0";
        }
        systermOs.equals(str);
    }
}
