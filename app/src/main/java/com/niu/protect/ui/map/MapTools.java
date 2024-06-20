package com.niu.protect.ui.map;

import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.model.CoordType;
import com.baidu.trace.model.TraceLocation;
public class MapTools {
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
        if (CoordType.wgs84 == location.getCoordType()) {
//            CoordinateConverter converter = new CoordinateConverter();
//            converter.from(CoordinateConverter.CoordType.GPS);
//            converter.coord(currentLatLng);
//            return converter.convert();
        }
        return currentLatLng;
    }

    public static com.baidu.trace.model.LatLng convertMap2Trace(LatLng latLng) {
        return new com.baidu.trace.model.LatLng(latLng.latitude, latLng.longitude);
    }

    public static LatLng convertTrace2Map(com.baidu.trace.model.LatLng traceLatLng) {
        return new LatLng(traceLatLng.latitude, traceLatLng.longitude);
    }
}
