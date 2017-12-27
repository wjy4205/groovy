package com.bunny.groovy.ui.fragment;

import android.app.Activity;
import android.view.View;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.presenter.SchedulePresenter;
import com.bunny.groovy.ui.MainActivity;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.IOverView;
import com.bunny.groovy.view.IScheduleView;
import com.bunny.groovy.weidget.MoveLayout;

import butterknife.Bind;

/****************************************
 * 功能说明: 表演者时间计划
 *
 * Author: Created by bayin on 2017/12/15.
 ****************************************/

public class ScheduleFragment extends BaseFragment<SchedulePresenter> implements IScheduleView {

    @Bind(R.id.moveLayout)
    MoveLayout mMoveLayout;


    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        mMoveLayout.setLoaction(UIUtils.getScreenHeight() / 2);
    }

    @Override
    protected SchedulePresenter createPresenter() {
        return new SchedulePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_schedule_layout;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public Activity get() {
        return mActivity;
    }
}
