package com.niu.protect.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
public class NetCheckUtil {
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) {
                return false;
            }
            if (Build.VERSION.SDK_INT >= 29) {
                Network network = cm.getActiveNetwork();
                NetworkCapabilities networkCapabilities = cm.getNetworkCapabilities(network);
                return networkCapabilities != null && networkCapabilities.hasCapability(12);
            }
            NetworkInfo info = cm.getActiveNetworkInfo();
            return info != null && info.isConnected() && info.isAvailable();
        } catch (Exception e) {
            return false;
        }
    }
}
