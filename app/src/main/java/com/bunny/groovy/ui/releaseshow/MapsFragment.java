package com.bunny.groovy.ui.releaseshow;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.EditText;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.listener.PermissionListener;
import com.bunny.groovy.presenter.MapPresenter;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.IMapView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;



import java.util.List;

import butterknife.Bind;

/**
 * 申请表演机会，地图
 * <p>
 * Created by Administrator on 2017/12/17.
 */

public class MapsFragment extends BaseFragment<MapPresenter> implements OnMapReadyCallback, IMapView {
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    @Bind(R.id.et_search)
    EditText etSearch;

    public static void launch(Activity from) {
        Bundle bundle = new Bundle();
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "EXPLORE SHOW OPPORTUNITY");
        FragmentContainerActivity.launch(from, MapsFragment.class, bundle);
    }

    @Override
    protected MapPresenter createPresenter() {
        return new MapPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_map_layout;
    }

    @Override
    public void afterAttach() {
        SupportMapFragment supportMapFragment = new SupportMapFragment();
        getChildFragmentManager().beginTransaction().add(R.id.map_container, supportMapFragment, "map_fragment").commit();
        supportMapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addApi(LocationServices.API)
                .build();
        LocationServices.
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mActivity.requestRuntimePermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, new PermissionListener() {
            @Override
            public void onGranted() {
                if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    // Add a marker in Sydney and move the camera
                    LatLng sydney = new LatLng(-34, 151);
                    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                }
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {
                UIUtils.showBaseToast("请开启定位服务");
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1)
    }
}
