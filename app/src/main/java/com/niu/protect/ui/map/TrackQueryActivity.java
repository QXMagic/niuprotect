package com.niu.protect.ui.map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.api.analysis.DrivingBehaviorRequest;
import com.baidu.trace.api.analysis.DrivingBehaviorResponse;
import com.baidu.trace.api.analysis.OnAnalysisListener;
import com.baidu.trace.api.analysis.SpeedingInfo;
import com.baidu.trace.api.analysis.StayPointRequest;
import com.baidu.trace.api.analysis.StayPointResponse;
import com.baidu.trace.api.track.DistanceResponse;
import com.baidu.trace.api.track.HistoryTrackRequest;
import com.baidu.trace.api.track.HistoryTrackResponse;
import com.baidu.trace.api.track.LatestPointResponse;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.api.track.SupplementMode;
import com.baidu.trace.api.track.TrackPoint;
import com.baidu.trace.model.CoordType;
import com.baidu.trace.model.Point;
import com.baidu.trace.model.ProcessOption;
import com.baidu.trace.model.SortType;
import com.baidu.trace.model.TransportMode;
import com.niu.protect.BabyApplication;
import com.niu.protect.map.maputil.BitmapUtil;
import com.niu.protect.map.maputil.CommonUtil;
import com.niu.protect.map.maputil.MapUtil;
import com.niu.protect.map.maputil.ViewUtil;
import com.niu.protect.ui.base.BaseActivity;
import com.niu.protect.widget.TrackAnalysisDialog;
import com.niu.protect.widget.TrackAnalysisInfoLayout;

import java.util.ArrayList;
import java.util.List;

import com.niu.protect.R;
public class TrackQueryActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, BaiduMap.OnMarkerClickListener, BaiduMap.OnMapClickListener {
    private TextView mHistoryTrackView;
    private BabyApplication trackApp = null;
    private ViewUtil viewUtil = null;
    private MapUtil mapUtil = null;
    private HistoryTrackRequest historyTrackRequest = new HistoryTrackRequest();
    private OnTrackListener mTrackListener = null;
    private TrackAnalysisDialog trackAnalysisDialog = null;
    private TrackAnalysisInfoLayout trackAnalysisInfoLayout = null;
    private Marker analysisMarker = null;
    private DrivingBehaviorRequest drivingBehaviorRequest = new DrivingBehaviorRequest();
    private StayPointRequest stayPointRequest = new StayPointRequest();
    private OnAnalysisListener mAnalysisListener = null;
    private long startTime = CommonUtil.getCurrentTime();
    private long endTime = CommonUtil.getCurrentTime();
    private List<LatLng> trackPoints = new ArrayList();
    private List<Point> speedingPoints = new ArrayList();
    private List<Point> harshAccelPoints = new ArrayList();
    private List<Point> harshBreakingPoints = new ArrayList();
    private List<Point> harshSteeringPoints = new ArrayList();
    private List<Point> stayPoints = new ArrayList();
    private List<Marker> speedingMarkers = new ArrayList();
    private List<Marker> harshAccelMarkers = new ArrayList();
    private List<Marker> harshBreakingMarkers = new ArrayList();
    private List<Marker> harshSteeringMarkers = new ArrayList();
    private List<Marker> stayPointMarkers = new ArrayList();
    private boolean isSpeeding = false;
    private boolean isHarshAccel = false;
    private boolean isHarshBreaking = false;
    private boolean isHarshSteering = false;
    private boolean isStayPoint = false;
    private SortType sortType = SortType.asc;
    private int pageIndex = 1;
    private long lastQueryTime = 0;

    static int Fun$504(TrackQueryActivity x0) {
        int i = x0.pageIndex + 1;
        x0.pageIndex = i;
        return i;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        setTitle(R.string.track_query_title);
        setOnClickListener(this);
        this.trackApp = (BabyApplication) getApplicationContext();
        this.isSpeeding = true;
        handleMarker(this.speedingMarkers, true);
        init();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_top);
        LinearLayout optionsButton = (LinearLayout) layout.findViewById(R.id.btn_activity_options);
        optionsButton.setOnClickListener(listener);
    }

    private void init() {
        this.viewUtil = new ViewUtil();
        MapUtil mapUtil = MapUtil.getInstance();
        this.mapUtil = mapUtil;
        mapUtil.init((MapView) findViewById(R.id.track_query_mapView));
        this.mapUtil.baiduMap.setOnMarkerClickListener(this);
        this.mapUtil.baiduMap.setOnMapClickListener(this);
        this.mapUtil.setCenter(this.trackApp);
        this.trackAnalysisInfoLayout = new TrackAnalysisInfoLayout(this, this.mapUtil.baiduMap);
        initListener();
        onTrackAnalysis();
    }

    public void onTrackAnalysis() {
        if (this.mapUtil.mapView != null) {
            this.mapUtil.mapView.removeView(this.mHistoryTrackView);
            this.mapUtil.baiduMap.setViewPadding(0, 0, 0, 0);
        }
        if (this.trackAnalysisDialog == null) {
            this.trackAnalysisDialog = new TrackAnalysisDialog(this);
        }
        if (Build.VERSION.SDK_INT < 24) {
            TrackAnalysisDialog trackAnalysisDialog = this.trackAnalysisDialog;
            trackAnalysisDialog.update(trackAnalysisDialog.getWidth(), this.trackAnalysisDialog.getHeight());
        }
        if (CommonUtil.getCurrentTime() - this.lastQueryTime > 60) {
            this.lastQueryTime = CommonUtil.getCurrentTime();
            this.speedingPoints.clear();
            this.harshAccelPoints.clear();
            this.harshBreakingPoints.clear();
            this.stayPoints.clear();
            queryDrivingBehavior();
            queryStayPoint();
            query();
        }
    }

    private void query() {
        this.trackPoints.clear();
        this.pageIndex = 1;
        this.startTime = CommonUtil.getCurrentTime() - 18000;
        this.endTime = CommonUtil.getCurrentTime();
        ProcessOption processOption = new ProcessOption();
        processOption.setRadiusThreshold(1000);
        processOption.setTransportMode(TransportMode.walking);
        processOption.setNeedDenoise(true);
        processOption.setNeedVacuate(true);
        processOption.setNeedMapMatch(true);
        this.historyTrackRequest.setProcessOption(processOption);
        this.historyTrackRequest.setLowSpeedThreshold(0.0d);
        this.historyTrackRequest.setSupplementMode(SupplementMode.walking);
        this.historyTrackRequest.setCoordTypeOutput(CoordType.bd09ll);
        SortType sortType = SortType.asc;
        this.sortType = sortType;
        this.historyTrackRequest.setSortType(sortType);
        this.historyTrackRequest.setProcessed(true);
        queryHistoryTrack();
    }

    @Override
    public void onActivityResult(int historyTrackRequestCode, int resultCode, Intent data) {
        super.onActivityResult(historyTrackRequestCode, resultCode, data);
        if (data == null) {
            return;
        }
        this.trackPoints.clear();
        this.pageIndex = 1;
        if (data.hasExtra("startTime")) {
            this.startTime = data.getLongExtra("startTime", (CommonUtil.getCurrentTime() - 216000) + 60);
        }
        if (data.hasExtra("endTime")) {
            this.endTime = data.getLongExtra("endTime", CommonUtil.getCurrentTime());
        }
        ProcessOption processOption = new ProcessOption();
        if (data.hasExtra("radius")) {
            processOption.setRadiusThreshold(data.getIntExtra("radius", 0));
        }
        if (data.hasExtra("transportMode")) {
            processOption.setTransportMode(TransportMode.valueOf(data.getStringExtra("transportMode")));
        }
        if (data.hasExtra("denoise")) {
            processOption.setNeedDenoise(data.getBooleanExtra("denoise", true));
        }
        if (data.hasExtra("vacuate")) {
            processOption.setNeedVacuate(data.getBooleanExtra("vacuate", true));
        }
        if (data.hasExtra("mapmatch")) {
            processOption.setNeedMapMatch(data.getBooleanExtra("mapmatch", true));
        }
        this.historyTrackRequest.setProcessOption(processOption);
        if (data.hasExtra("lowspeedthreshold")) {
            this.historyTrackRequest.setLowSpeedThreshold(0.0d);
        }
        if (data.hasExtra("supplementMode")) {
            this.historyTrackRequest.setSupplementMode(SupplementMode.walking);
        }
        if (data.hasExtra("sortType")) {
            SortType sortType = SortType.asc;
            this.sortType = sortType;
            this.historyTrackRequest.setSortType(sortType);
        }
        if (data.hasExtra("coordTypeOutput")) {
            this.historyTrackRequest.setCoordTypeOutput(CoordType.bd09ll);
        }
        if (data.hasExtra("processed")) {
            this.historyTrackRequest.setProcessed(true);
        }
        queryHistoryTrack();
    }

    public void queryHistoryTrack() {
        if (this.trackApp.mClient == null) {
            return;
        }
        this.trackApp.initRequest(this.historyTrackRequest);
        this.historyTrackRequest.setEntityName(this.trackApp.entityName);
        this.historyTrackRequest.setStartTime(this.startTime);
        this.historyTrackRequest.setEndTime(this.endTime);
        this.historyTrackRequest.setPageIndex(this.pageIndex);
        this.historyTrackRequest.setPageSize(5000);
        this.trackApp.mClient.queryHistoryTrack(this.historyTrackRequest, this.mTrackListener);
    }

    private void queryDrivingBehavior() {
        if (this.trackApp.mClient == null) {
            return;
        }
        this.trackApp.initRequest(this.drivingBehaviorRequest);
        this.drivingBehaviorRequest.setEntityName(this.trackApp.entityName);
        this.drivingBehaviorRequest.setStartTime(this.startTime);
        this.drivingBehaviorRequest.setEndTime(this.endTime);
        this.trackApp.mClient.queryDrivingBehavior(this.drivingBehaviorRequest, this.mAnalysisListener);
    }

    private void queryStayPoint() {
        if (this.trackApp.mClient == null) {
            return;
        }
        this.trackApp.initRequest(this.stayPointRequest);
        this.stayPointRequest.setEntityName(this.trackApp.entityName);
        this.stayPointRequest.setStartTime(this.startTime);
        this.stayPointRequest.setEndTime(this.endTime);
        this.stayPointRequest.setStayTime(60);
        this.trackApp.mClient.queryStayPoint(this.stayPointRequest, this.mAnalysisListener);
    }

    @Override
    public void onClick(View view) {
        view.getId();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Bundle bundle = marker.getExtraInfo();
        if (bundle == null || !marker.isVisible()) {
            return false;
        }
        bundle.getInt("type");
        this.analysisMarker = marker;
        InfoWindow trackAnalysisInfoWindow = new InfoWindow(this.trackAnalysisInfoLayout.mView, marker.getPosition(), -47);
        this.mapUtil.baiduMap.showInfoWindow(trackAnalysisInfoWindow);
        return false;
    }

    public void clearAnalysisList() {
        List<Point> list = this.speedingPoints;
        if (list != null) {
            list.clear();
        }
        List<Point> list2 = this.harshAccelPoints;
        if (list2 != null) {
            list2.clear();
        }
        List<Point> list3 = this.harshBreakingPoints;
        if (list3 != null) {
            list3.clear();
        }
        List<Point> list4 = this.harshSteeringPoints;
        if (list4 != null) {
            list4.clear();
        }
    }

    private void initListener() {
        this.mTrackListener = new OnTrackListener() {
            @Override
            public void onHistoryTrackCallback(HistoryTrackResponse response) {
                int total = response.getTotal();
                StringBuffer sb = new StringBuffer(256);
                if (response.getStatus() != 0) {
                    viewUtil.showToast(TrackQueryActivity.this, response.getMessage());
                } else if (total == 0) {
//                    ViewUtil viewUtil = viewUtil;
                    TrackQueryActivity trackQueryActivity = TrackQueryActivity.this;
                    viewUtil.showToast(trackQueryActivity, trackQueryActivity.getString(R.string.no_track_data));
                } else {
                    List<TrackPoint> points = response.getTrackPoints();
                    if (points != null) {
                        for (TrackPoint trackPoint : points) {
                            if (!CommonUtil.isZeroPoint(trackPoint.getLocation().getLatitude(), trackPoint.getLocation().getLongitude())) {
                                trackPoints.add(MapUtil.convertTrace2Map(trackPoint.getLocation()));
                            }
                        }
                    }
                    sb.append("总里程：");
                    sb.append(response.getDistance());
                    sb.append("米");
                    sb.append("\n收费里程：");
                    sb.append(response.getTollDistance());
                    sb.append("米");
                    sb.append("\n低速里程：");
                    sb.append(response.getLowSpeedDistance());
                    sb.append("米");
                    TrackQueryActivity trackQueryActivity2 = TrackQueryActivity.this;
                    trackQueryActivity2.addView(trackQueryActivity2.mapUtil.mapView);
                    mHistoryTrackView.setText(sb.toString());
                }
                if (total > pageIndex * 5000) {
                    historyTrackRequest.setPageIndex(TrackQueryActivity.Fun$504(TrackQueryActivity.this));
                    queryHistoryTrack();
                    return;
                }
                mapUtil.drawHistoryTrack(trackPoints, sortType);
            }

            @Override
            public void onDistanceCallback(DistanceResponse response) {
                super.onDistanceCallback(response);
            }

            @Override
            public void onLatestPointCallback(LatestPointResponse response) {
                super.onLatestPointCallback(response);
            }
        };
        this.mAnalysisListener = new OnAnalysisListener() {
            @Override
            public void onStayPointCallback(StayPointResponse response) {
                if (response.getStatus() != 0) {
                    lastQueryTime = 0L;
                    viewUtil.showToast(TrackQueryActivity.this, response.getMessage());
                } else if (response.getStayPointNum() != 0) {
                    stayPoints.addAll(response.getStayPoints());
                    TrackQueryActivity trackQueryActivity = TrackQueryActivity.this;
                    trackQueryActivity.handleOverlays(trackQueryActivity.stayPointMarkers, stayPoints, isStayPoint);
                }
            }

            @Override
            public void onDrivingBehaviorCallback(DrivingBehaviorResponse response) {
                if (response.getStatus() != 0) {
                    lastQueryTime = 0L;
                    viewUtil.showToast(TrackQueryActivity.this, response.getMessage());
                } else if (response.getSpeedingNum() != 0 || response.getHarshAccelerationNum() != 0 || response.getHarshBreakingNum() != 0 || response.getHarshSteeringNum() != 0) {
                    clearAnalysisList();
                    clearAnalysisOverlay();
                    List<SpeedingInfo> speedingInfos = response.getSpeedings();
                    for (SpeedingInfo info : speedingInfos) {
                        speedingPoints.addAll(info.getPoints());
                    }
                    harshAccelPoints.addAll(response.getHarshAccelerationPoints());
                    harshBreakingPoints.addAll(response.getHarshBreakingPoints());
                    harshSteeringPoints.addAll(response.getHarshSteeringPoints());
                    TrackQueryActivity trackQueryActivity = TrackQueryActivity.this;
                    trackQueryActivity.handleOverlays(trackQueryActivity.speedingMarkers, speedingPoints, isSpeeding);
                    TrackQueryActivity trackQueryActivity2 = TrackQueryActivity.this;
                    trackQueryActivity2.handleOverlays(trackQueryActivity2.harshAccelMarkers, harshAccelPoints, isHarshAccel);
                    TrackQueryActivity trackQueryActivity3 = TrackQueryActivity.this;
                    trackQueryActivity3.handleOverlays(trackQueryActivity3.harshBreakingMarkers, harshBreakingPoints, isHarshBreaking);
                    TrackQueryActivity trackQueryActivity4 = TrackQueryActivity.this;
                    trackQueryActivity4.handleOverlays(trackQueryActivity4.harshSteeringMarkers, harshSteeringPoints, isHarshSteering);
                }
            }
        };
    }

    public void handleOverlays(List<Marker> markers, List<? extends Point> points, boolean isVisible) {
        if (markers == null || points == null) {
            return;
        }
        for (Point point : points) {
            OverlayOptions overlayOptions = new MarkerOptions().position(MapUtil.convertTrace2Map(point.getLocation())).icon(BitmapUtil.bmGcoding).zIndex(9).draggable(true);
            Marker marker = (Marker) this.mapUtil.baiduMap.addOverlay(overlayOptions);
            Bundle bundle = new Bundle();
            marker.setExtraInfo(bundle);
            markers.add(marker);
        }
        handleMarker(markers, isVisible);
    }

    private void handleMarker(List<Marker> markers, boolean isVisible) {
        if (markers == null || markers.isEmpty()) {
            return;
        }
        for (Marker marker : markers) {
            marker.setVisible(isVisible);
        }
        if (markers.contains(this.analysisMarker)) {
            this.mapUtil.baiduMap.hideInfoWindow();
        }
    }

    public void addView(MapView mapView) {
        TextView textView = new TextView(this);
        this.mHistoryTrackView = textView;
        textView.setTextSize(15.0f);
        this.mHistoryTrackView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.mHistoryTrackView.setBackgroundColor(Color.parseColor("#AAA9A9A9"));
        this.mHistoryTrackView.setMovementMethod(ScrollingMovementMethod.getInstance());
        MapViewLayoutParams.Builder builder = new MapViewLayoutParams.Builder();
        builder.layoutMode(MapViewLayoutParams.ELayoutMode.absoluteMode);
        builder.width(mapView.getWidth());
        builder.height(200);
        builder.point(new android.graphics.Point(0, mapView.getHeight()));
        builder.align(1, 16);
        this.mapUtil.baiduMap.setViewPadding(0, 0, 0, 200);
        mapView.addView(this.mHistoryTrackView, builder.build());
    }

    public void clearAnalysisOverlay() {
        clearOverlays(this.speedingMarkers);
        clearOverlays(this.harshAccelMarkers);
        clearOverlays(this.harshBreakingMarkers);
        clearOverlays(this.stayPointMarkers);
    }

    private void clearOverlays(List<Marker> markers) {
        if (markers == null) {
            return;
        }
        for (Marker marker : markers) {
            marker.remove();
        }
        markers.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mapUtil.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mapUtil.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.trackAnalysisInfoLayout != null) {
            this.trackAnalysisInfoLayout = null;
        }
        TrackAnalysisDialog trackAnalysisDialog = this.trackAnalysisDialog;
        if (trackAnalysisDialog != null) {
            trackAnalysisDialog.dismiss();
            this.trackAnalysisDialog = null;
        }
        List<LatLng> list = this.trackPoints;
        if (list != null) {
            list.clear();
        }
        List<Point> list2 = this.stayPoints;
        if (list2 != null) {
            list2.clear();
        }
        clearAnalysisList();
        this.trackPoints = null;
        this.speedingPoints = null;
        this.harshAccelPoints = null;
        this.harshSteeringPoints = null;
        this.stayPoints = null;
        clearAnalysisOverlay();
        this.speedingMarkers = null;
        this.harshAccelMarkers = null;
        this.harshBreakingMarkers = null;
        this.stayPointMarkers = null;
        this.mapUtil.clear();
    }

    protected int getContentViewId() {
        return R.layout.activity_trackquery;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        this.mapUtil.mapView.removeView(this.mHistoryTrackView);
        this.mapUtil.baiduMap.setViewPadding(0, 0, 0, 0);
    }

    @Override
    public void onMapPoiClick(MapPoi mapPoi) {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    }
}
