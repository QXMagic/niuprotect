package com.niu.protect.accessibility.auto.device;
public class VivoDeviceInfo {

    public static final class MineAPP {
        public static final String Mine_app_PackageName = "com.jinli.accessibility";
        public static final String Mine_app_main_class = "com.jinli.accessibility.MainActivity";
        public static final String Mine_btn_Breeno = "com.jinli.accessibility:id/button_2";
        public static final String Mine_btn_OVERLAY_WINDOW = "com.jinli.accessibility:id/button_7";
        public static final String Mine_btn_auto_background = "com.jinli.accessibility:id/button_4";
        public static final String Mine_btn_notification = "com.jinli.accessibility:id/button_8";
        public static final String Mine_btn_use = "com.jinli.accessibility:id/button_5";
    }

    public static final class PageInfo {
    }

    public static final class StepFiveNOtification {
        public static final String Detail_Class = "com.coloros.notificationmanager.AppNotificationSettingsActivity";
        public static final String Detail_Click_TEXT = "允许通知";
        public static final String Detail_PackageName = "com.coloros.notificationmanager";
        public static final String Detail_checkBox_id = "android:id/switch_widget";
        public static final String SETTING_Notification_ONE = "通知管理";
        public static final String SETTING_PackageName = "com.android.settings";
        public static boolean STATUS_CLICKED = false;
        public static final int Step_Five_Notification = 5;
        public static final String setting_auto_class = "com.android.settings.applications.InstalledAppDetailsTop";
    }

    public static final class StepFourAppUse {
        public static final String Detail_Class = "com.android.settings.SubSettings";
        public static final String Detail_Click_TEXT = "允许查看使用情况";
        public static final String Detail_PackageName = "com.android.settings";
        public static final String SETTING_Click_TEXT = "Accessibility";
        public static final String SETTING_PackageName = "com.android.settings";
        public static boolean STATUS_CLICKED = false;
        public static final int Step_four_APP_USE_REMENBER = 4;
        public static final String setting_use_class = "com.android.settings.Settings$UsageAccessSettingsActivity";
    }

    public static final class StepSixOverlayWindows {
        public static final String Detail_Class = "com.android.settings.SubSettings";
        public static final String Detail_Click_TEXT = "允许显示在其他应用的上层";
        public static final String Detail_PackageName = "com.android.settings";
        public static final String Detail_checkBox_id = "android:id/switch_widget";
        public static final String SETTING_Overlays_ONE = "Accessibility";
        public static final String SETTING_Overlays_PackageName = "com.android.settings";
        public static boolean STATUS_CLICKED = false;
        public static final int Step_SIX_OVER = 6;
        public static final String setting_Overlays_class = "com.android.settings.Settings$OverlaySettingsActivity";
    }

    public static final class StepThreeBackRunning {
        public static final String Detail_Class = "com.coloros.powermanager.fuelgaue.PowerAppsBgSetting";
        public static final String Detail_Click_TEXT = "允许后台运行";
        public static final String Detail_PackageName = "com.coloros.oppoguardelf";
        public static final String Detail_checkBox_id = "com.coloros.oppoguardelf:id/color_tail_mark";
        public static final String SETTING_PackageName = "com.android.settings";
        public static final String SETTING_TEXT_ONE = "耗电保护";
        public static boolean STATUS_CLICKED = false;
        public static final int Step_three_background_running = 3;
        public static final String setting_auto_class = "com.android.settings.applications.InstalledAppDetailsTop";
    }

    public static final class StepTwoAutoStart {
        public static final String SETTING_AUTO_TEXT = "允许自动启动";
        public static final String SETTING_PackageName = "com.android.settings";
        public static boolean STATUS_CLICKED = false;
        public static final int Step_two_auto_start = 2;
        public static final String setting_auto_class = "com.android.settings.applications.InstalledAppDetailsTop";
    }
}
