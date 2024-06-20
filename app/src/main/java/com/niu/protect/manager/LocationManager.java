package com.niu.protect.manager;

import android.content.Context;
import android.util.Log;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.niu.protect.model.LatLngInfo;
import com.niu.protect.model.LineInfo;
import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.Tools;
//import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class LocationManager {
    private static LocationManager instance = new LocationManager();
    private Context context;
    String timeFormat;
    public List<LatLngInfo> latLngList = new ArrayList();
    List<LineInfo> lineInfoList = new ArrayList();
    int locationTimes = 0;
    int totalLocationTimes = 6;
    int failLocationTimes = 0;
    final int secSpeed = 100;
    long checkTime = 0;
    double olatitude = 0.0d;
    double olongitude = 0.0d;
    LatLngInfo lastLatLng = null;
    public List<LatLngInfo> tmplatLngList = new ArrayList();
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    private LocationManager() {
    }

    public static LocationManager getInstance() {
        return instance;
    }

    public void onCreate(Context context) {
        if (this.context != null) {
            return;
        }
        this.context = context;
        LocationClient.setAgreePrivacy(true);
        try {
            this.mLocationClient = new LocationClient(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LocationClient locationClient = this.mLocationClient;
        if (locationClient == null) {
            Log.d("location", "mLocationClient is null");
            return;
        }
        locationClient.registerLocationListener(this.myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("BD09ll");
        option.setScanSpan(2000);
        option.setOpenGps(true);
        option.setOpenAutoNotifyMode();
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(true);
        option.SetIgnoreCacheException(false);
        option.setEnableSimulateGps(false);
        option.setNeedNewVersionRgc(true);
        this.mLocationClient.setLocOption(option);
        this.mLocationClient.start();
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        public MyLocationListener() {
        }

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (locationTimes < totalLocationTimes) {
                floorLocation(location);
            }
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            float radius = location.getRadius();
            if (radius > 100.0f || location.getSpeed() > 100.0f) {
                ILog.d("location radus", "location radus >100 Speed >100 type:" + location.getLocTypeDescription());
                LocationManager locationManager = LocationManager.this;
                locationManager.failLocationTimes = locationManager.failLocationTimes + 1;
                if (failLocationTimes > 9) {
                    stopLocation();
                    return;
                }
                return;
            }
            String strlat = latitude + "";
            String strlon = longitude + "";
            if (strlat.equals("4.9E-324") || strlon.equals("4.9E-324")) {
                return;
            }
            LatLng latLng = new LatLng(latitude, longitude);
            LatLngInfo latLngInfo = new LatLngInfo();
            latLngInfo.setLocaltime(Long.valueOf(System.currentTimeMillis()));
            latLngInfo.setLatLng(latLng);
            latLngInfo.setIsdel(false);
            latLngInfo.setIndex(locationTimes);
            tmplatLngList.add(latLngInfo);
            locationTimes++;
            if (locationTimes >= totalLocationTimes) {
                upLocalLine();
                stopLocation();
            }
        }
    }

    public void stopLocation() {
        this.tmplatLngList.clear();
//        this.mLocationClient.stopIndoorMode();
        this.mLocationClient.stop();
        this.mLocationClient = null;
    }

    public void floorLocation(BDLocation location) {
        if (location.getFloor() != null) {
//            this.mLocationClient.startIndoorMode();
        }
    }

    public void initMsg() {
        this.checkTime = 0L;
        this.olatitude = 0.0d;
        this.olongitude = 0.0d;
        this.lastLatLng = null;
        this.tmplatLngList.clear();
    }

    public LatLngInfo checkLocal() {
        TXTManager.writeTxtAdd("gps", "\r\n");
        for (LatLngInfo latLngInfo : this.latLngList) {
            String mms = this.locationTimes + "------" + Tools.timeFormat(new Date(latLngInfo.getLocaltime().longValue()), "yyyy-MM-dd HH:mm:ss") + "-----------" + Tools.timeFormat(new Date(), "yyyy-MM-dd HH:mm:ss") + "-----" + latLngInfo.getLatLng().latitude +"-" + latLngInfo.getLatLng().longitude;
            TXTManager.writeTxtAdd("gps", mms);
        }
        LatLngInfo aInfo = null;
        boolean isSame = true;
        for (LatLngInfo latLngInfo2 : this.latLngList) {
            if (aInfo == null) {
                aInfo = latLngInfo2;
            } else if (aInfo.getLatLng().latitude != latLngInfo2.getLatLng().latitude || aInfo.getLatLng().longitude != latLngInfo2.getLatLng().longitude) {
                isSame = false;
                break;
            }
        }
        if (isSame || this.lineInfoList.size() < 9) {
            return null;
        }
        String str = this.timeFormat;
        if (str == null) {
            this.timeFormat = Tools.timeFormat(new Date(), "yyyy-MM-dd");
        } else if (!str.equals(Tools.timeFormat(new Date(), "yyyy-MM-dd"))) {
            this.timeFormat = Tools.timeFormat(new Date(), "yyyy-MM-dd");
            this.locationTimes = 0;
        }
        this.locationTimes++;
        double des = 0.0d;
        for (LineInfo lineInfo : this.lineInfoList) {
            des += lineInfo.getDistance();
        }
        double des2 = des / this.lineInfoList.size();
        for (LineInfo lineInfo2 : this.lineInfoList) {
            if (lineInfo2.getDes() > 100.0d && checkSecSpeed(lineInfo2) > 100.0d) {
                return null;
            }
            double scal = lineInfo2.getDes() / des2;
            if (scal >= 2.0d) {
                lineInfo2.getLatLng1().setIsdel(true);
                lineInfo2.getLatLng2().setIsdel(true);
                for (LatLngInfo latLngInfo3 : this.latLngList) {
                    if (latLngInfo3.getIndex() == lineInfo2.getLatLng1().getIndex()) {
                        latLngInfo3.setIsdel(true);
                    } else if (latLngInfo3.getIndex() == lineInfo2.getLatLng2().getIndex()) {
                        latLngInfo3.setIsdel(true);
                    }
                }
            }
        }
        Collections.sort(this.latLngList, new Comparator<LatLngInfo>() {
            @Override
            public int compare(LatLngInfo o1, LatLngInfo o2) {
                if (o1.getIndex() < o2.getIndex()) {
                    return 1;
                }
                if (o1.getIndex() == o2.getIndex()) {
                    return 0;
                }
                return -1;
            }
        });
        for (LatLngInfo latLngInfo4 : this.latLngList) {
            if (!latLngInfo4.isIsdel()) {
                return latLngInfo4;
            }
        }
        return null;
    }

    public double checkSecSpeed(LineInfo lineInfo) {
        long sec = Math.abs(lineInfo.getLatLng1().getLocaltime().longValue() - lineInfo.getLatLng2().getLocaltime().longValue());
        double scal = sec / 1000.0d;
        double speed = lineInfo.getDes() / scal;
        return speed;
    }

    public void upLocalLine() {
        String token = Tools.getToken(this.context);
        if (token == null) {
            return;
        }
        JSONArray alist = new JSONArray();
        for (LatLngInfo latLngInfo : this.tmplatLngList) {
            JSONObject object = new JSONObject();
            try {
                object.put("latitude", String.format("%.6f", Double.valueOf(latLngInfo.getLatLng().latitude)));
                object.put("longitude", String.format("%.6f", Double.valueOf(latLngInfo.getLatLng().longitude)));
                object.put("timeStamp", latLngInfo.getLocaltime().longValue() / 1000);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            alist.put(object);
        }
        JSONObject object2 = new JSONObject();
        try {
            object2.put("content", alist);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        Log.i("location", alist.toString());
        NetTools.getInstance().postAsynJSONHttp(this.context, StudentBaseUrl.traceGatherRecords_saveGatherRecords, object2, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    tmplatLngList.clear();
                    tmplatLngList = null;
                }
            }
        });
    }
}
