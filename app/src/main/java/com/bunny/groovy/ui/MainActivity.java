package com.bunny.groovy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.weidget.NoScrollViewPager;
import com.chaychan.library.BottomBarLayout;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    @Bind(R.id.vp_content)
    NoScrollViewPager noScrollViewPager;
    @Bind(R.id.bottom_bar)
    BottomBarLayout bottomBarLayout;

    private static PerformerUserModel mUserModel;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();

    }

    /**
     * 启动本页面
     *
     * @param activity
     * @param userModel
     */
    public static void launchWithData(Activity activity, PerformerUserModel userModel) {
        mUserModel = userModel;
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }
}
