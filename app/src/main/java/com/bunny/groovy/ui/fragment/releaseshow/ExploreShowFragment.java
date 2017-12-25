package com.bunny.groovy.ui.fragment.releaseshow;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseApp;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.model.OpportunityModel;
import com.bunny.groovy.presenter.ExplorerOpportunityPresenter;
import com.bunny.groovy.presenter.MapPresenter;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.IExploreView;
import com.bunny.groovy.view.IMapView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
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

public class ExploreShowFragment extends BaseFragment<ExplorerOpportunityPresenter> implements OnMapReadyCallback, IExploreView, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 11;
    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private boolean isMapInit = false;
    private List<OpportunityModel> mOpportunityModelList = new ArrayList<>();
    private List<Marker> mMarkerList = new ArrayList<>();
    private String distance = "10000";

    @Bind(R.id.et_search)
    EditText etSearch;
    private Location mLastLocation;

    @OnClick(R.id.map_filter)
    public void mapFilter() {

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
    protected void loadData() {

    }

    @Override
    public void afterAttach() {
        SupportMapFragment supportMapFragment = new SupportMapFragment();
        getChildFragmentManager().beginTransaction().add(R.id.map_container, supportMapFragment, "map_fragment").commit();
        supportMapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .enableAutoManage(getActivity(), this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onMapReady(GoogleMap map) {
        mGoogleMap = map;
        boolean success = mGoogleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                mActivity, R.raw.map_style));
        if (!success) {
            Log.e("xxxx", "Style parsing failed.");
        }
        //点击监听
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                OpportunityModel info = (OpportunityModel) marker.getTag();
                if (info != null) {
                    UIUtils.showBaseToast(info.getVenueName());
                    return true;
                }
                return false;
            }
        });
        //我的位置
        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                if (mGoogleMap.isMyLocationEnabled()) {
                    LatLng sydney = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
                    return true;
                }
                return false;
            }
        });
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
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

                LatLng sydney = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                mGoogleMap.addMarker(new MarkerOptions().position(sydney)
                        .title("Marker in ShangHai")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location))
                        .draggable(true));

                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));

                KLog.a("当前位置：" + mLastLocation.getLatitude() + " -- " + mLastLocation.getLongitude());
                isMapInit = true;
            }
        } else {
            Toast.makeText(getActivity(), "no_location_detected", Toast.LENGTH_LONG).show();
        }
        //请求机会数据
        requestAroundList();
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
