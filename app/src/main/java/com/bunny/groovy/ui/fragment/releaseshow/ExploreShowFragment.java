package com.bunny.groovy.ui.fragment.releaseshow;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bunny.groovy.R;
import com.bunny.groovy.adapter.NearByOppListAdapter;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.divider.HLineDecoration;
import com.bunny.groovy.model.OpportunityModel;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.presenter.ExplorerOpptnyPresenter;
import com.bunny.groovy.ui.fragment.apply.ApplyOppFragment;
import com.bunny.groovy.ui.fragment.apply.FilterFragment;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.LocationUtils;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.IExploreView;
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

import static android.app.Activity.RESULT_OK;

/**
 * 申请表演机会，地图
 * <p>
 * Created by Administrator on 2017/12/17.
 */

public class ExploreShowFragment extends BaseFragment<ExplorerOpptnyPresenter> implements
        OnMapReadyCallback,
        IExploreView
//        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks
{
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 11;
    private static final int TRY_AGAIN_LOC = 999;
    private GoogleMap mGoogleMap;
    //    private GoogleApiClient mGoogleApiClient;
    private boolean isMapInit = false;
    private List<OpportunityModel> mOpportunityModelList = new ArrayList<>();
    private OpportunityModel mCurrentBean;//当前选中的演出机会bean
    private List<Marker> mMarkerList = new ArrayList<>();
    private String distance = "200";//距离默认200km
    private int count;
    private Location mLastLocation;
    private boolean isMarkerShowing = false;

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
    @Bind(R.id.opp_recyclerview)
    RecyclerView mRecyclerView;
    private NearByOppListAdapter mAdapter;
    @Bind(R.id.map_layout)
    RelativeLayout mapLayout;
    private LocationManager locationManager;

    @OnClick(R.id.marker_tv_venue_detail)
    public void venueDetail() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(OpportunityDetailFragment.KEY_OPPORTUNITY_BEAN, mCurrentBean);
        OpportunityDetailFragment.launch(mActivity, bundle);
    }

    @OnClick(R.id.marker_iv_phone)
    public void call() {
        Utils.CallPhone(mActivity, mCurrentBean.getPhoneNumber());
    }

    @OnClick(R.id.marker_iv_email)
    public void email() {
        Utils.sendEmail(mActivity, mCurrentBean.getVenueEmail());
    }

    /**
     * 申请机会
     */
    @OnClick(R.id.marker_tv_apply)
    public void apply() {
        //跳转到申请页面
        Bundle bundle = new Bundle();
        bundle.putParcelable(ApplyOppFragment.KEY_OPP_BEAN, mCurrentBean);
        ApplyOppFragment.launch(mActivity, bundle);
    }

    @OnClick(R.id.map_filter)

    public void mapFilter() {
        filter();
    }

    /**
     * 跳转到条件页面
     */
    private void filter() {
        Bundle bundle = new Bundle();
        bundle.putInt(FilterFragment.KEY_DISTANCE, Integer.parseInt(distance));
        FilterFragment.launchForResult(mActivity, bundle, 1);
    }

    public static void launch(Activity from) {
        Bundle bundle = new Bundle();
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "EXPLORE SHOW OPPORTUNITY");
        FragmentContainerActivity.launch(from, ExploreShowFragment.class, bundle);
    }

    @Override
    protected ExplorerOpptnyPresenter createPresenter() {
        return new ExplorerOpptnyPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_map_layout;
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        mMarkerLayout.setVisibility(View.GONE);

        //初始化map
        SupportMapFragment supportMapFragment = new SupportMapFragment();
        getChildFragmentManager().beginTransaction().add(R.id.map_container, supportMapFragment, "map_fragment").commit();
        supportMapFragment.getMapAsync(this);

        //检查权限
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            initLocationListener();
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void afterAttach() {


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
        mTvDistance.setText(bean.getDistance() + "km");
        mTvScore.setText(bean.getVenueScore());
        Glide.with(mActivity).load(bean.getHeadImg()).error(R.mipmap.venue_instead_pic).into(mHeadImg);
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
                    if (isMarkerShowing) {
                        mMarkerLayout.setVisibility(View.GONE);
                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_opportunity));
                    } else {
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
        updateLoc();
        //请求机会数据
        requestAroundList();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initLocationListener();
                }
            }
        }
    }

    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            KLog.d("xxx", "获取位置" + location.getLatitude() + "   " + location.getLongitude());
            mLastLocation = location;
            updateLoc();
            if (locationManager != null) locationManager.removeUpdates(this);
        }

        @Override
        public void onProviderEnabled(String provider) {
            KLog.d("xxx", "onProviderEnabled -- provider=" + provider);
            updateLoc();
            if (locationManager != null) locationManager.removeUpdates(this);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    private Location getBestLocation(LocationManager locationManager) {
        Location result;
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            result = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (result != null) {
                return result;
            } else {
                result = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                return result;
            }
        }
        return null;
    }


    /**
     * 在地图上更新当前位置
     */
    private void updateLoc() {
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = getBestLocation(locationManager);
        if (mLastLocation == null || mLastLocation.getLongitude() == 0 || mLastLocation.getLatitude() == 0) {
            String latitude = AppCacheData.getPerformerUserModel().getLatitude();
            String longitude = AppCacheData.getPerformerUserModel().getLongitude();
            mLastLocation = new Location(LocationManager.GPS_PROVIDER);
            mLastLocation.setLatitude(Double.parseDouble(latitude));
            mLastLocation.setLongitude(Double.parseDouble(longitude));
        }
        if (mLastLocation != null) {
            KLog.a("当前位置：" + mLastLocation.getLatitude() + " -- " + mLastLocation.getLongitude());
            if (mGoogleMap != null) {
                mGoogleMap.clear();
                LatLng myLoc = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                mGoogleMap.addMarker(new MarkerOptions().position(myLoc)
                        .title("Your Location")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location))
                        .draggable(true));

                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 15));
            }
        }

    }


    /***
     * 初始化位置监听服务
     */
    private void initLocationListener() {
        //判断是否打开位置服务
        LocationUtils.checkLocationServiceEnable(mActivity);

        locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, listener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, listener);
    }

    private void requestAroundList() {
        HashMap<String, String> map = new HashMap<>();
        if (mLastLocation != null) {
            map.put("longitude", String.valueOf(mLastLocation.getLongitude()));
            map.put("latitude", String.valueOf(mLastLocation.getLatitude()));
            map.put("distance", distance);
            mPresenter.requestOpportunityList(map);
        }
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

        //判断是list / map
        if (showMap && mGoogleMap != null) {
            mapLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            //列表显示
            mapLayout.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        //地图数据
        resetMap();
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
        //列表数据
        if (mAdapter == null) {
            mAdapter = new NearByOppListAdapter(mOpportunityModelList);
            mAdapter.setPresenter(mPresenter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.addItemDecoration(new HLineDecoration(mActivity, HLineDecoration.VERTICAL_LIST,
                    R.drawable.shape_item_divider_line));
            mRecyclerView.setAdapter(mAdapter);
        } else mAdapter.refresh(mOpportunityModelList);

    }

    /**
     * 清空地图是原有的标记，添加当前位置
     */
    private void resetMap() {
        if (mGoogleMap != null) {
            mGoogleMap.clear();
            if (mLastLocation != null) {
                KLog.a("当前位置：" + mLastLocation.getLatitude() + " -- " + mLastLocation.getLongitude());
                LatLng myLoc = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                mGoogleMap.addMarker(new MarkerOptions().position(myLoc)
                        .title("Your Location")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location))
                        .draggable(true));

                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 15));
            }
        }
    }

    @Override
    public void applyResult(boolean success, String msg) {
        if (success) {
            UIUtils.showBaseToast("申请成功！");
        } else {
            UIUtils.showBaseToast("申请失败:" + msg);
        }
    }

    @Override
    public void showStylePop(List<StyleModel> modelList) {

    }

    private Menu mMenu;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_explore_opp, menu);
        mMenu = menu;
        mMenu.findItem(R.id.explore_menu_list).setVisible(true);
        mMenu.findItem(R.id.explore_menu_map).setVisible(false);
        mMenu.findItem(R.id.explore_menu_filter).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.explore_menu_filter:
                //过滤器
                filter();
                return true;
            case R.id.explore_menu_list:
                //列表显示
                showMap = false;
                mMenu.findItem(R.id.explore_menu_filter).setVisible(true);
                mMenu.findItem(R.id.explore_menu_map).setVisible(true);
                mMenu.findItem(R.id.explore_menu_list).setVisible(false);
                mRecyclerView.setVisibility(View.VISIBLE);
                mapLayout.setVisibility(View.GONE);
                return true;
            case R.id.explore_menu_map:
                //地图显示
                showMap = true;
                mMenu.findItem(R.id.explore_menu_list).setVisible(true);
                mMenu.findItem(R.id.explore_menu_filter).setVisible(false);
                mMenu.findItem(R.id.explore_menu_map).setVisible(false);
                mRecyclerView.setVisibility(View.GONE);
                mapLayout.setVisibility(View.VISIBLE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            HashMap<String, String> map = new HashMap<>();
            String dis = data.getStringExtra("distance");
            if (TextUtils.isEmpty(dis)) {
                map.put("distance", distance);
            } else {
                map.put("distance", dis);
            }

            String performStartDate = data.getStringExtra("performStartDate");
            if (!TextUtils.isEmpty(performStartDate)) {
                map.put("performStartDate", performStartDate);
            }

            if (mLastLocation != null) {
                map.put("longitude", String.valueOf(mLastLocation.getLongitude()));
                map.put("latitude", String.valueOf(mLastLocation.getLatitude()));
            }
//            showMap = false;
//            mMenu.findItem(R.id.explore_menu_filter).setVisible(true);
//            mMenu.findItem(R.id.explore_menu_map).setVisible(true);
//            mMenu.findItem(R.id.explore_menu_list).setVisible(false);
            mPresenter.requestOpportunityList(map);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) locationManager.removeUpdates(listener);
    }
}
