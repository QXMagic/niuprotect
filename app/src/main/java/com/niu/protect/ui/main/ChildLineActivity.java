package com.niu.protect.ui.main;

import android.os.Bundle;
import android.util.Log;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.niu.protect.R;
import com.niu.protect.manager.LocationManager;
import com.niu.protect.model.LatLngInfo;
import com.niu.protect.model.LineInfo;
import com.niu.protect.ui.base.BaseActivity;
import java.util.ArrayList;
import java.util.List;
public class ChildLineActivity extends BaseActivity {
    private BaiduMap mBaiduMap;
    private MapView mMapView;
    List<LatLngInfo> gpslist = new ArrayList();
    List<List<LatLngInfo>> msglist = new ArrayList();
    List<LatLng> points = new ArrayList();
    List<LatLng> xxpoints = new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_line);
        changeTitle("活动轨迹");
        showBack();
        this.gpslist.addAll(LocationManager.getInstance().latLngList);
        LatLngInfo olatLngInfo = null;
        List<LatLngInfo> sublist = null;
        LineInfo lineInfo = new LineInfo();
        for (LatLngInfo latLngInfo : this.gpslist) {
            if (olatLngInfo == null) {
                olatLngInfo = latLngInfo;
                sublist = new ArrayList<>();
                this.msglist.add(sublist);
            }
            lineInfo.setLatLng1(olatLngInfo);
            lineInfo.setLatLng2(latLngInfo);
            long timeout = Math.abs(latLngInfo.getLocaltime().longValue() - olatLngInfo.getLocaltime().longValue());
            //TODO 时间间隔
            if (timeout > 60000 && lineInfo.getDistance() > 100.0d) {
                sublist = new ArrayList<>();
                this.msglist.add(sublist);
                lineInfo.setLatLng1(latLngInfo);
                lineInfo.setLatLng2(latLngInfo);
            }
            double secSpeed = lineInfo.getSecSpeed();
            if (secSpeed < 100.0d) {
                sublist.add(latLngInfo);
            }
            olatLngInfo = latLngInfo;
        }
        Log.e("xxxxx", this.msglist.size() + "");
        initMap();
    }

    public void rePoint() {
    }

    @Override
    public void onResume() {
        super.onResume();
        MapView mapView = this.mMapView;
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MapView mapView = this.mMapView;
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MapView mapView = this.mMapView;
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    private void initMap() {
        MapView mapView = (MapView) findViewById(R.id.mMapView);
        this.mMapView = mapView;
        if (mapView == null) {
            return;
        }
        BaiduMap map = mapView.getMap();
        this.mBaiduMap = map;
        if (map == null) {
            return;
        }
        map.clear();
        this.mBaiduMap.setViewPadding(30, 0, 30, 20);
        LatLng center = new LatLng(30.453925d, 114.319966d);
        for (int j = 0; j < this.msglist.size(); j++) {
            List<LatLngInfo> mlist = this.msglist.get(j);
            if (mlist.size() >= 6) {
                this.points.clear();
                for (int i = 0; i < mlist.size(); i++) {
                    LatLngInfo lineInfo = mlist.get(i);
                    LatLng pt = new LatLng(lineInfo.getLatLng().latitude, lineInfo.getLatLng().longitude);
                    if (i % 2 == 0) {
                        this.points.add(pt);
                        center = pt;
                    }
                    if (j == 0) {
                        if (i == mlist.size() - 1) {
                            this.xxpoints.add(pt);
                        }
                    } else {
                        if (i == 0) {
                            this.xxpoints.add(pt);
                        }
                        if (i == mlist.size() - 1) {
                            this.xxpoints.add(pt);
                        }
                    }
                }
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(center, 20.0f);
                this.mBaiduMap.setMapStatus(mapStatusUpdate);
                LatLng p1 = null;
                LatLng p2 = null;
                for (int n = 0; n < this.xxpoints.size(); n++) {
                    if (n % 2 == 0) {
                        LatLng p12 = this.xxpoints.get(n);
                        p1 = p12;
                    } else {
                        LatLng p22 = this.xxpoints.get(n);
                        p2 = p22;
                    }
                    if (p1 != null && p2 != null) {
                        List<LatLng> pointsa = new ArrayList<>();
                        pointsa.add(p1);
                        pointsa.add(p2);
                        OverlayOptions mOverlayOptions = new PolylineOptions().width(5).color(-1426128896).dottedLine(true).points(pointsa);
                        this.mBaiduMap.addOverlay(mOverlayOptions);
                        p1 = null;
                        p2 = null;
                    }
                }
                OverlayOptions mOverlayOptions2 = new PolylineOptions().width(10).color(-1426128896).points(this.points);
                this.mBaiduMap.addOverlay(mOverlayOptions2);
            }
        }
    }
}
