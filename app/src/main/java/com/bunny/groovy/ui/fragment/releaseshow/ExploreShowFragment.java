package com.bunny.groovy.ui.fragment.releaseshow;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.model.OpportunityModel;
import com.bunny.groovy.presenter.ExplorerOpportunityPresenter;
import com.bunny.groovy.ui.fragment.usercenter.SettingsFragment;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.IExploreView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 申请表演机会，地图
 * <p>
 * Created by Administrator on 2017/12/17.
 */

public class ExploreShowFragment extends BaseFragment<ExplorerOpportunityPresenter> implements
        OnMapReadyCallback,
        IExploreView,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 11;
    private static final int TRY_AGAIN_LOC = 999;
    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private boolean isMapInit = false;
    private List<OpportunityModel> mOpportunityModelList = new ArrayList<>();
    private OpportunityModel mCurrentBean;//当前选中的演出机会bean
    private List<Marker> mMarkerList = new ArrayList<>();
    private String distance = "200";//距离默认200km
    private int count;
    private Location mLastLocation;
    private boolean isMarkerShowing = false;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TRY_AGAIN_LOC:
                    if (count < 2) {
                        initCurrentLocation();
                        count++;
                    } else {
                        Log.i("MyTag", "location not found");
                    }
                    break;
            }
        }
    };

    @Bind(R.id.map_fl_marker_layout)
    FrameLayout mMarkerLayout;
    @Bind(R.id.marker_tv_date)
    TextView mTvDate;
    @Bind(R.id.marker_iv_head)
    ImageView mHeadImg;
    @Bind(R.id.marker_tv_score)
    TextView mTvScore;
    @Bind(R.id.marker_tv_venue_name)
    TextView mTvName;
    @Bind(R.id.marker_tv_venue_address)
    TextView mTvadd;
    @Bind(R.id.marker_tv_time)
    TextView mTvTime;
    @Bind(R.id.marker_tv_distance)
    TextView mTvDistance;

    @OnClick(R.id.marker_tv_venue_detail)
    public void venueDetail() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(OpportunityDetailFragment.KEY_OPPORTUNITY_BEAN,mCurrentBean);
        OpportunityDetailFragment.launch(mActivity,bundle);
    }

    @OnClick(R.id.marker_iv_phone)
    public void call() {
        Utils.CallPhone(mActivity,mCurrentBean.getPhoneNumber());
    }

    @OnClick(R.id.marker_iv_email)
    public void email() {
        Utils.sendEmail(mActivity,mCurrentBean.getVenueEmail());
    }

    @OnClick(R.id.marker_tv_apply)
    public void apply() {

    }

    @OnClick(R.id.map_filter)
    public void mapFilter() {
        // TODO: 2017/12/26
    }

    public static void launch(Activity from) {
        Bundle bundle = new Bundle();
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "EXPLORE SHOW OPPORTUNITY");
        FragmentContainerActivity.launch(from, ExploreShowFragment.class, bundle);
    }

    @Override
    protected ExplorerOpportunityPresenter createPresenter() {
        return new ExplorerOpportunityPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_map_layout;
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        mMarkerLayout.setVisibility(View.GONE);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void afterAttach() {
        SupportMapFragment supportMapFragment = new SupportMapFragment();
        getChildFragmentManager().beginTransaction().add(R.id.map_container, supportMapFragment, "map_fragment").commit();
        supportMapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .enableAutoManage(mActivity, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();
    }

    /**
     * 设置marker窗口的信息
     *
     * @param bean
     */
    private void setMarkerData(OpportunityModel bean) {
        mCurrentBean = bean;
        mTvDate.setText(bean.getPerformDate());
        mTvName.setText(bean.getVenueName());
        mTvadd.setText(bean.getVenueAddress());
        mTvTime.setText(bean.getPerformTime());
        mTvDistance.setText(bean.getDistance());
        mTvScore.setText(bean.getVenueScore());
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mGoogleMap = map;
        mGoogleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                mActivity, R.raw.map_style));
        //点击监听
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                OpportunityModel info = (OpportunityModel) marker.getTag();
                if (info != null) {
                    if (isMarkerShowing)
                    {
                        mMarkerLayout.setVisibility(View.GONE);
                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_opportunity));
                    }
                    else {
                        setMarkerData(info);
                        mMarkerLayout.setVisibility(View.VISIBLE);
                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_opportunity_selected));
                    }
                    isMarkerShowing = !isMarkerShowing;
                    return true;
                }
                return false;
            }
        });
        //我的位置
//        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
//            @Override
//            public boolean onMyLocationButtonClick() {
//                if (mGoogleMap.isMyLocationEnabled()) {
//                    LatLng sydney = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
//                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initCurrentLocation();
                }
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        KLog.a("Google Map --- onConnectionFailed:" + connectionResult.getErrorMessage());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        KLog.a("Google Map --- onConnected:");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            initCurrentLocation();
        }
    }

    /***
     * 初始化当前位置
     */
    private void initCurrentLocation() {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null && mGoogleMap != null) {
            if (!isMapInit) {
                LatLng sydney = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                mGoogleMap.addMarker(new MarkerOptions().position(sydney)
                        .title("Your Location")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location))
                        .draggable(true));

                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));

                KLog.a("当前位置：" + mLastLocation.getLatitude() + " -- " + mLastLocation.getLongitude());
                isMapInit = true;
            }
            //请求机会数据
            requestAroundList();
        } else {
            mHandler.sendEmptyMessageDelayed(TRY_AGAIN_LOC, 2000);
        }

    }

    private void requestAroundList() {
        HashMap<String, String> map = new HashMap<>();
        map.put("performerID", AppCacheData.getPerformerUserModel().getUserID());
        if (mLastLocation != null) {
            map.put("longitude", String.valueOf(mLastLocation.getLongitude()));
            map.put("latitude", String.valueOf(mLastLocation.getLatitude()));
        }
        map.put("distance", distance);
        mPresenter.requestOpportunityList(map);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public Activity get() {
        return getActivity();
    }

    private boolean showMap = true;//显示的形式是列表还是map

    @Override
    public void setListData(List<OpportunityModel> list) {
        mOpportunityModelList.clear();
        mMarkerList.clear();
        mOpportunityModelList = list;
        //设置页面数据
        //判断是list / map
        if (showMap && mGoogleMap != null) {
            LatLng loc;
            for (int i = 0; i < list.size(); i++) {
                OpportunityModel model = list.get(i);
                loc = new LatLng(Double.parseDouble(model.getLatitude()), Double.parseDouble(model.getLongitude()));
                Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(loc)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_opportunity)));
                marker.setTag(model);
                mMarkerList.add(marker);
            }
        } else {
            //列表显示

        }
    }
}
