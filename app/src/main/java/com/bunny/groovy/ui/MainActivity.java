package com.bunny.groovy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bunny.groovy.R;
import com.bunny.groovy.adapter.MainTabAdapter;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.ui.fragment.MeFragment;
import com.bunny.groovy.ui.fragment.OverviewFragment;
import com.bunny.groovy.ui.fragment.ScheduleFragment;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.weidget.NoScrollViewPager;
import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    @Bind(R.id.vp_content)
    NoScrollViewPager noScrollViewPager;

    @Bind(R.id.bottom_bar)
    BottomBarLayout bottomBarLayout;

    @Bind(R.id.main_rl_titlebar)
    RelativeLayout actionBarLayout;

    @Bind(R.id.main_tv_title)
    TextView tvTitle;

    private List<BaseFragment> mFragments;
    private MainTabAdapter mTabAdapter;

    /**
     * @return 获取标题布局
     */
    public RelativeLayout getActionBarLayout() {
        return actionBarLayout;
    }

    /**
     * @return 标题
     */
    public TextView getTvTitle() {
        return tvTitle;
    }

    /**
     * 设置页面标题
     *
     * @param title
     */
    public void setPageTitle(String title) {
        if (Utils.isEnglish(title))
            tvTitle.setText(title.toUpperCase());
        else tvTitle.setText(title);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        intiFragments();
    }

    @Override
    public void initListener() {
        super.initListener();
        mTabAdapter = new MainTabAdapter(mFragments, getSupportFragmentManager());
        noScrollViewPager.setAdapter(mTabAdapter);
        noScrollViewPager.setOffscreenPageLimit(mFragments.size());
        bottomBarLayout.setViewPager(noScrollViewPager);
        //设置条目点击的监听
        bottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int position) {

            }
        });
    }

    private void intiFragments() {
        mFragments = new ArrayList<>(3);
        mFragments.add(new OverviewFragment());
        mFragments.add(new ScheduleFragment());
        mFragments.add(new MeFragment());

    }

    /**
     * 启动本页面
     *
     * @param activity
     */
    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public boolean enableSlideClose() {
        return false;
    }
}
