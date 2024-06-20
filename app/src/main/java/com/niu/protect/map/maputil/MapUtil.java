package com.niu.protect.map.maputil;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineDottedLineType;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.trace.model.SortType;
import com.baidu.trace.model.TraceLocation;
import com.niu.protect.BabyApplication;
import com.niu.protect.map.MapConstants;
import com.niu.protect.map.model.CurrentLocation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public class MapUtil {
    private static MapUtil INSTANCE = new MapUtil();
    private MapStatus mapStatus = null;
    private Marker mMoveMarker = null;
    public MapView mapView = null;
    public BaiduMap baiduMap = null;
    public LatLng lastPoint = null;
    public Overlay polylineOverlay = null;

    private MapUtil() {
    }

    public static MapUtil getInstance() {
        return INSTANCE;
    }

    public void init(MapView view) {
        this.mapView = view;
        this.baiduMap = view.getMap();
        this.mapView.showZoomControls(false);
    }

    public void onPause() {
        MapView mapView = this.mapView;
        if (mapView != null) {
            mapView.onPause();
        }
    }

    public void onResume() {
        MapView mapView = this.mapView;
        if (mapView != null) {
            mapView.onResume();
        }
    }

    public void clear() {
        this.lastPoint = null;
        Marker marker = this.mMoveMarker;
        if (marker != null) {
            marker.remove();
            this.mMoveMarker = null;
        }
        Overlay overlay = this.polylineOverlay;
        if (overlay != null) {
            overlay.remove();
            this.polylineOverlay = null;
        }
        BaiduMap baiduMap = this.baiduMap;
        if (baiduMap != null) {
            baiduMap.clear();
            this.baiduMap = null;
        }
        this.mapStatus = null;
        MapView mapView = this.mapView;
        if (mapView != null) {
            mapView.onDestroy();
            this.mapView = null;
        }
    }

    public static LatLng convertTraceLocation2Map(TraceLocation location) {
        if (location == null) {
            return null;
        }
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        if (Math.abs(latitude - 0.0d) < 1.0E-6d && Math.abs(longitude - 0.0d) < 1.0E-6d) {
            return null;
        }
        LatLng currentLatLng = new LatLng(latitude, longitude);
//        if (CoordType.wgs84 == location.getCoordType()) {
//            CoordinateConverter converter = new CoordinateConverter();
//            converter.from(CoordinateConverter.CoordType.GPS);
//            converter.coord(currentLatLng);
//            return converter.convert();
//        }
        return currentLatLng;
    }

    public static com.baidu.trace.model.LatLng convertMap2Trace(LatLng latLng) {
        return new com.baidu.trace.model.LatLng(latLng.latitude, latLng.longitude);
    }

    public static LatLng convertTrace2Map(com.baidu.trace.model.LatLng traceLatLng) {
        return new LatLng(traceLatLng.latitude, traceLatLng.longitude);
    }

    public void setCenter(BabyApplication trackApp) {
        if (!CommonUtil.isZeroPoint(CurrentLocation.latitude, CurrentLocation.longitude)) {
            LatLng currentLatLng = new LatLng(CurrentLocation.latitude, CurrentLocation.longitude);
            updateStatus(currentLatLng, false);
            return;
        }
        String lastLocation = trackApp.trackConf.getString(MapConstants.LAST_LOCATION, null);
        if (!TextUtils.isEmpty(lastLocation)) {
            String[] locationInfo = lastLocation.split(";");
            if (!CommonUtil.isZeroPoint(Double.parseDouble(locationInfo[1]), Double.parseDouble(locationInfo[2]))) {
                LatLng currentLatLng2 = new LatLng(Double.parseDouble(locationInfo[1]), Double.parseDouble(locationInfo[2]));
                updateStatus(currentLatLng2, false);
            }
        }
    }

    public void updateStatus(LatLng currentPoint, boolean showMarker) {
        BaiduMap baiduMap = this.baiduMap;
        if (baiduMap == null || currentPoint == null) {
            return;
        }
        if (baiduMap.getProjection() != null) {
            Point screenPoint = this.baiduMap.getProjection().toScreenLocation(currentPoint);
            if (screenPoint.y < 200 || screenPoint.y > BabyApplication.screenHeight - 500 || screenPoint.x < 200 || screenPoint.x > BabyApplication.screenWidth - 200 || this.mapStatus == null) {
                animateMapStatus(currentPoint, 15.0f);
            }
        } else if (this.mapStatus == null) {
            setMapStatus(currentPoint, 15.0f);
        }
        if (showMarker) {
            addMarker(currentPoint);
        }
    }

    public Marker addOverlay(LatLng currentPoint, BitmapDescriptor icon, Bundle bundle) {
        OverlayOptions overlayOptions = new MarkerOptions().position(currentPoint).icon(icon).zIndex(9).draggable(true);
        Marker marker = (Marker) this.baiduMap.addOverlay(overlayOptions);
        if (bundle != null) {
            marker.setExtraInfo(bundle);
        }
        return marker;
    }

    public void addMarker(LatLng currentPoint) {
        Marker marker = this.mMoveMarker;
        if (marker == null) {
            this.mMoveMarker = addOverlay(currentPoint, BitmapUtil.bmArrowPoint, null);
        } else if (this.lastPoint != null) {
            moveLooper(currentPoint);
        } else {
            this.lastPoint = currentPoint;
            marker.setPosition(currentPoint);
        }
    }

    public void moveLooper(LatLng endPoint) {
        LatLng latLng;
        this.mMoveMarker.setPosition(this.lastPoint);
        this.mMoveMarker.setRotate((float) CommonUtil.getAngle(this.lastPoint, endPoint));
        double slope = CommonUtil.getSlope(this.lastPoint, endPoint);
        boolean isReverse = this.lastPoint.latitude > endPoint.latitude;
        double intercept = CommonUtil.getInterception(slope, this.lastPoint);
        double xMoveDistance = isReverse ? CommonUtil.getXMoveDistance(slope) : (-1.0d) * CommonUtil.getXMoveDistance(slope);
        double latitude = this.lastPoint.latitude;
        while (true) {
            if ((latitude > endPoint.latitude) == isReverse) {
                if (slope != Double.MAX_VALUE) {
                    latLng = new LatLng(latitude, (latitude - intercept) / slope);
                } else {
                    latLng = new LatLng(latitude, this.lastPoint.longitude);
                }
                this.mMoveMarker.setPosition(latLng);
                latitude -= xMoveDistance;
            } else {
                return;
            }
        }
    }

    public void drawHistoryTrack(List<LatLng> points, SortType sortType) {
        LatLng startPoint;
        LatLng endPoint;
        this.baiduMap.clear();
        if (points == null || points.size() == 0) {
            Overlay overlay = this.polylineOverlay;
            if (overlay != null) {
                overlay.remove();
                this.polylineOverlay = null;
            }
        } else if (points.size() == 1) {
            OverlayOptions startOptions = new MarkerOptions().position(points.get(0)).icon(BitmapUtil.bmStart).zIndex(9).draggable(true);
            this.baiduMap.addOverlay(startOptions);
            animateMapStatus(points.get(0), 18.0f);
        } else {
            if (sortType == SortType.asc) {
                startPoint = points.get(0);
                endPoint = points.get(points.size() - 1);
            } else {
                startPoint = points.get(points.size() - 1);
                endPoint = points.get(0);
            }
            OverlayOptions startOptions2 = new MarkerOptions().position(startPoint).icon(BitmapUtil.bmStart).zIndex(9).draggable(true);
            OverlayOptions endOptions = new MarkerOptions().position(endPoint).icon(BitmapUtil.bmEnd).zIndex(9).draggable(true);
            OverlayOptions polylineOptions = new PolylineOptions().width(10).color(-16776961).points(points);
            this.baiduMap.addOverlay(startOptions2);
            this.baiduMap.addOverlay(endOptions);
            this.polylineOverlay = this.baiduMap.addOverlay(polylineOptions);
            OverlayOptions markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f).icon(BitmapUtil.bmArrowPoint).position(points.get(points.size() - 1)).rotate((float) CommonUtil.getAngle(points.get(0), points.get(1)));
            this.mMoveMarker = (Marker) this.baiduMap.addOverlay(markerOptions);
            animateMapStatus(points);
        }
    }

    public void drawHistoryTrackOrMarker(List<LatLng> points) {
        if (points.size() == 1) {
            OverlayOptions startOptions = new MarkerOptions().position(points.get(0)).icon(BitmapUtil.bmStart).zIndex(9).draggable(true);
            this.baiduMap.addOverlay(startOptions);
            animateMapStatus(points.get(0), 18.0f);
            return;
        }
        OverlayOptions polylineOptions = new PolylineOptions().width(10).color(-16776961).points(points);
        this.polylineOverlay = this.baiduMap.addOverlay(polylineOptions);
    }

    public void startAndEndMarker(List<LatLng> points, SortType sortType) {
        LatLng startPoint;
        LatLng endPoint;
        if (sortType == SortType.asc) {
            startPoint = points.get(0);
            endPoint = points.get(points.size() - 1);
        } else {
            startPoint = points.get(points.size() - 1);
            endPoint = points.get(0);
        }
        OverlayOptions startOptions = new MarkerOptions().position(startPoint).icon(BitmapUtil.bmStart).zIndex(9).draggable(true);
        OverlayOptions endOptions = new MarkerOptions().position(endPoint).icon(BitmapUtil.bmEnd).zIndex(9).draggable(true);
        this.baiduMap.addOverlay(startOptions);
        this.baiduMap.addOverlay(endOptions);
    }

    public void drawPolyline(List<LatLng> points) {
        OverlayOptions polylineOptions = new PolylineOptions().width(10).color(Color.GRAY).dottedLine(true).dottedLineType(PolylineDottedLineType.DOTTED_LINE_SQUARE).points(points);
        this.polylineOverlay = this.baiduMap.addOverlay(polylineOptions);
    }

    public void animateMapStatus(List<LatLng> points) {
        if (points == null || points.isEmpty()) {
            return;
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng point : points) {
            builder.include(point);
        }
        MapStatusUpdate msUpdate = MapStatusUpdateFactory.newLatLngBounds(builder.build());
        this.baiduMap.animateMapStatus(msUpdate);
    }

    public void animateMapStatus(LatLng point, float zoom) {
        MapStatus.Builder builder = new MapStatus.Builder();
        MapStatus build = builder.target(point).zoom(zoom).build();
        this.mapStatus = build;
        this.baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(build));
    }

    public void setMapStatus(LatLng point, float zoom) {
        MapStatus.Builder builder = new MapStatus.Builder();
        MapStatus build = builder.target(point).zoom(zoom).build();
        this.mapStatus = build;
        this.baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(build));
    }

    public void refresh() {
        LatLng mapCenter = this.baiduMap.getMapStatus().target;
        float mapZoom = this.baiduMap.getMapStatus().zoom - 1.0f;
        setMapStatus(mapCenter, mapZoom);
    }

    public boolean locTimeMinutes(long startTime, long endTime) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date1 = formatter.format(new Date(startTime * 1000));
            String date2 = formatter.format(new Date(1000 * endTime));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);
            long diff = d2.getTime() - d1.getTime();
            long days = diff / 86400000;
            Long.signum(days);
            long hours = (diff - (days * 86400000)) / 3600000;
            long minutes = ((diff - (86400000 * days)) - (3600000 * hours)) / 60000;
            Log.d("MapUtils", "差值:" + diff + "分钟" + minutes);
            if (minutes > 5) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
