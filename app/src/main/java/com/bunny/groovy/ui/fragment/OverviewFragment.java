package com.bunny.groovy.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.NextShowModel;
import com.bunny.groovy.presenter.OverviewPresenter;
import com.bunny.groovy.ui.MainActivity;
import com.bunny.groovy.ui.releaseshow.MapsFragment;
import com.bunny.groovy.ui.releaseshow.ReleaseShowFragment;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.view.IOverView;

import butterknife.Bind;
import butterknife.OnClick;

/****************************************
 * 功能说明: 表演者主页
 *
 * Author: Created by bayin on 2017/12/15.
 ****************************************/

public class OverviewFragment extends BaseFragment<OverviewPresenter> implements IOverView {
    @Bind(R.id.nextshow_layout)
    View nextShowLayout;
    @Bind(R.id.nextshow_iv_head)
    ImageView ivHead;
    @Bind(R.id.nextshow_tv_performerName)
    TextView tvName;
    @Bind(R.id.nextshow_tv_performerStar)
    TextView tvStar;
    @Bind(R.id.nextshow_tv_address)
    TextView tvAddress;
    @Bind(R.id.nextshow_tv_time)
    TextView tvTime;
    @OnClick(R.id.overview_tv_release_show)
    void releaseShow(){
        ReleaseShowFragment.launch(getActivity());
    }
    @OnClick(R.id.overview_tv_explore_show)
    void exploreShow(){
        MapsFragment.launch(getActivity());
    }

    @Override
    protected OverviewPresenter createPresenter() {
        return new OverviewPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_overview_layout;
    }

    @Override
    protected void loadData() {
        if (mActivity instanceof MainActivity) {
            ((MainActivity) mActivity).setPageTitle(AppCacheData.getPerformerUserModel().getStageName());
        }
        mPresenter.requestNextShow();
    }

    @Override
    public void initNextView(NextShowModel nextShowModel) {
        nextShowLayout.setVisibility(View.VISIBLE);
        Glide.with(this).load(nextShowModel.getPheadImg()).into(ivHead);
        tvName.setText(nextShowModel.getPerformerName());
        tvStar.setText(nextShowModel.getPvenueScore());
        tvAddress.setText("未知");
        tvTime.setText(nextShowModel.getPerformStartDate());
    }

    @Override
    public void showEmptyNextShow() {
        nextShowLayout.setVisibility(View.GONE);
    }
}
