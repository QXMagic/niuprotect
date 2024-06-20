package com.niu.protect.map.maputil;

import android.content.Context;
import android.content.res.Resources;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.niu.protect.R;
public class BitmapUtil {
    public static BitmapDescriptor bmArrowPoint = null;
    public static BitmapDescriptor bmStart = null;
    public static BitmapDescriptor bmEnd = null;
    public static BitmapDescriptor bmGeo = null;
    public static BitmapDescriptor bmGcoding = null;
    public static BitmapDescriptor bmCs = null;
    public static BitmapDescriptor bmJsc = null;
    public static BitmapDescriptor bmJzw = null;
    public static BitmapDescriptor bmStay = null;

    public static void init() {
        bmArrowPoint = BitmapDescriptorFactory.fromResource(R.mipmap.icon_point);
        bmStart = BitmapDescriptorFactory.fromResource(R.mipmap.icon_start);
        bmEnd = BitmapDescriptorFactory.fromResource(R.mipmap.icon_end);
        bmGeo = BitmapDescriptorFactory.fromResource(R.mipmap.icon_geo);
        bmGcoding = BitmapDescriptorFactory.fromResource(R.mipmap.icon_gcoding);
        bmCs = BitmapDescriptorFactory.fromResource(R.mipmap.icon_sc);
        bmJsc = BitmapDescriptorFactory.fromResource(R.mipmap.icon_jsc);
        bmJzw = BitmapDescriptorFactory.fromResource(R.mipmap.icon_jzw);
        bmStay = BitmapDescriptorFactory.fromResource(R.mipmap.icon_stay);
    }

    public static void clear() {
        bmArrowPoint.recycle();
        bmStart.recycle();
        bmEnd.recycle();
        bmGeo.recycle();
        bmCs.recycle();
        bmJsc.recycle();
        bmJzw.recycle();
        bmStay.recycle();
    }

    public static BitmapDescriptor getMark(Context context, int index) {
        int resourceId;
        Resources res = context.getResources();
        if (index > 10) {
            resourceId = res.getIdentifier("icon_markx", "mipmap", context.getPackageName());
        } else {
            resourceId = res.getIdentifier("icon_mark" + index, "mipmap", context.getPackageName());
        }
        return BitmapDescriptorFactory.fromResource(resourceId);
    }
}
