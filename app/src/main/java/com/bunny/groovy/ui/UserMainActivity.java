package com.bunny.groovy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bunny.groovy.R;
import com.bunny.groovy.adapter.MainTabAdapter;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.ui.fragment.apply.FilterFragment;
import com.bunny.groovy.ui.fragment.user.MyFavoriteListFragment;
import com.bunny.groovy.ui.fragment.user.MyHistoryListFragment;
import com.bunny.groovy.ui.fragment.user.UserMainFragment;
import com.bunny.groovy.ui.fragment.usercenter.SettingsFragment;
import com.bunny.groovy.ui.fragment.venue.VenueMeFragment;
import com.bunny.groovy.ui.fragment.venue.VenueOverviewFragment;
import com.bunny.groovy.ui.fragment.venue.VenueScheduleFragment;
import com.bunny.groovy.ui.fragment.wallet.WalletFragment;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.weidget.BottomBarItem;
import com.bunny.groovy.weidget.BottomBarLayout;
import com.bunny.groovy.weidget.NoScrollViewPager;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserMainActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_list)
    TextView mNavList;
    @Bind(R.id.ib_filter)
    ImageButton mFilterButton;
    @Bind(R.id.mydrawerlayout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    private boolean mIsMap = true;
    private BaseFragment mMapFragment;




    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.tv_list)
    public void switchFragment() {
        mIsMap = !mIsMap;
        mNavList.setText(mIsMap ? "LIST" : "MAP");
        mFilterButton.setVisibility(mIsMap ? View.GONE : View.VISIBLE);
        if(mMapFragment instanceof UserMainFragment){
            ((UserMainFragment) mMapFragment).switchListOrMap(mIsMap);
        }
    }

    @OnClick(R.id.ib_filter)
    public void ib_filter(){
        if(mMapFragment instanceof UserMainFragment){
            ((UserMainFragment) mMapFragment).mapFilter();
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_user_main_layout;
    }

    @Override
    public void initData() {
        intiFragments();
    }

    @Override
    public void initListener() {
        super.initListener();
    }

    private void intiFragments() {

        mMapFragment = new UserMainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mMapFragment).commit();
    }

    @Override
    public void initView() {
        super.initView();
        mToolbar.setNavigationIcon(R.drawable.icon_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(Gravity.START);
                }
            }
        });
        ViewGroup.LayoutParams params = mNavigationView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels * 3 / 4;
        mNavigationView.setLayoutParams(params);
        View headerView = mNavigationView.getHeaderView(0);
        CircleImageView mHeadImage = headerView.findViewById(R.id.nav_head);
        TextView mUserName = headerView.findViewById(R.id.nav_name);
        Glide.with(this).load(AppCacheData.getPerformerUserModel().getHeadImg()).into(mHeadImage);
        mUserName.setText(AppCacheData.getPerformerUserModel().getUserName());
        mHeadImage.setOnClickListener(this);
        headerView.findViewById(R.id.nav_wallet).setOnClickListener(this);
        headerView.findViewById(R.id.nav_history).setOnClickListener(this);
        headerView.findViewById(R.id.nav_favortie).setOnClickListener(this);
        headerView.findViewById(R.id.nav_switch).setOnClickListener(this);
        headerView.findViewById(R.id.nav_settings).setOnClickListener(this);
    }

    public static void start(Context outerContext) {
        Intent intent = new Intent(outerContext, UserMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        outerContext.startActivity(intent);
    }

    @Override
    public boolean enableSlideClose() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.nav_head:
                break;
            case R.id.nav_wallet:
                WalletFragment.launch(this);
                break;
            case R.id.nav_history:
                MyHistoryListFragment.launch(this);
                break;
            case R.id.nav_favortie:
                MyFavoriteListFragment.launch(this);
                break;
            case R.id.nav_switch:
                break;
            case R.id.nav_settings:
                SettingsFragment.launch(this);
                break;
        }
    }
}
