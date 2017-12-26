package com.bunny.groovy.ui.fragment.notify;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.weidget.SlidingTabLayout;

import butterknife.Bind;

/**
 * 用户通知界面
 *
 * Created by Administrator on 2017/12/26.
 */

public class NotificationFragment extends BaseFragment {

    @Bind(R.id.notification_pagetabstrip)
    SlidingTabLayout slidingTabLayout;

    @Bind(R.id.notification_viewpager)
    ViewPager viewPager;

    public static void launch(Activity from){
        Bundle bundle = new Bundle();
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE,"NOTIFICATIONS");
        FragmentContainerActivity.launch(from,NotificationFragment.class,bundle);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_notification_lalyout;
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);

    }

    @Override
    protected void loadData() {

    }
}
