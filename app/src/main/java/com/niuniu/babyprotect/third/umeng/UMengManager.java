package com.niuniu.babyprotect.third.umeng;

import android.content.Context;

import com.niuniu.babyprotect.BabyApplication;
import com.niuniu.babyprotect.model.UserInfo;
import com.niuniu.babyprotect.tools.ILog;

import java.util.Calendar;
public class UMengManager {
    public static final String APP_KEY = "6372fe9088ccdf4b7e65962e";
    public static final String MESSAGE_SECRET = "6372fe9088ccdf4b7e65962e";
    public static final String TAG = "UMengManager.Class";
    static int sendMessageTimes = 0;

    public static void initUmeng(BabyApplication context, String channel) {
//        UMConfigure.setLogEnabled(true);
//        UMConfigure.init(context, APP_KEY, channel, 1, "");
//        register(context);
//        MiPushRegistar.register(context, "2882303761518771588", "5351877148588");
//        addUmengMessageHandler(context);
    }

    public static void preInit(Context context, String channel) {
//        UMConfigure.preInit(context, "6372fe9088ccdf4b7e65962e", channel);
    }

    public static void register(Context context) {
    }

    public static void addAlias(Context context, UserInfo userInfo) {
//        PushAgent mPushAgent = PushAgent.getInstance(context);
//        ILog.d("Alias---", userInfo.getUsername());
//        if (userInfo != null) {
//            mPushAgent.deleteAlias(userInfo.getUsername(), "username", new UTrack.ICallBack() {
//                @Override
//                public void onMessage(boolean isSuccess, String message) {
//                }
//            });
//            mPushAgent.setAlias(userInfo.getUsername(), "username", new UTrack.ICallBack() {
//                @Override
//                public void onMessage(boolean isSuccess, String message) {
//                    ILog.d("mPushAgent", message);
//                }
//            });
//        }
    }

    private static void addUmengMessageHandler(Context context) {
//        new UmengMessageHandler() {
//            @Override
//            public void dealWithNotificationMessage(Context context2, UMessage msg) {
//                ILog.d(UMengManager.TAG, "notification receiver:" + msg.getRaw().toString());
//                super.dealWithNotificationMessage(context2, msg);
//            }
//
//            @Override
//            public Notification getNotification(Context context2, UMessage msg) {
//                return super.getNotification(context2, msg);
//            }
//
//            @Override
//            public void dealWithCustomMessage(Context context2, UMessage msg) {
//                super.dealWithCustomMessage(context2, msg);
//                ILog.d(UMengManager.TAG, "custom receiver:" + msg.getRaw().toString());
//                String content = msg.getRaw() != null ? msg.getRaw().toString() : "";
//                if (!TextUtils.isEmpty(content)) {
//                    UmengCustomMsg mUmengCustomMsg = (UmengCustomMsg) new Gson().fromJson(content, (Class<Object>) UmengCustomMsg.class);
//                    String custom = mUmengCustomMsg.getBody().getCustom();
//                    if (!TextUtils.isEmpty(custom)) {
//                        int customInt = Integer.valueOf(custom).intValue();
//                        if (customInt == 12) {
//                            context2.startService(new Intent(context2, LocationTraceService.class));
//                            return;
//                        }
//                    }
//                }
//                StudentMainController.getInstance().requestMainControl();
//                UMengManager.sendMessageTimes++;
//            }
//        };
    }

    private void editMessageSendTimes() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(5);
        ILog.d("day---", day + "");
    }
//
//    private void dealContent(UMessage msg) {
//    }
}
