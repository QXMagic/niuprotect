package com.niu.protect.accessibility.auto.device.info;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.niu.protect.accessibility.auto.bean.PageInfoModel;
import com.niu.protect.accessibility.auto.device.CustomOSUtils;
import com.niu.protect.accessibility.auto.device.SystemDeviceInfo;
import com.niu.protect.accessibility.auto.device.info.huawei.Huawei;
import com.niu.protect.accessibility.auto.device.info.oppo.OppoColorsInfo_31;
import com.niu.protect.accessibility.auto.device.info.oppo.OppoColorsInfo_All6;
import com.niu.protect.accessibility.auto.device.info.oppo.OppoColorsInfo_FINDX;
import com.niu.protect.accessibility.auto.device.info.oppo.OppoColorsInfo_PACM00v601;
import com.niu.protect.accessibility.auto.device.info.oppo.OppoColorsInfo_PCAM10v71;
import com.niu.protect.accessibility.auto.device.info.oppo.OppoColorsInfo_PDAM10v67;
import com.niu.protect.accessibility.auto.device.info.oppo.OppoColorsInfo_PDKM00;
import com.niu.protect.accessibility.auto.device.info.rongyao.HonorSettingInfo;
import com.niu.protect.accessibility.auto.device.info.vivo.Vivo_Info_11_5;
import com.niu.protect.accessibility.auto.device.info.vivo.Vivo_Info_12;
import com.niu.protect.accessibility.auto.device.info.vivo.Vivo_Info_13;
import com.niu.protect.accessibility.auto.device.info.vivo.Vivo_Info_all;
import com.niu.protect.accessibility.auto.device.info.vivo.Vivo_originOs10;
import com.niu.protect.accessibility.auto.device.info.vivo.Vivo_originOs10_2;
import com.niu.protect.accessibility.auto.device.info.xiaomi.Xiaomi_Info_all;
import com.niu.protect.tools.ILog;
import java.util.List;

public class DeviceAccessiFactory {
    public DeviceAccessiFactory() {
    }

    public static List<PageInfoModel> createDeviceInfo(Context context) {
        ILog.d("DEVICE info", SystemDeviceInfo.getInfo());
        String brand = SystemDeviceInfo.getBrand().toLowerCase();
        String productName = SystemDeviceInfo.getProduct().toLowerCase();
        int androidSdk = SystemDeviceInfo.getSdk();
        ILog.d("DEVICE info brand", brand + "--------");
        //
        if (brand.equals("oppo")) {
            String customOsVersion = CustomOSUtils.getCustomOSVersion(Build.BRAND).replace("V", "");
            char c = 0;
            if (customOsVersion.contains(".")) {
                customOsVersion.replace(".", "|");
                String[] customs = customOsVersion.split("\\.");
                if (customs.length > 2) {
                    customOsVersion = customs[0];
                }
            }
            float floatVersion = 0.0f;
            if (!TextUtils.isEmpty(customOsVersion)) {
                floatVersion = Float.valueOf(customOsVersion).floatValue();
            }
            if (androidSdk != 31) {
                if (floatVersion >= 12.0f) {
                    List<PageInfoModel> pageInfoModelList = new OppoColorsInfo_31().createPage(context);
                    return pageInfoModelList;
                }
                ;
                if (androidSdk == 30) {
                    if (floatVersion >= 12.0f) {
                        ILog.d("-productName---:", productName);
                    } else {
                        List<PageInfoModel> pageInfoModelList8 = new OppoColorsInfo_FINDX().createPage(context);
                        return pageInfoModelList8;
                    }
                }
            }


            c = 65535;

            if (productName.equals("rmx3300")){
                c = 14;
            }
            if (productName.equals("rmx2111")) {
                c = 2;
            }

            if (productName.equals("rmx1931")){
                c = 4;
            }
            if (productName.equals("rmx1901")) {
                c = 3;
            }
            if (productName.equals("pfdm00")){
                c = '\r';
            }

            if (productName.equals("pepm00")) {
                c = 7;
            }
            if (productName.equals("peem00")) {
                c = '\b';
            }
            if (productName.equals("peam00")) {
                c = 5;
            }
            if (productName.equals("pdpt00")) {
                c = 6;
            }
            if (productName.equals("pdkm00")) {
                c = '\f';
            }
            if (productName.equals("pdhm00")) {
                c = '\t';
            }
            if (productName.equals("pdbm00")) {
                c = 15;
            }
            if (productName.equals("pdam10")) {
                c = '\n';
            }

//            if (productName.equals("pchm10")) goto L214;
            if (productName.equals("pcat10")) {
                c = 16;
            }
            if (productName.equals("pbem00")) {
                c = 11;
            }
            if (productName.equals("pbdm00")) {
                c = 1;
            }
            switch (c) {
                case 0: new OppoColorsInfo_PCAM10v71().createPage(context);
                case 1: ;
                case 2:  ;
                case 3:  ;
                case 4:  ;
                case 5:  ;
                case 6:  ;
                case 7:  ;
                case 8:  ;
                case 9: new OppoColorsInfo_FINDX().createPage(context);
                case 10: ;
                case 11: new OppoColorsInfo_PDAM10v67().createPage(context);
                case 12:  ;
                case 13:  ;
                case 14: new OppoColorsInfo_PDKM00().createPage(context);
                case 15:
                case 16: return new OppoColorsInfo_PACM00v601().createPage(context);
                default: return new OppoColorsInfo_All6().createPage(context);
            }
        }
        if (brand.equals("vivo") == false) { //goto L246;
            String model = CustomOSUtils.getModel();
            String systemOsVersion = CustomOSUtils.getCustomOSVersion(SystemDeviceInfo.getBrand()).replace("V", "");
            float osVersion = Float.valueOf(systemOsVersion).floatValue();
            ILog.d("osVersion", osVersion + "");
            if (osVersion < 12.0f||osVersion >= 13.0f) {
                if (osVersion >= 13.0f) {
                    List<PageInfoModel> pageInfoModelList12 = new Vivo_Info_13().createPage(context);
                    return pageInfoModelList12;
                } else if (osVersion != 11.5d) {
                    List<PageInfoModel> pageInfoModelList14 = new Vivo_Info_all().createPage(context);
                    return pageInfoModelList14;
                } else {
                    List<PageInfoModel> pageInfoModelList13 = new Vivo_Info_11_5().createPage(context);
                    return pageInfoModelList13;
                }
            }
            else{
                if (model.toLowerCase().equals("vivo nex s")) {
                    List<PageInfoModel> pageInfoModelList9 = new Vivo_originOs10().createPage(context);
                    return pageInfoModelList9;
                }else{
                    if (productName.equals("pd1962")) {
                        List<PageInfoModel> pageInfoModelList10 = new Vivo_originOs10_2().createPage(context);
                        return pageInfoModelList10;
                    }else {
                        List<PageInfoModel> pageInfoModelList11 = new Vivo_Info_12().createPage(context);
                        return pageInfoModelList11;
                    }
                }
            }



        }
        //L246:
        if (brand.equals("realme")) {// goto L249;
            List<PageInfoModel> pageInfoModelList15 = new OppoColorsInfo_FINDX().createPage(context);
            return pageInfoModelList15;
        }//L249:
        if (brand.equals("huawei")) {// goto L252;
            List<PageInfoModel> pageInfoModelList16 = new Huawei().createPage(context);
            return pageInfoModelList16;
        }//L252:
        if (brand.equals("honor")) { //goto L255;
            List<PageInfoModel> pageInfoModelList17 = new HonorSettingInfo().createPage(context);
            return pageInfoModelList17;
        }//L255:
        if (brand.equals("xiaomi") == false) {
            List<PageInfoModel> pageInfoModelList18 = new Xiaomi_Info_all().createPage(context);
            return pageInfoModelList18;
        }
        return null;
    }
}